package com.baidu.maf.processer;

import com.baidu.im.frame.pb.ProUserLogin;
import com.baidu.maf.channel.EChannelId;
import com.baidu.maf.channel.MessageChannelInfo;
import com.baidu.maf.channel.MessageMicroChannel;
import com.baidu.maf.channel.MessageProcesser;
import com.baidu.maf.listener.ClientStatusListener;
import com.baidu.maf.listener.UserStatus;
import com.baidu.maf.message.DownPacketMessage;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.MicroProtoBufReqMessage;
import com.baidu.maf.util.UserPreference;

/**
 * Created by hanxin on 2016/5/26.
 */
public class LoginProcesser extends MessageProcesser {
    private String userName;
    private String password;
    private ClientStatusListener listener;

    public LoginProcesser(String userName, String password) {
        super(EChannelId.Login);

        this.userName = userName;
        this.password = password;
    }

    @Override
    public int setChannelReqData(MessageChannelInfo info, Message requestMessage) {
        MicroProtoBufReqMessage reqMessage = (MicroProtoBufReqMessage)requestMessage;
        ProUserLogin.LoginReq req = (ProUserLogin.LoginReq)reqMessage.getMicro();
        req.setChannelKey(getMafContext().getChannelKey());
        req.setLoginName(userName);
        req.setToken(password);
        return 0;
    }

    @Override
    public int getChannelRspData(MessageChannelInfo info, Message responseMessage, int errcode, String errInfo) {
        MessageMicroChannel.MessageMicroChannelInfo channelInfo = (MessageMicroChannel.MessageMicroChannelInfo)info;
        if (errcode == 0){
            getMafContext().setAppId(channelInfo.getAppId());
            getUserPreference().saveUid(channelInfo.getUid());
            getUserPreference().saveSessionId(channelInfo.getSessionId());
            listener.onUserStatusChanged(UserStatus.ONLINE);
        }
        else {
            listener.onLoginError(errcode, errInfo);
        }
        return 0;
    }

    public void setListener(ClientStatusListener listener) {
        this.listener = listener;
    }
}
