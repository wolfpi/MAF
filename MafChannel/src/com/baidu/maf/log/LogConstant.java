package com.baidu.maf.log;

public class LogConstant {

    final static int LOG_LEVEL_ERROR = 16;
    final static int LOG_LEVEL_WARN = 8;
    final static int LOG_LEVEL_INFO = 4;
    final static int LOG_LEVEL_DEBUG = 2;

    final static String V = "V";
    final static String D = "D";
    final static String I = "I";
    final static String W = "W";
    final static String E = "E";

//    public static int LOGCAT_LEVEL = 2;// logcat level
//    public static int FILE_LOG_LEVEL = 2;// log file level, must >= LOGCAT_LEVEL
//
//    boolean DEBUG = (LOGCAT_LEVEL <= LOG_LEVEL_DEBUG);
//    boolean INFO = (LOGCAT_LEVEL <= LOG_LEVEL_INFO);
//    boolean WARN = (LOGCAT_LEVEL <= LOG_LEVEL_WARN);
//    boolean ERROR = (LOGCAT_LEVEL <= LOG_LEVEL_ERROR);

    final static String MAINPROCESS_TYPE = ".mainprocess";
    final static String PROTOCOL_TYPE = ".protocol";
    final static String STATISTIC_TYPE = ".statistic";
    final static String ERROR_TYPE = ".error";


    public final static String DEFAULT_FILE_LOG_PATH = "/baidu/maf/sdk/";
    public final static String DEFAULT_FILE_LOG_NAME = "mafsdk";
    public final static String DEFAULT_FILE_LOG_TYPE = ".log";
    public final static int DEFAULT_CLEAR_LOG_HOURS = 24 * 7;

    public final static int MAX_CLEAR_LOG_HOURS = 24 * 30;
    public final static int MIN_CLEAR_LOG_HOURS = 24 * 1;

    public final static boolean LOG_DEV = false;

    public enum UPLOAD_TYPE {
        UNKNOWN(0),

        NORMAL(1),

        PERIODIC(2);

        int key;

        UPLOAD_TYPE(int key) {
            this.key = key;
        }

        public UPLOAD_TYPE valueOf(int key) {
            UPLOAD_TYPE[] values = UPLOAD_TYPE.values();
            if (null != values && values.length > 0) {
                for (UPLOAD_TYPE type : values) {
                    if (type.key == key) {
                        return type;
                    }
                }
            }
            return UNKNOWN;
        }
    }
}
