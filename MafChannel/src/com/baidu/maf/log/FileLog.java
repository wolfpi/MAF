package com.baidu.maf.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.baidu.maf.com.Constant;
import com.baidu.maf.util.FileUtil;

/**
 * Responsible for implementing calls to im sdk File system.
 */
public final class FileLog implements ILog {

    private int FILE_LOG_LEVEL = 2;

    private boolean DEBUG = (FILE_LOG_LEVEL <= LogConstant.LOG_LEVEL_DEBUG);
    private boolean INFO = (FILE_LOG_LEVEL <= LogConstant.LOG_LEVEL_INFO);
    private boolean WARN = (FILE_LOG_LEVEL <= LogConstant.LOG_LEVEL_WARN);
    private boolean ERROR = (FILE_LOG_LEVEL <= LogConstant.LOG_LEVEL_ERROR);

    private final static String LOG_ENTRY_FORMAT = "[%tF %tT][%s][%s]%s"; // [2010-01-22 13:39:1][D][com.a.c]msg
    private String LOG_FILE_PATH = LogConstant.DEFAULT_FILE_LOG_PATH;
    private String LOG_FILE_NAME = LogConstant.DEFAULT_FILE_LOG_NAME + LogConstant.DEFAULT_FILE_LOG_TYPE;

    private PrintStream logStream;
    private boolean initialized = false;

    private String suffixType;
    private boolean needFormat = false;
    private LogType logType = LogType.DAY;
    private int clearLogsTime = LogConstant.DEFAULT_CLEAR_LOG_HOURS;

    public FileLog(String path, String logName, boolean needFormat) {
        this(path, logName, LogType.DAY, LogConstant.DEFAULT_CLEAR_LOG_HOURS, needFormat);
    }

    public FileLog(String path, String logName, LogType logType, boolean needFormat){
        this(path, logName, logType, LogConstant.DEFAULT_CLEAR_LOG_HOURS, needFormat);
    }

    public FileLog(String path, String logName, LogType logType, int clearLogTime, boolean needFormat) {
        if (null != path && path.length() > 0) {
            LOG_FILE_PATH = path;
        } else {
            LOG_FILE_PATH = LogConstant.DEFAULT_FILE_LOG_PATH;
        }
        if (null != logName && logName.length() > 0) {
            this.LOG_FILE_NAME = logName + LogConstant.DEFAULT_FILE_LOG_TYPE;
        }

        this.logType = logType;

        if (clearLogTime >= LogConstant.MIN_CLEAR_LOG_HOURS && clearLogTime <= LogConstant.MAX_CLEAR_LOG_HOURS) {
            this.clearLogsTime = clearLogTime;
        } else if (clearLogTime > LogConstant.MAX_CLEAR_LOG_HOURS) {
            this.clearLogsTime = LogConstant.MAX_CLEAR_LOG_HOURS;
        } else { // clearLogTime < MIN_CLEAR_LOG_HOURS
            this.clearLogsTime = LogConstant.MIN_CLEAR_LOG_HOURS;
        }

        this.needFormat = needFormat;
    }

    /**
     * Open a new PrintStream for FileLog.
     * 
     * @param time using for the name of File Log.
     */
    public void open(String time) {
        if (null != time && time.length() > 0) {
            LOG_FILE_NAME = time + suffixType;
        } else {
            LOG_FILE_NAME = LogConstant.DEFAULT_FILE_LOG_NAME + suffixType;
        }
        // init();
    }

    /**
     * Close the current print stream.
     */
    public void close() {
        initialized = false;
        if (logStream != null) {
            logStream.close();
        }
    }

    /**
     * Clear Log Files
     */
    public void clear() {
        try {
            if (null != LOG_FILE_PATH && LOG_FILE_PATH.length() > 0) {
                File sdRoot = FileUtil.getSDRootFile();
                if (sdRoot != null) {
                    deleteFileByTime(new File(sdRoot + LOG_FILE_PATH), clearLogsTime);
                }
            }
        } catch (Throwable t) {
            DebugLogUtil.e("Clear File Log failed.", t);
        }
    }

