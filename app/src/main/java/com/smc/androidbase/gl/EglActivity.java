package com.smc.androidbase.gl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class EglActivity extends Activity {

    private final static String TAG = EglActivity.class.getSimpleName();

    private MyRender render;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, EglActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceView surfaceView = new SurfaceView(this);
        render = new MyRender("");
        render.start();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                render.render(holder.getSurface(), width, height);
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
        render.release();
    }

    class MyRender extends HandlerThread {

        private EGLDisplay mEGLDisplay;
        private EGLConfig mEGLConfig;
        private EGLContext mEGLContext;

        private static final String VERTEX_SHADER =
                "attribute vec2 vPosition;\n" +
                        "void main(){\n" +
                        "   gl_Position = vec4(vPosition,0,1);\n" +
                        "}";

        private static final String FRAGMENT_SHADER =
                "precision mediump float;\n" +
                        "uniform vec4 uColor;\n" +
                        "void main(){\n" +
                        "gl_FragColor = uColor;\n" +
                        "}";

        private int vPosition;
        private int uColor;
        private int program;

        public MyRender(String name) {
            super(name);
        }

        private int createProgram() {
            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
            if (0 == vertexShader) {
                return 0;
            }
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
            if (0 == fragmentShader) {
                return 0;
            }
            int program = GLES20.glCreateProgram();
            GLES20.glAttachShader(program, vertexShader);
            GLES20.glAttachShader(program, fragmentShader);
            GLES20.glLinkProgram(program);
            int[] iv = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, iv, 0);
            if (iv[0] == 0) {
                Log.e(TAG, "program compile error");
                GLES20.glDeleteProgram(program);
                program = 0;
            }
            return program;
        }

        private int loadShader(int shaderType, String shaderSource) {
            int shader = GLES20.glCreateShader(shaderType);
            if (0 != shader) {
                GLES20.glShaderSource(shader, shaderSource);
                GLES20.glCompileShader(shader);
                int[] iv = new int[1];
                GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, iv, 0);
                if (iv[0] == 0) {
                    Log.e(TAG, "shader compile error" + GLES20.glGetShaderInfoLog(shader));
                    GLES20.glDeleteShader(shader);
                    shader = 0;
                }
            }
            return shader;
        }

        private FloatBuffer getVertices() {
            float[] vertices = {
                    1f, -1f, 0f, 1f, -1f, -1f
            };
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
            floatBuffer.put(vertices);
            floatBuffer.position(0);
            return floatBuffer;
        }

        private void createGl() {
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
        }

        private void destroyGL() {
            EGL14.eglDestroyContext(mEGLDisplay, mEGLContext);
            mEGLDisplay = EGL14.EGL_NO_DISPLAY;
            mEGLContext = EGL14.EGL_NO_CONTEXT;
        }

        /**
         * 由于EGL方法的调用需要在同一个线程中，所以需要用HandlerThread来控制调用的线程
         * 这样保证了start()和release()是在同一线程中
         */
        public void start() {
            super.start();
            new Handler(getLooper()).post(new Runnable() {
                @Override
                public void run() {
                    createGl();
                }
            });
        }

        public void release() {
            new Handler(getLooper()).post(new Runnable() {
                @Override
                public void run() {
                    destroyGL();
                    quit();
                }
            });
        }

        public void render(final Surface surface, final int width, final int height){
            new Handler(getLooper()).post(new Runnable() {
                @Override
                public void run() {
                    renderGL(surface, width, height);
                }
            });
        }

        private void renderGL(Surface surface, int width, int height) {
            int[] surfaceAttribs = {
                    EGL14.EGL_NONE
            };
            EGLSurface eglSurface = EGL14.eglCreateWindowSurface(mEGLDisplay, mEGLConfig, surface, surfaceAttribs, 0);
            EGL14.eglMakeCurrent(mEGLDisplay, eglSurface, eglSurface, mEGLContext);

            program = createProgram();
            vPosition = GLES20.glGetAttribLocation(program, "vPosition");
            uColor = GLES20.glGetUniformLocation(program, "uColor");
            GLES20.glClearColor(1.0f, 1f, 1f, 1.0f);

            GLES20.glViewport(0, 0, width, height);

            FloatBuffer floatBuffer = getVertices();
            //把glClearColor()设置的颜色刷新出来，可以选择的参数有GL_DEPTH_BUFFER_BIT，GL_STENCIL_BUFFER_BIT，GL_COLOR_BUFFER_BIT
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            GLES20.glUseProgram(program);
            GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, floatBuffer);
            GLES20.glEnableVertexAttribArray(vPosition);
            GLES20.glUniform4f(uColor, 0f, 1f, 0f, 1f);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3);

            EGL14.eglSwapBuffers(mEGLDisplay, eglSurface);
            EGL14.eglDestroySurface(mEGLDisplay, eglSurface);
        }
    }
}
