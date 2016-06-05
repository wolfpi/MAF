package com.baidu.maf.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import com.apkfuns.logutils.LogUtils;
import com.baidu.maf.util.LogUtil;
import com.baidu.maf.util.ServiceControlUtil;

/**
 * Created by hanxin on 2016/5/15.
 */
public class MafService extends Service {
    public static final String TAG = "MafService";

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();

        Notification notification =
                new Notification(android.R.drawable.star_on, "mafAppservice running", System.currentTimeMillis());

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, MafService.class), 0);
        notification.setLatestEventInfo(this, "mafApp Service", "mafAppservice running", contentIntent);
        startForeground(0, notification);

        try {
            LogUtils.d(TAG, "service onCreate.");
            ServiceApplication.getInstance().initialize(this);

            // service杀不死
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, getClass());
            PendingIntent pendingIntent =
                    PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            long triggerAtTime = SystemClock.elapsedRealtime();
            int interval = 300 * 1000;
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, interval, pendingIntent);

        } catch (RuntimeException e) {
            LogUtils.e(TAG, e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e(TAG, "service Unbind");
        ServiceApplication.getInstance().onUnbind(intent);
        startService();
        return true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e(TAG, "service onBind");
        return ServiceApplication.getInstance().onBind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        ServiceApplication.getInstance().onTaskRemoved(rootIntent);
        startService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, START_STICKY, startId);
    }

    private void startService()
    {
        if(ServiceControlUtil.showInSeperateProcess(ServiceApplication.getInstance().getContext()) ) {
            if(ServiceApplication.getInstance().getContext() != null && MafService.class != null)
            {
                Intent startintent = new Intent(ServiceApplication.getInstance().getContext(), MafService.class);
                startintent.setAction("com.baidu.maf.service");
                try {
                    ServiceApplication.getInstance().getContext().startService(startintent);
                } catch (Exception e) {
                    LogUtils.e(TAG, "failed to start service", e);
                }
            }
        }

    }
}
