package com.smc.androidbase.launch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smc.androidbase.ContentProviderActivity;
import com.smc.androidbase.FragmentActivity;
import com.smc.androidbase.R;
import com.smc.androidbase.broadcast.MyDynamicBroadcastReceiver;
import com.smc.androidbase.message.HandlerActivity;
import com.smc.androidbase.service.BackService;
import com.smc.androidbase.service.BackService2;
import com.smc.androidbase.service.ForeService;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTvSplash, mTvSplashLive, mTvLogin, mTvLoginLive, mTvMain, mTvLaunch;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LaunchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mTvSplash = findViewById(R.id.tv_splash);
        mTvSplash.setOnClickListener(this);

        mTvSplashLive = findViewById(R.id.tv_splash_live);
        mTvSplashLive.setOnClickListener(this);

        mTvLogin = findViewById(R.id.tv_login);
        mTvLogin.setOnClickListener(this);

        mTvLoginLive = findViewById(R.id.tv_login_live);
        mTvLoginLive.setOnClickListener(this);

        mTvMain = findViewById(R.id.tv_main);
        mTvMain.setOnClickListener(this);

        mTvLaunch = findViewById(R.id.tv_launch);
        mTvLaunch.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //广播接收器在不用的时候，必须要反注册
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //这里必须要添加unBindService()，否则会报内存泄漏的错误
        //但是如果没有bindService直接unbindService()也是会报错的
        //unbindService(mServiceConnection);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_splash:
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("com.arcvideo.arcrtc",
                            "com.arcvideo.arcrtc.ui.login.activity.SplashActivity"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "请先安装该app", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_splash_live:
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("com.arcvideo.arcrtc",
                            "com.arcvideo.arcrtc.ui.login.activity.SplashActivity"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("callerid", "com.xxx.xxx");//包名
                    intent.putExtra("orgid", "123456");//组织号
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "请先安装该app", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_login:
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("com.arcvideo.arcrtc",
                            "com.arcvideo.arcrtc.ui.login.activity.LoginActivity"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("tag", "three");
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "请先安装该app", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_login_live:
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("com.arcvideo.arcrtc",
                            "com.arcvideo.arcrtc.ui.login.activity.LoginActivity"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("tag", "live");
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "请先安装该app", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_main:
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("com.arcvideo.arcrtc",
                            "com.arcvideo.arcrtc.ui.main.activity.MainActivity"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("tag", "three");
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "请先安装该app", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_launch:
                // 通过包名获取要跳转的app，创建intent对象
                Intent intentLaunch = getPackageManager().getLaunchIntentForPackage("com.arcvideo.arcrtc");
                // 这里如果intent为空，就说名没有安装要跳转的应用嘛
                if (intentLaunch != null) {
                    intentLaunch.putExtra("tag", "three");
                    startActivity(intentLaunch);
                } else {
                    // 没有安装要跳转的app应用，提醒一下
                }
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
