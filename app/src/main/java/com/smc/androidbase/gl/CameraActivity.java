package com.smc.androidbase.gl;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class CameraActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Camera camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Parameters p = camera.getParameters();
            }
        });
    }
}
