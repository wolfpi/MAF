package com.baidu.mafchannel.message;

import com.baidu.mafchannel.com.Buffer;

/**
 * Created by hanxin on 2016/5/21.
 */
public class RegChannelMessage extends AppMessage{
    private String channelData;

    public String getChannelData() {
        return channelData;
    }

    public void setChannelData(String channelData) {
        this.channelData = channelData;
    }

    @Override
    public boolean serializeTo(Buffer buffer) {
        buffer.write(channelData.getBytes());
        return true;
    }

    @Override
    public boolean parseFrom(Buffer buffer) {
        channelData = new String(buffer.toArray());
        return true;
    }

    @Override
    public MessageTypeEnum getMessageType() {
        return MessageTypeEnum.REGCHANNEL;
    }

    public static RegChannelMessage parse(byte[] buffer){
        RegChannelMessage message = new RegChannelMessage();
        if (message.parseFrom(buffer)){
            return message;
        }
        else {
            return null;
        }
    }
}
