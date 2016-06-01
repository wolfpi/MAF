package com.baidu.mafchannel.app;

import com.baidu.mafchannel.channel.DataChannel;
import com.baidu.mafchannel.channel.DispChannel;
import com.baidu.mafchannel.com.MafContext;
import com.baidu.mafchannel.message.DownPacketMessage;
import com.baidu.mafchannel.message.Message;
import com.baidu.mafchannel.message.UpPacketMessage;
import com.baidu.mafchannel.util.PreferenceUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        this.mafContext = context;
    }

    @Override
    public void receive(Message downPacket) throws Exception {
        super.receive(downPacket);
    }

    @Override
    public void receive(Message downPacket, DataChannel channel) throws Exception {
        if (null != channel){
            ListenChannel listenChannel = (ListenChannel)channel;
            DownPacketMessage downPacketMessage = (DownPacketMessage)downPacket;
            if (listenChannel.getRspMessage().parseFrom(downPacketMessage.getBusiData())){
                listenChannel.setErrcode(downPacketMessage.getBusiCode());
                listenChannel.receive(listenChannel.getRspMessage());
            }
        }
        else {
            //找不到channel
        }
    }

    @Override
    public void send(Message upPacket, DataChannel channel) throws Exception {
        super.send(upPacket, channel);
    }
}
