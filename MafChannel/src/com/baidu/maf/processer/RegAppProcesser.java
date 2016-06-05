package com.baidu.maf.processer;

import com.baidu.im.frame.pb.ObjDeviceInfo;
import com.baidu.im.frame.pb.ObjDeviceTypeInfo;
import com.baidu.im.frame.pb.ProRegApp;
import com.baidu.maf.channel.EChannelId;
import com.baidu.maf.channel.MessageChannelInfo;
import com.baidu.maf.channel.MessageProcesser;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.message.Message;
import com.baidu.maf.message.MicroProtoBufReqMessage;
import com.baidu.maf.util.DeviceInfoMapUtil;
import com.baidu.maf.util.DeviceInfoUtil;
import com.baidu.maf.util.LogUtil;
import com.baidu.maf.util.MD5Util;
import com.baidu.maf.util.SignatureUtil;

/**
 * Created by hanxin on 2016/5/26.
 */
public class RegAppProcesser extends MessageProcesser {
    private String apiKey = null;

    public RegAppProcesser(String apiKey) {
        super(EChannelId.RegApp);
        this.apiKey = apiKey;
    }

    @Override
    public int setChannelReqData(MessageChannelInfo info, Message requestMessage) {
        MicroProtoBufReqMessage reqMessage = (MicroProtoBufReqMessage)requestMessage;
        ProRegApp.RegAppReq req = (ProRegApp.RegAppReq)reqMessage.getMicro();
        req.setApiKey(apiKey);
        ObjDeviceInfo.DeviceInfo deviceInfo = new ObjDeviceInfo.DeviceInfo();
        DeviceInfoMapUtil.getDeviceInfo(getMafContext().getContext(), deviceInfo);
        req.setDeviceInfo(deviceInfo);
        ObjDeviceTypeInfo.DeviceTypeInfo deviceTypeInfo = new ObjDeviceTypeInfo.DeviceTypeInfo();
        DeviceInfoMapUtil.getDeviceTypeInfo(getMafContext().getContext(), deviceTypeInfo);
        req.setDeviceTypeInfo(deviceTypeInfo);
        String secureKey = SignatureUtil.getSecureKey(getMafContext().getContext());
        LogUtil.printMainProcess("get secure key = " + secureKey);
        secureKey = MD5Util.getMD5(secureKey);
        LogUtil.printMainProcess("get MD5(secure key) = " + secureKey);
        req.setChannelKey();
        req.setSign(secureKey);
        return 0;
    }

    @Override
    public int getChannelRspData(MessageChannelInfo info, Message responseMessage, int errcode, String errInfo) {
        return 0;
    }
}
