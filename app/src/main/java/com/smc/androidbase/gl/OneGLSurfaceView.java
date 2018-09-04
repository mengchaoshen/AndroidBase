package com.smc.androidbase.gl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/4
 * @description
 */

public class OneGLSurfaceView extends GLSurfaceView {

    private OneRender mOneRender;

    public OneGLSurfaceView(Context context) {
        this(context, null);
    }

    public OneGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        mOneRender = new OneRender();
        setRenderer(mOneRender);
    }
}
