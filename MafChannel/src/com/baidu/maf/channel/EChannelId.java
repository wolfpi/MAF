package com.baidu.maf.channel;

import com.baidu.im.frame.pb.*;

/**
 * Created by hanxin on 2016/5/24.
 */
public enum EChannelId {
    RegApp("CoreSession", "RegApp", ProRegApp.RegAppReq.class, ProRegApp.RegAppRsp.class),
    PushConfirm("Resend", "PushConfirm", ProPushConfirm.PushMsgConfirmReq.class, ProPushConfirm.PushMsgConfirmRsp.class),
    SetAppStatus("CoreSession", "SetAppStatus", ProSetAppStatus.SetAppStatusReq.class, ProSetAppStatus.SetAppStatusRsp.class),
    Login("CoreSession", "Login", ProUserLogin.LoginReq.class, ProUserLogin.LoginResp.class),
    Logout("CoreSession", "Logout", ProUserLogout.LogoutReq.class, ProUserLogout.LogoutResp.class),
    HeartBeat("CoreSession", "Heartbeat", ProHeartbeat.HeartbeatReq.class, ProHeartbeat.HeartbeatResp.class);

    private String channelServiceName;
    private String channelMethodName;
    private Class<?> reqClazz;
    private Class<?> rspClazz;

    EChannelId(String channelServiceName, String channelMethodName, Class<?> reqClazz, Class<?> rspClazz) {
        this.channelServiceName = channelServiceName;
        this.channelMethodName = channelMethodName;
        this.reqClazz = reqClazz;
        this.rspClazz = rspClazz;
    }

    String getChannelServiceName(){
        return channelServiceName;
    }
    String getChannelMethodName(){
        return channelMethodName;
    }

    public Class<?> getReqClazz() {
        return reqClazz;
    }

    public Class<?> getRspClazz() {
        return rspClazz;
    }
}
