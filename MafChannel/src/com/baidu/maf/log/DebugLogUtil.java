package com.baidu.maf.log;

import android.util.Log;

/**
 * Created by 欣 on 2016/6/9.
 */
public class DebugLogUtil {
    private static String LOG_TAG_STRING = "DebugLogUtil";

    public static void i(String msg) {
        if (LogConstant.LOG_DEV) {
            Log.i(LOG_TAG_STRING, msg);
        }
    }

    public static void i(String msg, Throwable t) {
        if (LogConstant.LOG_DEV) {
            Log.i(LOG_TAG_STRING, msg, t);
        }
    }

    public static void e(String msg) {
        if (LogConstant.LOG_DEV) {
            Log.e(LOG_TAG_STRING, msg);
        }
    }

    public static void e(String msg, Throwable t) {
        if (LogConstant.LOG_DEV) {
            Log.e(LOG_TAG_STRING, msg, t);
        }
    }
}
