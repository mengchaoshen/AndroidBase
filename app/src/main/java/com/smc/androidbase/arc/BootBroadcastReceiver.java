package com.smc.androidbase.arc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
    private Subscription mSubscription;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        mContext = context;
        Log.d(TAG, "onReceive() action : " + intent.getAction());
        if (intent.getAction().equals(ACTION)) {
//            threadSleep();
            mSubscription = Observable.interval(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            if (isNetworkConnected(mContext)) {
                                mSubscription.unsubscribe();
                                if (intent.getAction().equals(ACTION)) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        context.startForegroundService(new Intent(context, CheckService.class));
                                    } else {
                                        context.startService(new Intent(context, CheckService.class));
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
