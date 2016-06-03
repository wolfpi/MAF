package com.baidu.maf.message;

import com.baidu.maf.com.Buffer;

/**
 * Created by hanxin on 2016/5/17.
 */
public interface Message {
    boolean serializeTo(Buffer buffer);
    boolean serializeTo(byte[] buffer);
    boolean parseFrom(Buffer buffer);
    boolean parseFrom(byte[] buffer);
    int getMessageID();
    MessageTypeEnum getMessageType();
    byte[] toByteArray();
}
