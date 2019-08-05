package com.smc.androidbase.test;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class FinallyTest {

    public static void main(String[] args) {
        System.out.println(test());
//        System.out.println(test2());
    }

    /**
     * 执行结果为：try block
     * finally block
     * b > 25, b=100
     * 足以证明finally{}代码块执行的顺序是在return语句之后
     *
     * @return
     */
    private static int test() {
        int b = 20;
        try {
            System.out.println("try block");
            return b += 80;
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
            if (b > 25) {
                System.out.println("b > 25, b=" + b);
            }
            return 200;
        }
//        return b;
    }

    /**
     * 执行结果为try block
     * finally block
     * return block
     * 足以证明，finally{}语句块执行的顺序是在return返回结束之前
     *
     * @return
     */
    private static String test2() {
        try {
            System.out.println("try block");
        } finally {
            System.out.println("finally block");
        }
        return "return block";
    }
}
