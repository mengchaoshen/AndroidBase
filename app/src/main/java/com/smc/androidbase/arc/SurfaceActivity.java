package com.smc.androidbase.arc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceView;

import com.smc.androidbase.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class SurfaceActivity extends Activity {

    private final static String TAG = "AndroidBase";

    @BindView(R.id.surfaceview)
    SurfaceView mSurfaceview;

    public static void launch(Context context) {
        Intent intent = new Intent(context, SurfaceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);
        ButterKnife.bind(this);

        MyOrientationEventListener listener = new MyOrientationEventListener(this);
        listener.enable();
        listener.disable();
//        registerRotation();


//        unlockScreenOrientation();
    }

//    protected void unlockScreenOrientation() {
//        Log.d(TAG, "unlockScreenOrientation()");
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
//    }

    private void registerRotation() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        Sensor mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(new SensorListener() {
            @Override
            public void onSensorChanged(int sensor, float[] values) {
                Log.d(TAG, "getRotation onSensorChanged=" + sensor);
            }

            @Override
            public void onAccuracyChanged(int sensor, int accuracy) {
                Log.d(TAG, "getRotation onAccuracyChanged=" + sensor);
            }
        }, SensorManager.SENSOR_DELAY_GAME);
    }

    private int getRotation() {
        int rotation = -1;
        try {
            SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            Class clazzLegacy = Class.forName("android.hardware.LegacySensorManager");
            Constructor constructor = clazzLegacy.getConstructor(SensorManager.class);
            constructor.setAccessible(true);
            Object oLegacy = constructor.newInstance(sensorManager);
            Field fieldRotation = clazzLegacy.getDeclaredField("sRotation");
            fieldRotation.setAccessible(true);
            rotation = fieldRotation.getInt(oLegacy);
        } catch (Exception e) {
            Log.e(TAG, "getRotation e=" + e.getMessage());
            e.printStackTrace();
        }
        Log.d(TAG, "getRotation rotation=" + rotation);
        return rotation;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
        }
    }

    class MyOrientationEventListener extends OrientationEventListener {

        private static final int SENSOR_ANGLE = 10;

        public MyOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            Log.d(TAG, "onOrientationChanged orientation=" + orientation);
            if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                return;  //手机平放时，检测不到有效的角度
            }

            //下面是手机旋转准确角度与四个方向角度（0 90 180 270）的转换
            if (orientation > 360 - SENSOR_ANGLE || orientation < SENSOR_ANGLE) {
                orientation = 0;
            } else if (orientation > 90 - SENSOR_ANGLE && orientation < 90 + SENSOR_ANGLE) {
                orientation = 90;
            } else if (orientation > 180 - SENSOR_ANGLE && orientation < 180 + SENSOR_ANGLE) {
                orientation = 180;
            } else if (orientation > 270 - SENSOR_ANGLE && orientation < 270 + SENSOR_ANGLE) {
                orientation = 270;
            } else {
                return;
            }
        }
    }


}
