package com.baidu.mafchannel.app;

import com.baidu.mafchannel.com.MafContext;
import com.baidu.mafchannel.message.RequestMessage;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafUserChannelImpl extends MafChannelImpl implements MafUserChannel {

    public MafUserChannelImpl(MafContext context) {
        super(context);
    }

    @Override
    public void login(String userID, String userToken) {

    }

    @Override
    public void logout() {

    }

    @Override
    public void sendMessage(RequestMessage message, MafMessageListener listener) {

    }

    @Override
    public void enableNotification() {

    }

    @Override
    public void disableNotification() {

    }

    @Override
    public boolean isNotificationEnabled() {
        return false;
    }

    @Override
    public void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute) {

    }

    @Override
    public void receive(com.baidu.mafchannel.message.Message downPacket) throws Exception {
        super.receive(downPacket);
    }
}
