package com.smc.androidbase.test;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ParameterNames {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] arrays) throws NoSuchMethodException {

        Method method = ParameterNames.class.getMethod("main", String[].class);
        for (Parameter parameter : method.getParameters()) {
            System.out.println("Parameter:" + parameter.getName());
        }
    }
}
