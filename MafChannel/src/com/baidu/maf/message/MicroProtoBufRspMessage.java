package com.baidu.maf.message;

import com.google.protobuf.micro.MessageMicro;

/**
 * Created by hanxin on 2016/5/19.
 */
public class MicroProtoBufRspMessage extends MicroProtoBufMessage implements ResponseMessage{
    private int errcode = 0;
    private String errInfo;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public MicroProtoBufRspMessage(MessageMicro micro) {
        super(micro);
    }

    @Override
    public int getMessageID() {
        return 0;
    }
}
