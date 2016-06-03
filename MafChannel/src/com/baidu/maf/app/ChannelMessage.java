package com.baidu.maf.app;

import com.apkfuns.logutils.LogUtils;
import com.baidu.maf.channel.DataChannel;
import com.baidu.maf.message.DownPacketMessage;
import com.baidu.maf.message.Message;

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
        DownPacketMessage downPacketMessage = (DownPacketMessage)message;
        if (channel.getRspMessage().parseFrom(downPacketMessage.getBusiData())){
            channel.setErrcode(downPacketMessage.getBusiCode());
            channel.receive(channel.getRspMessage());
        }
        else {
            LogUtils.e("ChannelMessage", "Paser Down Packet Failed");
        }
    }

    public boolean canResend(){
        return channel.canResend();
    }
}
