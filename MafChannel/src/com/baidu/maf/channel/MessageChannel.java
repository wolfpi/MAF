package com.baidu.maf.channel;

import com.baidu.maf.message.Message;

/**
 * Created by hanxin on 2016/5/22.
 */
public abstract class MessageChannel implements DataChannel {
    private DispChannel nextChannel = null;

    @Override
    public void setNextChannel(DataChannel callback) {
        nextChannel = (DispChannel)callback;
    }


    public void send(Message upPacket) throws Exception{
        nextChannel.send(upPacket, this);
    }

    public DataChannel getNextChannel(){
        return nextChannel;
    }
}
