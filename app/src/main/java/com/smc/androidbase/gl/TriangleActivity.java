package com.smc.androidbase.gl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/4
 * @description
 */
public class TriangleActivity extends Activity {

    private final static String TAG = TriangleActivity.class.getSimpleName();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, TriangleActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        //设置EGL版本号
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new MyRender());
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setContentView(glSurfaceView);
    }

    public class MyRender implements GLSurfaceView.Renderer {

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

        /**
         * 加载定制化的shader
         *
         * @param shaderType
         * @param shaderSource
         * @return
         */
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

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            program = createProgram();
            vPosition = GLES20.glGetAttribLocation(program, "vPosition");
            uColor = GLES20.glGetUniformLocation(program, "uColor");
            GLES20.glClearColor(1f, 0f, 0f, 1f);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            FloatBuffer floatBuffer = getVertices();
            GLES20.glClear(GLES20.GL_COLOR_CLEAR_VALUE | GLES20.GL_DEPTH_CLEAR_VALUE);
            GLES20.glUseProgram(program);
            GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, floatBuffer);
            GLES20.glEnableVertexAttribArray(vPosition);
            GLES20.glUniform4f(uColor, 1f, 0f, 1f, 1f);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3);
        }
    }


}
