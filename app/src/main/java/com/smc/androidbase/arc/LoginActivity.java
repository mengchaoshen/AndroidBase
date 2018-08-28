package com.smc.androidbase.arc;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smc.androidbase.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/17
 * @description
 */

public class LoginActivity extends Activity {

    private final static String TAG = "LoginActivity";

    @BindView(R.id.tv_start)
    TextView mTvStart;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_start_service)
    TextView mTvStartService;

    //    private Time mNextTime = new Time(8, 25);
    private String mContent = "";

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_start, R.id.tv_start_service, R.id.tv_httpurlconnection, R.id.tv_set_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_set_time:
                setSysTime(16, 11);
                break;
            case R.id.tv_start:
                getLoginAsp();
                break;
            case R.id.tv_start_service:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(new Intent(this, CheckService.class));
                } else {
                    startService(new Intent(this, CheckService.class));
                }
                break;
            case R.id.tv_httpurlconnection:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doHttpUrlConnectionRequest();
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    public void setSysTime(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long when = c.getTimeInMillis();
        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) this.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }

    private void doHttpUrlConnectionRequest() {
        String message = "";
        try {
            URL url = new URL("http://vevi.pw:90/api/clientVersion/chkVersion");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-type", "application/json;charset=utf-8");
            httpURLConnection.connect();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            String param = "{\"mobileType\":\"0\",\"versionNumber\":\"1.0.0\",\"packageName\":\"com.mingrenjinfu\"}";
            outputStream.write(param.getBytes());
            outputStream.flush();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
//            byte[] data = new byte[1000];//1000->1次
            byte[] data = new byte[32];//16->5次
            StringBuffer stringBuffer = new StringBuffer();
            int length = 0;
            while ((length = inputStream.read(data)) != -1) {
                Log.d(TAG, "connect 1");
                String s = new String(data, Charset.forName("utf-8"));
                stringBuffer.append(s);
                data = new byte[32];
            }
            message = stringBuffer.toString().trim();
            inputStream.close();
            httpURLConnection.disconnect();

            Log.d(TAG, "doOutPut = " + httpURLConnection.getDoOutput());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "message = " + message);
    }

    private void getLoginAsp() {
        Log.d(TAG, "<==start check==>");
//        OkHttpClient client = new OkHttpClient();
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get().url("http://172.28.10.66/login.asp").build();
        Call call = HttpUtils.getInstance().getOkHttpClient()
                .newBuilder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return null;
                    }
                })
                .build()
                .newCall(request);
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

                runOnUiThread(new Runnable() {
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
                runOnUiThread(new Runnable() {
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "打卡成功, 打卡时间：" + checkTime,
                                Toast.LENGTH_SHORT).show();
                        mContent += "\n" + "打卡时间：" + checkTime;
                        mTvTime.setText(mContent);
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
