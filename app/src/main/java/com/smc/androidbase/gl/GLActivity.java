package com.smc.androidbase.gl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.smc.androidbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/4
 * @description 介绍OpenGL的文章：https://www.jianshu.com/p/92d02ac80611
 */

public class GLActivity extends Activity {

    @BindView(R.id.tv_one_gl)
    TextView mTvOneGl;
    @BindView(R.id.tv_triangle)
    TextView mTvTriangle;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, GLActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl);
        ButterKnife.bind(this);


//        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
//        //render是用来OpenGL显示渲染使用
//        glSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
//            @Override
//            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//                //GLSurfaceView创建成功是会回调这里，这里一般用于只需要执行一次的操作，初始化OpenGL或一些环境参数
//            }
//
//            @Override
//            public void onSurfaceChanged(GL10 gl, int width, int height) {
//
//            }
//
//            @Override
//            public void onDrawFrame(GL10 gl) {
//                //在这里用于绘制图像
//            }
//        });
//        setContentView(glSurfaceView);
    }

    @OnClick({R.id.tv_one_gl, R.id.tv_triangle, R.id.tv_egl, R.id.tv_egl2, R.id.tv_new_opengl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_new_opengl:
                com.smc.androidbase.opengles.TriangleActivity.launch(this);
                break;
            case R.id.tv_one_gl:
                OneOpenGLActivity.launch(this);
                break;
            case R.id.tv_triangle:
                TriangleActivity.launch(this);
                break;
            case R.id.tv_egl:
                EglActivity.launch(this);
                break;
            case R.id.tv_egl2:
                Egl2Activity.launch(this);
                break;
        }
    }
}
