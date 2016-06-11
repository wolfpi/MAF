package com.baidu.maf.log;

import com.baidu.maf.com.Constant;

/**
 * Created by 欣 on 2016/6/11.
 */
public class LogCenter {
    private LogcatLog logcatLog;
    private FileLog fileLog;

    public LogCenter(String path, String logName) {
        logcatLog = new LogcatLog();
        //fileLog = new FileLog(Constant.sdkExternalLogDir, logName, true);
        fileLog = new FileLog(path, logName, true);
    }

    /**
     * 获取logcat输出入口
     */
    public ILog getLogcatLog() {
        return logcatLog;
    }

    /**
     * 获取文件日志输出入口
     */
    public ILog getFileLog() {
        return fileLog;
    }

    public void setLogLevel(LogLevel level){
        if (null != getFileLog()){
            getFileLog().setLogLevel(level.getLevel());
        }
        if (null != getLogcatLog()) {
            getLogcatLog().setLogLevel(level.getLevel());
        }
    }

    public void wtf(String tag, String msg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().wtf(tag, msg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().wtf(tag, msg, tr);
        }
    }

    public void wtf(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().wtf(logcatTag, logcatMsg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().wtf(fileLogTag, fileLogMsg, tr);
        }
    }

    public void wtf(String tag, String msg) {
        if (null != getLogcatLog()) {
            getLogcatLog().wtf(tag, msg);
        }
        if (null != getFileLog()) {
            getFileLog().wtf(tag, msg);
        }
    }

    public void wtf(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg) {
        if (null != getLogcatLog()) {
            getLogcatLog().wtf(logcatTag, logcatMsg);
        }
        if (null != getFileLog()) {
            getFileLog().wtf(fileLogTag, fileLogMsg);
        }
    }

    public void e(String tag, String msg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().e(tag, msg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().e(tag, msg, tr);
        }
    }

    public void e(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().e(logcatTag, logcatMsg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().e(fileLogTag, fileLogMsg, tr);
        }
    }

    public void e(String tag, String msg) {
        if (null != getLogcatLog()) {
            getLogcatLog().e(tag, msg);
        }
        if (null != getFileLog()) {
            getFileLog().e(tag, msg);
        }
    }

    public void e(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg) {
        if (null != getLogcatLog()) {
            getLogcatLog().e(logcatTag, logcatMsg);
        }
        if (null != getFileLog()) {
            getFileLog().e(fileLogTag, fileLogMsg);
        }
    }

    public void w(String tag, String msg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().w(tag, msg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().w(tag, msg, tr);
        }
    }

    public void w(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().w(logcatTag, logcatMsg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().w(fileLogTag, fileLogMsg, tr);
        }
    }

    public void w(String tag, String msg) {
        if (null != getLogcatLog()) {
            getLogcatLog().w(tag, msg);
        }
        if (null != getFileLog()) {
            getFileLog().w(tag, msg);
        }
    }

    public void w(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg) {
        if (null != getLogcatLog()) {
            getLogcatLog().w(logcatTag, logcatMsg);
        }
        if (null != getFileLog()) {
            getFileLog().w(fileLogTag, fileLogMsg);
        }
    }

    public void i(String tag, String msg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().i(tag, msg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().i(tag, msg, tr);
        }
    }

    public void i(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().i(logcatTag, logcatMsg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().i(fileLogTag, fileLogMsg, tr);
        }
    }

    public void i(String tag, String msg) {
        if (null != getLogcatLog()) {
            getLogcatLog().i(tag, msg);
        }
        if (null != getFileLog()) {
            getFileLog().i(tag, msg);
        }
    }

    public void i(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg) {
        if (null != getLogcatLog()) {
            getLogcatLog().i(logcatTag, logcatMsg);
        }
        if (null != getFileLog()) {
            getFileLog().i(fileLogTag, fileLogMsg);
        }
    }

    public void d(String tag, String msg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().d(tag, msg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().d(tag, msg, tr);
        }
    }

    public void d(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().d(logcatTag, logcatMsg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().d(fileLogTag, fileLogMsg, tr);
        }
    }

    public void d(String tag, String msg) {
        if (null != getLogcatLog()) {
            getLogcatLog().d(tag, msg);
        }
        if (null != getFileLog()) {
            getFileLog().d(tag, msg);
        }
    }

    public void d(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg) {
        if (null != getLogcatLog()) {
            getLogcatLog().d(logcatTag, logcatMsg);
        }
        if (null != getFileLog()) {
            getFileLog().d(fileLogTag, fileLogMsg);
        }
    }

    public void v(String tag, String msg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().v(tag, msg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().v(tag, msg, tr);
        }
    }

    public void v(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg, Throwable tr) {
        if (null != getLogcatLog()) {
            getLogcatLog().v(logcatTag, logcatMsg, tr);
        }
        if (null != getFileLog()) {
            getFileLog().v(fileLogTag, fileLogMsg, tr);
        }
    }

    public void v(String tag, String msg) {
        if (null != getLogcatLog()) {
            getLogcatLog().v(tag, msg);
        }
        if (null != getFileLog()) {
            getFileLog().v(tag, msg);
        }
    }

    public void v(String logcatTag, String logcatMsg, String fileLogTag, String fileLogMsg) {
        if (null != getLogcatLog()) {
            getLogcatLog().v(logcatTag, logcatMsg);
        }
        if (null != getFileLog()) {
            getFileLog().v(fileLogTag, fileLogMsg);
        }
    }
}
