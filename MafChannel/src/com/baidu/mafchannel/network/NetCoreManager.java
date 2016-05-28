package com.baidu.mafchannel.network;

import android.content.Context;
import com.apkfuns.logutils.LogUtils;
import com.baidu.mafchannel.network.jni.*;

public class NetCoreManager {
    public static final String TAG = "NetCoreManager";

    public int isEnableHicoreLog = 1; // 0 or 1 to disable or enable hicore log,

    // for hi core
    private HiCoreEnv hiCoreEnv = new HiCoreEnv();
    private HiCoreSession hiCoreSession = null;
    private NetCoreLogCallback hiCoreLogCallback;
    private String deviceToken;
    private String channelKey;
    private String sdkVer;

    public NetCoreManager() {
    }

    public NetCoreManager(String deviceToken, String channelKey, String sdkVer) {
        this.deviceToken = deviceToken;
        this.channelKey = channelKey;
        this.sdkVer = sdkVer;
    }
    
    public void dumpSelf() {
        if (null != hiCoreSession) {
            hiCoreSession.dumpSelf();
        }
    }

    public int initHicore(Context context) {

        if (hiCoreEnv == null) {
            hiCoreEnv = new HiCoreEnv();
        }
        hiCoreEnv.initEnv("");

        hiCoreEnv.enableLog(isEnableHicoreLog);
        hiCoreLogCallback = new NetCoreLogCallback();
        hiCoreEnv.set_log_callback(hiCoreLogCallback);
//
//        Channelinfo cinfo = prepareConfig(context);
//        //LogUtil.i(TAG, data);
//        hiCoreEnv.initChannelInfo(cinfo);
//        hiCoreEnv.initIpist(HiChannelConfigUtil.readIpList(outAppConfig));
//        if(outAppConfig.getRunMode() == RUN_MODE.PRODUCT)
//        {
//        	hiCoreSession = hiCoreEnv.createSession(null);
//        }
//        else
//        {
//        	hiCoreSession = hiCoreEnv.createSession("8001");
//        }
        //todo
        hiCoreSession.initSession();

        return 0;
    }

    public void setListener(IEvtCallback callback) {

        if (hiCoreSession != null) {
            hiCoreSession.set_notify_callback(callback);// event callback, data comes
        }
    }

    public int connet() {
        if (hiCoreSession != null) {
            LogUtils.d("NetCoreManager", "connet");
            return hiCoreSession.connect();
        }
        return -1;
    }

    public boolean send(byte[] bytes, int seq) {
        if (hiCoreSession != null) {
            return hiCoreSession.postMessage(bytes, bytes.length, seq);
        }
        return false;
    }

    void deinitHicore() {

        LogUtils.d("NetCoreManager", "close");
        if (null != hiCoreSession) {
            hiCoreSession.deinitSession();

            hiCoreEnv.destroySession(hiCoreSession);

            if (null != hiCoreEnv) {
                hiCoreEnv.deinitEnv();
            }

            hiCoreSession = null;
            hiCoreEnv = null;
        }
    }

    public void disconnect() {
        if (null != hiCoreSession) {
            hiCoreSession.disconnect(true);
        }
    }

    public void heartbeat() {
        if (null != hiCoreSession) {
            hiCoreSession.sendKeepAlive();
        }
    }

    /**
     * 通知 hi core 网络变化
     */
    public void networkChanged(NetworkChange_T networkChange_T) {

        if (null != hiCoreSession) {
            hiCoreSession.networkChanged(networkChange_T);
        }
    }

}
