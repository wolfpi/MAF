package com.baidu.mafchannel.app;

import com.baidu.mafchannel.message.Message;

/**
 * Created by 欣 on 2016/5/31.
 */
public interface MessageHolder {
    Message getReqMessage();
    Message getRspMessage();
    int getId();
}
