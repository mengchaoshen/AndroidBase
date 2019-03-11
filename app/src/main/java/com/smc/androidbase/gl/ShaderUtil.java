package com.smc.androidbase.gl;

import android.opengl.GLES20;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ShaderUtil {

    private final static String TAG = ShaderUtil.class.getSimpleName();

    public static int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (0 == vertexShader) {
            return 0;
        }
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
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

    public static int loadShader(int shaderType, String shaderSource) {
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
}
