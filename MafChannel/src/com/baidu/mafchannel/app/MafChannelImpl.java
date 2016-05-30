package com.baidu.mafchannel.app;

import android.content.Context;
import com.baidu.mafchannel.channel.MessageChannel;
import com.baidu.mafchannel.message.Message;
import com.baidu.mafchannel.network.INetworkChangeListener;
import com.baidu.mafchannel.network.NetChannelStatus;
import com.baidu.mafchannel.util.PreferenceUtil;

/**
 * Created by 欣 on 2016/5/29.
 */
public class MafChannelImpl extends MessageChannel implements MafChannel, INetworkChangeListener {
    private Context context = null;
    private String apiKey = null;
    private MessageRouter messageRouter = new MessageRouter(this);

    public MafChannelImpl(Context context, String apiKey) {
        this.context = context;
        this.apiKey = apiKey;
    }

    public void initialize(PreferenceUtil preferenceUtil){
        messageRouter.initialize(context, preferenceUtil);
    }

    @Override
    public void enableNotification() {

    }

    @Override
    public void disableNotification() {

    }

    @Override
    public boolean isNotificationEnabled() {
        return false;
    }

    @Override
    public void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute) {

    }

    @Override
    public void setNotifyListener(MafNotifyListener notifyListener) {

    }

    @Override
    public void receive(Message downPacket) throws Exception {
    }

    @Override
    public void onChanged(NetChannelStatus networkChannelStatus) {

    }
}
