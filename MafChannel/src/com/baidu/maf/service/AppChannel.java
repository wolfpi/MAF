package com.baidu.maf.service;

import android.os.Bundle;
import android.os.Messenger;
import com.baidu.maf.channel.DataChannel;
import com.baidu.maf.com.Buffer;
import com.baidu.maf.message.Message;
import com.baidu.maf.util.LogUtil;

/**
 * Created by hanxin on 2016/5/20.
 */
public class AppChannel implements DataChannel {
    private int appId = 0;
    private String appKey = null;
    private Messenger clientMsger = null;
    private Messenger serverMsger = null;
    private DataChannel sendChannel = null;
    private boolean bEnable = true;

    public void setClientMsger(Messenger clientMsger) {
        this.clientMsger = clientMsger;
    }

    public void setServerMsger(Messenger serverMsger) {
        this.serverMsger = serverMsger;
    }

    public boolean isbEnable() {
        return bEnable;
    }

    public void setbEnable(boolean bEnable) {
        this.bEnable = bEnable;
    }

    public AppChannel(String appKey) {
        this.appKey = appKey;
    }

    public int getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }

    @Override
    public void send(Message upPacket) throws Exception {
        sendChannel.send(upPacket);
    }

    @Override
    public void receive(Message downPacket) throws Exception{
        android.os.Message message = android.os.Message.obtain();

        Bundle bundle = new Bundle();
        if (downPacket != null) {
            Buffer buffer = new Buffer();
            downPacket.serializeTo(buffer);
            // Send normal msg
            bundle.putByteArray(downPacket.getMessageType().getType(), buffer.toArray());
        }
        message.setData(bundle);
        // LogUtil.printMainProcess(TAG, "sendMessage: ppId = " + appId);
        clientMsger.send(message);
        // LogUtil.printMainProcess(TAG, "sendMessage: first  5 AppId = " + appId);
        LogUtil.d("AppChannel", "sendMessage: messenger = " + clientMsger.hashCode());
    }

    @Override
    public void setNextChannel(DataChannel callback) {
        sendChannel = callback;
    }
}
