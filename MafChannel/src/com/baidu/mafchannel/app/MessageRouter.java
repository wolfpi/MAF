package com.baidu.mafchannel.app;

import com.baidu.mafchannel.channel.DispChannel;
import com.baidu.mafchannel.com.MafContext;
import com.baidu.mafchannel.message.Message;
import com.baidu.mafchannel.util.PreferenceUtil;

/**
 * Created by 欣 on 2016/5/29.
 */
public class MessageRouter extends DispChannel {
    private MafChannelImpl mafChannel = null;
    private AppBindChannel bindChannel = new AppBindChannel();

    public MessageRouter(MafChannelImpl mafChannel) {
        this.mafChannel = mafChannel;
    }

    public void initialize(MafContext context){
        bindChannel.setNextChannel(this);
        bindChannel.initialize(context);
    }

    @Override
    public void receive(final Message downPacket) throws Exception {
        super.receive(downPacket);
    }

    public void sendMessage(Message message, MafMessageListener listener) throws Exception{
        ListenChannel listenChannel = new ListenChannel(listener, this);
        send(message, listenChannel);
    }

    @Override
    public void receiveUnknownIdMessage(Message message) {

    }
}
