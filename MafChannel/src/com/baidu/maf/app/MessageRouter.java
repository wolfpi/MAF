package com.baidu.maf.app;

import com.baidu.im.frame.pb.EnumPacketType;
import com.baidu.maf.channel.DataChannel;
import com.baidu.maf.channel.DispChannel;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.message.DownPacketMessage;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.NotifyMessage;
import com.baidu.maf.processer.PushConfirmProcesser;
import com.baidu.maf.util.LogUtil;

/**
 * Created by 欣 on 2016/5/29.
 */
public class MessageRouter extends DispChannel {
    private MafChannelImpl mafChannel = null;
    private MafContext mafContext = null;
    private AppBindChannel bindChannel = new AppBindChannel();

    public MessageRouter(MafChannelImpl mafChannel) {
        this.mafChannel = mafChannel;
    }

    public void initialize(MafContext context){
        bindChannel.setNextChannel(this);
        bindChannel.initialize(context);
        setNextChannel(bindChannel);
        this.mafContext = context;
    }

    @Override
    public void receive(Message downPacket) throws Exception {
        super.receive(downPacket);
    }

    @Override
    public void receive(Message downPacket, DataChannel channel) throws Exception {
        if (null != channel){
            channel.receive(downPacket);
        }
        else {
            //找不到channel, 判断是否是Push
            DownPacketMessage downPacketMessage = (DownPacketMessage)downPacket;
            if (EnumPacketType.NOTIFYCATION == ((DownPacketMessage) downPacket).getPacketType()){
                NotifyMessage notifyMessage = (NotifyMessage)downPacketMessage.parseMessage(new NotifyMessage());
                notifyMessage.setAppId(downPacketMessage.getAppId());
                mafChannel.notify(notifyMessage);
                confirm(notifyMessage);
            }
        }
    }

    @Override
    public void send(Message upPacket, DataChannel channel) throws Exception {
        super.send(upPacket, channel);
    }

    private void confirm(NotifyMessage notifyMessage){
        PushConfirmProcesser processer = new PushConfirmProcesser();
        processer.setNextChannel(this);
        processer.setNotifyMessage(notifyMessage);
        try{
            processer.process(mafContext);
        }
        catch (Exception e){
            LogUtil.e("MessageRouter", "Confirm Failed");
        }
    }
}
