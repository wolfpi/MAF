package com.baidu.mafchannel.app;

import com.baidu.mafchannel.message.Message;

/**
 * Created by hanxin on 2016/5/28.
 */
public interface MafMessageListener {
    void receive(Message message, int errcode, String errInfo);
}
