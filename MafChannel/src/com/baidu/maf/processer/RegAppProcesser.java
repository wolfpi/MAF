package com.baidu.maf.processer;

import com.baidu.maf.channel.EChannelId;
import com.baidu.maf.channel.MessageChannelInfo;
import com.baidu.maf.channel.MessageProcesser;

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
