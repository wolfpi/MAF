package com.baidu.maf.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author zhaowei10
 * 
 */
public class PreferenceUtil implements Preference{
    public static final String TAG = "PreferenceUtil";

    private  SharedPreferences globalPreferences;
    private  Context ct = null;
        
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
    public void initialize(Context context, String key) {

    	if(context == null)
    		return ;
    	ct = context.getApplicationContext() ;
        if (key != null && globalPreferences == null) {
            globalPreferences = context.getSharedPreferences(key, Context.MODE_MULTI_PROCESS);
        }
    }

    @Override
    public int getSeq() {
        return 0;
    }

    public  boolean save(PreferenceKey key, String value) {
    	if(globalPreferences != null) {
        Editor editor = globalPreferences.edit();
        if(value == null)
        	value = "";
        editor.putString(key.name(), value);
        return editor.commit();
    	}
    	return false;
    }

    public  boolean save(PreferenceKey key, long value) {
    	if(globalPreferences != null) {
        Editor editor = globalPreferences.edit();
        editor.putLong(key.name(), value);
        return editor.commit();
    	}
    	return false;
    }

    public  boolean save(PreferenceKey key, int value) {
        if(globalPreferences != null) {
        Editor editor = globalPreferences.edit();
        editor.putInt(key.name(), value);
        return editor.commit();
    	}
    	return false;
    }

    public  boolean save(PreferenceKey key, boolean value) {
    	if(globalPreferences != null) {
        Editor editor = globalPreferences.edit();
        editor.putBoolean(key.name(), value);
        return editor.commit();
    	}
    	return false;
    }

    public  String getString(PreferenceKey key) {
    	if(globalPreferences != null) {
        return globalPreferences.getString(key.name(), "");
    	}
    	return "";
    }

    public  long getLong(PreferenceKey key) {
    	if(globalPreferences != null) {
        return globalPreferences.getLong(key.name(), 0);
    	}
    	return 0;
    }

    public  boolean getBoolean(PreferenceKey key) {
        return getBoolean(key, false);
    }

    public  boolean getBoolean(PreferenceKey key, boolean defaultValue) {
    	if(globalPreferences != null) {
        return globalPreferences.getBoolean(key.name(), defaultValue);
    	}
    	return false;
    }

    public  int getInt(PreferenceKey key) {
        return getInt(key, 0);
    }

    public  int getInt(PreferenceKey key, int defaultValue) {
    	if(globalPreferences != null ) {
    		return globalPreferences.getInt(key.name(), defaultValue);
    	}
    	return 0;
    }

    public void clear() {
    	if(globalPreferences != null)  {
        Editor editor = globalPreferences.edit();
        editor.clear();
        editor.commit();
    	}
    }

    public void remove(PreferenceKey key) {
    	if(globalPreferences != null)  {
        Editor editor = globalPreferences.edit();
        editor.remove(key.name());
        editor.commit();
    	}
    }
}
