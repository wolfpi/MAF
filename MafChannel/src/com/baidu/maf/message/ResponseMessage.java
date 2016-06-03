package com.baidu.maf.message;

/**
 * Created by hanxin on 2016/5/30.
 */
public interface ResponseMessage extends Message{
    int getErrcode();
    String getErrInfo();
}
