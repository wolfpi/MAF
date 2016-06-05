package com.baidu.maf.channel;

import com.baidu.maf.message.Message;
import com.baidu.maf.message.RequestMessage;
import com.baidu.maf.message.ResponseMessage;

/**
 * Created by hanxin on 2016/5/23.
 */
public interface MessageListener {
    public  int setChannelReqData(MessageChannelInfo info, Message requestMessage);

    public  int getChannelRspData(MessageChannelInfo info, Message responseMessage, int errcode, String errInfo);
}
