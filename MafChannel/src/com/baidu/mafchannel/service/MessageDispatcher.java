package com.baidu.mafchannel.service;

import android.os.RemoteException;
import com.apkfuns.logutils.LogUtils;
import com.baidu.im.frame.pb.EnumPacketType;
import com.baidu.mafchannel.channel.DataChannel;
import com.baidu.mafchannel.channel.DispChannel;
import com.baidu.mafchannel.message.DownPacketMessage;
import com.baidu.mafchannel.message.Message;
import com.baidu.mafchannel.message.NotifyMessage;
import com.baidu.mafchannel.message.UpPacketMessage;
import com.baidu.mafchannel.util.LogUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by hanxin on 2016/5/20.
 */
public class MessageDispatcher extends DispChannel{
    private Map<Integer, DataChannel> channelMap = new HashMap<Integer, DataChannel>();
    private Map<Integer, String> seq2appKeyMap = new HashMap<Integer, String>();
    private Map<Integer, String> appId2AppKeyMap = new HashMap<Integer, String>();
    private Map<String,  AppChannel> appKeyChannelMap = new HashMap<String, AppChannel>();
    private Set<Integer> unknownAppIdSet = new HashSet<Integer>();
    private DataChannel sendChannel = null;

    public MessageDispatcher(DataChannel sendChannel) {
        this.sendChannel = sendChannel;
    }

    public void addAppKeyChannel(String appKey, AppChannel channel){
        appKeyChannelMap.put(appKey, channel);
    }
    public void addAppChannel(Message upPacket, AppChannel channel) throws Exception{
        unknownAppIdSet.add(upPacket.getMessageID());
    }

    public void send(Message upPacket, DataChannel channel) throws Exception{
        channelMap.put(upPacket.getMessageID(), channel);
        sendChannel.send(upPacket);
    }

    @Override
    public void send(Message upPacket) throws Exception {
        switch (upPacket.getMessageType()){
            case NORMAL:
            {
                UpPacketMessage upPacketMessage = (UpPacketMessage)upPacket;
                //app消息
                if (null != upPacketMessage.getAppKey()) {
                    AppChannel channel = appKeyChannelMap.get(upPacketMessage.getAppKey());
                    if (null != channel)
                        channelMap.put(upPacket.getMessageID(), channel);
                    else {
                        LogUtils.e("MessageDispatcher", "not found app channel");
                    }
                    int appId = upPacketMessage.getAppId();
                    if (0 != appId) {
                        appId2AppKeyMap.put(appId, upPacketMessage.getAppKey());
                    }
                    else {
                        seq2appKeyMap.put(upPacketMessage.getMessageID(), upPacketMessage.getAppKey());
                    }
                }
            }
            break;
        }

        if (null != sendChannel){
            sendChannel.send(upPacket);
        }
    }

    @Override
    public void receive(Message downPacket) throws Exception{
        try {
            switch (downPacket.getMessageType()){
                case NORMAL:
                {
                    DownPacketMessage downPacketMessage = (DownPacketMessage)downPacket;
                    //LogUtil.printProtocol("收包<--------\r\n" + ProtobufLogUtil.print(downPacketMessage.getMicro()));
                    String appKey = appId2AppKeyMap.get(downPacketMessage.getAppId());
                    DataChannel channel = channelMap.get(downPacket.getMessageID());
                    if (null == channel){
                        if (EnumPacketType.NOTIFYCATION == downPacketMessage.getPacketType()){
                            channel = appKeyChannelMap.get(appKey);
                            if (null != channel){
                                try {
                                    channel.receive(downPacket);
                                }
                                catch (RemoteException e){
                                    LogUtils.e("MessageDispatcher", "receiveNotify error, appId = " + downPacketMessage.getAppId() + e.getMessage());
                                    // LogUtil.printMainProcess(TAG, "sendMessage: first 7 AppId = " + appId);
                                    AppChannel appChannel = (AppChannel)channel;
                                    appChannel.setbEnable(false);
                                    appKeyChannelMap.remove(appKey);
                                }
                            }
                            else {
                                NotifyMessage notifyMessage = (NotifyMessage)downPacketMessage.parseMessage(new NotifyMessage());
                                ServiceApplication.getInstance().pushConfirm(notifyMessage);
                            }
                        }
                    }
                    else {
                        try {
                            String key = seq2appKeyMap.get(downPacket.getMessageID());
                            if (null != key){
                                appId2AppKeyMap.put(downPacketMessage.getAppId(), key);
                                seq2appKeyMap.remove(downPacketMessage.getMessageID());
                            }
                            channel.receive(downPacket);
                        }
                        catch (RemoteException e){
                            LogUtils.e("MessageDispatcher", "receiveMessage error,seqId = " + downPacket.getMessageID() + e.getMessage());
                            // LogUtil.printMainProcess(TAG, "sendMessage: first 7 AppId = " + appId);
                            AppChannel appChannel = (AppChannel)channel;
                            appChannel.setbEnable(false);

                            appKeyChannelMap.remove(appChannel.getAppKey());

                            ServiceApplication.getInstance().setAppOffline(downPacketMessage.getAppId());
                        }
                        finally {
                            channelMap.remove(downPacket.getMessageID());
                        }
                    }
                }
                break;
                case CHECKOFFLINE:
                case REGCHANNEL:
                {
                    for (Map.Entry<String, AppChannel> entry : appKeyChannelMap.entrySet()){
                        AppChannel appChannel = entry.getValue();
                        try {
                            appChannel.receive(downPacket);
                        }
                        catch (RemoteException e){
                            LogUtils.e("MessageDispatcher", "receive error,seqId = " + downPacket.getMessageID() + e.getMessage());
                            // LogUtil.printMainProcess(TAG, "sendMessage: first 7 AppId = " + appId);

                            appChannel.setbEnable(false);

                            appKeyChannelMap.remove(appChannel.getAppId());
                        }
                    }
                }
                break;
            }
        }
        catch (Exception e){
            LogUtil.e("MessageDispatcher", "error:" + e.getMessage());
        }
    }

    @Override
    public synchronized void setNextChannel(DataChannel callback) {
        sendChannel = callback;
    }

    public synchronized void addSeqCallback(int seq, DataChannel callback){
        channelMap.put(seq, callback);
    }

    public void delCallback(DataChannel channel){

    }
}
