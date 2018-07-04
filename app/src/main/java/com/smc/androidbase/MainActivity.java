package com.smc.androidbase;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.smc.androidbase.bitmap.BitmapActivity;
import com.smc.androidbase.broadcast.MyDynamicBroadcastReceiver;
import com.smc.androidbase.event_dispatch.EventActivity;
import com.smc.androidbase.handler_thread.HandlerThreadActivity;
import com.smc.androidbase.ipc.AidlActivity;
import com.smc.androidbase.ipc.MessageActivity;
import com.smc.androidbase.ipc.SocketActivity;
import com.smc.androidbase.launch.LaunchActivity;
import com.smc.androidbase.message.HandlerActivity;
import com.smc.androidbase.service.ServiceActivity;
import com.smc.androidbase.utils.LocationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MainActivity";

    MyDynamicBroadcastReceiver myBroadcastReceiver;
    @BindView(R.id.tv_service)
    TextView mTvService;
    @BindView(R.id.tv_contentProvider)
    TextView mTvContentProvider;
    @BindView(R.id.tv_fragment)
    TextView mTvFragment;
    @BindView(R.id.tv_handler)
    TextView mTvHandler;
    @BindView(R.id.tv_arcrtc)
    TextView mTvArcrtc;
    @BindView(R.id.tv_event)
    TextView mTvEvent;
    @BindView(R.id.tv_handler_thread)
    TextView mTvHandlerThread;
    @BindView(R.id.tv_lru_cache)
    TextView mTvLruCache;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_view)
    TextView mTvView;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.tv_aidl)
    TextView mTvAidl;
    @BindView(R.id.tv_socket)
    TextView mTvSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate()");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @OnClick({R.id.tv_service, R.id.tv_contentProvider, R.id.tv_fragment, R.id.tv_handler,
            R.id.tv_arcrtc, R.id.tv_event, R.id.tv_handler_thread, R.id.tv_lru_cache,
            R.id.tv_location, R.id.tv_view, R.id.tv_message, R.id.tv_aidl, R.id.tv_socket
            , R.id.tv_bitmap})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_contentProvider:
                ContentProviderActivity.launch(MainActivity.this);
                break;
            case R.id.tv_fragment:
                FragmentActivity.launch(MainActivity.this);
                break;
            case R.id.tv_socket:
                SocketActivity.launch(this);
                break;
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
            case R.id.tv_aidl:
                AidlActivity.launch(this);
                break;
            case R.id.tv_bitmap:
                BitmapActivity.launch(this);
                break;
        }
    }
}
