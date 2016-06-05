package com.baidu.maf.message;

import com.baidu.maf.app.ProcessorCode;
import com.baidu.maf.com.Buffer;

/**
 * Created by 欣 on 2016/6/5.
 */
public class ErrorMessage implements ResponseMessage {

    private int messageId;
    private ProcessorCode code;

    public ErrorMessage(int messageId, ProcessorCode code) {
        this.messageId = messageId;
        this.code = code;
    }

    @Override
    public int getErrcode() {
        return code.getCode();
    }

    @Override
    public String getErrInfo() {
        return code.getMsg();
    }

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
        return false;
    }

    @Override
    public int getMessageID() {
        return messageId;
    }

    @Override
    public MessageTypeEnum getMessageType() {
        return null;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }
}
