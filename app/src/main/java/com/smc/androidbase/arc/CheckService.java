package com.smc.androidbase.arc;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.smc.androidbase.MainActivity;
import com.smc.androidbase.NotificationShowActivity;
import com.smc.androidbase.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/19
 * @description
 */

public class CheckService extends Service {

    private final static String TAG = "CheckService";

    private Time mNextTime = new Time(0, 0);

    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initTime();
        startCheckProcess();
        beginForeService();
    }

    private void beginForeService() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder mBuilder = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("CheckService")
                    .setContentText("常驻服务")
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true)
                    .setAutoCancel(false);
            //获取NotificationManager
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //生成Notification
            Notification notification = mBuilder.build();
            nm.notify(0, notification);
            startForeground(0, notification);
        }
    }

    private void initTime() {
        if (null == mNextTime || mNextTime.getHour() == 0 || mNextTime.getMinute() == 0) {
            Time currentTime = DateUtil.getTime(System.currentTimeMillis());
            if (currentTime.getHour() > 9 && currentTime.getHour() < 18) {
                mNextTime = new Time(17, 56);
            } else {
                mNextTime = new Time(8, 23);
            }
        }
        Log.d(TAG, "initTime mNextTime = " + mNextTime.toString());
    }

    private void startCheckProcess() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Time currentTime = DateUtil.getTime(System.currentTimeMillis());
                    if (checkTime(currentTime)) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                getLoginAsp();
                            }
                        });
                        getNextTime();
                    }
                    try {
                        Thread.sleep(1 * 30 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private boolean checkTime(Time current) {
        Log.d(TAG, "checkTime currentTime = " + current.toString());
        Log.d(TAG, "mNextTime = " + mNextTime.toString());
        if (current.getHour() == mNextTime.getHour() && current.getMinute() > mNextTime.getMinute()) {
            return true;
        }
        return false;
    }

    private void getNextTime() {
        int minute = 0;
        int hour = 0;
        if (mNextTime.getHour() == 0 || mNextTime.getHour() == 17) {
            minute = (int) (Math.random() * 7) + 20;
            hour = 8;
        } else {
            minute = (int) (Math.random() * 7) + 50;
            hour = 17;
        }
        mNextTime = new Time(hour, minute);
        Log.d(TAG, "getNextTime NextTime is = " + mNextTime.toString());
    }

    private void getLoginAsp() {
        Log.d(TAG, "<==start check==>");
//        OkHttpClient client = new OkHttpClient();
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get().url("http://172.28.10.66/login.asp").build();
        Call call = HttpUtils.getInstance().getOkHttpClient().newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                ToastUtil.showToast(GetActivity.this, "Get 失败");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseStr = response.body().string();
//                Log.d(TAG, "loginAsp = " + responseStr);
                final String userNameKey = "userName" + getUserNameKeyByLoginAsp(responseStr);
                Log.d(TAG, "userNameKey = " + userNameKey);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        confirm(userNameKey);
                    }
                });
            }
        });
    }

    public void confirm(String userNameKey) {
//        OkHttpClient client = new OkHttpClient();
        //构建FormBody，传入要提交的参数
        FormBody formBody = new FormBody
                .Builder()
                .add(userNameKey, "smc")
                .add("password", "Xxzj88088175")
                .build();
        final Request request = new Request.Builder()
                .url("http://172.28.10.66/confirm.asp")
                .post(formBody)
                .build();
        Call call = HttpUtils.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                ToastUtil.showToast(PostParameterActivity.this, "Post Parameter 失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
//                Log.d(TAG, "ConfirmAsp = " + responseStr);
                final String session = getSessionByConfirmAsp(responseStr);
                Log.d(TAG, "session = " + session);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        check(session);
                    }
                });
            }
        });
    }

    private void check(final String session) {
//        OkHttpClient client = new OkHttpClient();
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get()
                .url("http://172.28.10.66/hrinfo/attendance/check.asp?action=check&r=" + session)
                .addHeader("Referer", "http://172.28.10.66/hrinfo/attendance/viewRecord.asp")
                .build();
        Call call = HttpUtils.getInstance().getOkHttpClient().newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                ToastUtil.showToast(GetActivity.this, "Get 失败");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseStr = response.body().string();
//                Log.d(TAG, "CheckAsp = " + responseStr);
                final String checkTime = getCheckTimeByCheckAsp(responseStr);
                Log.d(TAG, "checkTime = " + checkTime);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CheckService.this, "打卡成功, 打卡时间：" + checkTime,
                                Toast.LENGTH_SHORT).show();
//                        mContent += "\n" + "打卡时间：" + checkTime;
//                        mTvTime.setText(mContent);
//                        confirm(userNameKey);
                    }
                });
            }
        });
    }

    private String getUserNameKeyByLoginAsp(String loginAsp) {
        if (!TextUtils.isEmpty(loginAsp)) {
            String[] array = loginAsp.split("userName");
            if (array.length > 1) {
                if (array[1].length() >= 3) {
                    return array[1].substring(0, 3);
                }
            }
        }
        return "";
    }

    private String getSessionByConfirmAsp(String confirmAsp) {
        if (!TextUtils.isEmpty(confirmAsp)) {
            String[] array = confirmAsp.split("check&r=");
            if (array.length > 1) {
                if (array[1].length() >= 8) {
                    return array[1].substring(0, 8);
                }
            }
        }
        return "";
    }

    private String getCheckTimeByCheckAsp(String checkAsp) {
        if (!TextUtils.isEmpty(checkAsp)) {
            String[] array = checkAsp.split("check time:");
            if (array.length > 1) {
                if (array[1].length() >= 20) {
                    return array[1].substring(0, 20);
                }
            }
        }
        return "";
    }


}
