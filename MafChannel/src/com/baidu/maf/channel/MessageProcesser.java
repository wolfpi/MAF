package com.baidu.maf.channel;

import com.baidu.maf.com.MafContext;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.UpPacketMessage;

/**
 * Created by hanxin on 2016/5/25.
 */
public abstract class MessageProcesser extends MessageChannel implements Processer, MessageListener {
    private MessageChannelInfo channelInfo;
    private MafContext context;
    private int errCode;
    private String errInfo;

    public MessageProcesser(EChannelId channelId) {
        this.channelInfo = createChannelInfo(channelId);
        channelInfo.setNextChannel(this);
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrInfo() {
        return errInfo;
    }

    @Override
    public void send(Message upPacket) throws Exception {
        UpPacketMessage upPacketMessage = new UpPacketMessage();
        upPacketMessage.setMessage(upPacket);
        upPacketMessage.setAppKey(context.getAppKey());
        upPacketMessage.setAppId(context.getAppId());
        upPacketMessage.setServiceName(channelInfo.getChannelId().getChannelServiceName());
        upPacketMessage.setMethodName(channelInfo.getChannelId().getChannelMethodName());

        super.send(upPacketMessage);
    }
    @Override
    public void process(MafContext context) throws Exception {
        this.context = context;
        Message req = channelInfo.getReqMessage();
        setChannelReqData(channelInfo, req);
        channelInfo.setNextChannel(this);
        channelInfo.send(req);
    }

    @Override
    public void receive(Message downPacket) throws Exception {
        channelInfo.receive(downPacket);
    }

    synchronized MessageChannelInfo createChannelInfo(EChannelId channelId){
        PacketChannel packetChannel = PacketChannelManager.getInstance().getChannelById(channelId);
        if (null != packetChannel)
            return packetChannel.createChannelInfo();
        else {
            packetChannel = newInstance(channelId);
            PacketChannelManager.getInstance().registChannel(packetChannel);
            return packetChannel.createChannelInfo();
        }
    }

    public PacketChannel newInstance(EChannelId channelId){
        return new MessageMicroChannel(channelId);
    }

    public MafContext getMafContext() {
        return context;
    }

    public MessageChannelInfo getChannelInfo() {
        return channelInfo;
    }
}
