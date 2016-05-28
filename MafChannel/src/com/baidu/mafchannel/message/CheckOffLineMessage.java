package com.baidu.mafchannel.message;

import com.baidu.mafchannel.com.Buffer;

/**
 * Created by hanxin on 2016/5/21.
 */
public class CheckOffLineMessage extends AppMessage{
    @Override
    public boolean serializeTo(Buffer buffer) {
        byte a = 0;
        buffer.writeByte(a);
        return true;
    }

    @Override
    public boolean parseFrom(Buffer buffer) {
        byte b = buffer.readByte();
        return true;
    }

    @Override
    public MessageTypeEnum getMessageType() {
        return MessageTypeEnum.CHECKOFFLINE;
    }

    @Override
    public byte[] toByteArray() {
        return ComMessageFactory.EncodeMessage(this);
    }
}
