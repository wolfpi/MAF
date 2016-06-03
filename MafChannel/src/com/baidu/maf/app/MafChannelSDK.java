package com.baidu.maf.app;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.baidu.maf.com.Constant;
import com.baidu.maf.com.MafContext;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafChannelSDK {
    private static String mAppkey = null;
    private static Context mContext = null;
    private static MafChannelImpl channel = null;
    private static MafUserChannelImpl userChannel = null;
    private static MafContext mafContext = null;

    public static synchronized MafChannel getChannelInstByAppKey(){
        if (null == channel && null != mAppkey){
            if (channel == null) {
                channel = new MafChannelImpl(mafContext);
                channel.initialize();
            }
        }

        return channel;
    }

    public static synchronized MafUserChannel getLoginChannelInstByAppKey(){
        if (null == userChannel && null != mAppkey){
            if (userChannel == null) {
                userChannel = new MafUserChannelImpl(mafContext);
                userChannel.initialize();
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
            mafContext = new MafContext(mContext, mAppkey);

            IntentFilter dynamic_filter = new IntentFilter();
            dynamic_filter.addAction(Constant.sdkPushAction);
            context.registerReceiver(new PushReceiver(mafContext), dynamic_filter);

        } catch (Throwable t) {
            Log.e("MafChannelSDK", t.getMessage(), t);
        }
    }
}
