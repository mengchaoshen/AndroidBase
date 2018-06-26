package com.smc.androidbase.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/29
 * @description 与前台没有交互，会采用startService()的方式启动
 */

public class BackService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BackService", "onCreate()");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BackService", "onStartCommand()");
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("BackService", "onBind()");

        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("BackService", "onDestroy()");
        super.onDestroy();
    }
}
