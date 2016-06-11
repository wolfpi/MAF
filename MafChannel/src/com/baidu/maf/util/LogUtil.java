package com.baidu.maf.util;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.maf.com.Constant;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.log.LogCenter;
import com.baidu.maf.log.LogLevel;

public class LogUtil extends BaseLogUtil {

    public static final String TAG = "LogUtil";

    /**
     * 错误日志，写文件
     * 
     * @param thread
     * @param e
     */
    public static void printError(Throwable e) {
        printError(e, false);
    }

    private static void printError(Throwable e, boolean isService) {
        if (Constant.DEBUG) {
            Thread currentThread = Thread.currentThread();
            String currentThreadInfo = null;
            if (null != currentThread) {
                currentThreadInfo =
                        "UncaughtException, thread: " + currentThread + " name: " + currentThread.getName() + " id: "
                                + currentThread.getId();
            }
            String text = formatMessage(LogLevel.ERROR.getTag(), LogLevel.ERROR.getName(), currentThreadInfo);
            if (isService) {
                appLogCenter.e(LogLevel.ERROR.getName(), text);
            } else {
                serviceLogCenter.e(LogLevel.ERROR.getName(), text);
            }
        }
    }

    public static void printError(String msg, Throwable e) {
        printError(msg, e, false);
    }

    private static void printError(String msg, Throwable e, boolean isService) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.ERROR.getTag(), LogLevel.ERROR.getName(), msg);
            if (isService) {
                appLogCenter.e(LogLevel.ERROR.getName(), text);
            } else {
                serviceLogCenter.e(LogLevel.ERROR.getName(), text);
            }
        }
    }

    public static void printMainProcess(String log) {
        printMainProcess(log, false);
    }

    private static void printMainProcess(String log, boolean isService) {
        if (Constant.DEBUG) {
            String text =
                    "u=" + android.os.Process.myUid() + "p=" + android.os.Process.myPid() + " t="
                            + android.os.Process.myTid() + "_" + Thread.currentThread().getName() + "  " + log;
            text = formatMessage(LogLevel.MAINPROGRESS.getTag(), LogLevel.MAINPROGRESS.getName(), text);
            if (isService) {
                appLogCenter.i(LogLevel.MAINPROGRESS.getName(), text);
            } else {
                serviceLogCenter.i(LogLevel.MAINPROGRESS.getName(), text);
            }
        }
    }

    public static void printMainProcess(String tag, String log) {
        printMainProcess(tag, log, false);
    }

    private static void printMainProcess(String tag, String log, boolean isService) {
        if (Constant.DEBUG) {
            String printTag = Thread.currentThread().getName() + ":" + tag;
            String text = formatMessage(LogLevel.MAINPROGRESS.getTag(), LogLevel.MAINPROGRESS.getName(), log);
            if (isService) {
                appLogCenter.i(LogLevel.MAINPROGRESS.getName() + ":" + printTag, text);
            } else {
                serviceLogCenter.i(LogLevel.MAINPROGRESS.getName() + ":" + printTag, text);
            }
        }
    }

    public static void printDebug(String tag, String msg) {
        printDebug(tag, msg, false);
    }

    private static void printDebug(String tag, String msg, boolean isService) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.DEBUG.getTag(), LogLevel.DEBUG.getName(), tag + ", " + msg);
            if (isService) {
                appLogCenter.d(LogLevel.DEBUG.getName(), text);
            } else {
                serviceLogCenter.d(LogLevel.DEBUG.getName(), text);
            }
        }
    }

    public static void e(String msg){
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        e(tag, msg);
    }
    /**
     * UserInterface for the user of sdk
     * 
     * @param TAG
     * @param log
     */
    public static void e(String TAG, String log) {
        e(TAG, log, null);
    }

    /**
     * UserInterface for the user of sdk
     */
    public static void e(String TAG, Throwable e) {
        e(TAG, "", e);
    }

    public static void e(String TAG, String log, Throwable e) {
        e(TAG, log, e, false);
    }

    private static void e(String tag, String msg, Throwable t, boolean isService) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.ERROR.getTag(), tag, msg);
            if (isService) {
                appLogCenter.e(tag, text, t);
            } else {
                serviceLogCenter.e(tag, text, t);
            }
        }
    }

    public static void w(String msg){
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        w(tag, msg);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, false);
    }

    private static void w(String tag, String msg, boolean isService) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.WARN.getTag(), tag, msg);
            if (isService) {
                appLogCenter.w(tag, text);
            } else {
                serviceLogCenter.w(tag, text);
            }
        }
    }

    public static void i(String msg){
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        i(tag, msg);
    }

    public static void i(String tag, String msg) {
        i(tag, msg, false);
    }

    private static void i(String tag, String msg, boolean isService) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.INFO.getTag(), tag, msg);
            if (isService) {
                appLogCenter.i(tag, text);
            } else {
                serviceLogCenter.i(tag, text);
            }
        }
    }

    public static void d(String tag, String msg) {
        d(tag, msg, false);
    }

    private static void d(String tag, String msg, boolean isService) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.DEBUG.getTag(), tag, msg);
            if (isService) {
                appLogCenter.d(tag, text);
            } else {
                serviceLogCenter.d(tag, text);
            }
        }
    }

    public static void setLogLevel(LogLevel level) {
        setLogLevel(level, false);
    }

    private static void setLogLevel(LogLevel level, boolean isService) {
        if (isService) {
            appLogCenter.setLogLevel(level);
        } else {
            serviceLogCenter.setLogLevel(level);
        }
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(Line:%d)"; // 占位符
        String callerClazzName = caller.getClassName(); // 获取到类名
        callerClazzName = callerClazzName.substring(callerClazzName
                .lastIndexOf(".") + 1);
        return String.format(tag, callerClazzName, caller.getMethodName(),
                caller.getLineNumber()); // 替换
    }

    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    private static LogCenter appLogCenter = null;
    private static LogCenter serviceLogCenter = null;


    public static void initialize(MafContext context){
        if (context.getDeviceToken() == null){
            serviceLogCenter = new LogCenter(Constant.sdkExternalLogDir, "mafapp");
        }
        else{
            appLogCenter = new LogCenter(Constant.sdkExternalLogDir, "mafservice");
        }
    }

}