    /**
     * Set the level of filelog.
     * 
     * @param level
     */
    public void setLogLevel(int level) {
        FILE_LOG_LEVEL = level;
        DEBUG = (FILE_LOG_LEVEL <= LogConstant.LOG_LEVEL_DEBUG);
        INFO = (FILE_LOG_LEVEL <= LogConstant.LOG_LEVEL_INFO);
        WARN = (FILE_LOG_LEVEL <= LogConstant.LOG_LEVEL_WARN);
        ERROR = (FILE_LOG_LEVEL <= LogConstant.LOG_LEVEL_ERROR);
    }

    /**
     * Delete Log File whose last_modified_time is less than nowTime minus clearLogsTime
     * 
     * @param file target file
     * @param hours clearLogsTime
     */
    private void deleteFileByTime(File file, int hours) {
        long now = System.currentTimeMillis();
        long h = hours * 60 * 60 * 1000;
        long t = 0;
        if (file != null) {
            if (file.isFile()) {
                String dateString = file.getName().substring(file.getName().lastIndexOf("."));
                Date date = null;
                try{
                    date = new SimpleDateFormat(logType.getFormatString()).parse(dateString);
                }
                catch (ParseException e){
                    DebugLogUtil.e("File log found unregnized file", e);
                }
                if (date != null){
                    long diff = new Date().getTime() - date.getTime();
                    if (diff > h){
                        FileUtil.deleteFile(file);
                    }
                }
                else{
                    t = file.lastModified();
                    if ((now - t) > h) {
                        DebugLogUtil.i("FileLog deleteByTime FilePath" + file);
                        FileUtil.deleteFile(file);
                    }
                }
            } else {
                File[] f = file.listFiles();
                if (f != null) {
                    for (int i = 0; i < f.length; i++) {
                        deleteFileByTime(f[i], hours);
                    }
                }
            }
        }
    }

    /**
     * Get the current level of file log.
     */
    public int getLogLevel() {
        return FILE_LOG_LEVEL;
    }

    /**
     * Remove all log files
     */
    public void remove() {
        try {
            if (null != LOG_FILE_PATH && LOG_FILE_PATH.length() > 0) {
                File sdRoot = FileUtil.getSDRootFile();
                if (sdRoot != null) {
                    deleteFile(new File(sdRoot + LOG_FILE_PATH));
                }
            }
        } catch (Throwable t) {
            DebugLogUtil.e("Clear File Log failed.", t);
        }
    }

    /**
     * Delete Log File
     * 
     * @param file target file
     */
    private void deleteFile(File file) {
        if (file != null) {
            if (file.isFile()) {
                DebugLogUtil.i("FileLog delete FilePath" + file);
                FileUtil.deleteFile(file);
            } else {
                File[] f = file.listFiles();
                if (f != null) {
                    for (int i = 0; i < f.length; i++) {
                        deleteFile(f[i]);
                    }
                }
            }
        }
    }

    /**
     * Write a VERBOSE log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     */
    public void v(String tag, String msg) {
        if (DEBUG) {
            write(LogConstant.V, tag, msg, null);
        }
    }

    /**
     * Write a VERBOSE log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public void v(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            write(LogConstant.V, tag, msg, tr);
        }
    }

    /**
     * Write a DEBUG log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     */
    public void d(String tag, String msg) {
        if (DEBUG) {
            write(LogConstant.D, tag, msg, null);
        }
    }

    /**
     * Write a DEBUG log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            write(LogConstant.D, tag, msg, tr);
        }
    }

    /**
     * Write a DEBUG log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     */
    public void i(String tag, String msg) {
        if (INFO) {
            write(LogConstant.I, tag, msg, null);
        }
    }

