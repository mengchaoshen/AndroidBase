package com.smc.androidbase.opengles;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ShaderUtil {

    private final static String TAG = ShaderUtil.class.getSimpleName();

    /**
     * 读取raw中的文件
     *
     * @param context
     * @param rawId
     * @return
     */
    public static String readRawTxt(Context context, int rawId) {
        InputStream inputStream = context.getResources().openRawResource(rawId);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String line = null;
            try {
                line = bufferedReader.readLine();
                if (null != line) {
                    stringBuilder.append(line).append("\n");
                } else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

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
        shader = GLES30.glCreateShader(type);
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
                Log.e(TAG, "loadShader error : " + infoLog);
            }
            //删除shader
            GLES30.glDeleteShader(shader);
            return -1;
        }
        return shader;
    }
}
