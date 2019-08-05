package com.smc.androidbase.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class GenericTest {

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {

        Class<Book> clazz = Book.class;
        Field field = clazz.getDeclaredField("map");
        field.getType();//getType()只能获取直接的Type，要获取泛型的类型需要使用getGenericType
        Type type = field.getGenericType();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type rawType = parameterizedType.getRawType();//用来获取本身的类型
        Type[] types = parameterizedType.getActualTypeArguments();//获取了类型所带泛型数组
        System.out.println("---");

        Method method = clazz.getDeclaredMethod("getMap");
        boolean isAnnotationPresent = method.isAnnotationPresent(MyAnnotation.class);
        if (isAnnotationPresent) {
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            String name = myAnnotation.name();
            System.out.println("name=" + name);
        }
    }
}
