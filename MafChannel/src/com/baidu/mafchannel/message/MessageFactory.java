package com.baidu.mafchannel.message;

/**
 * Created by hanxin on 2016/5/19.
 */
public interface MessageFactory {
    Message createMessage(Class<? extends Message> clazz);
}
