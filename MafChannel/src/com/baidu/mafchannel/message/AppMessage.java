package com.baidu.mafchannel.message;

import com.baidu.mafchannel.com.Buffer;

/**
 * Created by hanxin on 2016/5/21.
 */
public class AppMessage implements Message {
    @Override
    public boolean serializeTo(Buffer buffer) {
        return false;
    }

    @Override
    public boolean serializeTo(byte[] buffer) {
        return false;
    }

    @Override
    public boolean parseFrom(Buffer buffer) {
        return false;
    }

    @Override
    public boolean parseFrom(byte[] buffer) {
        Buffer buf = Buffer.wrapReadableContent(buffer);
        return parseFrom(buf);
    }

    @Override
    public int getMessageID() {
        return 0;
    }

    @Override
    public MessageTypeEnum getMessageType() {
        return null;
    }

    @Override
    public byte[] toByteArray() {
        return ComMessageFactory.EncodeMessage(this);
    }
}
