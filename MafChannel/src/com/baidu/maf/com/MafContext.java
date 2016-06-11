package com.baidu.maf.com;

import android.content.Context;

import com.baidu.maf.app.SendBox;
import com.baidu.maf.network.IChannelChangeListener;
import com.baidu.maf.network.NetChannelStatus;
import com.baidu.maf.util.MafPreference;

/**
 * Created by hanxin on 2016/5/25.
 */
public class MafContext {
    private Context context = null;
    private int appId = 0;
    private String appKey = null;
    private String channelKey = null;
    private IChannelChangeListener networkChangeListener = null;
    private MafPreference mPreference = new MafPreference();
    private SendBox sendBox = null;

    public MafContext(Context context, String appKey) {
        this.context = context;
        this.appKey = appKey;
        mPreference.initialize(context, appKey);
        appId = mPreference.getAppId();
        channelKey = mPreference.getChannelkey();
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
        mPreference.saveAppId(appId);
    }

    public String getAppKey() {
        return appKey;
    }

    public String getChannelKey() {
        if (null == channelKey){
            channelKey = mPreference.getChannelkey();
        }
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
        mPreference.saveChanneKey(channelKey);
    }

    public String getDeviceToken(){
        return mPreference.getDeviceToken();
    }

    public void saveDeviceToken(String deviceToken){
        mPreference.saveDeviceToken(deviceToken);
    }

    public Context getContext() {
        return context;
    }

    public void setNetworkChangeListener(IChannelChangeListener networkChangeListener) {
        this.networkChangeListener = networkChangeListener;
    }

    public int getSeq(){
        return mPreference.getSeq();
    }

    public void networkChange(NetChannelStatus status){
        networkChangeListener.onChanged(status);
    }

    public void onAvaliable(String channelKey){
        networkChangeListener.onAvaliable(channelKey);
    }

    public void setSendBox(SendBox sendBox) {
        this.sendBox = sendBox;
    }

    public void triggerResend(){
        sendBox.resend();
    }
}
