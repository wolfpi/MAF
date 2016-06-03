package com.baidu.maf.app;

import com.baidu.maf.message.RequestMessage;
import com.baidu.maf.message.ResponseMessage;

/**
 * Created by 欣 on 2016/5/31.
 */
public interface MafMessageProcedure {
    RequestMessage getRequest();
    ResponseMessage getResponse();
    void setRequestData(RequestMessage requestData);
    void getResponseData(ResponseMessage responseMessage, int errCode, String errInfo);
    boolean canRetry();
}
