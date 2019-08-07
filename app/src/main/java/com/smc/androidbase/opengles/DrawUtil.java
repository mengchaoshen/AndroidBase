package com.smc.androidbase.opengles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import com.smc.androidbase.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class DrawUtil {

    private final static String TAG = DrawUtil.class.getSimpleName();

    public static final float[] COLORS = {

            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,

            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
    };


    /**
     * 使用获取映射缓冲区的方式，来填充数据
     *
     * @param esContext
     */
    public static void drawMapBuffer(EsContext esContext) {
        ByteBuffer vtxMappedBuf;
        ByteBuffer idxMappedbuf;
        UserData userData = esContext.getUserData();
        int[] vbos = userData.getVobIds();
        short[] indices = {0, 1, 2};
        float[] vertexs = {
                0.0f, 1.0f, 1.0f, 1.0f,     //c1
                0.0f, 0.5f, 0.0f,           //v1
                1.0f, 0.0f, 1.0f, 1.0f,     //c2
                -0.5f, -0.5f, 0.0f,         //v2
                1.0f, 1.0f, 0.0f, 1.0f,     //c3
                0.5f, -0.5f, 0.0f           //v3
        };
        int numIndices = 3;
        int numVertices = 3;
        int vtxStride = 7 * Float.BYTES;
        GLES30.glGenBuffers(2, vbos, 0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbos[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vtxStride * numVertices, null, GLES30.GL_STATIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vbos[1]);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, Short.BYTES * numIndices, null, GLES30.GL_STATIC_DRAW);

        //复制顶点数据
        //获取顶点属性所指定的ByteBuffer
        vtxMappedBuf = (ByteBuffer) GLES30.glMapBufferRange(
                GLES30.GL_ARRAY_BUFFER,
                0,
                vtxStride * numVertices,
                GLES30.GL_MAP_WRITE_BIT);
        if (vtxMappedBuf == null) {
            Log.e(TAG, "glError vtxMappedBuf == NULL");
            return;
        }
        //获取到ByteBuffer后，给ByteBuffer填充需要的参数
        vtxMappedBuf.order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexs).position(0);
        //取消映射，在glUnmapBuffer调用完成之后，opengles也会把数据刷新进去
        if (GLES30.glUnmapBuffer(GLES30.GL_ARRAY_BUFFER) == false) {
            Log.e(TAG, "glError vtxMappedBuf unmap error");
            return;
        }

        //赋值indices数据
        idxMappedbuf = (ByteBuffer) GLES30.glMapBufferRange(
                GLES30.GL_ELEMENT_ARRAY_BUFFER,
                0,
                Short.BYTES * numIndices,
                GLES30.GL_MAP_WRITE_BIT);
        if (idxMappedbuf == null) {
            Log.e(TAG, "glError idxMappedbuf == NULL");
            return;
        }
        idxMappedbuf.order(ByteOrder.nativeOrder()).asShortBuffer().put(indices).position(0);

        if (GLES30.glUnmapBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER) == false) {
            Log.e(TAG, "glError idxMappedbuf unmap error");
            return;
        }

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glVertexAttribPointer(0, 4, GLES30.GL_FLOAT, false, vtxStride, 0);
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, vtxStride, 4 * Float.BYTES);


        //glDrawElements()中的第三个参数，现在只能用GL_UNSIGNED_SHORT，否则会报0x0500 GL_INVALID_ENUM的错误
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, numIndices, GLES30.GL_UNSIGNED_SHORT, 0);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * color 和 position分开 并且使用顶点缓冲对象
     *
     * @param esContext
     */
    public static void drawSeparateWithVbos(EsContext esContext) {
        UserData userData = esContext.getUserData();
        int[] vboIds = userData.getVobIds();
        float[] colors = {
                0.0f, 1.0f, 1.0f, 1.0f,     //c1
                1.0f, 0.0f, 1.0f, 1.0f,     //c2
                1.0f, 1.0f, 0.0f, 1.0f,     //c3
        };
        float[] positions = {
                0.0f, 0.5f, 0.0f,           //v1
                -0.5f, -0.5f, 0.0f,         //v2
                0.5f, -0.5f, 0.0f           //v3
        };
        short[] indices = {0, 1, 2};
        int numIndices = 3;
        int numVertices = 3;
        int colorStride = 4 * Float.BYTES;
        int positionStride = 3 * Float.BYTES;
        //初始化和把数据绑定到缓冲区
        if (vboIds[0] == 0 && vboIds[1] == 0 && vboIds[2] == 0) {
            GLES30.glGenBuffers(3, vboIds, 0);

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboIds[0]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, colorStride * numVertices, getFloatBuffer(colors), GLES30.GL_STATIC_DRAW);

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboIds[1]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, positionStride * numVertices, getFloatBuffer(positions), GLES30.GL_STATIC_DRAW);

            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vboIds[2]);
            GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, Short.BYTES * numIndices, getShortBuffer(indices), GLES30.GL_STATIC_DRAW);
        }
        //把缓冲区内的数据，加载到顶点属性中
        //bind color
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboIds[0]);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 4, GLES30.GL_FLOAT, false, colorStride, 0);

        //bind position
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboIds[1]);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, positionStride, 0);

        //bind indices
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vboIds[2]);

        //draw
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, numIndices, GLES30.GL_UNSIGNED_SHORT, 0);

        //disable
        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);

        //bind 0
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
        checkGlError("555");
    }

    public static void drawTogetherWithVaos(EsContext esContext) {
        UserData userData = esContext.getUserData();
        int[] vboIds = userData.getVobIds();
        int[] vaoIds = userData.getVaoIds();
        float[] vertexs = {
                0.0f, 1.0f, 1.0f, 1.0f,     //c1
                0.0f, 0.5f, 0.0f,           //v1
                1.0f, 0.0f, 1.0f, 1.0f,     //c2
                -0.5f, -0.5f, 0.0f,         //v2
                1.0f, 1.0f, 0.0f, 1.0f,     //c3
                0.5f, -0.5f, 0.0f           //v3
        };
        short[] indices = {0, 1, 2};
        int numIndices = 3;
        int numVertices = 3;
        int vtxStride = 7 * Float.BYTES;
        if (vboIds[0] == 0 && vboIds[1] == 0) {
            GLES30.glGenBuffers(2, vboIds, 0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboIds[0]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vtxStride * numVertices, getFloatBuffer(vertexs), GLES30.GL_STATIC_DRAW);

            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vboIds[1]);
            GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, Short.BYTES * numIndices, getShortBuffer(indices), GLES30.GL_STATIC_DRAW);
        }
        //VAO 开始使用顶点数组对象
        GLES30.glGenVertexArrays(1, vaoIds, 0);
        GLES30.glBindVertexArray(vaoIds[0]);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboIds[0]);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vboIds[1]);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glVertexAttribPointer(0, 4, GLES30.GL_FLOAT, false, vtxStride, 0);
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, vtxStride, 4 * Float.BYTES);

        //glDrawElements()中的第三个参数，现在只能用GL_UNSIGNED_SHORT，否则会报0x0500 GL_INVALID_ENUM的错误
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, numIndices, GLES30.GL_UNSIGNED_SHORT, 0);

        //绘制完成之后，回复使用默认的顶点数组对象
        GLES30.glBindVertexArray(0);

        //删除顶点数组对象
        GLES30.glDeleteVertexArrays(1, getIntBuffer(vaoIds));

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public static void drawTogetherWithVbos(EsContext esContext) {
        UserData userData = esContext.getUserData();
        int[] vboIds = userData.getVobIds();
        float[] vertexs = {
                0.0f, 1.0f, 1.0f, 1.0f,     //c1
                0.0f, 0.5f, 0.0f,           //v1
                1.0f, 0.0f, 1.0f, 1.0f,     //c2
                -0.5f, -0.5f, 0.0f,         //v2
                1.0f, 1.0f, 0.0f, 1.0f,     //c3
                0.5f, -0.5f, 0.0f           //v3
        };
        short[] indices = {0, 1, 2};
        int numIndices = 3;
        int numVertices = 3;
        int vtxStride = 7 * Float.BYTES;
        if (vboIds[0] == 0 && vboIds[1] == 0) {
            GLES30.glGenBuffers(2, vboIds, 0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboIds[0]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vtxStride * numVertices, getFloatBuffer(vertexs), GLES30.GL_STATIC_DRAW);

            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vboIds[1]);
            GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, Short.BYTES * numIndices, getShortBuffer(indices), GLES30.GL_STATIC_DRAW);
        }
        checkGlError("111");
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboIds[0]);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vboIds[1]);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        checkGlError("222");

        GLES30.glVertexAttribPointer(0, 4, GLES30.GL_FLOAT, false, vtxStride, 0);
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, vtxStride, 4 * Float.BYTES);

        checkGlError("223");
        //glDrawElements()中的第三个参数，现在只能用GL_UNSIGNED_SHORT，否则会报0x0500 GL_INVALID_ENUM的错误
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, numIndices, GLES30.GL_UNSIGNED_SHORT, 0);
        checkGlError("333");

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
        checkGlError("444");
    }

    /**
     * 绘制多个三角形
     */
    public static void drawTriangles() {
        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
        float[] positions = {
                0.0f, 0f, 0.0f, //v0
                0.5f, 0f, 0.0f, //v1
                0.2f, 0.3f, 0.0f,//v2

                0.6f, 0f, 0.0f, //v3
                0.4f, 0.6f, 0.0f,//v4
                0.7f, 0.8f, 0.0f//v5
        };
        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors));
        //把顶点数组的形式指定Vertext的参数值a_position
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 6);
        GLES30.glDisableVertexAttribArray(1);
    }

    /**
     * 三角条带
     * GL_TRIANGLE_STRIP环绕规则
     * n = 奇数 T= [n-2, n-1, n] 例如 n=3(v0, v1, v2) n=5(v2, v3, v4)
     * n = 偶数 T = [n-1, n-2, n] 例如 n=4(v2, v1, v3) n=6(v4, v3, v5)
     */
    public static void drawTriangleStrip() {
        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
        float[] positions = {
                0.0f, 0f, 0.0f, //v0
                0f, 0.5f, 0.0f, //v1
                0.3f, 0f, 0.0f,//v2

                0.6f, 0.5f, 0.0f, //v3
                0.8f, 0f, 0.0f,//v4
//                0.7f, 0.8f, 0.0f//v5
        };
        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors));
        //把顶点数组的形式指定Vertext的参数值a_position
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 5);
        GLES30.glDisableVertexAttribArray(1);
    }

    /**
     * 三角扇形
     * 绘制规则（n, 圆心, n-1） 例如n=3（v2, v0, v1） n=4（v3, v0, v2）n=5（v4, v0, v3）n
     */
    public static void drawTriangleFan() {
        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
        float[] positions = {
                0.0f, 0f, 0.0f, //v0
                0f, 0.5f, 0.0f, //v1
                0.3f, 0f, 0.0f,//v2

                0.6f, 0.5f, 0.0f, //v3
                0.8f, 0f, 0.0f,//v4
//                0.7f, 0.8f, 0.0f//v5
        };
        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors));
        //把顶点数组的形式指定Vertext的参数值a_position
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 5);
        GLES30.glDisableVertexAttribArray(1);
    }

    /**
     * 绘制多条线段，两个顶点为一组
     */
    public static void drawLines() {
        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
        float[] positions = {
                0.0f, 0.8f, 0.0f, //v0
                0.6f, 0.9f, 0.0f, //v1
                0f, 0f, 0.0f,//v2

                0.7f, 0.5f, 0.0f, //v3
                0.2f, 1f, 0.0f,//v4
                0.8f, 0.2f, 0.0f//v5
        };
        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors));
        //把顶点数组的形式指定Vertext的参数值a_position
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glDrawArrays(GLES30.GL_LINES, 0, 6);
        GLES30.glDisableVertexAttribArray(1);
    }

    /**
     * 绘制线段，相连的线段，(v0, v1)(v1, v2)(v2, v3)依次类推
     */
    public static void drawLineStrip() {
        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
        float[] positions = {
                0.0f, 0.8f, 0.0f, //v0
                0.6f, 0.9f, 0.0f, //v1
                0f, 0f, 0.0f,//v2

                0.7f, 0.5f, 0.0f, //v3
                0.2f, 1f, 0.0f,//v4
                0.8f, 0.2f, 0.0f//v5
        };
        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors));
        //把顶点数组的形式指定Vertext的参数值a_position
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 0, 6);
        GLES30.glDisableVertexAttribArray(1);
    }

    /**
     * 绘制线段，相连的线段，(v0, v1)(v1, v2)(v2, v3)依次类推
     * 并且最后会把(vn, v0)也进行连接
     */
    public static void drawLineLoop() {

        float[] positions = {
                0.0f, 0.8f, 0.0f, //v0
                0.6f, 0.9f, 0.0f, //v1
                0f, 0f, 0.0f,//v2

                0.7f, 0.5f, 0.0f, //v3
                0.2f, 1f, 0.0f,//v4
                0.8f, 0.2f, 0.0f//v5
        };
        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors));
        //把顶点数组的形式指定Vertext的参数值a_position
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glLineWidth(5.0f);//设置线段的宽度
        GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, 6);
        GLES30.glDisableVertexAttribArray(1);
    }


    /**
     * 使用gl_triangles的方法来绘制立方体
     */
    public static void drawCubeByTriangles() {

        float[] positions = {
                0.7f, 0.7f, 0.0f, //v0
                0.0f, 0.7f, 0.0f, //v1
                0.0f, 0.0f, 0.0f,//v2
                0.7f, 0.7f, 0.0f, //v0
                0.0f, 0.0f, 0.0f,//v2
                0.7f, 0.0f, 0.0f, //v3

                0.7f, 0.7f, 0.0f, //v0
                0.7f, 0f, 0.0f, //v3
                0.9f, 0.3f, 0.0f,//v4
                0.7f, 0.7f, 0.0f, //v0
                0.9f, 0.3f, 0.0f,//v4
                0.9f, 0.9f, 0.0f,//v5

                0.7f, 0.7f, 0.0f, //v0
                0.0f, 0.7f, 0.0f, //v1
                0.3f, 0.9f, 0.0f,//v6
                0.7f, 0.7f, 0.0f, //v0
                0.9f, 0.9f, 0.0f,//v5
                0.3f, 0.9f, 0.0f,//v6

                0.3f, 0.3f, 0.0f,//v7
                0f, 0f, 0.0f,//v2
                0.7f, 0f, 0.0f, //v3
                0.3f, 0.3f, 0.0f,//v7
                0.7f, 0f, 0.0f, //v3
                0.9f, 0.3f, 0.0f,//v4

                0.3f, 0.3f, 0.0f,//v7
                0f, 0.7f, 0.0f, //v1
                0f, 0f, 0.0f,//v2
                0.3f, 0.3f, 0.0f,//v7
                0f, 0.7f, 0.0f, //v1
                0.3f, 0.9f, 0.0f,//v6

                0.3f, 0.3f, 0.0f,//v7
                0.9f, 0.3f, 0.0f,//v4
                0.9f, 0.9f, 0.0f,//v5
                0.3f, 0.3f, 0.0f,//v7
                0.9f, 0.9f, 0.0f,//v5
                0.3f, 0.9f, 0.0f//v6

        };
//        float[] colors1 = {1.0f, 0.0f, 1.0f, 1.0f};
//        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors1));


        GLES30.glVertexAttribPointer(0, 4, GLES30.GL_FLOAT, false, 0, getFloatBuffer(COLORS));
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 18);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    public static void drawElementTriangleStrip() {
        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
        float[] positions = {
                0.0f, 0.0f, 0.0f, //v0
                0.0f, 0.5f, 0.0f, //v1
                0.15f, 0.0f, 0.0f,//v2
                0.35f, 0.5f, 0.0f, //v3

                0.2f, 0.0f, 0.0f,//v8
                0.4f, 0.4f, 0.0f, //v9
                0.6f, 0.0f, 0.0f,//v10
                0.7f, 0.6f, 0.0f//v11
        };
        //一次draw绘制出两个三角形条带，使用了退化三角形也就是无法绘制出来的三角形
        int[] indices = {
                0, 1, 2, 3, 3,
                4, 4, 5, 6, 7,
        };
        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors));
        //把顶点数组的形式指定Vertext的参数值a_position
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glEnable(GLES30.GL_PRIMITIVE_RESTART_FIXED_INDEX);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, indices.length, GLES30.GL_UNSIGNED_INT, getIntBuffer(indices));
        GLES30.glDisable(GLES30.GL_PRIMITIVE_RESTART_FIXED_INDEX);
        GLES30.glDisableVertexAttribArray(1);
    }

    public static void testFeedback(int queryObject) {
        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
        float[] positions = {
                0.0f, 0.0f, 0.0f, //v0
                0.0f, 0.5f, 0.0f, //v1
                0.15f, 0.0f, 0.0f,//v2
                0.35f, 0.5f, 0.0f, //v3

                0.2f, 0.0f, 0.0f,//v8
                0.4f, 0.4f, 0.0f, //v9
                0.6f, 0.0f, 0.0f,//v10
                0.7f, 0.6f, 0.0f//v11
        };
        //一次draw绘制出两个三角形条带，使用了退化三角形也就是无法绘制出来的三角形
        int[] indices = {
                0, 1, 2, 3, 3,
                4, 4, 5, 6, 7,
        };
        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors));
        //把顶点数组的形式指定Vertext的参数值a_position
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glEnable(GLES30.GL_PRIMITIVE_RESTART_FIXED_INDEX);

        GLES30.glBeginTransformFeedback(GLES30.GL_POINTS);
        GLES30.glBeginQuery(GLES30.GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN, queryObject);
