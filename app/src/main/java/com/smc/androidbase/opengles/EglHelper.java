package com.smc.androidbase.opengles;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.view.Surface;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class EglHelper {

    /**
     * 初始化EGL
     *
     * @param surface
     * @return
     */
    public static boolean initEgl(Surface surface, EsContext esContext) {
        //选择EGLConfig所用到的参数
        int[] configAttribs = {
                EGL14.EGL_SURFACE_TYPE, EGL14.EGL_WINDOW_BIT,
                EGL14.EGL_RED_SIZE, 8,
                EGL14.EGL_GREEN_SIZE, 8,
                EGL14.EGL_BLUE_SIZE, 8,
                EGL14.EGL_DEPTH_SIZE, 24,
                EGL14.EGL_NONE
        };
        //创建EGLContext所用到的参数
        int[] contextAttribs = {
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 3,
                EGL14.EGL_NONE
        };
        //创建EGLSurface所用到的参数
        int[] surfaceAttribs = {
                EGL14.EGL_NONE
        };
        //获取EGLDisplay对象
        esContext.eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        if (esContext.eglDisplay == EGL14.EGL_NO_DISPLAY) {
            return false;
        }
        int[] major = new int[1];
        int[] minor = new int[1];
        //初始化EGL
        if (!EGL14.eglInitialize(esContext.eglDisplay, major, 0, minor, 0)) {
            return false;
        }
        EGLConfig[] config = new EGLConfig[1];
        int[] numConfigs = new int[1];
        //把参数传递给EGL让他选择一个EGLConfig
        if (!EGL14.eglChooseConfig(esContext.eglDisplay, configAttribs, 0, config, 0, 1, numConfigs, 0)) {
            return false;
        }
        esContext.eglConfig = config[0];
        //创建一个EGLSurface
        esContext.eglSurface = EGL14.eglCreateWindowSurface(esContext.eglDisplay, config[0], surface, surfaceAttribs, 0);
        if (esContext.eglSurface == EGL14.EGL_NO_SURFACE) {
            return false;
        }
        //创建一个EGLContext
        esContext.eglContext = EGL14.eglCreateContext(esContext.eglDisplay, config[0], EGL14.EGL_NO_CONTEXT, contextAttribs, 0);
        if (esContext.eglContext == EGL14.EGL_NO_CONTEXT) {
            return false;
        }
        return true;
    }
}
