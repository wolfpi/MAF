package com.baidu.maf.com;

import android.os.Environment;

/**
 * Created by 欣 on 2016/6/3.
 */
public class Constant {

    public static final boolean DEBUG = true;

    public static final String SDK_ROOT_PATH = "/baidu/maf";

    public static final String sdkExternalDir = Environment.getExternalStorageDirectory() + "/baidu/maf";

    public static final String sdkExternalLogoDir = sdkExternalDir + "/logo";

    public static final String sdkExternalLogDir = SDK_ROOT_PATH + "/log/";

    public static final int seqSize = 10000;

    public static final String  sdkPushAction = "com.baidu.maf.channel.push";

    public static final String  sdkBDAppId = "appid";

    public static final String  sdkBDData = "infodata";
}
