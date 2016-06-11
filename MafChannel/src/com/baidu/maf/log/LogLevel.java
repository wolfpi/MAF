package com.baidu.maf.log;

public enum LogLevel {
    // include ERROR
    ERROR(16, "error", "E"),
    // include ERROR, PROTOCOL
    PROTOCOL(8, "protocol", "W"),
    // include ERROR, PROTOCOL, MAINPROGRESS
    MAINPROGRESS(4, "mainprogress", "I"),
    // include ERROR, PROTOCOL, MAINPROGRESS, DEBUG
    DEBUG(2, "debug", "D"),
    // include ERROR, WARN
    WARN(8, "warn", "W"),
    // include ERROR, WARN, INFO
    INFO(4, "info", "I");

    private int level;

    private String name;

    private String tag;

    LogLevel(int level, String name, String tag) {
        this.level = level;
        this.name = name;
        this.tag = tag;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

}
