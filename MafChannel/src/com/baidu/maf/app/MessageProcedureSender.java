package com.baidu.maf.app;

/**
 * Created by 欣 on 2016/5/31.
 */
public interface MessageProcedureSender {
    void send(MafMessageProcedure procedure) throws Exception;
}
