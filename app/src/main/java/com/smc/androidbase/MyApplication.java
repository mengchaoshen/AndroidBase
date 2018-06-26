package com.smc.androidbase;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.smc.androidbase.service.CheckExitService;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/12
 * @description
 */

public class MyApplication extends Application {

    private final static String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
//        Intent intent = new Intent(this, CheckExitService.class);
//        startService(intent);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate()");
    }
}
