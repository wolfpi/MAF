package com.baidu.maf.app;

import com.apkfuns.logutils.LogUtils;
import com.baidu.maf.channel.DataChannel;
import com.baidu.maf.channel.MessageChannel;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.NotifyMessage;
import com.baidu.maf.message.UpPacketMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 欣 on 2016/6/1.
 */
public class MessageSendBox extends MessageChannel implements SendBox{
    private MessageRouter messageRouter = null;
    private Map<Integer, ChannelMessage> messageMap = new ConcurrentHashMap<>();
    private boolean isEnableSend = false;
    private ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private MafContext mafContext = null;
    private MafChannelImpl mafChannel = null;

    public MessageSendBox(MafChannelImpl mafChannel) {
        this.mafChannel = mafChannel;
        messageRouter = new MessageRouter(mafChannel);
    }

    public void initialize(MafContext context){
        this.mafContext = context;
        mafContext.setSendBox(this);
        setNextChannel(messageRouter);
        messageRouter.initialize(context);
    }
    @Override
    public void receive(final Message downPacket) throws Exception {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                ChannelMessage channelMessage = messageMap.remove(downPacket.getMessageID());
                if (null != channelMessage){
                    try{
                        channelMessage.receive(downPacket);
                    }
                    catch (Exception e){
                        LogUtils.e("MessageSendBox", "exception:" + e.getMessage());
                    }
                }
                else {
                    if (downPacket instanceof NotifyMessage){
                        mafChannel.notify((NotifyMessage)downPacket);
                    }
                }
            }
        });
    }

    @Override
    public void send(Message upPacket) throws Exception {
        if (isEnableSend)
            super.send(upPacket);
    }

    public void  send(Message message, DataChannel channel) throws  Exception{
        messageRouter.send(message, channel);
    }

    public void send(MafMessageProcedure procedure, UpPacketMessage upPacketMessage) throws Exception{
        ListenChannel listenChannel = new ListenChannel(procedure, messageRouter);
        ChannelMessage channelMessage = new ChannelMessage(upPacketMessage, listenChannel, this);
        messageMap.put(upPacketMessage.getMessageID(), channelMessage);
        send(upPacketMessage);
    }

    public  void resend(){
        for (final Map.Entry<Integer, ChannelMessage> entry : messageMap.entrySet()){
            if (entry.getValue().canResend()){
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            entry.getValue().send();
                        }
                        catch (Exception e){
                            LogUtils.e("MessageSendBox", "Retry Failed");
                        }
                    }
                });
            }
        }
    }

    public void setEnableSend(boolean enableSend) {
        isEnableSend = enableSend;
    }
}
