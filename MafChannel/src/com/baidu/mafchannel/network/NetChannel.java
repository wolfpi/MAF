package com.baidu.mafchannel.network;

import android.text.TextUtils;
import com.apkfuns.logutils.LogUtils;
import com.baidu.mafchannel.channel.DataChannel;
import com.baidu.mafchannel.com.Buffer;
import com.baidu.mafchannel.com.MafCallback;
import com.baidu.mafchannel.message.DownPacketMessage;
import com.baidu.mafchannel.message.Message;
import com.baidu.mafchannel.network.jni.IEvtCallback;
import com.baidu.mafchannel.network.jni.LoginResult_T;
import com.baidu.mafchannel.network.jni.LoginState_T;
import com.baidu.mafchannel.network.jni.NetworkChange_T;
import com.baidu.mafchannel.service.ServiceApplication;
import com.baidu.mafchannel.util.LogUtil;
import com.baidu.mafchannel.util.PreferenceUtil;

import java.util.Arrays;

/**
 * Created by hanxin on 2016/5/17.
 */
public class NetChannel implements DataChannel, INetworkChangeListener {

    private NetChannelStatus networkStatus = NetChannelStatus.Closed;
    private DataChannel callback = null;
    private NetCoreManager hiCoreManager;
    private NetCoreNotifyCallback hiCoreNotifyCallback = new NetCoreNotifyCallback();
    private PreferenceUtil mPreference = null;
    private INetworkChangeListener networkChannelListener;
    private MafCallback saveChannelKeyCallback;


    public NetChannel(String deviceToken, String channelKey, String sdkVer) {
        LogUtils.i("NetChannel", "start to initialize hichannel...");
        hiCoreManager = new NetCoreManager(deviceToken, channelKey, sdkVer);
    }

    @Override
    public void send(Message upPacket) {
        if (getNetChannelStatus() == NetChannelStatus.Closed) {
            LogUtils.e("NetChannel", "send error for the channel had been closed.");
        }
        Buffer buffer = new Buffer();
        upPacket.serializeTo(buffer);
        hiCoreManager.send(buffer.toArray(), upPacket.getMessageID());
    }

    @Override
    public void receive(Message downPacket) throws Exception{
        if (downPacket == null) {
            LogUtils.d("received a null downPacket.", null);
            return;
        }
        callback.receive(downPacket);
    }

    @Override
    public void setNextChannel(DataChannel callback) {
        this.callback = callback;
    }

    public void setSaveChannelKeyCallback(MafCallback saveChannelKeyCallback) {
        this.saveChannelKeyCallback = saveChannelKeyCallback;
    }

    public NetChannelStatus getNetChannelStatus() {
        return networkStatus;
    }

    public void setNetworkChannelListener(INetworkChangeListener networkChannelListener) {
        this.networkChannelListener = networkChannelListener;
    }

    public synchronized void connect(){
        if (getNetChannelStatus() == NetChannelStatus.Closed) {
            LogUtils.d("NetChannel", "event: init channel.");
            hiCoreManager.initHicore(ServiceApplication.getInstance().getContext());
            hiCoreManager.setListener(hiCoreNotifyCallback);
            int result = hiCoreManager.connet();
            LogUtils.d("success to connect hichannel." + result);
            onChanged(NetChannelStatus.Connecting);
        }
    }

    @Override
    public void onChanged(NetChannelStatus networkChannelStatus) {
        networkStatus = networkChannelStatus;
        if (networkChannelListener != null) {
            networkChannelListener.onChanged(networkChannelStatus);
        }
    }

    public void networkChanged(int value) {
        if (networkStatus == NetChannelStatus.Closed) {
            LogUtil.printMainProcess("NetChannel", "network change error for the channel had been closed.");
            hiCoreManager.networkChanged(NetworkChange_T.NW_DISCONNECTED);
            return;
        }
        LogUtil.printMainProcess("network changed. current status=" + value);

        if (value == 0) {
            hiCoreManager.networkChanged(NetworkChange_T.NW_DISCONNECTED);
        } else if (value == 1) {
            hiCoreManager.networkChanged(NetworkChange_T.NW_CONNECTED);
        }
    }

    public void dump() {
        hiCoreManager.dumpSelf();
    }

    public void close() {
        if (getNetChannelStatus() != NetChannelStatus.Closed) {
            LogUtil.printMainProcess("NetChannel", "event: deinit channel.");
            hiCoreManager.deinitHicore();
            networkStatus = NetChannelStatus.Closed;
        }
    }

