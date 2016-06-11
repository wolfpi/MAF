package com.baidu.maf.log;

public interface ILog {
    public void v(java.lang.String tag, java.lang.String msg);

    public void v(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    public void d(java.lang.String tag, java.lang.String msg);

    public void d(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    public void i(java.lang.String tag, java.lang.String msg);

    public void i(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    public void w(java.lang.String tag, java.lang.String msg);

    public void w(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    public void e(java.lang.String tag, java.lang.String msg);

    public void e(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    public void wtf(java.lang.String tag, java.lang.String msg);

    public void wtf(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    public java.lang.String getStackTraceString(java.lang.Throwable tr);

    public void setLogLevel(int level);
}
