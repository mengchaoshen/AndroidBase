package com.smc.androidbase.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.smc.androidbase.MainActivity;
import com.smc.androidbase.NotificationShowActivity;
import com.smc.androidbase.R;

import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/29
 * @description
 */

public class ForeService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        beginForeService();
    }

    private void beginForeService(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder mBuilder = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("ForeService")
                    .setContentText("您有一条未读消息")
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(false)
                    .setAutoCancel(true);

            //创建一个TaskStackBuilder
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            //添加落地页
            Intent intentMain = new Intent(this, MainActivity.class);
            intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            stackBuilder.addNextIntent(intentMain);
//            stackBuilder.addParentStack(NotificationShowActivity.class);
            //添加落地页，为什么这个要添加两次我也不是很懂，反正不怎么写，会错误

            Intent intent = new Intent(this, NotificationShowActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            stackBuilder.addNextIntent(intent);
            //生成PendingIntent
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //把PendingIntent放入Builder
            mBuilder.setContentIntent(pendingIntent);

            //获取NotificationManager
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //生成Notification
            Notification notification = mBuilder.build();
            nm.notify(0, notification);
            startForeground(0, notification);

            Log.d("ForeService", "isAlive = " + isAppAlive(this, ""));
        }
    }

    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

}
