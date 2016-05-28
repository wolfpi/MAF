package com.baidu.mafchannel.app;

import android.content.Context;
import android.util.Log;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafChannelSDK {
    private static String mAppkey = null;
    private static MafChannel channel = null;

    public static synchronized MafChannel getChannelInstByAppKey(String appKey, Context context){
        if (appKey == null || appKey.isEmpty() || context == null) {
            Log.e("MafChannelSDK", "ClientID or context is incorrect");
            return null;
        }

        if (mAppkey != null)
            return channel;
        mAppkey = appKey;
        try {
            channel = new MafChannel(mAppkey);
        } catch (Throwable t) {
            Log.e("MafChannelSDK", t.getMessage(), t);
        }

        return channel;
    }

    public static synchronized MafChannel getLoginChannelInstByAppKey(String appKey, Context context){
        if (appKey == null || appKey.isEmpty() || context == null) {
            Log.e("MafChannelSDK", "ClientID or context is incorrect");
            return null;
        }

        if (mAppkey != null)
            return channel;
        mAppkey = appKey;
        try {
            channel = new MafChannel(mAppkey);
        } catch (Throwable t) {
            Log.e("MafChannelSDK", t.getMessage(), t);
        }

        return channel;
    }
}
