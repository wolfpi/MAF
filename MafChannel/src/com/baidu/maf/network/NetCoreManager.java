package com.baidu.maf.network;

import android.content.Context;

import com.baidu.maf.com.Constant;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.network.jni.*;
import com.baidu.maf.util.ChannelConfigUtil;
import com.baidu.maf.util.DeviceInfoMapUtil;
import com.baidu.maf.util.DeviceInfoUtil;
import com.baidu.maf.util.LogUtil;
import com.baidu.maf.util.StringUtil;

public class NetCoreManager {
    public static final String TAG = "NetCoreManager";

    public int isEnableHicoreLog = 1; // 0 or 1 to disable or enable hicore log,

    // for hi core
    private HiCoreEnv hiCoreEnv = new HiCoreEnv();
    private HiCoreSession hiCoreSession = null;
    private NetCoreLogCallback hiCoreLogCallback;
    private String sdkVer;
    private MafContext mafContext;

    public NetCoreManager() {
    }

    public NetCoreManager(MafContext mafContext) {
        this.mafContext = mafContext;
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

        Channelinfo cinfo = prepareConfig(context);
        //LogUtil.i(TAG, data);
        hiCoreEnv.initChannelInfo(cinfo);
        hiCoreEnv.initIpist(ChannelConfigUtil.readIpList());
        if(Constant.buildMode == Constant.BuildMode.Online)
        {
        	hiCoreSession = hiCoreEnv.createSession(null);
        }
        else
        {
        	hiCoreSession = hiCoreEnv.createSession("8001");
        }
        //todo
        hiCoreSession.initSession();

        return 0;
    }

    private Channelinfo prepareConfig(Context context) {

        //JSONObject jsonObj = HiChannelConfigUtil.readConfig(outAppConfig, context);

        // Channelinfo channelinfo = new Channelinfo();
        // channelinfo.setChannelKey(value);

        //PreferenceUtil globalPreference = new PreferenceUtil();
        //globalPreference.initialize(context, null);
        //mPref.saveChanneKey("");

        LogUtil.printMainProcess("HiChannel", "prepare config: channelKey=" + mafContext.getChannelKey() + ", DeviceToken="
                + mafContext.getDeviceToken());

        /*HiChannelConfigUtil.setChannelKey(jsonObj, channelKey, deviceToken);*/

        Channelinfo channelinfo = new Channelinfo();
        DeviceInfoMapUtil.getChannelinfo(context, channelinfo);

        if(channelinfo.getOsversion() == null)
            channelinfo.setOsversion("");
        channelinfo.setExtraInfo("");
        channelinfo.setChannelKey(mafContext.getChannelKey());
        channelinfo.setDeviceToken(mafContext.getDeviceToken());
        channelinfo.setSdkversion("AND_1.1.1.1");

        return channelinfo;
        // return jsonObj.toString();
    }

    public void setListener(IEvtCallback callback) {

        if (hiCoreSession != null) {
            hiCoreSession.set_notify_callback(callback);// event callback, data comes
        }
    }

    public int connet() {
        if (hiCoreSession != null) {
            LogUtil.d("NetCoreManager", "connet");
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

        LogUtil.d("NetCoreManager", "close");
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
