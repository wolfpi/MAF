package com.baidu.maf.com;

import android.content.Context;
import com.baidu.maf.network.INetworkChangeListener;
import com.baidu.maf.network.NetChannelStatus;
import com.baidu.maf.util.PreferenceUtil;

/**
 * Created by hanxin on 2016/5/25.
 */
public class MafContext {
    private Context context = null;
    private int appId = 0;
    private String appKey = null;
    private String channelKey = null;
    private INetworkChangeListener networkChangeListener = null;
    private PreferenceUtil mPreference = new PreferenceUtil();

    public MafContext(Context context, String appKey) {
        this.context = context;
        this.appKey = appKey;
        mPreference.initialize(context, appKey);
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
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

    public Context getContext() {
        return context;
    }

    public void setNetworkChangeListener(INetworkChangeListener networkChangeListener) {
        this.networkChangeListener = networkChangeListener;
    }

    public int getSeq(){
        return mPreference.getSeq();
    }

    public void networkChange(NetChannelStatus status){
        networkChangeListener.onChanged(status);
    }
}
