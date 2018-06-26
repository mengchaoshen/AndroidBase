package com.smc.androidbase.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/13
 * @description
 */

public class LocationService extends Service {

    LocationManager locationManager;
    MyLocationListener myLocationListener;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
//        sp = getSharedPreferences("config", MODE_PRIVATE);
// 1.获取位置的管理者
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
// 2.获取定位方式
// 2.1获取所有的定位方式，true:返回可用的定位方式
        List<String> providers = locationManager.getProviders(true);
        for (String string : providers) {
            System.out.println(string + "3");
        }
// 2.2获取最佳的定位方式
        Criteria criteria = new Criteria();
// 设置可以定位海拔,true：表示可以定位海拔
        criteria.setAltitudeRequired(true);// 只有gps可以定位海拔
// criteria ： 设置定位属性
// enabledOnly ： 如果定位可以就返回
        String bestProvider = locationManager.getBestProvider(criteria, true);
        System.out.println("GPSService 最佳的定位方式:" + bestProvider);
// 3.定位
        myLocationListener = new MyLocationListener();
// provider : 定位方式
// minTime ：定位的最小时间间隔
// minDistance　：　最小的定位距离
// listener ： LocationListener监听
        locationManager.requestLocationUpdates(bestProvider, 1000, 0,
                new MyLocationListener());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 4.创建一个定位的监听
    private class MyLocationListener implements LocationListener {
        // 定位位置发生变化的时候调用
// location ： 当前的位置
        @Override
        public void onLocationChanged(Location location) {
            System.out.println(location + "oooooooooooooooooo");
// 5.获取经纬度
            location.getAccuracy();// 获取精确的位置
            location.getAltitude();// 获取海拔
            double latitude = location.getLatitude();// 获取纬度，平行
            double longitude = location.getLongitude();// 获取经度
            Log.d("LocationService", "latitude = " + latitude);
            Log.d("LocationService", "longitude = " + longitude);
//保存经纬度坐标
//            SharedPreferences.Editor edit = sp.edit();
//            edit.putString("longitude", longitude + "");
//            edit.putString("latitude", latitude + "");
//            edit.commit();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
