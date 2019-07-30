package com.smc.androidbase.opengles;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class OpenglesUtil {

    private final static String TAG = "OpenGLES";

    /**
     * 加载shader
     *
     * @param type      具体shader的类型
     * @param shaderSrc shader源码
     * @return
     */
    public static int loadShader(int type, String shaderSrc) {
        int shader;
        int[] compiled = new int[1];
        //创建shader
        shader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
//        shader = GLES30.glCreateShader(type);
        checkGlError("loadShader");
        Log.d(TAG, "loadShader glCreateShader=" + shader);
        if (shader == 0) {
            return 0;
        }
        //把shader源码加载进去
        GLES30.glShaderSource(shader, shaderSrc);
        //编译shader源码
        GLES30.glCompileShader(shader);
        //获取shader源码编译的结果
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
        //0表示异常
        if (compiled[0] == 0) {
            int[] infoLen = new int[1];
            //获取shader log的长度
            GLES30.glGetShaderiv(shader, GLES30.GL_INFO_LOG_LENGTH, infoLen, 0);
            if (infoLen[0] > 1) {
                //获取shader log具体内容
                String infoLog = GLES30.glGetShaderInfoLog(shader);
                Log.d(TAG, "loadShader error : " + infoLog);
            }
            //删除shader
            GLES30.glDeleteShader(shader);
            return -1;
        }
        return shader;
    }

    public static void checkGlError(String op) {
        int error = GLES30.glGetError();
        if (error != GLES30.GL_NO_ERROR) {
            String msg = op + ": glError 0x" + Integer.toHexString(error);
            Log.e(TAG, msg);
            throw new RuntimeException(msg);
        }
    }

}
