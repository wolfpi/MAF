package com.baidu.mafchannel.app;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;
import com.baidu.mafchannel.channel.DispChannel;
import com.baidu.mafchannel.message.Message;
import com.baidu.mafchannel.util.PreferenceUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 欣 on 2016/5/29.
 */
public class MessageRouter extends DispChannel {
    private MafChannelImpl mafChannel = null;
    private AppBindChannel bindChannel = new AppBindChannel();

    public MessageRouter(MafChannelImpl mafChannel) {
        this.mafChannel = mafChannel;
    }

    public void initialize(Context context, PreferenceUtil preferenceUtil){
        bindChannel.setNextChannel(this);
        bindChannel.initialize(context, preferenceUtil);
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
