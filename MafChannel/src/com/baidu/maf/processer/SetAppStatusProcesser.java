package com.baidu.maf.processer;

import com.baidu.im.frame.pb.EnumAppStatus;
import com.baidu.im.frame.pb.ProSetAppStatus;
import com.baidu.maf.channel.EChannelId;
import com.baidu.maf.channel.MessageChannelInfo;
import com.baidu.maf.channel.MessageMicroChannel;
import com.baidu.maf.channel.MessageProcesser;

/**
 * Created by hanxin on 2016/5/23.
 */
public class SetAppStatusProcesser extends MessageProcesser {
    private int appId;
    private String channelKey;


    public SetAppStatusProcesser() {
        super(EChannelId.SetAppStatus);
    }

    @Override
    public int setChannelReqData(MessageChannelInfo info) {
        MessageMicroChannel.MessageMicroChannelInfo channelInfo = (MessageMicroChannel.MessageMicroChannelInfo)info;
        ProSetAppStatus.SetAppStatusReq req = channelInfo.getReq();
        req.addAppIds(appId);
        req.setChannelKey(channelKey);
        req.setStatus(EnumAppStatus.APP_OFFLINE);
        return 0;
    }

    @Override
    public int getChannelRspData(MessageChannelInfo info, int errcode, String errInfo) {
        MessageMicroChannel.MessageMicroChannelInfo channelInfo = (MessageMicroChannel.MessageMicroChannelInfo)info;

        return 0;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }
}
