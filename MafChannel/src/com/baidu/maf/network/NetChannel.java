package com.baidu.maf.network;

import android.text.TextUtils;
import com.baidu.maf.channel.DataChannel;
import com.baidu.maf.com.Buffer;
import com.baidu.maf.com.MafCallback;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.message.DownPacketMessage;
import com.baidu.maf.message.Message;
import com.baidu.maf.network.jni.IEvtCallback;
import com.baidu.maf.network.jni.LoginResult_T;
import com.baidu.maf.network.jni.LoginState_T;
import com.baidu.maf.network.jni.NetworkChange_T;
import com.baidu.maf.service.ServiceApplication;
import com.baidu.maf.util.LogUtil;
import com.baidu.maf.util.PreferenceUtil;

import java.util.Arrays;

/**
 * Created by hanxin on 2016/5/17.
 */
public class NetChannel implements DataChannel, IChannelChangeListener {

    private NetChannelStatus networkStatus = NetChannelStatus.Closed;
    private DataChannel callback = null;
    private NetCoreManager hiCoreManager;
    private NetCoreNotifyCallback hiCoreNotifyCallback = new NetCoreNotifyCallback();
    private IChannelChangeListener networkChannelListener;
    private static String TAG = "NetChannel";


    public NetChannel(MafContext mafContext) {
        LogUtil.i("NetChannel", "start to initialize hichannel...");
        hiCoreManager = new NetCoreManager(mafContext);
    }

    @Override
    public void send(Message upPacket) {
        if (getNetChannelStatus() == NetChannelStatus.Closed) {
            LogUtil.e("NetChannel", "send error for the channel had been closed.");
        }
        Buffer buffer = new Buffer();
        upPacket.serializeTo(buffer);
        hiCoreManager.send(buffer.toArray(), upPacket.getMessageID());
    }

    @Override
    public void receive(Message downPacket) throws Exception{
        if (downPacket == null) {
            LogUtil.d("ChannelMessage", "received a null downPacket.");
            return;
        }
        callback.receive(downPacket);
    }

    @Override
    public void setNextChannel(DataChannel callback) {
        this.callback = callback;
    }

    public NetChannelStatus getNetChannelStatus() {
        return networkStatus;
    }

    public void setNetworkChannelListener(IChannelChangeListener networkChannelListener) {
        this.networkChannelListener = networkChannelListener;
    }

    public synchronized void connect(){
        if (getNetChannelStatus() == NetChannelStatus.Closed) {
            LogUtil.d("NetChannel", "event: init channel.");
            hiCoreManager.initHicore(ServiceApplication.getInstance().getContext());
            hiCoreManager.setListener(hiCoreNotifyCallback);
            int result = hiCoreManager.connet();
            LogUtil.d("NetChannel", "success to connect hichannel." + result);
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

    @Override
    public void onAvaliable(String channelKey) {

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
            LogUtil.i(TAG, "HiCoreNotifyCallback::finalize()");
        }

        @Override
        public void notify(byte[] data, int len, int seq) {

            if (getNetChannelStatus() == NetChannelStatus.Closed) {
                LogUtil.i(TAG, "notify error for the channel had been closed.");
            }
            try {

                if (data == null) {
                    LogUtil.i("HiChannel", "receive a null data");
                    return;
                }

                if (data.length != len) {
                    LogUtil.i("HiChannel", "data.length != len.  " + data.length + ":" + len);
                    return;
                }

                DownPacketMessage downPacketMessage = new DownPacketMessage();
                if (downPacketMessage.parseFrom(data)){
                    LogUtil.e("NetChannel",
                            "receive a unkown packe, len = " + data.length + "  data =" + Arrays.toString(data));
                }
                // ServiceApplication.getInstance().getNetworkLayer().onReceive(downPacket);
                receive(downPacketMessage);

                // 将协议日志写文件，便于在test工程mock。
                // NetworkLayer.saveProtocolFile(downPacket);
            } catch (Throwable t) {

                LogUtil.i("hichannel error in message send to in APP", t.getMessage());
            }
        }

        public void notify(LoginState_T state, long err, LoginResult_T res) {

            if (getNetChannelStatus() == NetChannelStatus.Closed) {
                LogUtil.i(TAG, "notify loginState error for the channel had been closed.");
            }
            try {
                String channelKey = "";
                if (state == LoginState_T.LS_LOGGEDIN) {
                    channelKey = res.getChannelkey();
                    if (!TextUtils.isEmpty(channelKey)) {
                        LogUtil.i("NetChannel", "save channelkey. channelkey=" + channelKey);
                        ServiceApplication.getInstance().setChannelKey(channelKey);
                        //mPreference.saveChanneKey(channelKey);
                        if(networkChannelListener != null)
                        {
                            networkChannelListener.onAvaliable(channelKey);
                            //LogUtil.i("get channelKeyis:"+ mPreference.getChannelkey());
                        }
                    }
                    else
                    {
                        channelKey = ServiceApplication.getInstance().getChannelKey();
                        if (!TextUtils.isEmpty(channelKey))
                        {
                            if(networkChannelListener != null)
                            {
                                ServiceApplication.getInstance().setChannelKey(channelKey);
                                networkChannelListener.onAvaliable(channelKey);
                                LogUtil.i(TAG, "get channelkey confirm:" + channelKey);
                            }
                        }
                    }
                    if (TextUtils.isEmpty(channelKey)) {
                        LogUtil.i(TAG, "hichannel error, did not return channelkey from hichannel.");
                    } else {
                        LogUtil.i(TAG, "hichannel is ready now.");
                        onChanged(NetChannelStatus.Connected);
                        //ServiceApplication.getInstance().getTransactionFlow().outAppHeartbeat();
                    }
                } else if (state == LoginState_T.LS_UNLOGIN) {
                    // 掉线
                    LogUtil.i(TAG, "hichannel is offline now.");
                    onChanged(NetChannelStatus.Disconnected);
                } else if (state == LoginState_T.LS_LOGGING || state == LoginState_T.LS_RETRYING) {
                    // 连接或者断网重连中
                    LogUtil.i(TAG, "hichannel is connecting...");
                    onChanged(NetChannelStatus.Disconnected);
                } else if (state == LoginState_T.LS_RETRYCOUNTING) {
                    // 重连倒计时中
                    LogUtil.i(TAG, "hichannel is counting-down...");
                    onChanged(NetChannelStatus.Disconnected);
                } else {
                    // ignore now
                }

            } catch (Throwable t) {

                LogUtil.i("hichannel  status error error,", t.getMessage());
            }
        }

        public void onError(long err, int arg1, byte[] data, int len) {
            if (err == 1000005) {
                return;
            }
            LogUtil.i(TAG, "收到hichannel的回包~~~~~~~~~~~~~~~~~~~~~onError(long err, int arg1, byte[] data, int len)");
            LogUtil.i(TAG, err + "  " + arg1 + "  " + Arrays.toString(data) + "  " + len);
        }

        public void onDnsLookup(byte[] data, int len, int tag) {
        }
    }
}
