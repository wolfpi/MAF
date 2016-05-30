package com.baidu.mafchannel.message;

import com.google.protobuf.micro.MessageMicro;

/**
 * Created by hanxin on 2016/5/19.
 */
public class MicroProtoBufReqMessage extends MicroProtoBufMessage implements RequestMessage{
    private String serviceName;
    private String methodName;

    public MicroProtoBufReqMessage(MessageMicro micro) {
        super(micro);
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }
}
