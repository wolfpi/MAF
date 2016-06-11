package com.baidu.maf.service;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.baidu.maf.com.Constant;
import com.baidu.maf.util.LogUtil;

/**
 * Created by 欣 on 2016/6/10.
 */
public class ServiceConfig {
    private static final String TAG = "ServiceConfig";
    private static final String RUN_MODE = "RUN_MODE";

    public static void init(Context context) {
        if (null != context) {
            try {
                ApplicationInfo applicationInfo =
                        context.getApplicationContext().getPackageManager()
                                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if ((null != applicationInfo) && (null != applicationInfo.metaData)) {
                    String modeStr = applicationInfo.metaData.getString(RUN_MODE);
                    if (!TextUtils.isEmpty(modeStr)) {
                        Constant.BuildMode tmpMode = Constant.BuildMode.parse(modeStr);
                        if (null != tmpMode) {
                            Constant.buildMode = tmpMode;
                        }
                        LogUtil.printMainProcess(TAG, "GetOutAppConfig mode" + Constant.buildMode);
                    } else {
                        LogUtil.printMainProcess(TAG, "Can not get BDIM_RUN_MODE string.");
                    }
                } else {
                    LogUtil.printMainProcess(TAG, "Can not get meta data.");
                }
            } catch (Exception e) {
                LogUtil.printError("GetOutAppConfig error", e);
            }
        }
    }
}
