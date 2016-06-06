package com.baidu.maf.service;


import com.apkfuns.logutils.LogUtils;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.maf.channel.DataChannel;
import com.baidu.maf.message.DownPacketMessage;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.RegChannelMessage;
import com.baidu.maf.message.UpPacketMessage;
import com.baidu.maf.network.IChannelChangeListener;
import com.baidu.maf.network.NetChannel;
import com.baidu.maf.network.NetChannelStatus;
import com.baidu.maf.util.*;

import java.io.IOException;

/**
 * 网络层，用于发送和接收消息。封装了网络通道、重试、超时，网络超时会抛出异常。
 * 
 * @author zhaowei10
 * 
 */
public class NetworkLayer implements DataChannel{

    public static final String TAG = "NetworkLayer";

    /**
     * Instance one of the channels.
     */
    private NetChannel networkChannel;
    private NetworkUtil.NetworkType networkType = NetworkUtil.NetworkType.broken;
    private DataChannel receiveChannel;

    /**
     * @throws IOException
     * 
     */
    public NetworkLayer(MafPreference preference) throws IOException {

        if (preference == null)
            throw new RuntimeException("preference can not be null");

        LogUtil.printMainProcess("start to initialize network channel.");

        networkChannel = new NetChannel(preference.getDeviceToken(), preference.getChannelkey(), "Android_1_0_0_0");

        networkChannel.setNextChannel(this);
    }

    public void initialized()
    {
        if (networkChannel != null) {

            networkChannel.setNetworkChannelListener(new IChannelChangeListener() {
                @Override
                public void onChanged(NetChannelStatus networkChannelStatus) {
                    try {
                        ServiceApplication.getInstance().getServiceChannel().sendNetworkChange(getNetworkChannelStatus());
                    } catch (RuntimeException e) {
                        LogUtil.e(TAG, e);
                    }
                }

                @Override
                public void onAvaliable(String channelKey) {
                    RegChannelMessage regChannelMessage = new RegChannelMessage();
                    regChannelMessage.setChannelData(channelKey);
                    try {
                        receiveChannel.receive(regChannelMessage);
                    }
                    catch (Exception e){
                        LogUtils.e("NetworkLayer", "error" + e.getMessage());
                    }
                }
            });

            networkChannel.connect();
        }
    }
    
    public NetChannelStatus getNetworkChannelStatus() {
    	if(networkChannel == null)
    	{
    		LogUtil.printIm(TAG, "networkchannel status can not be null");
    		return NetChannelStatus.Disconnected;
    	}else
    	{
    		return networkChannel.getNetChannelStatus();
    	}
    }

    public void reConnect() {
        LogUtil.i(TAG, "Channel Reconnect");
        if(networkChannel != null)
        {
            if(NetworkUtil.isNetworkConnected(ServiceApplication.getInstance().getContext()))
            {
                //networkChannel.networkChanged(0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    LogUtil.e(TAG, e);
                }
                networkChannel.networkChanged(1);
            }else
            {
                networkChannel.networkChanged(0);
            }

        }else
        {
            LogUtil.printIm(TAG, "Channel reconnect error");
        }
    }

    @Override
    public void send(Message upPacket) throws Exception {
        if (upPacket != null) {
            UpPacketMessage upPacketMessage = (UpPacketMessage)upPacket;
            LogUtil.printProtocol("发送-------->\r\n" + ProtobufLogUtil.print((UpPacket)upPacketMessage.getMicro()) + "\r\nlen="
                    + upPacket.toByteArray().length);
            if (networkChannel != null) {
                networkChannel.send(upPacket);
            }
        }
    }

    /**
     * 接收消息。
     * 
     * @param downPacket
     */
    
    //static int send = 0;
    @Override
    public void receive(Message downPacket) {

        if (downPacket == null) {
            return;
        }
        LogUtil.printProtocol("收包<--------\r\n" + ProtobufLogUtil.print((DownPacket) ((DownPacketMessage) downPacket).getMicro()));

        try {
            receiveChannel.receive(downPacket);
        }
        catch (Exception e){
            LogUtils.e("NetworkLayer", "receive message failed");
        }
    }

    @Override
    public void setNextChannel(DataChannel callback) {
        receiveChannel = callback;
    }

    public void destroy() {
        if (networkChannel != null) {
            networkChannel.close();
        }
    }

    public void saveProtocolFile(DownPacket downPacket) {
    	
    	if(downPacket == null)
    		return ;
        FileUtil.saveBytesToFileInSdkFolder("downPacket_" + "_seq(" + downPacket.getSeq() + ")_channelCode("
                        + downPacket.getChannelCode() + ")_bizCode(" + downPacket.getBizPackage().getBizCode() + ")",
                downPacket.toByteArray());

        FileUtil.saveBytesToFileInSdkFolder("downPacket_" + "_seq(" + downPacket.getSeq() + ")_channelCode("
                        + downPacket.getChannelCode() + ")_bizCode(" + downPacket.getBizPackage().getBizCode() + ")_Context",
                ProtobufLogUtil.print(downPacket).getBytes());
    }

    public void networkChanged() {
    	LogUtil.i(TAG, "networkchanged is called");
        try {
            NetworkUtil.NetworkType currentNetworkType = NetworkUtil.getNetworkType(ServiceApplication.getInstance().getContext());

            switch (currentNetworkType) {
                case broken:
                    // 如果有网络变为无网络，则调用底层接口
                    if (networkType != currentNetworkType) {
                        networkChannel.networkChanged(0);
                    }
                    break;

                default:
                    // 如果之前无网络,则通知网络可以用
                    if (networkType == NetworkUtil.NetworkType.broken) {
                        networkChannel.networkChanged(1);
                    } else if (networkType != currentNetworkType) {
                        // 如果有网络，但是网络类型改变，则需要通知重连
                        networkChannel.networkChanged(0);
                        Thread.sleep(10);
                        networkChannel.networkChanged(1);
                    }
                    break;
            }
            networkType = currentNetworkType;
            if( currentNetworkType != NetworkUtil.NetworkType.broken ) {
            	networkChannel.networkChanged(1);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "networkChanged error.", e);
        }
    }

    public void dumpChannel() {
    	if(networkChannel != null)
    		networkChannel.dump();
    }

	public void sendReconnect() {
		networkChanged();
	}
}
