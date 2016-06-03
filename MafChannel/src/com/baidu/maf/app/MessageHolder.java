package com.baidu.maf.app;

import com.baidu.maf.message.Message;

/**
 * Created by 欣 on 2016/5/31.
 */
public interface MessageHolder {
    Message getReqMessage();
    Message getRspMessage();
    int getId();
}
