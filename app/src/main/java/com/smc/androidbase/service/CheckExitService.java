package com.smc.androidbase.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/12
 * @description
 */

public class CheckExitService extends Service {

    private final static String TAG = "CheckExitService";
    private String packageName = "com.smc.androidbase";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        ActivityManager activtyManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activtyManager.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            if (packageName.equals(runningAppProcesses.get(i).processName)) {
                Toast.makeText(this, "app还在运行中", Toast.LENGTH_LONG).show();
            }
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved()");
        Toast.makeText(CheckExitService.this, "App要退出了", Toast.LENGTH_SHORT).show();
    }
}
