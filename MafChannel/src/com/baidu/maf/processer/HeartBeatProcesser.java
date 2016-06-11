package com.baidu.maf.processer;

import com.baidu.im.frame.pb.ProHeartbeat;
import com.baidu.im.frame.pb.ProUserLogin;
import com.baidu.maf.channel.EChannelId;
import com.baidu.maf.channel.MessageChannelInfo;
import com.baidu.maf.channel.MessageProcesser;
import com.baidu.maf.listener.ClientStatusListener;
import com.baidu.maf.listener.UserStatus;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.MicroProtoBufReqMessage;
import com.baidu.maf.util.LogUtil;

/**
 * Created by hanxin on 2016/5/26.
 */
public class HeartBeatProcesser extends MessageProcesser {
    private String sessionId;
    private String channelKey;
    private ClientStatusListener listener;

    public HeartBeatProcesser() {
        super(EChannelId.HeartBeat);
    }

    @Override
    public int setChannelReqData(MessageChannelInfo info, Message requestMessage) {
        MicroProtoBufReqMessage reqMessage = (MicroProtoBufReqMessage)requestMessage;
        ProHeartbeat.HeartbeatReq req = (ProHeartbeat.HeartbeatReq) reqMessage.getMicro();
        req.setChannelKey(channelKey);
        req.setBackground(false);
        ProHeartbeat.HeartbeatInfo heartbeatInfo = new ProHeartbeat.HeartbeatInfo();
        heartbeatInfo.setAppId(getMafContext().getAppId());
        heartbeatInfo.setBackground(false);
        heartbeatInfo.setSessionId(sessionId);
        req.addInfo(heartbeatInfo);
        return 0;
    }

    @Override
    public int getChannelRspData(MessageChannelInfo info, Message responseMessage, int errcode, String errInfo) {
        if (errcode != 0){
            if (getResendCount() < 3){
                try {
                    resend();
                }
                catch (Exception e){
                    LogUtil.e("HeartBeatProcesser", e.getMessage());
                }
            }
            else {

            }
        }
        else {
            listener.onUserStatusChanged(UserStatus.ONLINE);
            getMafContext().triggerResend();
        }
        return 0;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public void setListener(ClientStatusListener listener) {
        this.listener = listener;
    }
}
