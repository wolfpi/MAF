package com.baidu.mafchannel.channel;

import com.baidu.mafchannel.message.*;

/**
 * Created by hanxin on 2016/5/22.
 */
public abstract class PacketChannel extends MessageChannel {
    private EChannelId channelId;

    public PacketChannel(EChannelId channelId) {
        this.channelId = channelId;
    }



    @Override
    public void receive(Message downPacket) throws Exception {

    }

    public EChannelId getChannelId(){
        return channelId;
    }

    public abstract MessageChannelInfo createChannelInfo();
}
