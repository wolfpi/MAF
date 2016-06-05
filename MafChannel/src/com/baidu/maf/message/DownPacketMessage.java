package com.baidu.maf.message;

import com.baidu.im.frame.pb.ObjDownPacket;
import com.baidu.maf.app.ProcessorCode;

/**
 * Created by hanxin on 2016/5/17.
 */
public class DownPacketMessage extends MicroProtoBufRspMessage {

    public DownPacketMessage() {
        super(new ObjDownPacket.DownPacket());
    }

    @Override
    public int getErrcode() {
        ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
        return downPacket.getChannelCode();
    }

    @Override
    public int getMessageID() {
        ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
        return downPacket.getSeq();
    }

    public int getAppId(){
        ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
        return downPacket.getAppId();
    }

    public int getPacketType(){
        ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
        return downPacket.getBizPackage().getPacketType();
    }

    public ProcessorCode getChannelCode(){
        ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
        return ProcessorCode.parse(downPacket.getChannelCode());
    }

    public int getBusiCode(){
        ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
        return downPacket.getBizPackage().getBizCode();
    }

    public byte[] getBusiData(){
        ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
        return downPacket.getBizPackage().getBizData().toByteArray();
    }

    public Message parseMessage(Message rspMessage){
        ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
        if (rspMessage.parseFrom(downPacket.getBizPackage().getBizData().toByteArray())){
            return rspMessage;
        }
        else {
            return null;
        }
    }

    public static DownPacketMessage parse(byte[] bytes){
        DownPacketMessage downPacketMessage = new DownPacketMessage();
        if (downPacketMessage.parseFrom(bytes)){
            return downPacketMessage;
        }
        else {
            return null;
        }
    }
/*    @Override
    public boolean serializeTo(Buffer buffer) {
        Buffer tmp = new Buffer();
        ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
        if (encode(tmp)){
            ObjBizDownPacket.BizDownPackage bizDownPackage = new ObjBizDownPacket.BizDownPackage();
            downPacket.setBizPackage(bizDownPackage.setBizData(ByteStringMicro.copyFrom(tmp.toArray())));
            return super.serializeTo(buffer);
        }
        return false;
    }

    @Override
    public boolean parseFrom(Buffer buffer) {
        if (super.parseFrom(buffer)){
            ObjDownPacket.DownPacket downPacket = (ObjDownPacket.DownPacket)getMicro();
            return decode(Buffer.wrapReadableContent(downPacket.getBizPackage().getBizData().toByteArray()));
        }
        return false;
    }*/

}
