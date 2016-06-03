package com.baidu.maf.message;

import com.baidu.maf.com.Buffer;

/**
 * Created by hanxin on 2016/5/21.
 */
public class ComMessageFactory implements MessageFactory {
    @Override
    public Message createMessage(Class<? extends Message> clazz) {
        try {
            return clazz.newInstance();
        }
        catch (Exception e){
            return null;
        }
    }

    public static byte[] EncodeMessage(Message msg){
        Buffer buffer = new Buffer();
        if (msg.serializeTo(buffer)){
            return buffer.toArray();
        }
        return null;
    }
}
