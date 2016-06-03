package com.baidu.maf.channel;

import com.baidu.maf.message.Message;

/**
 * Created by hanxin on 2016/5/17.
 */
public interface DataChannel {
    /**
     * 发送上行包
     * @param upPacket
     */
    void send(Message upPacket) throws Exception;

    /**
     * 接收下行包
     * @param downPacket
     */
    void receive(Message downPacket) throws Exception;

    /**
     * 设置下一个channel
     * @param callback
     */
    void setNextChannel(DataChannel callback);
}
