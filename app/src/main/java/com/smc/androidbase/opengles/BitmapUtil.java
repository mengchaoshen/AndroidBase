package com.smc.androidbase.opengles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.smc.androidbase.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author shenmengchao
 * @version 1.0.0
 */
public class BitmapUtil {

    public static void draw(Bitmap bitmap, int program) {
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_camera_begin);
        dealDraw(program, bitmap);
    }

    private static int createTexture(Bitmap bitmap) {
        int[] texture = new int[1];
        if (bitmap != null) {
            GLES30.glGenTextures(1, texture, 0);
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture[0]);
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
            return texture[0];
        }
        return 0;
    }

    private static void dealDraw(int mProgram, Bitmap bitmap) {

         final float[] sCoord = {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };
        ByteBuffer cc = ByteBuffer.allocateDirect(sCoord.length * 4);
        cc.order(ByteOrder.nativeOrder());
        FloatBuffer bCoord = cc.asFloatBuffer();
        bCoord.put(sCoord);
        bCoord.position(0);

//        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
//        GLES30.glUseProgram(mProgram);


        int glHPosition = GLES30.glGetAttribLocation(mProgram, "vPosition");
        int glHCoordinate = GLES30.glGetAttribLocation(mProgram, "vCoordinate");
        int glHTexture = GLES30.glGetAttribLocation(mProgram, "vTexture");

        GLES30.glEnableVertexAttribArray(glHPosition);
        GLES30.glEnableVertexAttribArray(glHCoordinate);
        GLES30.glUniform1i(glHTexture, 0);
        int textureId = createTexture(bitmap);
        //传入顶点坐标
        GLES30.glVertexAttribPointer(glHPosition, 2, GLES30.GL_FLOAT, false, 0, DrawUtil.getFloatBuffer(sCoord));
        //传入纹理坐标
        GLES30.glVertexAttribPointer(glHCoordinate, 2, GLES30.GL_FLOAT, false, 0, bCoord);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);

    }
}
