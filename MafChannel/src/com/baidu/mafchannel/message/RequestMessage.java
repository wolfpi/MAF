package com.baidu.mafchannel.message;

/**
 * Created by hanxin on 2016/5/30.
 */
public interface RequestMessage extends Message {
    String getServiceName();
    String getMethodName();
}
