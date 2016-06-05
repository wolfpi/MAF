package com.baidu.maf.network;

public interface IChannelChangeListener {

    void onChanged(NetChannelStatus networkChannelStatus);

    void onAvaliable(String channelKey);
}
