package com.baidu.maf.processer;

import com.baidu.maf.channel.EChannelId;
import com.baidu.maf.channel.MessageChannelInfo;
import com.baidu.maf.channel.MessageProcesser;
import com.baidu.maf.message.Message;

/**
 * Created by hanxin on 2016/5/26.
 */
public class LogoutProcesser extends MessageProcesser {
    public LogoutProcesser() {
        super(EChannelId.Logout);
    }

    @Override
    public int setChannelReqData(MessageChannelInfo info, Message requestMessage) {
        return 0;
    }

    @Override
    public int getChannelRspData(MessageChannelInfo info, Message responseMessage, int errcode, String errInfo) {
        return 0;
    }
}
