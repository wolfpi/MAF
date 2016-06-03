package com.baidu.maf.app;

import com.baidu.maf.channel.MessageChannel;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.NotifyMessage;
import com.baidu.maf.message.RequestMessage;
import com.baidu.maf.message.UpPacketMessage;
import com.baidu.maf.network.INetworkChangeListener;
import com.baidu.maf.network.NetChannelStatus;

/**
 * Created by 欣 on 2016/5/29.
 */
public class MafChannelImpl extends MessageChannel implements MafChannel, INetworkChangeListener, MessageProcedureSender {
    private MafContext context = null;
    private String apiKey = null;
    private MessageSendBox messageSendBox = new MessageSendBox(this);
    private MafNotifyListener listener = null;

    public MafChannelImpl(MafContext context) {
        this.context = context;
        context.setNetworkChangeListener(this);
    }

    public void initialize(){
        messageSendBox.initialize(context);
    }

    @Override
    public void enableNotification() {

    }

    @Override
    public void disableNotification() {

    }

    @Override
    public boolean isNotificationEnabled() {
        return false;
    }

    @Override
    public void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute) {

    }

    @Override
    public void setNotifyListener(MafNotifyListener notifyListener) {
        this.listener = notifyListener;
    }

    @Override
    public void receive(Message downPacket) throws Exception {
    }

    @Override
    public void onChanged(NetChannelStatus networkChannelStatus) {

    }

    @Override
    public void send(MafMessageProcedure procedure) throws Exception{
        messageSendBox.send(procedure, makeUpPacket(procedure));
    }

    protected  UpPacketMessage makeUpPacket(MafMessageProcedure procedure){
        RequestMessage requestMessage = procedure.getRequest();
        procedure.setRequestData(requestMessage);
        UpPacketMessage upPacketMessage = new UpPacketMessage();
        upPacketMessage.setMessage(requestMessage);
        upPacketMessage.setAppKey(context.getAppKey());
        upPacketMessage.setAppId(context.getAppId());
        upPacketMessage.setServiceName(requestMessage.getServiceName());
        upPacketMessage.setMethodName(requestMessage.getMethodName());
        upPacketMessage.setSeq(context.getSeq());
        upPacketMessage.setbSyspacket(false);
        upPacketMessage.setMessageID(upPacketMessage.getSeq());

        return  upPacketMessage;
    }

    public void notify(NotifyMessage notifyMessage){
        if (null != listener)
            listener.receive(notifyMessage);
        else {

        }
    }
}
