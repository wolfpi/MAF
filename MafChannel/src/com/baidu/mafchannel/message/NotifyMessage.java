package com.baidu.mafchannel.message;

import com.baidu.im.frame.pb.ProPush;

/**
 * Created by hanxin on 2016/5/23.
 */
public class NotifyMessage extends MicroProtoBufMessage {
    public NotifyMessage() {
        super(new ProPush.PushMsgs());
    }
}
