package com.baidu.maf.message;

import com.baidu.maf.com.Buffer;
import com.baidu.maf.network.NetChannelStatus;

/**
 * Created by hanxin on 2016/5/21.
 */
public class NetworkChangeMessage extends AppMessage{
    private NetChannelStatus status = NetChannelStatus.Closed;

    public NetChannelStatus getStatus() {
        return status;
    }

    public void setStatus(NetChannelStatus status) {
        this.status = status;
    }

    @Override
    public boolean serializeTo(Buffer buffer) {
        buffer.write(status.name().getBytes());
        return false;
    }

    @Override
    public boolean parseFrom(Buffer buffer) {
        status = NetChannelStatus.valueOf(new String(buffer.toArray()));
        return true;
    }

    @Override
    public MessageTypeEnum getMessageType() {
        return MessageTypeEnum.NETWORK_CHANGE;
    }

    public static NetworkChangeMessage parse(byte[] buffer){
        NetworkChangeMessage message = new NetworkChangeMessage();
        if (message.parseFrom(buffer)){
            return message;
        }
        else{
            return null;
        }
    }
}
