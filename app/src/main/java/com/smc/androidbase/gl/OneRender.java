package com.smc.androidbase.gl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/4
 * @description
 */

public class OneRender implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //绘制背景色
        GLES20.glClearColor(0.2f, 0.4f, 0.5f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //大致意思应该是以COLOR_BUFFER_BIT的形式去渲染
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
