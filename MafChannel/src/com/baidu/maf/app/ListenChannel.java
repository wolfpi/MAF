package com.baidu.maf.app;

import com.baidu.maf.channel.DispChannel;
import com.baidu.maf.channel.MessageChannel;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.ResponseMessage;

/**
 * Created by 欣 on 2016/5/29.
 */
public class ListenChannel extends MessageChannel implements MessageHolder{
    private MafMessageProcedure procedure = null;
    private int errcode;
    private String errInfo;
    private int id = 0;

    public ListenChannel(MafMessageProcedure procedure, DispChannel dispChannel) {
        this.procedure = procedure;
        this.setNextChannel(dispChannel);
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public boolean parse(byte[] bytes){
        return procedure.getResponse().parseFrom(bytes);
    }

    @Override
    public void send(Message upPacket) throws Exception {
        id = upPacket.getMessageID();
        super.send(upPacket);
    }

    @Override
    public void receive(Message downPacket) throws Exception {
        procedure.getResponseData((ResponseMessage) downPacket, errcode, errInfo);
    }

    @Override
    public Message getReqMessage() {
        return procedure.getRequest();
    }

    @Override
    public Message getRspMessage() {
        return procedure.getResponse();
    }

    @Override
    public int getId() {
        return id;
    }

    public boolean canResend(){
        return  procedure.canRetry();
    }
}
