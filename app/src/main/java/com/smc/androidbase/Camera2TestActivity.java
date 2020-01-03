package com.smc.androidbase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.TextureView;

import java.util.Arrays;

/**
 * @author shenmengchao
 * @version 1.0.0
 *
 * 1.通过Context#getSystemService()获取到CameraManager实例
 * 2.根据CameraManager#getCameraIdList()和CameraManager#getCameraCharacteristics()确定需要启动的Camera的Id
 * 3.调用CameraManager#openCamera()开启摄像头
 * 4.在CameraDevice.CameraCallback#onOpened()中去创建会话开启预览
 * 5.调用CameraDevice#createCaptureSession()传入由TextureView得到SurfaceTexture创建得到的Surface，创建出CameraCaptureSession
 * 6.调用CameraDevice#createCaptureRequest()传入上一步的surface
 * 7.在CameraCaptureSession.StateCallback#onConfigured()中调用CameraCaptureSession#setRepeatingRequest()
 */
public class Camera2TestActivity extends Activity {

    TextureView mTextureView;
    CameraManager mCameraManager;
    CameraDevice mCameraDevice;
    CaptureRequest mCaptureRequest;
    CameraCaptureSession mCameraCaptureSession;

    Handler mMainHandler = new Handler(Looper.getMainLooper());

    public static void launch(Context context) {
        Intent intent = new Intent(context,Camera2TestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        mTextureView = findViewById(R.id.tv_texture);
        try {
            openCamera();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("MissingPermission")
    private void openCamera() throws CameraAccessException {
        mCameraManager = getSystemService(CameraManager.class);
        String[] cameraIds = mCameraManager.getCameraIdList();
        String frontId = "";
        for(String s : cameraIds){
            CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics(s);
        }
        frontId = cameraIds[0];
        mCameraManager.openCamera(frontId, new CameraDevice.StateCallback(){

            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                mCameraDevice = camera;
                try {
                    createSession();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {

            }

            @Override
            public void onError(@NonNull CameraDevice camera, int error) {

            }
        }, mMainHandler);

    }

    private void createSession() throws CameraAccessException {
        if (mCameraDevice != null) {
            SurfaceTexture surfaceTexture = mTextureView.getSurfaceTexture();
            Surface surface = new Surface(surfaceTexture);

            mCameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback(){

                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, mCaptureCallback, mMainHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, mMainHandler);
            CaptureRequest.Builder builder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            builder.addTarget(surface);
            mCaptureRequest = builder.build();
        }
    }

    CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            super.onCaptureProgressed(session, request, partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
        }

        @Override
        public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession session, int sequenceId, long frameNumber) {
            super.onCaptureSequenceCompleted(session, sequenceId, frameNumber);
        }

        @Override
        public void onCaptureSequenceAborted(@NonNull CameraCaptureSession session, int sequenceId) {
            super.onCaptureSequenceAborted(session, sequenceId);
        }

        @Override
        public void onCaptureBufferLost(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull Surface target, long frameNumber) {
            super.onCaptureBufferLost(session, request, target, frameNumber);
        }
    };
}
