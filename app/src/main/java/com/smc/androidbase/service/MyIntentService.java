package com.smc.androidbase.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/7
 * @description
 */

public class MyIntentService extends IntentService {


    private final static String TAG = "MyIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() thread = " + android.os.Process.myTid());
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart() startId = " + startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() thread = " + android.os.Process.myTid());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //onHandleIntent()会去子线程执行，而且同一个Service的intent都会在同一个子线程执行
        //实现原理就是在onCreate()里面使用HandlerThread去开启一个子线程，然后每次startService-Intent到来的时候，会执行onStartCommand()
        //这个时候使用handler发消息，去HandlerThread执行。
        String taskName = intent.getStringExtra("taskName");
        Log.d(TAG, "onHandleIntent() taskName = " + taskName + "thread = " + android.os.Process.myTid());

        //这里每次执行完，都会执行stopSelf(startId);需要注意的是，startId，并不是结束所有Intent,只是结束本次进入的Intent
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() thread = " + android.os.Process.myTid());
    }
}