    public class NetCoreNotifyCallback extends IEvtCallback {

        private static final String TAG = "HiCoreNotifyCallback";

        protected void finalize() {
            LogUtils.i(TAG, "HiCoreNotifyCallback::finalize()");
        }

        @Override
        public void notify(byte[] data, int len, int seq) {

            if (getNetChannelStatus() == NetChannelStatus.Closed) {
                LogUtils.i(TAG, "notify error for the channel had been closed.");
            }
            try {

                if (data == null) {
                    LogUtils.i("HiChannel", "receive a null data");
                    return;
                }

                if (data.length != len) {
                    LogUtils.i("HiChannel", "data.length != len.  " + data.length + ":" + len);
                    return;
                }

                DownPacketMessage downPacketMessage = new DownPacketMessage();
                if (downPacketMessage.parseFrom(data)){
                    LogUtils.e("NetChannel",
                            "receive a unkown packe, len = " + data.length + "  data =" + Arrays.toString(data));
                }
                // ServiceApplication.getInstance().getNetworkLayer().onReceive(downPacket);
                receive(downPacketMessage);

                // 将协议日志写文件，便于在test工程mock。
                // NetworkLayer.saveProtocolFile(downPacket);
            } catch (Throwable t) {

                LogUtils.i("hichannel error in message send to in APP", t.getMessage());
            }
        }

        public void notify(LoginState_T state, long err, LoginResult_T res) {

            if (getNetChannelStatus() == NetChannelStatus.Closed) {
                LogUtils.i(TAG, "notify loginState error for the channel had been closed.");
            }
            try {
                String channelKey = "";
                if (state == LoginState_T.LS_LOGGEDIN) {
                    channelKey = res.getChannelkey();
                    if (!TextUtils.isEmpty(channelKey)) {
                        LogUtils.i("save channelkey. channelkey=" + channelKey);
                        ServiceApplication.getInstance().setChannelKey(channelKey);
                        //mPreference.saveChanneKey(channelKey);
                        if(saveChannelKeyCallback != null)
                        {
                            saveChannelKeyCallback.run(channelKey);
                            //LogUtils.i("get channelKeyis:"+ mPreference.getChannelkey());
                        }
                    }
                    else
                    {
                        channelKey = mPreference.getChannelkey();
                        if (!TextUtils.isEmpty(channelKey))
                        {
                            if(saveChannelKeyCallback != null)
                            {
                                ServiceApplication.getInstance().setChannelKey(channelKey);
                                saveChannelKeyCallback.run(channelKey);
                                LogUtils.i("get channelkey confirm:" + channelKey);
                            }
                        }
                    }
                    if (TextUtils.isEmpty(channelKey)) {
                        LogUtils.i("hichannel error, did not return channelkey from hichannel.");
                    } else {
                        LogUtils.i("hichannel is ready now.");
                        onChanged(NetChannelStatus.Connected);
                        //ServiceApplication.getInstance().getTransactionFlow().outAppHeartbeat();
                    }
                } else if (state == LoginState_T.LS_UNLOGIN) {
                    // 掉线
                    LogUtils.i("hichannel is offline now.");
                    onChanged(NetChannelStatus.Disconnected);
                } else if (state == LoginState_T.LS_LOGGING || state == LoginState_T.LS_RETRYING) {
                    // 连接或者断网重连中
                    LogUtils.i("hichannel is connecting...");
                    onChanged(NetChannelStatus.Disconnected);
                } else if (state == LoginState_T.LS_RETRYCOUNTING) {
                    // 重连倒计时中
                    LogUtils.i("hichannel is counting-down...");
                    onChanged(NetChannelStatus.Disconnected);
                } else {
                    // ignore now
                }

            } catch (Throwable t) {

                LogUtils.i("hichannel  status error error,", t.getMessage());
            }
        }

        public void onError(long err, int arg1, byte[] data, int len) {
            if (err == 1000005) {
                return;
            }
            LogUtils.i("收到hichannel的回包~~~~~~~~~~~~~~~~~~~~~onError(long err, int arg1, byte[] data, int len)");
            LogUtils.i(err + "  " + arg1 + "  " + Arrays.toString(data) + "  " + len);
        }

        public void onDnsLookup(byte[] data, int len, int tag) {
        }
    }
}
