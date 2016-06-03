package com.baidu.maf.message;

import com.baidu.im.frame.pb.ProPush;

import java.util.List;

/**
 * Created by hanxin on 2016/5/23.
 */
public class NotifyMessage extends MicroProtoBufMessage {
    private int appId = 0;

    public NotifyMessage() {
        super(new ProPush.PushMsgs());
    }

    public List<ProPush.PushOneMsg> getMessageList(){
        return ((ProPush.PushMsgs)getMicro()).getMsgsList();
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }
}
