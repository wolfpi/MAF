package com.baidu.mafchannel.processer;

import com.baidu.mafchannel.channel.EChannelId;
import com.baidu.mafchannel.channel.MessageChannelInfo;
import com.baidu.mafchannel.channel.MessageProcesser;
import com.baidu.mafchannel.message.NotifyMessage;

/**
 * Created by hanxin on 2016/5/23.
 */
public class PushConfirmProcesser extends MessageProcesser{
    private NotifyMessage notifyMessage;
    public PushConfirmProcesser() {
        super(EChannelId.PushConfirm);
    }

    @Override
    public int setChannelReqData(MessageChannelInfo info) {
        return 0;
    }

    @Override
    public int getChannelRspData(MessageChannelInfo info, int errcode, String errInfo) {
        return 0;
    }

    public void setNotifyMessage(NotifyMessage notifyMessage) {
        this.notifyMessage = notifyMessage;
    }


}
