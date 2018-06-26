package com.smc.androidbase.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/29
 * @description 与前台有交互的Service 会采用bindService()的方式启动
 */
public class BackService2 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BackService2", "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BackService2", "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("BackService2", "onBind()");
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        public int add(int a, int b) {
            return a + b;
        }
    }
}
