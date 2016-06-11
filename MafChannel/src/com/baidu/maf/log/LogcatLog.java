package com.baidu.maf.log;

import android.util.Log;

/**
 * Responsible for implementing calls to the Android logging system.
 */
public final class LogcatLog implements ILog {

    private int LOGCAT_LOG_LEVEL = 2;

    private boolean DEBUG = (LOGCAT_LOG_LEVEL <= LogConstant.LOG_LEVEL_DEBUG);
    private boolean INFO = (LOGCAT_LOG_LEVEL <= LogConstant.LOG_LEVEL_INFO);
    private boolean WARN = (LOGCAT_LOG_LEVEL <= LogConstant.LOG_LEVEL_WARN);
    private boolean ERROR = (LOGCAT_LOG_LEVEL <= LogConstant.LOG_LEVEL_ERROR);
    
    public LogcatLog() {

    }

    public void setLogLevel(int level) {
        LOGCAT_LOG_LEVEL = level;
        DEBUG = (LOGCAT_LOG_LEVEL <= LogConstant.LOG_LEVEL_DEBUG);
        INFO = (LOGCAT_LOG_LEVEL <=LogConstant. LOG_LEVEL_INFO);
        WARN = (LOGCAT_LOG_LEVEL <= LogConstant.LOG_LEVEL_WARN);
        ERROR = (LOGCAT_LOG_LEVEL <= LogConstant.LOG_LEVEL_ERROR);
    }

    public int getLogLevel() {
        return LOGCAT_LOG_LEVEL;
    }

    public void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public void v(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.v(tag, msg, tr);
        }
    }

    public void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    public void i(String tag, String msg) {
        if (INFO) {
            Log.i(tag, msg);
        }
    }

    public void i(String tag, String msg, Throwable tr) {
        if (INFO) {
            Log.i(tag, msg, tr);
        }
    }

    public void w(String tag, String msg) {
        if (WARN) {
            Log.w(tag, msg);
        }
    }

    public void w(String tag, String msg, Throwable tr) {
        if (WARN) {
            Log.w(tag, msg, tr);
        }
    }

    public void e(String tag, String msg) {
        if (ERROR) {
            Log.e(tag, msg);
        }
    }

    public void e(String tag, String msg, Throwable tr) {
        if (ERROR) {
            Log.e(tag, msg, tr);
        }
    }

    public void wtf(String tag, String msg) {
        if (ERROR) {
            Log.wtf(tag, msg);
        }
    }

    public void wtf(String tag, String msg, Throwable tr) {
        if (ERROR) {
            Log.wtf(tag, msg, tr);
        }
    }

    public String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }
}
