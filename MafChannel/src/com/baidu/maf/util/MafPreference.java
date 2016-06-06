package com.baidu.maf.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 欣 on 2016/6/6.
 */
public class MafPreference {
    public static final String TAG = "PreferenceUtil";
    private static final String ChannelKey = "channelkey";
    private static final String DEVICE_TOKEN = "deviceToken";
    private static final String HeartBeatTime = "lastheartbeat";
    private static final String AppId = "appid";
    private static final String AppKey = "appkey";

    private  PreferenceUtil globalPreferences = new PreferenceUtil();
    private  SharedPreferences seqPreferences;
    private  SharedPreferences apipreference = null;
    private  SharedPreferences deviceTokenPreferences = null;
    private  int mSeq = 0;
    private Context ct = null;

    private Context getTargetContext(Context context) {

    	/*try {
			return context.createPackageContext("com.baidu.hi.sdk", Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
        LogUtil.i("preference", "get target is null");
        */
        return context;
    }
    public void initialize(Context context, String apiKey) {

        if(context == null)
            return ;
        ct = context.getApplicationContext() ;
        if (apiKey != null && globalPreferences == null) {
            globalPreferences.initialize(context, apiKey);
        }
        if (seqPreferences == null) {
            seqPreferences = getTargetContext(context).getSharedPreferences("seq", Context.MODE_WORLD_READABLE + Context.MODE_MULTI_PROCESS + Context.MODE_WORLD_WRITEABLE);
        }
        if (apipreference == null) {
            apipreference = context.getSharedPreferences("appkey", Context.MODE_MULTI_PROCESS);
        }
        if (deviceTokenPreferences == null){
            deviceTokenPreferences = context.getSharedPreferences("devicetoken", Context.MODE_MULTI_PROCESS);
        }
    }

    public synchronized  int getSeq() {
        if(seqPreferences != null) {
            int seq = seqPreferences.getInt("seq", 100);
            if (mSeq < seq) {
                mSeq = seq;
            }
            mSeq++;
            if (mSeq > Long.MAX_VALUE / 2) {
                mSeq = 100;
            }
            SharedPreferences.Editor editor = seqPreferences.edit();
            editor.putLong("seq", mSeq);
            editor.commit();
            return mSeq;
        }
        return -1;
    }

    public synchronized  long getLastHeartbeatTime()
    {
        if(seqPreferences != null) {
            return seqPreferences.getLong(HeartBeatTime, 0);
        }
        return 0;
    }
    public synchronized void saveLastHeartbeatTime(long lastHeartbeatTime)
    {
        if(seqPreferences != null) {
            SharedPreferences.Editor editor = seqPreferences.edit();
            editor.putLong(HeartBeatTime, lastHeartbeatTime);
            editor.commit();
        }
    }

    public synchronized int getAppId(){
        return globalPreferences.getInt(PreferenceKey.appId, 0);
    }

    public synchronized void saveAppId(int appId){
        globalPreferences.save(PreferenceKey.appId, appId);
    }

    public synchronized String getDeviceToken() {
        if (deviceTokenPreferences != null) {
            return deviceTokenPreferences.getString(DEVICE_TOKEN, "");
        }
        return "";
    }

    public synchronized void saveDeviceToken(String deviceToken) {
        if (deviceTokenPreferences != null) {
            SharedPreferences.Editor editor = deviceTokenPreferences.edit();
            editor.putString(DEVICE_TOKEN, deviceToken);
            editor.commit();
        }
    }

    public synchronized  String getChannelkey()
    {
        if(deviceTokenPreferences != null) {
            return deviceTokenPreferences.getString(ChannelKey, "");
        }
        return "";
    }

    public synchronized void  saveChanneKey(String channelKey)
    {
    	/*LogUtil.i("preference",ct.getApplicationContext().getPackageName());
    	LogUtil.i("preference",ct.getPackageName());

    	Field field;
		try {
			field = ContextWrapper.class.getDeclaredField("mBase");
			field.setAccessible(true);
			// 获取mBase变量的值
			Object obj;
			try {
				obj = field.get(ct);
				field = obj.getClass().getDeclaredField("mPreferencesDir");
				field.setAccessible(true);
				//LogUtil.i("preference",field.get(obj).toString());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		*/


        if(deviceTokenPreferences != null) {
            SharedPreferences.Editor editor = deviceTokenPreferences.edit();
            editor.putString(ChannelKey, channelKey);
            //        editor.apply();
            editor.commit();
        }
    }
}
