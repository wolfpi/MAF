package com.baidu.mafchannel.channel;

import com.baidu.mafchannel.message.Message;

/**
 * Created by hanxin on 2016/5/17.
 */
public interface DataChannel {
    /**
     * �������а�
     * @param upPacket
     */
    void send(Message upPacket) throws Exception;

    /**
     * �������а�
     * @param downPacket
     */
    void receive(Message downPacket) throws Exception;

    /**
     * ������һ��channel
     * @param callback
     */
    void setNextChannel(DataChannel callback);
}
