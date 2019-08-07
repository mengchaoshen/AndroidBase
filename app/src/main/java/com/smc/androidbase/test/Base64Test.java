package com.smc.androidbase.test;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Base64Test {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {

        //使用Base64进行编码和解码
        String text = "Base64 finally in Java 8!";
        String encoded = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        System.out.println("encoded : " + encoded);
        String decoded = new String(Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8)));
        System.out.println("decoded : " + decoded);
    }
}