//        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 10);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, indices.length, GLES30.GL_UNSIGNED_INT, getIntBuffer(indices));
        GLES30.glEndQuery(GLES30.GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN);
        GLES30.glEndTransformFeedback();
        int[] numPoints = new int[1];
        GLES30.glGetQueryObjectuiv(queryObject, GLES30.GL_QUERY_RESULT, numPoints, 0);


        GLES30.glDisable(GLES30.GL_PRIMITIVE_RESTART_FIXED_INDEX);
        GLES30.glDisableVertexAttribArray(1);
    }

    /**
     * 使用texture来加载
     */
    public static void drawTexture() {
        int[] textureId = new int[1];
        int[] ints = {
                255, 0, 0,
                0, 255, 0,
                0, 0, 255,
                255, 255, 0,

                255, 0, 0,
                0, 255, 0,
                0, 0, 255,
                255, 255, 0,

                255, 0, 0,
                0, 255, 0,
                0, 0, 255,
                255, 255, 0,

                255, 0, 0,
                0, 255, 0,
                0, 0, 255,
                255, 255, 0,
        };
        checkGlError("drawTexture1");
        GLES30.glPixelStorei(GLES30.GL_UNPACK_ALIGNMENT, 1);//设置解包对齐
        checkGlError("drawTexture2");
        GLES30.glGenTextures(1, textureId, 0);
        checkGlError("drawTexture3");
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId[0]);
        checkGlError("drawTexture4");
        //glTexImage2D上传纹理数据
        GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RGB, 4, 4, 0,
                GLES30.GL_RGB, GLES30.GL_UNSIGNED_BYTE, getByteBufferInt(ints));

        checkGlError("drawTexture");

        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);


        GLES30.glEnableVertexAttribArray(1);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 16);

