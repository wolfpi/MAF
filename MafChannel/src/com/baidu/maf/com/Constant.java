package com.baidu.maf.com;

import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 欣 on 2016/6/3.
 */
public class Constant {

    public static final boolean DEBUG = true;

    public static BuildMode buildMode = BuildMode.Online;

    public static final String SDK_ROOT_PATH = "/baidu/maf";

    public static final String sdkExternalDir = Environment.getExternalStorageDirectory() + "/baidu/maf";

    public static final String sdkExternalLogoDir = sdkExternalDir + "/logo";

    public static final String sdkExternalLogDir = SDK_ROOT_PATH + "/log/";

    public static final int seqSize = 10000;

    public static final String  sdkPushAction = "com.baidu.maf.channel.push";

    public static final String  sdkBDAppId = "appid";

    public static final String  sdkBDData = "infodata";


    public enum BuildMode {
        Online("https://passport.baidu.com", "PRODUCT"),

        QA("https://passport.qatest.baidu.com", "DEVELOP"),

        RD("https://passport.rdtest.baidu.com", "QUALITY");

        private String passport;
        private String mode;

        BuildMode(String passport, String mode) {
            this.passport = passport;
            this.mode = mode;
        }

        public String getPassport() {
            return passport;
        }

        public String getMode() {
            return mode;
        }

        public static BuildMode parse(String mode) {
            BuildMode[] buildModes = BuildMode.values();
            for (BuildMode buildMode : buildModes) {
                if (buildMode.getMode().equals(mode)) {
                    return buildMode;
                }
            }
            return Online;
        }
    }

    public enum EChannelInfo {

        imPlatform_rd("10.44.88.50", 8001, BuildMode.RD),

        imPlatform_qa("10.44.88.50", 8001, BuildMode.QA),

        imPlatform_online1("121.40.80.246", 14000, BuildMode.Online),

        imPlatform_online2("121.40.80.246", 80, BuildMode.Online),

        imPlatform_online3("121.40.80.246", 8080, BuildMode.Online),

        imPlatform_online4("121.40.80.246", 443, BuildMode.Online);

        // tieba_rd(EChannelType.tieba, "tc-testing-all-forum34-vm.epc.baidu.com", 8800),

        // tieba_online(EChannelType.tieba, "sh01-backup-pool380.sh01", 8800),

        // tieba_qa(EChannelType.tieba, "", 8800),

        String ip;
        int port;
        BuildMode mode;

        private EChannelInfo(String ip, int port, BuildMode mode) {
            this.ip = ip;
            this.port = port;
            this.mode = mode;
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }

        public BuildMode getMode() {
            return mode;
        }

        public static List<EChannelInfo> getIPList(){
            List<EChannelInfo> channelInfoList = new ArrayList<>();
            EChannelInfo[] channelInfos = EChannelInfo.values();
            for (EChannelInfo channelInfo : channelInfos) {
                if (channelInfo.getMode() == buildMode) {
                    channelInfoList.add(channelInfo);
                }
            }
            return channelInfoList;
        }
    }
}
