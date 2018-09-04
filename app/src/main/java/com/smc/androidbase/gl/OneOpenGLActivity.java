package com.smc.androidbase.gl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/4
 * @description
 */

public class OneOpenGLActivity extends Activity {


    public static void launch(Context context) {
        context.startActivity(new Intent(context, OneOpenGLActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneGLSurfaceView glSurfaceView = new OneGLSurfaceView(this);
        setContentView(glSurfaceView);
    }
}
