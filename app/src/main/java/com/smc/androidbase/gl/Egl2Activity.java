package com.smc.androidbase.gl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Egl2Activity extends Activity {

    GLRender mGLRender;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, Egl2Activity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceView surfaceView = new SurfaceView(this);


        mGLRender = new MyRender();
        mGLRender.startRender();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                GlSurface glSurface = new GlSurface(holder.getSurface(), width, height);
                mGLRender.addSurface(glSurface);
                mGLRender.reqRender();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        setContentView(surfaceView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGLRender.release();
        mGLRender = null;
    }
}