//        GLUtils.texImage2D();
    }

    /**
     * 使用texture把bitmap绘制出来
     *
     * @param program
     * @param context
     */
    public static void drawTextureImage(int program, Context context) {
        //顶点坐标
        float vertexData[] = {   // in counterclockwise order:
                -1f, -1f, 0.0f, // bottom left
                1f, -1f, 0.0f, // bottom right
                -1f, 1f, 0.0f, // top left
                1f, 1f, 0.0f,  // top right
        };

        //纹理坐标  对应顶点坐标  与之映射
        float textureData[] = {   // in counterclockwise order:
                0f, 1f, 0.0f, // bottom left
                1f, 1f, 0.0f, // bottom right
                0f, 0f, 0.0f, // top left
                1f, 0f, 0.0f,  // top right
        };
        //每一次取点的时候取几个点
        final int COORDS_PER_VERTEX = 3;
        final int vertexCount = vertexData.length / COORDS_PER_VERTEX;
        final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

        int avPostion = GLES30.glGetAttribLocation(program, "a_position");
        int afPostion = GLES30.glGetAttribLocation(program, "af_position");

        int[] textures = new int[1];
        GLES30.glGenTextures(1, textures, 0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[0]);

        //设置纹理方法缩小时所使用的策略
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);

        //生成bitmap并且把它加载到纹理中去
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_camera_begin);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);

        //把顶点坐标和纹理坐标的数据传入
        GLES30.glUseProgram(program);
        GLES30.glEnableVertexAttribArray(avPostion);
        GLES30.glEnableVertexAttribArray(afPostion);
        GLES30.glVertexAttribPointer(avPostion, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, vertexStride, getFloatBuffer(vertexData));
        GLES30.glVertexAttribPointer(afPostion, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, vertexStride, getFloatBuffer(textureData));
        //绘制三角形条带
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, vertexCount);
        //禁止顶点参数数组设置
        GLES30.glDisableVertexAttribArray(avPostion);
        GLES30.glDisableVertexAttribArray(afPostion);
        checkGlError("drawTextureImage");
    }

    public static void drawPolygon() {

        float[] colors = {
                1.0f, 1.0f, 0.0f, 0.0f, //c0
                1.0f, 1.0f, 0.0f, 0.0f, //c0
                1.0f, 1.0f, 0.0f, 0.0f, //c0
                1.0f, 1.0f, 0.0f, 0.0f, //c0

                1.0f, 0.0f, 1.0f, 0.0f,//v4
                1.0f, 0.0f, 1.0f, 0.0f,//v4
                1.0f, 0.0f, 1.0f, 0.0f,//v4
                1.0f, 0.0f, 1.0f, 0.0f,//v4
        };

        float[] positions = {
                0.0f, 0.0f, 0.0f, //v0
                1.0f, 0.0f, 0.0f, //v1
                1.0f, 1.0f, 0.0f,//v2
                0.0f, 1.0f, 0.0f, //v3

                0.3f, 0.3f, 0.0f,//v4
                0.6f, 0.3f, 0.0f, //v5
                0.6f, 0.6f, 0.0f,//v6
                0.3f, 0.6f, 0.0f//v7
        };

        int[] indices1 = {
                0, 1, 2, 0, 2, 3,
        };

        int[] indices2 = {
                4, 5, 6, 4, 6, 7,
        };

        GLES30.glVertexAttribPointer(0, 4, GLES30.GL_FLOAT, false, 0, getFloatBuffer(colors));
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);


        int[] ids = new int[1];
        GLES30.glGenQueries(1, ids, 0);
        GLES30.glBeginQuery(GLES30.GL_ANY_SAMPLES_PASSED, ids[0]);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, indices1.length, GLES30.GL_UNSIGNED_INT, getIntBuffer(indices1));
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, indices2.length, GLES30.GL_UNSIGNED_INT, getIntBuffer(indices2));

        GLES30.glEndQuery(GLES30.GL_ANY_SAMPLES_PASSED);
        int[] params = new int[1];
        GLES30.glGetQueryObjectuiv(ids[0], GLES30.GL_QUERY_RESULT, params, 0);


        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    /**
     * 使用drawElements()的方式来绘制立方体
     */
    public static void drawElementsCubeTriangles() {

        float[] colors = {
//                1.0f, 0.0f, 1.0f, 1.0f,
//                1.0f, 1.0f, 0.0f, 1.0f,
//                0.0f, 1.0f, 1.0f, 1.0f,
//                1.0f, 0.0f, 0.0f, 1.0f,
//                0.0f, 0.0f, 1.0f, 1.0f,
//                0.0f, 1.0f, 0.0f, 1.0f,
//                0.5f, 1.0f, 0.0f, 1.0f,
//                1.0f, 0.5f, 0.0f, 1.0f,

                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,

                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
        };
        float[] positions = {
                0.7f, 0.7f, 0.0f,//v0
                0.0f, 0.7f, 0.0f,//v1
                0.0f, 0.0f, 0.0f,//v2
                0.7f, 0.0f, 0.0f,//v3

                0.9f, 0.3f, 0.0f,//v4
                0.9f, 0.9f, 0.0f,//v5
                0.3f, 0.9f, 0.0f,//v6
                0.3f, 0.3f, 0.0f,//v7
        };
        int[] indices = {
                0, 1, 2, 0, 2, 3,
                0, 3, 4, 0, 4, 5,
                0, 5, 6, 0, 6, 1,

                7, 1, 6, 7, 2, 1,
                7, 5, 4, 7, 6, 5,
                7, 3, 2, 7, 4, 3,
        };
        GLES30.glVertexAttribPointer(0, 4, GLES30.GL_FLOAT, false, 0, getFloatBuffer(colors));
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, getFloatBuffer(positions));
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, indices.length, GLES30.GL_UNSIGNED_INT, getIntBuffer(indices));

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    public static void drawColorPositionSeparate() {
        //使用常量的方式指定Vertext的参数值
        float[] colors = {1.0f, 0.0f, 1.0f, 1.0f};
        GLES30.glVertexAttrib4fv(0, getFloatBuffer(colors));
//        使用顶点数组的形式指定Vertext的参数值a_color
//        float[] colors = {
//                0.0f, 1.0f, 0.0f, 1.0f,
//                0.0f, 0.0f, 1.0f, 1.0f,
//                1.0f, 0.0f, 0.0f, 1.0f
//        };
//        GLES30.glVertexAttribPointer(0,
//                4,
//                GLES30.GL_FLOAT,
//                false,
//                0,
//                getFloatBuffer(colors));


        float[] positions = {
                0.0f, -1f, 0.0f,
                0f, 0f, 0.0f,
                -1f, -1f, 0.0f
        };

        //把顶点数组的形式指定Vertext的参数值a_position
        GLES30.glVertexAttribPointer(1, //被指定属性的index值
                3,//顶点属性指定的分量数量
                GLES30.GL_FLOAT,//数据格式
                false,//非浮点数在转为浮点数时，是否规范化
                0,//获取索引的跨距
                getFloatBuffer(positions));//顶点数据
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
        GLES30.glDisableVertexAttribArray(1);
    }

    public static void drawColorPositionTogether() {
        //把两个color和position两种顶点坐标用一个顶点数组来表示
        float[] vertexs = {
                0.0f, 1.0f, 0.0f, 1.0f,     //c1
                0.0f, 0.5f, 0.0f,           //v1
                0.0f, 0.0f, 1.0f, 1.0f,     //c2
                -0.5f, -0.5f, 0.0f,         //v2
                1.0f, 0.0f, 0.0f, 1.0f,     //c3
                0.5f, -0.5f, 0.0f           //v3
        };
        GLES30.glVertexAttribPointer(0, 4, GLES30.GL_FLOAT, false, 7 * Float.BYTES, getFloatBuffer(vertexs));
        float[] vertexsCopy = new float[vertexs.length - 4];
        System.arraycopy(vertexs, 4, vertexsCopy, 0, vertexsCopy.length);
        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 7 * Float.BYTES, getFloatBuffer(vertexsCopy));

        //如果要使用glVertexAttribPointer传递参数，必须调用glEnableVertexAttribArray()传入需要开启的index
        //需要开启一个index就需要调用一次
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

