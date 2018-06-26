package com.smc.androidbase.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/29
 * @description
 */

public class MyDynamicBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DynamicBroadcast", "动态注册广播接收器，收到消息了");
    }

}
