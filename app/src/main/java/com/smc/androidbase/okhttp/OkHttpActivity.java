package com.smc.androidbase.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/26
 * @description
 */

public class OkHttpActivity extends Activity {

    public static void main(String args[]) {
//        ArrayList<String> list = new ArrayList<>();
//        list.add("0");
//        list.add("1");
//        ArrayList<String> list2 = (ArrayList<String>) list.subList(1, 2);
//        System.out.println(list2);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


        //GET请求数据
        //POST上传数据(form表单，json数据，文件，图片等),搞清楚content-type
        //下载图片文件等
        //CONNECT连接

//        client.dispatcher().runningCalls()
    }

    private void get() throws IOException {
        /*sync 同步执行
        RealCall#execute()中，call.dispatch().execute();目的就是把call加入到Dispatch#runningSyncCalls队列中去
        用处是可以采用client.dispatcher().runningCalls()的方式，去获取正在运行的Call。
         */


        OkHttpClient client = new OkHttpClient
                .Builder()
                .eventListener(new EventListener() {
                    @Override
                    public void callStart(Call call) {
                        super.callStart(call);
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest = request.newBuilder().header("", "").build();
                        Response response = chain.proceed(newRequest);
                        //进行一些操作
                        return response;
                    }
                })
                //设置缓存
                .cache(new Cache(new File("cache"), 24 * 1024 * 1024))
                .build();
        Request request = new Request.Builder()
                .get()
                .url("")
                .build();
        Call call = client.newCall(request);
        call.execute();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.cacheResponse();
                response.networkResponse();
            }
        });
    }

    private void postJson() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        //content-type=application/json;charset=utf-8
        //content-type=application/x-www-form-urlencoded;charset=utf-8

        MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaTypeJson, "");
        Request request = new Request.Builder()
                .post(requestBody)
                .url("")
                .addHeader("content-type", "")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void postFile() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        MediaType mediaTypeJson = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaTypeJson, "");
        Request request = new Request.Builder().post(requestBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


}
