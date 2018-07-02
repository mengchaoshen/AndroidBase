package com.smc.androidbase;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.smc.androidbase.broadcast.MyDynamicBroadcastReceiver;
import com.smc.androidbase.event_dispatch.EventActivity;
import com.smc.androidbase.handler_thread.HandlerThreadActivity;
import com.smc.androidbase.ipc.MessageActivity;
import com.smc.androidbase.launch.LaunchActivity;
import com.smc.androidbase.message.HandlerActivity;
import com.smc.androidbase.service.ServiceActivity;
import com.smc.androidbase.utils.LocationUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MainActivity";

    TextView mTvService, mTvContentProvider, mTvFragment, mTvHandler;
    TextView mTvArcRtc, mTvEvent, mTvHandlerThread, mTvLruCache, mTvLocation;
    TextView mTvView, mTvMessage;

    MyDynamicBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate()");
        setContentView(R.layout.activity_main);

//        mTvStartService = findViewById(R.id.tv_startService);
//        mTvStartService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, BackService.class);
//                startService(intent);
//            }
//        });
//        mTvBindService = findViewById(R.id.tv_bindService);
//        mTvBindService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, BackService2.class);
//                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
//            }
//        });
//
//        mTvForeService = findViewById(R.id.tv_foreService);
//        mTvForeService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ForeService.class);
//                startService(intent);
//            }
//        });

        mTvService = findViewById(R.id.tv_service);
        mTvService.setOnClickListener(this);

        mTvContentProvider = findViewById(R.id.tv_contentProvider);
        mTvContentProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentProviderActivity.launch(MainActivity.this);
            }
        });

        mTvFragment = findViewById(R.id.tv_fragment);
        mTvFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity.launch(MainActivity.this);
            }
        });

        mTvHandler = findViewById(R.id.tv_handler);
        mTvHandler.setOnClickListener(this);

        mTvArcRtc = findViewById(R.id.tv_arcrtc);
        mTvArcRtc.setOnClickListener(this);

        mTvArcRtc.measure(0, 0);

        mTvEvent = findViewById(R.id.tv_event);
        mTvEvent.setOnClickListener(this);

        mTvHandlerThread = findViewById(R.id.tv_handler_thread);
        mTvHandlerThread.setOnClickListener(this);

        mTvLruCache = findViewById(R.id.tv_lru_cache);
        mTvLruCache.setOnClickListener(this);

        mTvLocation = findViewById(R.id.tv_location);
        mTvLocation.setOnClickListener(this);

        mTvView = findViewById(R.id.tv_view);
        mTvView.setOnClickListener(this);

        mTvMessage = findViewById(R.id.tv_message);
        mTvMessage.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume()");
        myBroadcastReceiver = new MyDynamicBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //广播接收器在不用的时候，必须要反注册
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //这里必须要添加unBindService()，否则会报内存泄漏的错误
        //但是如果没有bindService直接unbindService()也是会报错的
        //unbindService(mServiceConnection);

        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_service:
                ServiceActivity.launch(this);
                break;
            case R.id.tv_handler:
                HandlerActivity.launch(this);
                break;
            case R.id.tv_arcrtc:
                LaunchActivity.launch(this);
                break;
            case R.id.tv_event:
                EventActivity.launch(this);
                break;
            case R.id.tv_handler_thread:
                HandlerThreadActivity.launch(this);
                break;
            case R.id.tv_lru_cache:
                LruCacheActivity.launch(this);
                break;
            case R.id.tv_location:
//                Location location = LocationUtil.getLocationByGps(this);
                Location location = LocationUtil.getLocationByNetwork(this);
                if (null != location) {
                    Log.d(TAG, "long = " + location.getLongitude());
                    Log.d(TAG, "lat = " + location.getLatitude());
                }
                break;
            case R.id.tv_view:
                ViewActivity.launch(this);
                break;
            case R.id.tv_message:
                MessageActivity.launch(this);
                break;
            default:
                break;
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
