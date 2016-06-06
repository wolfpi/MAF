package com.baidu.maf.app;

import com.baidu.maf.com.MafContext;
import com.baidu.maf.message.UpPacketMessage;
import com.baidu.maf.processer.HeartBeatProcesser;
import com.baidu.maf.util.UserPreference;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafUserChannelImpl extends MafChannelImpl implements MafUserChannel {

    private MafContext mafContext;

    public MafUserChannelImpl(MafContext context) {
        super(context);
        this.mafContext = context;
    }

    private UserPreference userPreference = new UserPreference();

    @Override
    public void initialize() {
        super.initialize();

    }

    @Override
    public void login(String userID, String userToken) {
        userPreference.initialize(mafContext.getContext(), userID);
    }

    @Override
    public void logout() {

    }

    @Override
    protected UpPacketMessage makeUpPacket(MafMessageProcedure procedure) {
        UpPacketMessage upPacketMessage = super.makeUpPacket(procedure);
        upPacketMessage.setUid(userPreference.getUid());
        upPacketMessage.setSessionId(userPreference.getSessionId());
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
    public void receive(com.baidu.maf.message.Message downPacket) throws Exception {
        super.receive(downPacket);
    }

    @Override
    public void onAvaliable(String channelKey) {
        super.onAvaliable(channelKey);
        HeartBeatProcesser heartBeatProcesser = new HeartBeatProcesser();
    }
}
