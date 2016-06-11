package com.baidu.maf.app;

import android.text.TextUtils;

import com.baidu.maf.com.MafContext;
import com.baidu.maf.listener.ClientStatusListener;
import com.baidu.maf.message.UpPacketMessage;
import com.baidu.maf.processer.HeartBeatProcesser;
import com.baidu.maf.processer.LoginProcesser;
import com.baidu.maf.processer.LogoutProcesser;
import com.baidu.maf.util.LogUtil;
import com.baidu.maf.util.UserPreference;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafUserChannelImpl extends MafChannelImpl implements MafUserChannel {

    private MafContext mafContext;
    private ClientStatusListener listener = null;

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
    public synchronized void login(String userID, String userToken) {
        userPreference.initialize(mafContext.getContext(), userID);
        if (TextUtils.isEmpty(userPreference.getSessionId())){
            LoginProcesser loginProcesser = new LoginProcesser(userID, userToken);
            loginProcesser.setListener(listener);
            loginProcesser.setUserPreference(userPreference);
            try {
                loginProcesser.process(mafContext);
            }
            catch (Exception e){
                LogUtil.e("MafUserChannelImpl", "login failed" + e.getMessage());
            }
        }
        else {
            HeartBeatProcesser heartBeatProcesser = new HeartBeatProcesser();
            heartBeatProcesser.setChannelKey(mafContext.getChannelKey());
            heartBeatProcesser.setSessionId(userPreference.getSessionId());
            heartBeatProcesser.setListener(listener);
            heartBeatProcesser.setUserPreference(userPreference);
            try{
                heartBeatProcesser.process(mafContext);
            }
            catch (Exception e){

            }
        }
    }

    @Override
    public void logout() {
        LogoutProcesser logoutProcesser = new LogoutProcesser();
        logoutProcesser.setUserPreference(userPreference);
        logoutProcesser.setListener(listener);
        try {
            logoutProcesser.process(mafContext);
        }
        catch (Exception e){
            LogUtil.e("MafUserChannelImpl", "logout error:" + e.getMessage());
        }
    }

    @Override
    public void setClientStatusListener(ClientStatusListener listener) {
        this.listener = listener;
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
        if (!TextUtils.isEmpty(userPreference.getSessionId())){
            HeartBeatProcesser heartBeatProcesser = new HeartBeatProcesser();
            heartBeatProcesser.setSessionId(userPreference.getSessionId());
            heartBeatProcesser.setChannelKey(channelKey);
            heartBeatProcesser.setListener(listener);
            heartBeatProcesser.setUserPreference(userPreference);
            try{
                heartBeatProcesser.process(mafContext);
            }
            catch (Exception e){
                LogUtil.e("MafUserChannelImpl", e.getMessage());
            }
        }
    }
}
