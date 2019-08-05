package com.smc.androidbase.test;


import java.util.Arrays;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Java8Test {

    public static void main(String[] args) {
        lambdaTest();
    }

    private static void lambdaTest() {
        //使用lambda表达式，把一个方法作为参数传递给方法
//        Arrays.asList("a", "b", "c").forEach(e -> System.out.println(e));
//        Arrays.asList("a", "b", "c").forEach((String e) -> System.out.println(e));
        int a = 10;//在lambda表达式中使用的局部变量将会自动添加final关键字
        Arrays.asList("a", "b", "c").forEach((String e) -> {
            System.out.println(e + a);
            return;
//            System.out.println(e);
        });
    }

    /**
     * 方法引用
     */
    private static void methodQuoteTest(){

    }

}
