package com.baidu.maf.util;

import com.baidu.im.sdk.IMessageCallback;

public interface IReloginCallback extends IMessageCallback {
    void reloginSuccess();

    void reloginFail(int errCode);
}
