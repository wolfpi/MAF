package com.baidu.maf.channel;

import com.baidu.maf.message.*;
import com.google.protobuf.micro.MessageMicro;

/**
 * Created by hanxin on 2016/5/22.
 */
public class MessageMicroChannel extends PacketChannel{

    public MessageMicroChannel(EChannelId channelId) {
        super(channelId);
    }

    @Override
    public MessageChannelInfo createChannelInfo(){
        try {
            MessageChannelInfo info = new MessageMicroChannelInfo(getReqClazz(), getRspClazz());
            info.setChannelId(getChannelId());
            return info;
        }
        catch (Exception e){
            return null;
        }
    }

    public Class<? extends MessageMicro> getReqClazz() {
        return (Class<? extends MessageMicro>)getChannelId().getReqClazz();
    }

    public Class<? extends MessageMicro> getRspClazz() {
        return (Class<? extends MessageMicro>)getChannelId().getRspClazz();
    }

    public class MessageMicroChannelInfo implements MessageChannelInfo{
        private MicroProtoBufMessage req;
        private MicroProtoBufMessage rsp;
        private MessageProcesser channel;
        private EChannelId channelId;

        @Override
        public void setChannelId(EChannelId channelId) {
            this.channelId = channelId;
        }

        public MessageMicroChannelInfo(Class<? extends MessageMicro> reqClazz, Class<? extends MessageMicro> rspClazz) {
            try {
                this.req = new MicroProtoBufReqMessage(reqClazz.newInstance());
                this.rsp = new MicroProtoBufRspMessage(rspClazz.newInstance());
            }
            catch (Exception e){
                this.req = null;
                this.rsp = null;
            }
        }

        @Override
        public Message getReqMessage() {
            return req;
        }

        @Override
        public Message getRspMessage() {
            return rsp;
        }

        @Override
        public EChannelId getChannelId() {
            return channelId;
        }

        public <T> T getReq(){
            return (T)req.getMicro();
        }

        public <T> T  getRsp(){
            return (T)rsp.getMicro();
        }

        @Override
        public void send(Message upPacket) throws Exception {
            channel.send(getReqMessage());
        }

        @Override
        public void receive(Message downPacket) throws Exception {
            DownPacketMessage downPacketMessage = (DownPacketMessage)downPacket;
            Message rsp = getRspMessage();
            if (null != rsp){
                rsp = downPacketMessage.parseMessage(rsp);
                channel.getChannelRspData(this, rsp, downPacketMessage.getBusiCode(), "");
            }
        }

        @Override
        public void setNextChannel(DataChannel callback) {
            channel = (MessageProcesser)callback;
        }
    }
}
