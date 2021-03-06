package com.baidu.maf.channel;

import com.baidu.maf.message.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanxin on 2016/5/22.
 */
public class DispChannel implements DataChannel {
    private Map<Integer, DataChannel> channelMap = new HashMap<Integer, DataChannel>();
    private DataChannel nextChannel;

    @Override
    public void send(Message upPacket) throws Exception {
        nextChannel.send(upPacket);
    }

    @Override
    public void receive(Message downPacket) throws Exception {
        DataChannel channel = channelMap.get(downPacket.getMessageID());
        receive(downPacket, channel);
    }

    @Override
    public void setNextChannel(DataChannel callback) {
        nextChannel = callback;
    }

    public void send(Message upPacket, DataChannel channel) throws Exception {
        channelMap.put(upPacket.getMessageID(), channel);
        send(upPacket);
    }

    public void receive(Message downPacket, DataChannel channel) throws Exception{
        channel.receive(downPacket);
    }
}
