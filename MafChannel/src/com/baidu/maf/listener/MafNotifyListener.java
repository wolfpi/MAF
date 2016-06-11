package com.baidu.maf.listener;

import com.baidu.maf.message.NotifyMessage;

/**
 * Created by hanxin on 2016/5/28.
 */
public interface MafNotifyListener {
    void receive(NotifyMessage notifyMessage);
}
