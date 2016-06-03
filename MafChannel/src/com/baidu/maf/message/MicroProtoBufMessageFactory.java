package com.baidu.maf.message;

import com.baidu.maf.util.PreferenceUtil;

/**
 * Created by hanxin on 2016/5/20.
 */
public class MicroProtoBufMessageFactory implements MessageFactory {
    private PreferenceUtil preferenceUtil = new PreferenceUtil();
    @Override
    public Message createMessage(Class<? extends Message> clazz) {
        try {
            if (UpPacketMessage.class == clazz){
                UpPacketMessage upPacketMessage = (UpPacketMessage)clazz.newInstance();
                upPacketMessage.setMessageID(preferenceUtil.getSeq());
            }
            else
                return clazz.newInstance();
        }
        catch (Exception e){
            return null;
        }
        return null;
    }
}
