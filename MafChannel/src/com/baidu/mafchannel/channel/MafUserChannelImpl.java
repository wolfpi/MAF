package com.baidu.mafchannel.channel;

import com.baidu.mafchannel.app.MafUserChannel;
import com.baidu.mafchannel.rpc.RpcChannelDelegate;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafUserChannelImpl implements MafUserChannel {
    private RpcChannelDelegate rpcChannelDelegate = null;

    public RpcChannelDelegate getRpcChannelDelegate() {
        return rpcChannelDelegate;
    }

    public void setRpcChannelDelegate(RpcChannelDelegate rpcChannelDelegate) {
        this.rpcChannelDelegate = rpcChannelDelegate;
    }

    @Override
    public void login(String userID, String userToken) {

    }

    @Override
    public void logout() {

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
    public void callMethod(Descriptors.MethodDescriptor methodDescriptor, RpcController rpcController, Message message, Message message1, RpcCallback<Message> rpcCallback) {
        //todo check login state
        rpcChannelDelegate.callMethod(methodDescriptor, rpcController, message, message1, rpcCallback);
    }
}
