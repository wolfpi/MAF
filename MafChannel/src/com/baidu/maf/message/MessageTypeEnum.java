package com.baidu.maf.message;

/**
 * Created by hanxin on 2016/5/20.
 */
public enum MessageTypeEnum {
    NORMAL("0"),

    NETWORK_CHANGE("1"),

    REGCHANNEL("2"),

    SYSTEM("9"),

    APPKEY("1000"),
    APPID("1001"),

    ALIVE("1002"),
    RECONNECT("1003"),
    SEQFETCH("1004"),
    CHECKOFFLINE("1005");

    String type;

    private MessageTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
