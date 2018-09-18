package com.smc.androidbase.gl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/4
 * @description
 */

public class Triangle {

    private FloatBuffer vertexBuffer;

    /**
     * 每个顶点的坐标个数，这里每个顶点有三个坐标
     */
    static final int COORDINATES_PER_VERTEX = 3;
    /**
     * 绘制三角形顶点的坐标，三个值为一组（一个顶点），三组（三个顶点）作为一个三角形。
     */
    static float sTriangleCoordinates[] = {
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,

            -1f, 1f, 0.0f,
            -1f, -0f, 0.0f,
            0f, 1f, 0.0f,
    };
    /**
     * 颜色数组，以RGBA的形式存储
     */
    float color[] = {
            255, 0, 0, 1.0f};
    //顶点
//    private final String vertexShaderCode =
//            "attribute vec4 vPosition;" +
//                    "void main() {" +
//                    " gl_Position = vPosition;" +
//                    "}";
    //片段




    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    " gl_FragColor = vColor;" +
                    "}";


    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" + "}";


    private final int vertexStride = COORDINATES_PER_VERTEX * 4;
    private final int vertexCount = sTriangleCoordinates.length / COORDINATES_PER_VERTEX;


    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMvpMatrixHandle;


    public Triangle() {
        //数据转换
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(sTriangleCoordinates.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(sTriangleCoordinates);
        vertexBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDINATES_PER_VERTEX, GLES20.GL_FLOAT,
                false, vertexStride, vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        mMvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

}
