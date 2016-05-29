package com.baidu.mafchannel.app;

import com.baidu.mafchannel.channel.DispChannel;
import com.baidu.mafchannel.channel.MessageChannel;
import com.baidu.mafchannel.message.Message;

/**
 * Created by 欣 on 2016/5/29.
 */
public class ListenChannel extends MessageChannel {
    private MafMessageListener listener = null;
    private int errcode;
    private String errInfo;

    public ListenChannel(MafMessageListener listener, DispChannel dispChannel) {
        this.listener = listener;
        this.setNextChannel(dispChannel);
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    @Override
    public void receive(Message downPacket) throws Exception {
        listener.receive(downPacket, errcode, errInfo);
    }
}
