package com.baidu.mafchannel.app;

import com.baidu.mafchannel.channel.DataChannel;
import com.baidu.mafchannel.message.Message;

/**
 * Created by 欣 on 2016/6/1.
 */
public class ChannelMessage {
    private Message message = null;
    private ListenChannel channel = null;
    private DataChannel sendChannel = null;

    public ChannelMessage(Message message, ListenChannel channel, DataChannel sendChannel) {
        this.message = message;
        this.channel = channel;
        this.sendChannel = sendChannel;
    }

    public Message getMessage() {
        return message;
    }

    public DataChannel getChannel() {
        return channel;
    }

    public void send() throws  Exception{
        sendChannel.send(message);
    }

    public void receive(Message message) throws Exception{
        channel.receive(message);
    }

    public boolean canResend(){
        return channel.canResend();
    }
}
