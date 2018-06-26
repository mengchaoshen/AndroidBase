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

public class MyStaticBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("StaticBroadcastReceiver", "静态注册广播，收到消息了 action = " + intent.getAction());
    }

}
