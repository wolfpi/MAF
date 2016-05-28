package com.baidu.mafchannel.app;

import com.baidu.mafchannel.message.NotifyMessage;

/**
 * Created by hanxin on 2016/5/28.
 */
public interface MafNotifyListener {
    void receive(NotifyMessage notifyMessage);
}
