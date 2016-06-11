package com.baidu.maf.app;
import com.baidu.maf.channel.DataChannel;
import com.baidu.maf.channel.Processer;
import com.baidu.maf.message.DownPacketMessage;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.ResponseMessage;
import com.baidu.maf.util.LogUtil;

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
        if (message instanceof DownPacketMessage){
            DownPacketMessage downPacketMessage = (DownPacketMessage)message;
            if (downPacketMessage.getChannelCode() == ProcessorCode.SUCCESS){
                if (channel.getRspMessage().parseFrom(downPacketMessage.getBusiData())){
                    channel.setErrcode(downPacketMessage.getBusiCode());
                    channel.receive(channel.getRspMessage());
                }
                else {
                    channel.setErrcode(ProcessorCode.PARSE_RESPONSE_ERROR.getCode());
                    channel.setErrInfo(ProcessorCode.PARSE_RESPONSE_ERROR.getMsg());
                    channel.receive(null);
                    LogUtil.e("ChannelMessage", "Paser Down Packet Failed");
                }
            }
            else {
                channel.setErrcode(downPacketMessage.getChannelCode().getCode());
                channel.setErrInfo(downPacketMessage.getChannelCode().getMsg());
                channel.receive(null);
            }
        }
        else{
            ResponseMessage responseMessage = (ResponseMessage)message;
            channel.setErrcode(responseMessage.getErrcode());
            channel.setErrInfo(responseMessage.getErrInfo());
            channel.receive(null);
        }
    }

    public boolean canResend(){
        return channel.canResend();
    }
}
