package com.smc.androidbase.opengles;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ShaderUtil {

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
}
