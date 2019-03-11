package com.smc.androidbase.gl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.smc.androidbase.R;

import java.nio.IntBuffer;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Egl2Activity extends Activity {

    GLRender mGLRender;
    private SurfaceView mSurfaceView;
    private ImageView mImageView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, Egl2Activity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egl2);
        mSurfaceView = findViewById(R.id.surfaceview);
        mImageView = findViewById(R.id.image);


        mGLRender = new MyRender();
        GLSurface glSurface = new GLSurface(512, 512);
        mGLRender.addSurface(glSurface);
        mGLRender.startRender();
        mGLRender.reqRender();
        mGLRender.runnable(new Runnable() {
            @Override
            public void run() {
                IntBuffer ib = IntBuffer.allocate(512 * 512);
                GLES20.glReadPixels(0, 0, 512, 512, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
                final Bitmap bitmap = frameToBitmap(512, 512, ib);
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
            }
        });

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                GLSurface glSurface = new GLSurface(holder.getSurface(), width, height);
                mGLRender.addSurface(glSurface);
                mGLRender.reqRender();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }

    private static Bitmap frameToBitmap(int width, int height, IntBuffer ib) {
        int pixs[] = ib.array();
        // 扫描转置(OpenGl:左上->右下 Bitmap:左下->右上)
        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width; x++) {
                int pos1 = y * width + x;
                int pos2 = (height - 1 - y) * width + x;

                int tmp = pixs[pos1];
                pixs[pos1] = (pixs[pos2] & 0xFF00FF00) | ((pixs[pos2] >> 16) & 0xff) | ((pixs[pos2] << 16) & 0x00ff0000); // ABGR->ARGB
                pixs[pos2] = (tmp & 0xFF00FF00) | ((tmp >> 16) & 0xff) | ((tmp << 16) & 0x00ff0000);
            }
        }
        if (height % 2 == 1) { // 中间一行
            for (int x = 0; x < width; x++) {
                int pos = (height / 2 + 1) * width + x;
                pixs[pos] = (pixs[pos] & 0xFF00FF00) | ((pixs[pos] >> 16) & 0xff) | ((pixs[pos] << 16) & 0x00ff0000);
            }
        }

        return Bitmap.createBitmap(pixs, width, height, Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGLRender.release();
        mGLRender = null;
    }
}
