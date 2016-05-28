package com.baidu.mafchannel.processer;

import com.baidu.mafchannel.channel.EChannelId;
import com.baidu.mafchannel.channel.MessageChannelInfo;
import com.baidu.mafchannel.channel.MessageProcesser;

/**
 * Created by hanxin on 2016/5/26.
 */
public class RegAppProcesser extends MessageProcesser {
    public RegAppProcesser() {
        super(EChannelId.RegApp);
    }

    @Override
    public int setChannelReqData(MessageChannelInfo info) {
        return 0;
    }

    @Override
    public int getChannelRspData(MessageChannelInfo info, int errcode, String errInfo) {
        return 0;
    }
}
