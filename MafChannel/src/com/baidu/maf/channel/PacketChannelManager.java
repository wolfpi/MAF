package com.baidu.maf.channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hanxin on 2016/5/24.
 */
public class PacketChannelManager {
    private static PacketChannelManager messageChannelManager = new PacketChannelManager();
    private Map<EChannelId, PacketChannel> channelMap = new ConcurrentHashMap<EChannelId, PacketChannel>();

    static {
/*        PacketChannelManager.getInstance().registChannel(new MessageMicroChannel(EChannelId.PushConfirm));
        PacketChannelManager.getInstance().registChannel(new MessageMicroChannel(EChannelId.SetAppStatus));
        PacketChannelManager.getInstance().registChannel(new MessageMicroChannel(EChannelId.RegApp));*/
    }

    private PacketChannelManager() {
    }

    public static PacketChannelManager getInstance(){
        return messageChannelManager;
    }

    public void registChannel(PacketChannel channel){
        channelMap.put(channel.getChannelId(), channel);
    }

    public PacketChannel getChannelById(EChannelId id){
        return channelMap.get(id);
    }

    public MessageChannelInfo createChannelInfoById(EChannelId id){
        PacketChannel channel = getChannelById(id);
        if (null != channel){
            return channel.createChannelInfo();
        }
        else {
            return null;
        }
    }
}
