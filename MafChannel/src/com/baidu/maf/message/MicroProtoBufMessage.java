package com.baidu.maf.message;

import com.baidu.maf.com.Buffer;
import com.baidu.maf.com.BufferInputStream;
import com.baidu.maf.com.BufferOutputStream;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.MessageMicro;

import java.io.IOException;

/**
 * Created by hanxin on 2016/5/17.
 */
public class MicroProtoBufMessage implements Message {
    private MessageMicro micro;
    private int id = 0;

    public MessageMicro getMicro() {
        return micro;
    }

    public MicroProtoBufMessage(MessageMicro micro) {
        this.micro = micro;
    }

    @Override
    public boolean serializeTo(Buffer buffer) {
        try{
            micro.writeTo(CodedOutputStreamMicro.newInstance(new BufferOutputStream(buffer)));
        }
        catch (IOException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean serializeTo(byte[] buffer) {
        try {
            micro.toByteArray(buffer, 0, micro.getSerializedSize());
        }
        catch (RuntimeException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean parseFrom(Buffer buffer) {
        try{
            micro.mergeFrom(CodedInputStreamMicro.newInstance(new BufferInputStream(buffer)));
        }
        catch (IOException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean parseFrom(byte[] buffer) {
        Buffer buf = Buffer.wrapReadableContent(buffer);
        return parseFrom(buf);
    }

    @Override
    public MessageTypeEnum getMessageType() {
        return MessageTypeEnum.NORMAL;
    }

    @Override
    public byte[] toByteArray() {
        return micro.toByteArray();
    }

    public void setMessageID(int id) {
        this.id = id;
    }

    @Override
    public int  getMessageID() {
        return id;
    }
}
