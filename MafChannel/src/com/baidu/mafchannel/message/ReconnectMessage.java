package com.baidu.mafchannel.message;

import com.baidu.mafchannel.com.Buffer;

/**
 * Created by hanxin on 2016/5/21.
 */
public class ReconnectMessage extends AppMessage {
    @Override
    public boolean serializeTo(Buffer buffer) {
        return true;
    }

    @Override
    public boolean parseFrom(Buffer buffer) {
        return true;
    }

    @Override
    public MessageTypeEnum getMessageType() {
        return MessageTypeEnum.Reconnect;
    }
}
