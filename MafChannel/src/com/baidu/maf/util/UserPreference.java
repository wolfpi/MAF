package com.baidu.maf.util;

import android.content.Context;

/**
 * Created by 欣 on 2016/6/6.
 */
public class UserPreference {
    private  PreferenceUtil globalPreferences = new PreferenceUtil();
    private long uid = 0;
    private String sessionId = null;


    public void initialize(Context context, String userName) {
        if(context == null)
            return ;
        if (userName != null && globalPreferences == null) {
            globalPreferences.initialize(context, userName);
        }
    }

    public long getUid() {
        return globalPreferences.getInt(PreferenceKey.uid, 0);
    }

    public String getSessionId() {
        return globalPreferences.getString(PreferenceKey.sessionId);
    }

    public void saveUid(long uid){
        globalPreferences.save(PreferenceKey.uid, uid);
    }

    public void saveSessionId(String sessionId){
        globalPreferences.save(PreferenceKey.sessionId, sessionId);
    }

    public void reset(){
        globalPreferences.clear();
    }
}