    /**
     * Write a INFO log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public void i(String tag, String msg, Throwable tr) {
        if (INFO) {
            write(LogConstant.I, tag, msg, tr);
        }
    }

    /**
     * Write a WARN log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     */
    public void w(String tag, String msg) {
        if (WARN) {
            write(LogConstant.W, tag, msg, null);
        }
    }

    /**
     * Write a WARN log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public void w(String tag, String msg, Throwable tr) {
        if (WARN) {
            write(LogConstant.W, tag, msg, tr);
        }
    }

    /**
     * Write a ERROR log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     */
    public void e(String tag, String msg) {
        if (ERROR) {
            write(LogConstant.E, tag, msg, null);
        }
    }

    /**
     * Write a ERROR log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public void e(String tag, String msg, Throwable tr) {
        if (ERROR) {
            write(LogConstant.E, tag, msg, tr);
        }
    }

    /**
     * Write a WTF log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     */
    public void wtf(String tag, String msg) {
        if (ERROR) {
            write(LogConstant.E, tag, msg, null);
        }
    }

    /**
     * Write a WTF log message.
     * 
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public void wtf(String tag, String msg, Throwable tr) {
        if (ERROR) {
            write(LogConstant.E, tag, msg, tr);
        }
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     *
     * @param tr An exception to log
     */
    public String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    /**
     * Write Log String in print stream.
     * 
     * @param level the curent log level tag
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     * @param error An exception to log
     */
    private void write(String level, String tag, String msg, Throwable error) {
        if (!initialized) {
            init();
        }
        if (initialized) {
            if (logStream == null || logStream.checkError()) {
                initialized = false;
                return;
            }
            if (needFormat) {
                Date now = new Date();
                logStream.printf(LOG_ENTRY_FORMAT, now, now, level, tag, msg);
                logStream.println();
            } else {
                logStream.println(msg);
            }
            if (error != null) {
                error.printStackTrace(logStream);
                logStream.println();
            }
        }
    }

    /**
     * Initializing a new Print Stream for the current time and file name.
     */
    public synchronized void init() {
        if (initialized)
            return;
        try {
            File sdRoot = FileUtil.getSDRootFile();
            if (sdRoot != null) {
                File logFilePath = new File(sdRoot + LOG_FILE_PATH);
                if (!logFilePath.exists()) {
                    logFilePath.mkdirs();
                }
                clear();
                DebugLogUtil.i("FileLog initializing FilePath" + logFilePath);
                String logFileName = LOG_FILE_NAME + "." + logType.getCurrentTimeLogSuffix();
                File logFile = new File(sdRoot + LOG_FILE_PATH, logFileName);
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
                DebugLogUtil.i("FileLog initializing FileName" + logFileName);
                if (logStream != null) {
                    logStream.close();
                }
                logStream = new PrintStream(new FileOutputStream(logFile, true), true);
                initialized = true;
            }
        } catch (Exception e) {
            DebugLogUtil.e(LOG_FILE_NAME + " init log stream failed", e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

    public String getFilePath() {
        return LOG_FILE_PATH;
    }

    public String getFileName() {
        return LOG_FILE_NAME;
    }

    public String getSuffixType() {
        return suffixType;
    }

    public void setSuffixType(String suffixType) {
        this.suffixType = suffixType;
    }

    public enum LogType{
        HOUR{
            @Override
            public String getCurrentTimeLogSuffix() {
                return new SimpleDateFormat("yyyymmddHH").format(new Date());
            }

            @Override
            public String getFormatString() {
                return "yyyymmddHH";
            }
        },
        DAY{
            @Override
            public String getCurrentTimeLogSuffix() {
                return new SimpleDateFormat("yyyymmdd").format(new Date());
            }

            @Override
            public String getFormatString() {
                return "yyyymmdd";
            }
        },
        MONTH{
            @Override
            public String getCurrentTimeLogSuffix() {
                return new SimpleDateFormat("yyyymm").format(new Date());
            }

            @Override
            public String getFormatString() {
                return "yyyymm";
            }
        };

        public abstract String getCurrentTimeLogSuffix();

        public abstract String getFormatString();
    }
}
