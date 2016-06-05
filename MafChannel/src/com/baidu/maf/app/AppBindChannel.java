package com.baidu.maf.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;

import com.apkfuns.logutils.LogUtils;
import com.baidu.maf.channel.MessageChannel;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.message.*;
import com.baidu.maf.network.NetChannelStatus;
import com.baidu.maf.service.MafService;
import com.baidu.maf.util.*;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hanxin on 2016/5/21.
 */
public class AppBindChannel extends MessageChannel {

    public static final String TAG = "AppBindChannel";

    /** The primary interface we will be calling on the service. */
    private Messenger serverMessenger = null;
    private Messenger clientMessenger = null;
    // private static MessageListener mMsgListener = null;

    private boolean mIsBound;
    private static HandlerThread messengerThread;
    private Handler handler;
    final Handler.Callback handlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(android.os.Message message) {
            LogUtil.printMainProcess("app receive from service, message=" + message);
            receive(message);
            return true;
        }
    };
    private MafContext mafContext;
    private Context context;

    public void initialize(MafContext context) {
        synchronized(handlerCallback) {
            if (messengerThread == null) {
                messengerThread = new HandlerThread("MafServiceBinding");
                messengerThread.start();
                handler = new Handler(messengerThread.getLooper(), handlerCallback);
            }
        }
        clientMessenger = new Messenger(handler);
        this.mafContext = context;
        this.context = mafContext.getContext();
        bind();
    }

    /**
     * Class for interacting with the interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            serverMessenger = new Messenger(service);
            LogUtil.printMainProcess("onServiceConnected. serverMessenger = " + serverMessenger);
            ToastUtil.toast("Connected remote service.");

            LogUtil.e(TAG, "check network is called in connection");
            // 连接上需要检测网络状态
            try {
                regChannel();
            } catch (RemoteException e) {
                LogUtil.printMainProcess("onServiceConnected. Can not send checkNetworkChannelStatus message");
                e.printStackTrace();
            }

        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            serverMessenger = null;
            ToastUtil.toast("Remote service disconnected");

            IDGenerator.reset();

            //通知上层连接变化
            try {
                mafContext.networkChange(NetChannelStatus.Disconnected);
            } catch (RuntimeException e) {
                LogUtil.e(TAG, e);
            }

            GlobalTimerTasks.getInstance().removeAllTask();
            bind();
        }
    };

    public void bind() {

        // Establish a couple connections with the service, binding by interface
        // names. This allows other applications to be installed that replace
        // the remote service by implementing the same interface.

        Intent startintent = new Intent(context, MafService.class);
        startintent.setAction("com.baidu.maf.service");

        if(ServiceControlUtil.showInSeperateProcess(context)) {
            try {
                context.startService(startintent);
            } catch (Exception e) {
                LogUtil.printImE(TAG, "fail to startService", e);
            }
        }

        //Intent intent = new Intent(InAppApplication.getInstance().getContext(), OutAppService.class);


        boolean result =
                context.bindService(startintent, mConnection, Context.BIND_AUTO_CREATE);

        LogUtil.printMainProcess("Service bind. reslut = " + result);
        LogUtil.printMainProcess("bind. serverMessenger = " + serverMessenger);
        mIsBound = result;
    }

    public void unbind() {
        if (mIsBound) {

            // Detach our existing connection.
            context.unbindService(mConnection);
            mIsBound = false;
            IDGenerator.reset();
        }
    }

    public void destroy() {
        unbind();
    }

    public void sendReconnect() {
        LogUtil.printMainProcess("reconnect network");
        ReconnectMessage message = new ReconnectMessage();
        try {
            send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            LogUtils.e("AppBindChannel", "Reconnect Failed" + e.getMessage());
        }
    }

    /**
     * 发送消息给OutAppService
     */
    public void regChannel() throws RemoteException {

        LogUtil.printMainProcess("Check network status is send");
        RegChannelMessage reqMessage = new RegChannelMessage();
        try{
            send(reqMessage);
        }
        catch (RemoteException e){
            throw e;
        }
        catch (Exception e){
            LogUtils.e("AppBindChannel", "Regchannel Failed" + e.getMessage());
        }
    }

    private void send(android.os.Message message) throws RemoteException {
        LogUtil.printMainProcess("send. serverMessenger = " + serverMessenger);
        if (mIsBound && null != serverMessenger) {
            try {
                serverMessenger.send(message);
            } catch (Exception e) {
                bind();
                e.printStackTrace();
            }
        } else {
            LogUtil.e(TAG, "send message in unbind service, retry it late");
            if (!mIsBound) {
                LogUtil.printMainProcess("force bind 1 time");
                bind();
            }
        }
    }

    private void receive(android.os.Message message) {
        // Get bundle
        Bundle bundle = message.getData();
        if (null != bundle) {
            // Receive normal message
            com.baidu.maf.message.Message msg = null;
            if (bundle.containsKey(MessageTypeEnum.NORMAL.getType())){
                byte[] normalData = bundle.getByteArray(MessageTypeEnum.NORMAL.getType());
                if (null != normalData && normalData.length != 0) {
                    msg = DownPacketMessage.parse(normalData);
                }
            }

            if (bundle.containsKey(MessageTypeEnum.NETWORK_CHANGE.getType())){
                byte[] networkData = bundle.getByteArray(MessageTypeEnum.NETWORK_CHANGE.getType());
                if (null != networkData && networkData.length != 0) {
                    msg = NetworkChangeMessage.parse(networkData);
                }

            }

            if (bundle.containsKey(MessageTypeEnum.REGCHANNEL.getType())){
                byte[] networkData = bundle.getByteArray(MessageTypeEnum.REGCHANNEL.getType());
                if (null != networkData && networkData.length >10) {
                    msg = RegChannelMessage.parse(networkData);
                }
            }

            try{
                receive(msg);
            }
            catch (Exception e) {
                LogUtil.e(TAG, "error", e);
            }

        } else {
            LogUtil.e(TAG, "Receive a message with nothing.");
        }
    }

    @Override
    public void send(final com.baidu.maf.message.Message upPacket) throws Exception {
        if (upPacket == null)
            return;

        android.os.Message message = android.os.Message.obtain(handler);
        message.replyTo = clientMessenger;
        Bundle data = new Bundle();

        data.putByteArray(upPacket.getMessageType().getType(), upPacket.toByteArray());
        data.putString(MessageTypeEnum.APPKEY.getType(), mafContext.getAppKey());
        // data.putInt(APPID, mPref.getInt(PreferenceKey.appId));

        message.setData(data);
        send(message);

        GlobalTimerTasks.getInstance().addTask(upPacket.getMessageID(), new TimerTask() {
            @Override
            public void run() {
                ErrorMessage timeoutMessage = new ErrorMessage(upPacket.getMessageID(), ProcessorCode.SEND_TIME_OUT);
                try {
                    receive(timeoutMessage);
                }
                catch (Exception e){
                    LogUtils.e(TAG, e.getMessage());
                }
            }
        }, 1000);
    }

    @Override
    public void receive(final com.baidu.maf.message.Message downPacket) throws Exception {
        switch (downPacket.getMessageType()){
            case NORMAL:
            {
                LogUtil.printMainProcess(TAG, "Receive NORMAL msg: " + downPacket);

                GlobalTimerTasks.getInstance().removeTask(downPacket.getMessageID());

                getNextChannel().receive(downPacket);
//                threadPool.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            getNextChannel().receive(downPacket);
//                        }
//                        catch (Exception e){
//                            LogUtils.e("MessageRouter","receive message error" + e.getMessage());
//                        }
//                    }
//                });
            }
            break;
            case NETWORK_CHANGE:
            {
                // Receive network change message
                NetworkChangeMessage networkChangeMessage = (NetworkChangeMessage)downPacket;
                LogUtil.printMainProcess(TAG, "Receive NETWORK_CHANGE msg: " + networkChangeMessage.getStatus());
                mafContext.networkChange(networkChangeMessage.getStatus());
            }
            break;
            case REGCHANNEL:
            {
                RegChannelMessage regChannelMessage = (RegChannelMessage)downPacket;
                try {
                    String olderChannelkey = mafContext.getChannelKey();
                    if(!regChannelMessage.getChannelData().equals(olderChannelkey))
                    {
                        mafContext.setChannelKey(regChannelMessage.getChannelData());
                        SendBox sendBox = mafContext.getSendBox();
                        sendBox.resend();
                    }
                    LogUtil.printMainProcess(TAG, "Receive channelKey msg: " + regChannelMessage.getChannelData());

                } catch (RuntimeException e) {
                    LogUtil.printError(e);
                }
            }
            break;
        }
    }

}
