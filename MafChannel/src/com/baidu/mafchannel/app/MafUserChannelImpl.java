package com.baidu.mafchannel.app;

import com.baidu.mafchannel.com.MafContext;
import com.baidu.mafchannel.message.RequestMessage;
import com.baidu.mafchannel.message.UpPacketMessage;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafUserChannelImpl extends MafChannelImpl implements MafUserChannel {

    public MafUserChannelImpl(MafContext context) {
        super(context);
    }

    private long uid = 0;
    private String sessionId = null;

    @Override
    public void login(String userID, String userToken) {

    }

    @Override
    public void logout() {

    }

    @Override
    protected UpPacketMessage makeUpPacket(MafMessageProcedure procedure) {
        UpPacketMessage upPacketMessage = super.makeUpPacket(procedure);
        upPacketMessage.setUid(uid);
        upPacketMessage.setSessionId(sessionId);
        return upPacketMessage;
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
