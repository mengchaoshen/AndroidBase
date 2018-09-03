package com.smc.androidbase.arc;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author shenmengchao
 * @version 1.6.30
 * @date 2017/7/7
 * @description
 */

public class HttpUtils {

    public static final String mBaseUrl = "http://172.28.10.66";//该地址由app/build.gradle里面HTTP_HOST参数配置，这里作为默认地址

    private static HttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;

    private HttpUtils() {

    }

    public static HttpUtils getInstance() {
        if (null == mInstance) {
            mInstance = new HttpUtils();
        }
        return mInstance;
    }

//    public HttpUtils setBaseUrl(String baseUrl) {
//        this.mBaseUrl = baseUrl;
//        return this;
//    }

    public void init() {
        Gson gson = new GsonBuilder()
//                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();
        //build okhttp client
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = builder
                .cookieJar(new CookieJar() {

                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        Log.d("HttpUtils", "host = " + url.host());
                        List<Cookie> listOld = cookieStore.get(url.host());
                        List<Cookie> listNew;
                        if (null != listOld) {
                            listNew = new ArrayList<>();
                            listNew.addAll(listOld);
                            listNew.addAll(cookies);
                        } else {
                            listNew = cookies;
                        }
                        if(null != listNew && listNew.size() > 0){
                            cookieStore.put(url.host(), listNew);
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return null != cookies ? cookies : new ArrayList<Cookie>();
                    }
                })
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        //build retrofit client
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxTransformErrorCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    public Retrofit getRetrofitClient() {
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

}