//        setUniform(esContext);
//        setBlock(esContext);

        //绘制条形图案
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        //画完图形之后，把顶点数组形式传递参数禁用
        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    public static ShortBuffer getShortBuffer(short[] shorts) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(shorts.length * Short.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(shorts);
        shortBuffer.position(0);
        return shortBuffer;
    }

    public static IntBuffer getIntBuffer(int[] ints) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(ints.length * Integer.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(ints);
        intBuffer.position(0);
        return intBuffer;
    }

    public static FloatBuffer getFloatBuffer(float[] floats) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floats.length * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(floats);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public static ByteBuffer getByteBufferFloat(float[] floats) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floats.length * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        for (int i = 0; i < floats.length; i++) {
            byteBuffer.putFloat(i, floats[i]);
        }
        byteBuffer.position(0);
        return byteBuffer;
    }

    public static ByteBuffer getByteBufferShort(short[] shorts) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(shorts.length * Short.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        for (int i = 0; i < shorts.length; i++) {
            byteBuffer.putShort(i, shorts[i]);
        }
        byteBuffer.position(0);
        return byteBuffer;
    }

    public static ByteBuffer getByteBufferInt(int[] ints) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(ints.length * Integer.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        for (int i = 0; i < ints.length; i++) {
            byteBuffer.putInt(i, ints[i]);
        }
        byteBuffer.position(0);
        return byteBuffer;
    }

    public static FloatBuffer getVertices() {
        float[] vertices = {
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(vertices);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public static void checkGlError(String op) {
        int error = GLES30.glGetError();
        if (error != GLES30.GL_NO_ERROR) {
            String msg = op + ": glError 0x" + Integer.toHexString(error);
            Log.e(TAG, msg);
//            throw new RuntimeException(msg);
        }
    }
}
