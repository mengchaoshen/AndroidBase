package com.smc.androidbase.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.smc.androidbase.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/7
 * @description
 */

public class ServiceActivity extends Activity {


    private final static String TAG = "ServiceActivity";

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ServiceActivity.class));
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackService2.MyBinder myBinder = (BackService2.MyBinder) service;
            int result = myBinder.add(1, 2);
            Log.d(TAG, "result = " + result);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_startService, R.id.tv_bindService, R.id.tv_foreService, R.id.tv_intentService, R.id.tv_locationService})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_startService:
                intent = new Intent(ServiceActivity.this, BackService.class);
                startService(intent);

                startService(intent);
                break;
            case R.id.tv_bindService:
                intent = new Intent(ServiceActivity.this, BackService2.class);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.tv_foreService:
                intent = new Intent(ServiceActivity.this, ForeService.class);
                startService(intent);
                break;
            case R.id.tv_intentService:
                Log.d(TAG, "onClick() R.id.tv_intentService thread = " + android.os.Process.myTid());
                Intent intent1 = new Intent("com.base.intentService");
                //api5.0以后，如果要用隐藏式跳转来启动，就必须要setPackage()来指定报名
                intent1.setPackage("com.smc.androidbase");
                intent1.putExtra("taskName", "tom");
                startService(intent1);

                Intent intent2 = new Intent("com.base.intentService");
                intent2.setPackage("com.smc.androidbase");
                intent2.putExtra("taskName", "jerry");
                startService(intent2);


//                stopService()
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startService(intent1);
                Log.d(TAG, "sleep end");

                //这些Intent请求都是按照顺序执行的，所以不适用于需要并行处理的逻辑
                //IntentService其实就是一个带有异步执行的Service，但是它的优先级比异步线程要高，因为他是四大组件之一Service
                //像上面连续三次启动同一个IntentService,会执行一次onCreate(),三次onStartCommand()，
                // 三次onHandleIntent(),运行完毕后会执行onDestroy()
                //然后再次点击按钮，会再次onCreate()...跟第一次一样。
                //这说明连续startService()，会按照顺序执行onHandleIntent()，intent执行完后，会销毁这个IntentService。
                break;
            case R.id.tv_locationService:
                startService(new Intent(ServiceActivity.this, LocationService.class));
                break;
        }
    }
}
