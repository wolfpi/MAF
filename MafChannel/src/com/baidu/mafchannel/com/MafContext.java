package com.baidu.mafchannel.com;

import android.content.Context;

/**
 * Created by hanxin on 2016/5/25.
 */
public class MafContext {
    private Context context;
    private int appId = 0;
    private String appKey;
    private String channelKey;

    public MafContext(Context context) {
        this.context = context;
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

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }
}
