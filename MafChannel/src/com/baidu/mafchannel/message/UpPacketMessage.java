package com.baidu.mafchannel.message;

import com.baidu.im.frame.pb.ObjBizUpPackage;
import com.baidu.im.frame.pb.ObjUpPacket;
import com.baidu.mafchannel.com.Buffer;
import com.google.protobuf.micro.ByteStringMicro;

/**
 * Created by hanxin on 2016/5/17.
 */
public class UpPacketMessage extends MicroProtoBufReqMessage {
    private long uid;
    private int appId;
    private int seq;
    private String serviceName;
    private String methodName;
    private boolean bSyspacket;
    private String sessionId;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public boolean isbSyspacket() {
        return bSyspacket;
    }

    public void setbSyspacket(boolean bSyspacket) {
        this.bSyspacket = bSyspacket;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String appKey = null;
    private Message message = null;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public UpPacketMessage(){
        super(new ObjUpPacket.UpPacket());
    }

    @Override
    public boolean serializeTo(Buffer buffer) {
        Buffer tmp = new Buffer();
        ObjUpPacket.UpPacket upPacket = (ObjUpPacket.UpPacket)getMicro();
        if (message.serializeTo(tmp)){
            ObjBizUpPackage.BizUpPackage bizUpPackage = new ObjBizUpPackage.BizUpPackage();
            upPacket.setBizPackage(bizUpPackage.setBusiData(ByteStringMicro.copyFrom(tmp.toArray())));
            return super.serializeTo(buffer);
        }
        return false;
    }

    @Override
    public boolean parseFrom(Buffer buffer) {
        if (super.parseFrom(buffer)){
            ObjUpPacket.UpPacket upPacket = (ObjUpPacket.UpPacket)getMicro();
            return message.parseFrom(Buffer.wrapReadableContent(upPacket.getBizPackage().getBusiData().toByteArray()));
        }
        return false;
    }

    public int getAppId(){
        ObjUpPacket.UpPacket upPacket = (ObjUpPacket.UpPacket)getMicro();
        return upPacket.getAppId();
    }

    public static UpPacketMessage parse(byte[] bytes){
        UpPacketMessage upPacketMessage = new UpPacketMessage();
        if (upPacketMessage.parseFrom(bytes)){
            return upPacketMessage;
        }
        else {
            return null;
        }
    }
}
