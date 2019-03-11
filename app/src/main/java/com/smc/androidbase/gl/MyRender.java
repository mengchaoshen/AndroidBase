package com.smc.androidbase.gl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class MyRender extends GLRender {

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

    private int program;
    private int vPosition;
    private int uColor;

    @Override
    public void onCreate() {
        program = ShaderUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        uColor = GLES20.glGetUniformLocation(program, "uColor");
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onDrawFrame() {
        GLES20.glClearColor(1.0f, 1f, 1f, 1.0f);

//        GLES20.glViewport(0, 0, width, height);

        FloatBuffer floatBuffer = getVertices();
        //把glClearColor()设置的颜色刷新出来，可以选择的参数有GL_DEPTH_BUFFER_BIT，GL_STENCIL_BUFFER_BIT，GL_COLOR_BUFFER_BIT
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(program);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, floatBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glUniform4f(uColor, 0f, 1f, 0f, 1f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3);
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
    public void onDestroy() {

    }
}
