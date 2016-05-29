package com.baidu.mafchannel.app;

import android.content.Context;
import android.util.Log;

import com.baidu.mafchannel.util.PreferenceUtil;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafChannelSDK {
    private static String mAppkey = null;
    private static Context mContext = null;
    private static MafChannelImpl channel = null;
    private static MafUserChannelImpl userChannel = null;
    private static PreferenceUtil mPreference = new PreferenceUtil();

    public static synchronized MafChannel getChannelInstByAppKey(){
        if (null == channel && null != mAppkey){
            if (channel == null) {
                channel = new MafChannelImpl(mContext, mAppkey);
                channel.init();
            }
        }

        return channel;
    }

    public static synchronized MafUserChannel getLoginChannelInstByAppKey(){
        if (null == userChannel && null != mAppkey){
            if (userChannel == null) {
                userChannel = new MafUserChannelImpl(mContext, mAppkey);
                userChannel.init();
            }
        }
        return userChannel;
    }

    public static void initialize(String appKey, Context context){
        if (appKey == null || appKey.isEmpty() || context == null) {
            Log.e("MafChannelSDK", "ClientID or context is incorrect");
            return;
        }

        if (mAppkey != null)
            return;

        mContext = context;
        mAppkey = appKey;
        try {
            mPreference.initialize(context, appKey);

        } catch (Throwable t) {
            Log.e("MafChannelSDK", t.getMessage(), t);
        }
    }
}
