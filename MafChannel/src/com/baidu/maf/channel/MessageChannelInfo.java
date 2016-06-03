package com.baidu.maf.channel;

import com.baidu.maf.message.Message;

/**
 * Created by hanxin on 2016/5/24.
 */
public interface MessageChannelInfo extends DataChannel{
    public Message getReqMessage();
    public Message getRspMessage();
    public EChannelId getChannelId();
    public void setChannelId(EChannelId channelId);
}
