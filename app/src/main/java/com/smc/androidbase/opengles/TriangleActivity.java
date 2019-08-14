package com.smc.androidbase.opengles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES30;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.smc.androidbase.R;
import com.smc.androidbase.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shenmengchao
 * @version 1.0.0
 *          <p>
 *          OpenGLES+EGL渲染出一个三角形的流程:
 *          1.初始化EGL并确保makeCurrent
 *          2.初始化opengl program
 *          3.开始渲染
 */
public class TriangleActivity extends Activity {

    private final static String TAG = TriangleActivity.class.getSimpleName();
    @BindView(R.id.surfaceview)
    SurfaceView mSurfaceview;

    int programObject;

    public static void launch(Context context) {
        Intent intent = new Intent(context, TriangleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tri);
        ButterKnife.bind(this);

        mSurfaceview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                EsContext esContext = new EsContext();
                UserData userData = new UserData();
                userData.setVobIds(new int[3]);
                userData.setVaoIds(new int[3]);
                userData.setFbo(new int[1]);
                userData.setColorTexId(new int[4]);
                esContext.setUserData(userData);
                esContext.setWidth(mSurfaceview.getWidth());
                esContext.setHeight(mSurfaceview.getHeight());
                esMain(esContext);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void esMain(EsContext esContext) {
        if (!EglHelper.initEgl(mSurfaceview.getHolder().getSurface(), esContext)) {
            throw new RuntimeException("initEgl 失败");
        }
        boolean current = EGL14.eglMakeCurrent(esContext.eglDisplay, esContext.eglSurface, esContext.eglSurface, esContext.eglContext);
        //在init opengles之前必须要先初始化好EGLContext 并且要调用eglMakeCurrent()
        if (!ProgramUtil.init(this, esContext)) {
            ToastUtil.showToast("init 失败");
            throw new RuntimeException("init 失败");
        }

        if (current) {
            draw(esContext);
            EGL14.eglSwapBuffers(esContext.eglDisplay, esContext.eglSurface);
        }
    }


    /**
     * 开始绘制
     *
     * @param esContext
     */
    private void draw(EsContext esContext) {
        UserData userData = esContext.getUserData();
        //确定渲染位置
        GLES30.glViewport(0, 0, esContext.getWidth(), esContext.getHeight());
        //清除数据
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        //使用program
        GLES30.glUseProgram(userData.getProgramObject());

//        DrawUtil.drawColorPositionSeparate();
//        StencilHelper.draw(esContext.program);
        MrtsHelper.draw(esContext);

        checkGlError("draw");
    }

    public static void checkGlError(String op) {
        int error = GLES30.glGetError();
        if (error != GLES30.GL_NO_ERROR) {
            String msg = op + ": glError 0x" + Integer.toHexString(error);
            Log.e(TAG, msg);
        }
    }

}
