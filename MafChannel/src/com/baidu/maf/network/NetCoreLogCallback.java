package com.baidu.maf.network;

import com.apkfuns.logutils.LogUtils;

import java.io.UnsupportedEncodingException;
import com.baidu.maf.network.jni.ILogCallback;

public class NetCoreLogCallback extends ILogCallback {

    private final static String TAG = "HiChannel";

    public void d(byte[] data, int len) {
        String msg = "";
        try {
            msg = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            LogUtils.e(TAG, "", e1);
        }
        LogUtils.d(TAG, msg);
    }

    public void i(byte[] data, int len) {
        String msg = "";
        try {
            msg = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            LogUtils.e(TAG, "", e1);
        }
        LogUtils.i(TAG, msg);
    }

    public void w(byte[] data, int len) {
        String msg = "";
        try {
            msg = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            LogUtils.e(TAG, "", e1);
        }
        LogUtils.w(TAG, msg);
    }

    public void e(byte[] data, int len) {
        String msg = "";
        try {
            msg = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            LogUtils.e(TAG, "", e1);
        }
        LogUtils.e(TAG, msg);
    }

}
