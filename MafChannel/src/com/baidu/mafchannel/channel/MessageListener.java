package com.baidu.mafchannel.channel;

/**
 * Created by hanxin on 2016/5/23.
 */
public interface MessageListener {
    public  int setChannelReqData(MessageChannelInfo info);

    public  int getChannelRspData(MessageChannelInfo info, int errcode, String errInfo);
}
