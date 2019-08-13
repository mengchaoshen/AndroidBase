package com.smc.androidbase.opengles;

import android.opengl.GLES20;
import android.opengl.GLES30;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class StencilHelper {

    static float[] vertices = {
            -0.75f, 0.25f, 0.50f, //v0
            -0.25f, 0.25f, 0.50f, //
            -0.25f, 0.75f, 0.50f, //
            -0.75f, 0.75f, 0.50f, //

            0.25f, 0.25f, 0.90f, //v1
            0.75f, 0.25f, 0.90f, //
            0.75f, 0.75f, 0.90f, //
            0.25f, 0.75f, 0.90f, //

            -0.75f, -0.75f, 0.50f, //v2
            -0.25f, -0.75f, 0.50f, //
            -0.25f, 0.25f, 0.50f, //
            -0.75f, 0.25f, 0.50f, //

            0.25f, -0.75f, 0.50f, //v3
            0.75f, -0.75f, 0.50f, //
            0.75f, -0.25f, 0.50f, //
            0.25f, -0.25f, 0.50f, //

            -1.00f, -1.00f, 0.00f, //Big Quad
            1.00f, -1.00f, 0.00f, //
            1.00f, 1.00f, 0.00f, //
            -1.00f, 1.00f, 0.00f, //
    };

    static byte indices[][] = {
            {0, 1, 2, 0, 2, 3},
            {4, 5, 6, 4, 6, 7},
            {8, 9, 10, 8, 10, 11},
            {12, 13, 14, 12, 14, 15},
            {16, 17, 18, 16, 18, 19},
    };

    static int NumTests = 4;

    static float[][] colors = {
            {1.0f, 0.0f, 0.0f, 1.0f},
            {0.0f, 1.0f, 0.0f, 1.0f},
            {0.0f, 0.0f, 1.0f, 1.0f},
            {1.0f, 1.0f, 0.0f, 1.0f},
    };

    static int[] stencilValues = {
            0x7,
            0x0,
            0x2,
            0xff
    };

    /**
     * 模板测试
     * @param program
     */
    public static void draw(int program) {

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_STENCIL_BUFFER_BIT);
        GLES30.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, DrawUtil.getFloatBuffer(vertices));
        GLES30.glEnableVertexAttribArray(0);

//        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
//        GLES30.glVertexAttrib4fv(1, DrawUtil.getFloatBuffer(colors));

        //test0
        GLES30.glStencilFunc(GLES30.GL_LESS, 0x7, 0x3);
        GLES30.glStencilOp(GLES30.GL_REPLACE, GLES30.GL_DECR, GLES30.GL_DECR);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, 6, GLES20.GL_UNSIGNED_BYTE, DrawUtil.getByteBuffer(indices[0]));

        //test1
        GLES30.glStencilFunc(GLES30.GL_EQUAL, 0x3, 0x3);
        GLES30.glStencilOp(GLES30.GL_KEEP, GLES30.GL_INCR, GLES30.GL_KEEP);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, 6, GLES20.GL_UNSIGNED_BYTE, DrawUtil.getByteBuffer(indices[1]));

        //test2
        GLES30.glStencilFunc(GLES30.GL_EQUAL, 0x1, 0x3);
        GLES30.glStencilOp(GLES30.GL_KEEP, GLES30.GL_INCR, GLES30.GL_INCR);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, 6, GLES20.GL_UNSIGNED_BYTE, DrawUtil.getByteBuffer(indices[2]));

        //test3
        GLES30.glStencilFunc(GLES30.GL_EQUAL, 0x2, 0x1);
        GLES30.glStencilOp(GLES30.GL_INVERT, GLES30.GL_KEEP, GLES30.GL_KEEP);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, 6, GLES20.GL_UNSIGNED_BYTE, DrawUtil.getByteBuffer(indices[3]));

        int[] numStencilBits = new int[1];
        stencilValues[3] = ~(((1 << numStencilBits[0]) - 1) & 0x1) & 0xff;
        GLES30.glGetIntegerv(GLES30.GL_STENCIL_BITS, numStencilBits, 0);
        GLES30.glStencilMask(0x0);

        int colorLocation = GLES30.glGetUniformLocation(program, "uColor");
        for (int i = 0; i < NumTests; i++) {
            GLES30.glStencilFunc(GLES30.GL_EQUAL, stencilValues[i], 0xff);
            GLES30.glUniform4fv(colorLocation, 1, DrawUtil.getFloatBuffer(colors[i]));
            GLES30.glDrawElements(GLES30.GL_TRIANGLES, 6, GLES30.GL_UNSIGNED_BYTE, DrawUtil.getByteBuffer(indices[4]));
        }

    }
}
