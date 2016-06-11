package com.baidu.maf.processer;

import android.content.Intent;

import com.baidu.im.frame.pb.ObjInformMessage;
import com.baidu.im.frame.pb.ProPush;
import com.baidu.im.frame.pb.ProPushConfirm;
import com.baidu.maf.channel.EChannelId;
import com.baidu.maf.channel.MessageChannelInfo;
import com.baidu.maf.channel.MessageProcesser;
import com.baidu.maf.com.Constant;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.MicroProtoBufReqMessage;
import com.baidu.maf.message.NotifyMessage;
import com.baidu.maf.util.LogUtil;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

/**
 * Created by hanxin on 2016/5/23.
 */
public class PushConfirmProcesser extends MessageProcesser{
    private NotifyMessage notifyMessage;
    public PushConfirmProcesser() {
        super(EChannelId.PushConfirm);
    }

    @Override
    public int setChannelReqData(MessageChannelInfo info, Message requestMessage) {

        MicroProtoBufReqMessage reqMessage = (MicroProtoBufReqMessage)requestMessage;
        ProPushConfirm.PushMsgConfirmReq pushMsgConfirmReqBuilder = (ProPushConfirm.PushMsgConfirmReq)reqMessage.getMicro();  // migrate from builder
        for (ProPush.PushOneMsg pushOneMsg : notifyMessage.getMessageList()) {
            if (pushOneMsg.getOfflineMsg() != null) {
                LogUtil.i("PushConfirmProcesser", "receive a offline message.  messageId=" + pushOneMsg.getMsgId());

                if (pushOneMsg.getConfirmMode() == ProPush.ALWAYS_YES) {
                    ProPushConfirm.PushMsgStatus pushMsgStatusBuilder = new ProPushConfirm.PushMsgStatus();  // migrate from builder
                    pushMsgStatusBuilder.setAckId(pushOneMsg.getAckId());
                    pushMsgStatusBuilder.setOfflineStatus(ProPushConfirm.STATUS_SUCCESS);
                    pushMsgConfirmReqBuilder.addMsgStatus(pushMsgStatusBuilder);
                }

                try {
                   /* if (NotificationUtil.isNotificationEnable()
                            &&*/
                    {

                        ObjInformMessage.InformMessage informMessage =
                                ObjInformMessage.InformMessage.parseFrom(pushOneMsg.getOfflineMsg().toByteArray());


                        Intent intent = new Intent();
                        intent.setAction(Constant.sdkPushAction);
                        intent.putExtra(Constant.sdkBDAppId, notifyMessage.getAppId());
                        intent.putExtra(Constant.sdkBDData, pushOneMsg.getOfflineMsg().toByteArray());


                        getMafContext().getContext().sendBroadcast(intent);

                        LogUtil.i("PushConfirmProcesser", "broadCast send ok");

                        //NotificationUtil.showNormal(OutAppApplication.getInstance().getContext(), informMessage);
                    }
                } catch (InvalidProtocolBufferMicroException e) {
                    LogUtil.printError(e);
                }

            }else {
                LogUtil.printMainProcess("PushConfirmProcesser" + ": warning, OfflineMsg should not be null.");
            }

        }
        return 0;
    }

    @Override
    public int getChannelRspData(MessageChannelInfo info, Message responseMessage, int errcode, String errInfo) {
        return 0;
    }

    public void setNotifyMessage(NotifyMessage notifyMessage) {
        this.notifyMessage = notifyMessage;
    }


}
