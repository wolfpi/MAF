package com.baidu.maf.processer;

import com.baidu.im.frame.pb.ProUserLogin;
import com.baidu.im.frame.pb.ProUserLogout;
import com.baidu.maf.channel.EChannelId;
import com.baidu.maf.channel.MessageChannelInfo;
import com.baidu.maf.channel.MessageProcesser;
import com.baidu.maf.listener.ClientStatusListener;
import com.baidu.maf.listener.UserStatus;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.MicroProtoBufReqMessage;

/**
 * Created by hanxin on 2016/5/26.
 */
public class LogoutProcesser extends MessageProcesser {
    private ClientStatusListener listener;

    public LogoutProcesser() {
        super(EChannelId.Logout);
    }

    @Override
    public int setChannelReqData(MessageChannelInfo info, Message requestMessage) {
        MicroProtoBufReqMessage reqMessage = (MicroProtoBufReqMessage)requestMessage;
        ProUserLogout.LogoutReq req = (ProUserLogout.LogoutReq) reqMessage.getMicro();
        getUserPreference().reset();
        return 0;
    }

    @Override
    public int getChannelRspData(MessageChannelInfo info, Message responseMessage, int errcode, String errInfo) {
        listener.onUserStatusChanged(UserStatus.LOGOUT);
        return 0;
    }

    public ClientStatusListener getListener() {
        return listener;
    }

    public void setListener(ClientStatusListener listener) {
        this.listener = listener;
    }
}
