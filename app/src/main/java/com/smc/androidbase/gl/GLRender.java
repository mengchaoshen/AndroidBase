package com.smc.androidbase.gl;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public abstract class GLRender extends Thread {

    private final static String TAG = GLRender.class.getSimpleName();

    private GLSurface mGlSurface;
    private EGLDisplay mEGLDisplay;
    private EGLConfig mEGLConfig;
    private EGLContext mEGLContext;
    private boolean mIsRelease;
    private boolean mIsRendering;
    private ArrayBlockingQueue<Event> mEventQueue;
    private List<GLSurface> mOutputSurfaceList;

    public GLRender() {
        mEventQueue = new ArrayBlockingQueue<>(100);
        mOutputSurfaceList = new ArrayList<>();
    }


    private boolean makeOutputSurface(GLSurface glSurface) {
        switch (glSurface.type) {
            case GLSurface.TYPE_WINDOW_SURFACE: {
                final int[] attribList = {
                        EGL14.EGL_NONE
                };
                glSurface.eglSurface = EGL14.eglCreateWindowSurface(mEGLDisplay, mEGLConfig,
                        glSurface.surface, attribList, 0);
                break;
            }
            case GLSurface.TYPE_PBUFFER_SURFACE: {
                final int[] attribList = {
                        EGL14.EGL_WIDTH, glSurface.viewPort.width,
                        EGL14.EGL_HEIGHT, glSurface.viewPort.height,
                        EGL14.EGL_NONE
                };
                glSurface.eglSurface = EGL14.eglCreatePbufferSurface(mEGLDisplay, mEGLConfig,
                        attribList, 0);
                break;
            }
            default:
                break;
        }
        return true;
    }

    public void addSurface(GLSurface surface) {
        Event event = new Event();
        event.type = Event.ADD_SURFACE;
        event.param = surface;
        boolean isSuccess = mEventQueue.add(event);
        if (!isSuccess) {
            Log.e(TAG, "addSurface() mEventQueue.add is error");
        }
    }

    public void removeSurface(GLSurface surface) {
        Event event = new Event();
        event.type = Event.REMOVE_SURFACE;
        event.param = surface;
        boolean isSuccess = mEventQueue.add(event);
        if (!isSuccess) {
            Log.e(TAG, "removeSurface() mEventQueue.add is error");
        }
    }

    public void startRender() {
        boolean isSuccess = mEventQueue.add(new Event(Event.START_RENDER));
        if (!isSuccess) {
            Log.e(TAG, "startRender() mEventQueue.add is error");
        }
        if (getState() == State.NEW) {
            super.start();
        }
    }

    public void reqRender() {
        boolean isSuccess = mEventQueue.add(new Event(Event.REQ_RENDER));
        if (!isSuccess) {
            Log.e(TAG, "reqRender() mEventQueue.add is error");
        }
    }

    public void stopRender() {
        boolean isSuccess = mEventQueue.add(new Event(Event.STOP_RENDER));
        if (!isSuccess) {
            Log.e(TAG, "stopRender() mEventQueue.add is error");
        }
    }

    public void runnable(Runnable runnable) {
        Event event = new Event(Event.RUNNABLE);
        event.param = runnable;
        boolean isSuccess = mEventQueue.add(event);
        if (!isSuccess) {
            Log.e(TAG, "runnable() mEventQueue.add is error");
        }
    }

    public void release() {
        boolean isSuccess = mEventQueue.add(new Event(Event.RELEASE));
        if (!isSuccess) {
            Log.e(TAG, "release() mEventQueue.add is error");
        }
    }


    @Override
    public void run() {
        super.run();
        createGL();
        onCreate();
        while (!mIsRelease) {
            Event event = null;
            try {
                //这里使用ArrayBlockingQueue.take()的原因是，当队列里面没有数据时，会进入等待
                event = mEventQueue.take();
                GLSurface surface = null;
                switch (event.type) {
                    case Event.ADD_SURFACE:
                        surface = (GLSurface) event.param;
                        makeOutputSurface(surface);
                        mOutputSurfaceList.add(surface);
                        break;
                    case Event.REMOVE_SURFACE:
                        surface = (GLSurface) event.param;
                        EGL14.eglDestroySurface(mEGLDisplay, surface.eglSurface);
                        mOutputSurfaceList.remove(surface);
                        break;
                    case Event.START_RENDER:
                        mIsRendering = true;
                        break;
                    case Event.REQ_RENDER:
                        if (mIsRendering) {
                            onUpdate();
                            render();
                        }
                        break;
                    case Event.STOP_RENDER:
                        mIsRendering = false;
                        break;
                    case Event.RUNNABLE:
                        ((Runnable) event.param).run();
                        break;
                    case Event.RELEASE:
                        mIsRelease = true;
                        break;
                    default:
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        onDestroy();
        for (GLSurface output : mOutputSurfaceList) {
            EGL14.eglDestroySurface(mEGLDisplay, output.eglSurface);
            output.eglSurface = EGL14.EGL_NO_SURFACE;
        }
        destroyGL();
        mEventQueue.clear();
    }

    private void destroyGL() {
        EGL14.eglDestroyContext(mEGLDisplay, mEGLContext);
        mEGLDisplay = EGL14.EGL_NO_DISPLAY;
        mEGLContext = EGL14.EGL_NO_CONTEXT;
    }

    private void render() {
        for (GLSurface glSurface : mOutputSurfaceList) {
            if (glSurface.eglSurface == EGL14.EGL_NO_SURFACE) {
                makeOutputSurface(glSurface);
            }
            EGL14.eglMakeCurrent(mEGLDisplay, glSurface.eglSurface, glSurface.eglSurface, mEGLContext);
            GLES20.glViewport(glSurface.viewPort.x, glSurface.viewPort.y, glSurface.viewPort.width, glSurface.viewPort.height);
            onDrawFrame();
            EGL14.eglSwapBuffers(mEGLDisplay, glSurface.eglSurface);
        }
    }

    private void createGL() {
        mEGLDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        int[] version = new int[2];
        boolean initSuccess = EGL14.eglInitialize(mEGLDisplay, version, 0, version, 1);
        if (!initSuccess) {
            Log.e(TAG, "eglInitialize error " + EGL14.eglGetError());
            return;
        }
        int[] configAttribs = {
                EGL14.EGL_BUFFER_SIZE, 32,
                EGL14.EGL_RED_SIZE, 4,
                EGL14.EGL_GREEN_SIZE, 4,
                EGL14.EGL_BLUE_SIZE, 4,
                EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
                EGL14.EGL_SURFACE_TYPE, EGL14.EGL_WINDOW_BIT,
                EGL14.EGL_NONE
        };
        int[] numberConfigs = new int[1];
        EGLConfig[] configs = new EGLConfig[1];
        boolean chooseConfigSuccess = EGL14.eglChooseConfig(mEGLDisplay, configAttribs, 0, configs, 0,
                configs.length, numberConfigs, 0);
        if (!chooseConfigSuccess) {
            Log.e(TAG, "chooseConfig error " + EGL14.eglGetError());
            return;
        }
        mEGLConfig = configs[0];
        int[] contextAttribs = {
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL14.EGL_NONE
        };
        mEGLContext = EGL14.eglCreateContext(mEGLDisplay, mEGLConfig, EGL14.EGL_NO_CONTEXT, contextAttribs, 0);
        if (mEGLContext == EGL14.EGL_NO_CONTEXT) {
            Log.e(TAG, "eglCreateContext error " + EGL14.eglGetError());
            return;
        }
        EGL14.eglMakeCurrent(mEGLDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, mEGLContext);
    }

    public abstract void onCreate();

    public abstract void onUpdate();

    public abstract void onDrawFrame();

    public abstract void onDestroy();

    class Event {
        public final static int ADD_SURFACE = 1;
        public final static int REMOVE_SURFACE = 2;
        public final static int START_RENDER = 3;
        public final static int REQ_RENDER = 4;
        public final static int STOP_RENDER = 5;
        public final static int RUNNABLE = 6;
        public final static int RELEASE = 7;

        public Event() {

        }

        public Event(int type) {
            this.type = type;
        }

        public Object param;
        public int type;

    }

}

