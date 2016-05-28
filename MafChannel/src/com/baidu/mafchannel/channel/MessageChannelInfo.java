package com.baidu.mafchannel.channel;

import com.baidu.mafchannel.message.Message;

/**
 * Created by hanxin on 2016/5/24.
 */
public interface MessageChannelInfo extends DataChannel{
    public Message getReqMessage();
    public Message getRspMessage();
    public EChannelId getChannelId();
    public void setChannelId(EChannelId channelId);
}
