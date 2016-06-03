package com.baidu.maf.util;

import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

public class BizCodeProcessUtil {

    public static ProcessorCode procProcessorCode(String processorName, DownPacket downPacket) {

        if (processorName == null)
            processorName = "";

        if (downPacket != null) {
            // Return channelCode immediately if not success.
            ChannelCode channelCode = ChannelCode.parse(downPacket.getChannelCode());
            ProcessorCode channelResult = procChannelCode(channelCode);
            if (channelResult != ProcessorCode.SUCCESS) {
                LogUtil.printMainProcess(processorName, channelResult.getMsg());
                return channelResult;
            }

            if (downPacket.getBizPackage() != null) {
                ProcessorCode bizResult = procBizCode(downPacket.getBizPackage().getBizCode());
                LogUtil.printMainProcess(processorName, bizResult.getMsg());
                return bizResult;
            }
        } else {
            LogUtil.printMainProcess(processorName + " null downPacket or null bizPacket",
                    ProcessorCode.UNKNOWN_ERROR.getMsg());
        }
        return ProcessorCode.UNKNOWN_ERROR;
    }

    private static ProcessorCode procBizCode(int code) {
        BizCode bizCode = BizCode.parse(code);
        switch (bizCode) {
            case SESSION_SUCCESS:
                return ProcessorCode.SUCCESS;
            case SESSION_PARAM_ERROR:
            case SESSION_CLIENT_ERROR_4:
            case SESSION_CLIENT_ERROR_5:
            case SESSION_CLIENT_ERROR_6:
            case SESSION_CLIENT_ERROR_7:
            case SESSION_CLIENT_ERROR_8:
            case SESSION_CLIENT_ERROR_9:
                return ProcessorCode.PARAM_ERROR;
            case SESSION_TOKEN_NOT_EXIST:
                return ProcessorCode.TOKEN_ERROR;
            case SESSION_INVALID_APIKEY_SECRET_KEY:
                return ProcessorCode.INVALID_APIKEY_SECRET_KEY;
            case SESSION_APP_NOT_EXIST:
                return ProcessorCode.UNREGISTERED_APP;
            case SESSION_SESSION_NOT_EXIST:
                // TODO re connect
                return ProcessorCode.SESSION_ERROR;
            case SESSION_STATUS_ERROR1:
            case SESSION_STATUS_ERROR2:
            case SESSION_STATUS_ERROR3:
            case SESSION_STATUS_ERROR4:
            case SESSION_STATUS_ERROR5:
            case SESSION_STATUS_ERROR6:
            case SESSION_STATUS_ERROR7:
            case SESSION_STATUS_ERROR8:
            case SESSION_STATUS_ERROR9:
                return ProcessorCode.SESSION_OTHER_ERROR;
            case SESSION_UNKNOWN_SERVER_ERROR:
            case SESSION_UNKNOWN_SERVER_ERROR1:
            case SESSION_UNKNOWN_SERVER_ERROR2:
            case SESSION_UNKNOWN_SERVER_ERROR3:
            case SESSION_UNKNOWN_SERVER_ERROR4:
            case SESSION_UNKNOWN_SERVER_ERROR5:
            case SESSION_UNKNOWN_SERVER_ERROR6:
            case SESSION_UNKNOWN_SERVER_ERROR7:
            case SESSION_UNKNOWN_SERVER_ERROR8:
            case SESSION_UNKNOWN_SERVER_ERROR9:
                return ProcessorCode.SERVER_ERROR;
            case CONFIG_PARAM_ERROR:
            case CONFIG_JSON_PARSE_ERROR:
            case CONFIG_NO_PLATFORMTYPE:
                return ProcessorCode.CONFIG_UPDATE_ERROR;
            default:
                return ProcessorCode.UNKNOWN_ERROR;
        }
    }

    public static ProcessorCode procChannelCode(ChannelCode code) {

        if (code == null)
            return ProcessorCode.CHANNEL_SERVER_ERROR;

        switch (code) {
            case CHANNEL_AUTHENTICATION_ERROR:
                return ProcessorCode.SESSION_ERROR;
            case CHANNEL_DISPATCH_ERROR:
                return ProcessorCode.CHANNEL_DISPATCH_ERROR;
            case CHANNEL_SUCCESS:
                return ProcessorCode.SUCCESS;
            default:
                return ProcessorCode.CHANNEL_SERVER_ERROR;
        }
    }

    public static int processBizCode(int code) {
        return procBizCode(code).getCode();
    }

    public enum ChannelCode {

        CHANNEL_SUCCESS(200, "Channel success"),

        CHANNEL_AUTHENTICATION_ERROR(400, "Authentication error."),

        CHANNEL_DISPATCH_ERROR(520, "Wrong service name or method name."),

        // unknown error
        CHANNEL_UNKNOWN_ERROR(-1, "CHANNEL_UNKNOWN_ERROR");

        private int code;
        private String msg;

        ChannelCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static ChannelCode parse(int code) {
            ChannelCode[] channelCodes = ChannelCode.values();
            if (null != channelCodes) {
                for (ChannelCode channelCode : channelCodes) {
                    if (channelCode.getCode() == code) {
                        return channelCode;
                    }
                }
            }
            return ChannelCode.CHANNEL_UNKNOWN_ERROR;
        }
    }
}

