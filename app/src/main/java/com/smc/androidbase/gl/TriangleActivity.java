package com.smc.androidbase.gl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/4
 * @description
 */

public class TriangleActivity extends Activity {

    Triangle mTriangle;
    Square mSquare;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    public static void launch(Context context) {
        context.startActivity(new Intent(context, TriangleActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);//这个必须要设置，否则不会绘制任何内容
        glSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                //绘制背景色
                GLES20.glClearColor(0.1f, 0.0f, 0.0f, 1.0f);


                mTriangle = new Triangle();
                mSquare = new Square();
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                GLES20.glViewport(0, 0, width, height);
                float ratio = (float)width / height;
                Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                Matrix.setLookAtM(mViewMatrix, 0, 0,0, -3, 0f ,
                        0f ,0f ,0f ,1.0f, 0.0f);
                Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

                mTriangle.draw(mMVPMatrix);
                mSquare.draw();
            }
        });
        setContentView(glSurfaceView);
    }
}
