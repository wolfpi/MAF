package com.baidu.maf.service;

import android.os.*;
import com.baidu.maf.channel.DataChannel;
import com.baidu.maf.message.CheckOffLineMessage;
import com.baidu.maf.message.MessageTypeEnum;
import com.baidu.maf.message.NetworkChangeMessage;
import com.baidu.maf.message.UpPacketMessage;
import com.baidu.maf.network.NetChannelStatus;
import com.baidu.maf.util.LogUtil;
import com.baidu.maf.util.StringUtil;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

/**
 * Created by hanxin on 2016/5/17.
 */
public class ServiceChannel implements DataChannel {
    private MessageDispatcher dispatcher = null;
    private DataChannel netChannel = null;
    public static final String TAG = "ServiceChannel";

    public ServiceChannel(DataChannel netChannel) {
        dispatcher = new MessageDispatcher(this);
        this.netChannel = netChannel;
    }

    /**
     * 由service主线程获得的handler，用于处理aidl消息。
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message message) {
            try {
                receive(message);
            }
            catch (Exception e){
                LogUtil.e("ServiceChannel", "receive message exception" + e.getMessage());
            }
        }
    };

    /**
     * Server端持有的messenger。
     */
    private Messenger serverMessenger = new Messenger(handler);

    private static boolean isBind = false;


    public IBinder getBinder() {
        isBind = true;
        LogUtil.printMainProcess("dynamicLoader1 intent=" + serverMessenger.getBinder());
        return serverMessenger.getBinder();
    }

    public void onUnbind() {

        CheckOffLineMessage checkOffLineMessage = new CheckOffLineMessage();
        try {
            dispatcher.receive(checkOffLineMessage);
        }
        catch (Exception e){
            LogUtil.e("ServiceChannel", "send check offline message failed");
        }
        isBind = false;
    }

    public boolean isBind() {
        return isBind;
    }

    /**
     * Send a network_change msg to all InApp
     */
    public void sendNetworkChange(NetChannelStatus status) {

    	/*
    	if(OutAppApplication.getInstance() != null && OutAppApplication.getInstance().getNetworkLayer() != null) {
    		OutAppApplication.getInstance().getNetworkLayer().networkChanged();
    	}*/
        NetworkChangeMessage networkChangeMessage = new NetworkChangeMessage();
        networkChangeMessage.setStatus(status);

        try {
            receive(networkChangeMessage);
        }
        catch (Exception e){
            LogUtil.e(TAG,"error:" + e.getMessage());
        }
    }


    @Override
    public void send(com.baidu.maf.message.Message upPacket) throws Exception{
        netChannel.send(upPacket);
    }

    @Override
    public void receive(com.baidu.maf.message.Message downPacket) throws Exception{
        if (null == downPacket)
            return;
        dispatcher.receive(downPacket);
    }

    @Override
    public void setNextChannel(DataChannel callback) {
        netChannel = callback;
    }

    private  void receive(android.os.Message message) throws Exception{

        if (message == null) {
            return;
        }

        if (ServiceApplication.getInstance().getContext() == null) {
            return;
        }

        Messenger clientMessenger = message.replyTo;

        Bundle bundle = message.getData();
        // Receive network change msg
        if (bundle.containsKey(MessageTypeEnum.REGCHANNEL.getType())) {
            NetChannelStatus networkChannelStatus = ServiceApplication.getInstance().getNetStatus();
            if(networkChannelStatus == NetChannelStatus.Connected)
            {
                String channelKey =  ServiceApplication.getInstance().getChannelKey();
                if(channelKey == null)
                {
                    LogUtil.i(TAG, "impossible in channelkey");
                }else
                {
                    LogUtil.i(TAG, "channelkey send");
                    send(MessageTypeEnum.REGCHANNEL.getType(),
                            channelKey.getBytes(),
                            clientMessenger);
                }
            }
            try {
                send(0, networkChannelStatus.name().getBytes(), MessageTypeEnum.NETWORK_CHANGE.getType(), clientMessenger);
                String apiKey = bundle.getString(MessageTypeEnum.APPKEY.getType());
                AppChannel channel = new AppChannel(apiKey);
                channel.setClientMsger(clientMessenger);
                channel.setServerMsger(serverMessenger);
                channel.setNextChannel(this);
                dispatcher.addAppKeyChannel(apiKey, channel);
            }
            catch (RemoteException e){
                LogUtil.e(TAG, "send regchannel");
            }

            return;
        }

        if (bundle.containsKey(MessageTypeEnum.RECONNECT.getType())) {
            LogUtil.printMainProcess("Reconnect is called");
            ServiceApplication.getInstance().reconnect();
            return;
        }

        // Receive normal msg
        if (bundle.containsKey(MessageTypeEnum.NORMAL.getType())) {
            byte[] data = bundle.getByteArray(MessageTypeEnum.NORMAL.getType());
            String apiKey = bundle.getString(MessageTypeEnum.APPKEY.getType());

            try {
                UpPacketMessage upPacketMessage = UpPacketMessage.parse(data);
                upPacketMessage.setAppKey(apiKey);
                dispatcher.send(upPacketMessage);

            } catch (InvalidProtocolBufferMicroException e) {
                LogUtil.printError(e);
            }
        }
    }

    public void initialize() {

    }

    public void destroy() {
    }

    public void send(int appId, byte[] data, String messgeType, Messenger clientMessenger) throws RemoteException{

        if (StringUtil.isStringInValid(messgeType)) {
            return;
        }

        // LogUtil.printMainProcess(TAG, "sendMessage: first 1 AppId = " + appId);

        android.os.Message message = Message.obtain();

        Bundle bundle = new Bundle();
        if (data != null) {
            // Send normal msg
            bundle.putByteArray(messgeType, data);
        }
        message.setData(bundle);
        // LogUtil.printMainProcess(TAG, "sendMessage: first 2AppId = " + appId);
        Messenger messenger = clientMessenger;
        if (messenger != null) {
            messenger.send(message);
            // LogUtil.printMainProcess(TAG, "sendMessage: first  5 AppId = " + appId);
            LogUtil.printMainProcess(TAG, "sendMessage: messenger = " + messenger.hashCode());
        } else {
            LogUtil.printMainProcess(TAG, "sendMessage: can not find client messenger. AppId = " + appId);
        }

    }

    public static void  send(String type,int data, Messenger msger) {
        android.os.Message msg = android.os.Message.obtain();

        Bundle bundle2 = new Bundle();
        bundle2.putInt(type, data);
        msg.setData(bundle2);
        try {
            msger.send(msg);
        } catch (RemoteException e) {
            LogUtil.e(TAG, e);
        }
    }

    public static void  send(String type, byte[] data, Messenger msger) {
        android.os.Message msg = android.os.Message.obtain();

        Bundle bundle2 = new Bundle();
        bundle2.putByteArray(type, data);
        msg.setData(bundle2);
        try {
            msger.send(msg);
        } catch (RemoteException e) {
            LogUtil.e(TAG, e);
        }
    }
}
