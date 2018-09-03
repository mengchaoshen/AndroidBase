package com.smc.androidbase.arc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/8/21
 * @description
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    public final static String TAG = BootBroadcastReceiver.class.getSimpleName();
    public final static String ACTION = "android.intent.action.BOOT_COMPLETED";

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Log.d(TAG, "onReceive() action : " + intent.getAction());
        if (intent.getAction().equals(ACTION)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, CheckService.class));
            } else {
                context.startService(new Intent(context, CheckService.class));
            }
        }
    }
}
