package com.smc.androidbase.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/13
 * @description
 */

public class LocationUtil {

    public static Location mLocation;

    @SuppressLint("MissingPermission")
    public static Location getLocationByGps(Context context) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        while (true) {
//            Log.d("LocationUtil", "获取权限中。。");
//            mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (null != mLocation) {
//                Log.d("LocationUtil", "权限获取成功");
//                return mLocation;
//            }
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 1, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    if (null != mLocation) {
//                        Log.d("LocationUtil", "权限获取成功");
//                        return;
//                    }
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//                    mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    if (null != mLocation) {
//                        Log.d("LocationUtil", "权限获取成功");
//                        return;
//                    }
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//                    mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    if (null != mLocation) {
//                        Log.d("LocationUtil", "权限获取成功");
//                        return;
//                    }
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//                    mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    if (null != mLocation) {
//                        Log.d("LocationUtil", "权限获取成功");
//                        return;
//                    }
//                }
//            });
//        }
        return null;
    }

    @SuppressLint("MissingPermission")
    public static Location getLocationByNetwork(Context context){
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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
        });
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        return location;
    }

}
