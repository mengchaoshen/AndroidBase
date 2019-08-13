package com.smc.androidbase.opengles;


import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class EsContext {

    UserData userData;
    int width;
    int height;

    EGLConfig eglConfig;
    EGLSurface eglSurface;
    EGLContext eglContext;
    EGLDisplay eglDisplay;

    int program;

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
