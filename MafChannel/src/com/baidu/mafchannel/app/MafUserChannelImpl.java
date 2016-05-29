package com.baidu.mafchannel.app;

import android.content.Context;

import com.baidu.mafchannel.app.MafUserChannel;
import com.baidu.mafchannel.channel.MessageChannel;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafUserChannelImpl extends MafChannelImpl implements MafUserChannel {

    public MafUserChannelImpl(Context context, String apiKey) {
        super(context, apiKey);
    }

    @Override
    public void login(String userID, String userToken) {

    }

    @Override
    public void logout() {

    }

    @Override
    public void sendMessage(com.baidu.mafchannel.message.Message message, MafMessageListener listener) {

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
