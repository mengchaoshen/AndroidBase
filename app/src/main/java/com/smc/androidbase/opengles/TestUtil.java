package com.smc.androidbase.opengles;

import android.opengl.GLES30;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class TestUtil {

    private final static String TAG = TestUtil.class.getSimpleName();

    public static void testMaxVertexAttribs() {

        int[] vertexs = new int[10];
        //可以指定的顶点属性的最大数量
        GLES30.glGetIntegerv(GLES30.GL_MAX_VERTEX_ATTRIBS, vertexs, 0);
        //可以使用的vec4统一变量的最大数量
        GLES30.glGetIntegerv(GLES30.GL_MAX_VERTEX_UNIFORM_VECTORS, vertexs, 1);
        //
        GLES30.glGetIntegerv(GLES30.GL_MAX_VARYING_VECTORS, vertexs, 2);
        //可以用的纹理单元的最大数量
        GLES30.glGetIntegerv(GLES30.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS, vertexs, 3);
        //顶点和片段着色器可用的纹理单元最大数量
        GLES30.glGetIntegerv(GLES30.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, vertexs, 4);

        Log.d(TAG, "GL_MAX_VERTEX_ATTRIBS=" + vertexs[0]);
        Log.d(TAG, "GL_MAX_VERTEX_UNIFORM_VECTORS=" + vertexs[1]);
        Log.d(TAG, "GL_MAX_VARYING_VECTORS=" + vertexs[2]);
        Log.d(TAG, "GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS=" + vertexs[3]);
        Log.d(TAG, "GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS=" + vertexs[4]);
    }


    public static String MATRIX_CHANGE_DEFINE =
            "#define NUM_TEXTURE 2                                                  \n" +
                    "uniform mat4 tex_matrix[NUM_TEXTURE]                           ;\n" +
                    "uniform bool enable_tex[NUM_TEXTURE]                           ;\n" +
                    "uniform bool enable_tex_matrix[NUM_TEXTURE]                           ;\n" +
                    "in vec4 a_texcoord0                                            ;\n" +
                    "in vec4 a_texcoord1                                            ;\n" +
                    "";

    public static String MATRIX_CHANGE_MAIN =
            "v_texcood[0] = vec4(0.0, 0.0, 0.0, 1.0)                                ;\n" +
                    "if(enable_tex[0]){                                             \n" +
                    "if(enable_tex_matrix[0])                                       \n" +
                    "v_texcood[0] = tex_matrix[0] * a_texcoord0                       ;\n" +
                    "else                                                           \n" +
                    "v_texcood[0] = * a_texcoord0                                   ;\n" +
                    "}                                                              \n"+
                    "";
}
