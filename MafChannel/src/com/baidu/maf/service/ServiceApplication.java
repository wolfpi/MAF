package com.baidu.maf.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.apkfuns.logutils.LogUtils;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.message.NotifyMessage;
import com.baidu.maf.network.NetChannelStatus;
import com.baidu.maf.processer.PushConfirmProcesser;
import com.baidu.maf.processer.SetAppStatusProcesser;
import com.baidu.maf.util.MafPreference;
import com.baidu.maf.util.ConfigUtil;
import com.baidu.maf.util.DeviceInfoUtil;
import com.baidu.maf.util.LogUtil;
import com.baidu.maf.util.StringUtil;

import java.io.IOException;

/**
 * Created by hanxin on 2016/5/20.
 */
public class ServiceApplication {

    public static final String TAG = "ServiceApplication";

    /**
     * HiCoreService context.
     */
    private Context context;

    private static ServiceApplication outAppApplication = new ServiceApplication();

    private ServiceChannel serviceChannel;
    private NetworkLayer networkLayer;
    private Handler handler;
    private MafPreference mPreference = null;
    private int  mSeq = 100 ;
    private int  mOutAppSeq = 9000100;
    private int  mCurOutAppSeq = mOutAppSeq;
    private String mChannelKey = null;

    private MafContext mafContext = null;
    // private GlobalTimer mGlobalTimer = new GlobalTimerTasks();


    private ServiceApplication() {
    }

    static {
        System.loadLibrary("hichannel-jni");
    }

    /**
     * 获取全局实例。
     */
    public static synchronized ServiceApplication getInstance() {
        return outAppApplication;
    }

    /**
     * 获取Hi core service的 context.
     */
    public Context getContext() {
        return context;
    }

    /**
     * 用于和in-app的部分通信。
     *
     * @return
     */
    public ServiceChannel getServiceChannel() {
        return serviceChannel;
    }



    public void setChannelKey(String channelKey)
    {
        mChannelKey = channelKey;
        mafContext.setChannelKey(channelKey);
    }
    public String getChannelKey()
    {
        return mChannelKey;
    }

    public MafContext getMafContext() {
        return mafContext;
    }

    public void destroy() {
        if (serviceChannel != null) {
            serviceChannel.destroy();
            serviceChannel = null;
        }
        this.context = null;
    }

    public void initialize(Context context) {
        handler = new Handler(Looper.getMainLooper());

        mPreference = new MafPreference();
        mPreference.initialize(context, null);

        if (StringUtil.isStringInValid(mPreference.getDeviceToken())){
            mPreference.saveDeviceToken(DeviceInfoUtil.getDeviceToken(context));
        }
        ConfigUtil.load();


        this.context = context.getApplicationContext();

        mafContext = new MafContext(context, null);

        try {
            networkLayer = new NetworkLayer(mPreference);
        }
        catch (IOException e){
            LogUtils.e(TAG, "error:" + e.getMessage());
        }
        serviceChannel = new ServiceChannel(networkLayer);
        networkLayer.setNextChannel(serviceChannel);

        networkLayer.initialized();
    }

    public void runOnMainThread(Runnable runnable) {

        handler.post(runnable);
    }


    public synchronized IBinder onBind(Intent intent) {
        LogUtil.printMainProcess(TAG, "service onBind.");
        try {
            if (serviceChannel != null) {

                LogUtil.printMainProcess("dynamicLoader0 intent=" + intent);
                LogUtil.printMainProcess("dynamicLoader0 Binder=" + null);
                return serviceChannel.getBinder();
            }
        } catch (RuntimeException e) {
            LogUtil.e(TAG, e);
        }
        return null;
    }

    public synchronized void onTaskRemoved(Intent intent) {
        LogUtil.printMainProcess(TAG, "service onTaskRemoved.");
        try {
            if (serviceChannel != null) {
                serviceChannel.onUnbind();
            }
        } catch (RuntimeException e) {
            LogUtil.e(TAG, e);
        }
    }

    public synchronized void onUnbind(Intent intent) {
        LogUtil.printMainProcess(TAG, "service onUnbind.");
        try {
            if (serviceChannel != null) {
                serviceChannel.onUnbind();
            }
        } catch (RuntimeException e) {
            LogUtil.e(TAG, e);
        }
    }

    public NetChannelStatus getNetStatus(){
        return networkLayer.getNetworkChannelStatus();
    }

    public void reconnect(){
        networkLayer.networkChanged();
    }

    public void setAppOffline(int appId){
        try {
            SetAppStatusProcesser processer = new SetAppStatusProcesser();
            processer.setNextChannel(serviceChannel);
            processer.process(getMafContext());
        }
        catch (Exception e){

        }
    }

    public void pushConfirm(NotifyMessage notifyMessage){
        try{
            PushConfirmProcesser processer = new PushConfirmProcesser();
            processer.setNextChannel(serviceChannel);
            processer.setNotifyMessage(notifyMessage);
            processer.process(getMafContext());
        }
        catch (Exception e){

        }
    }
}
