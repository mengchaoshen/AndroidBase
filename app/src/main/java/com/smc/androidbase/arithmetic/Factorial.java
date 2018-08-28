package com.smc.androidbase.arithmetic;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/13
 * @description 使用递归，实现阶乘
 */
public class Factorial {

    public static void main(String args[]) {
        Long l1 = 0x90000000l;
        long l2 = 0x90000000l;
        System.out.println("long l1 = " + l1);
        System.out.println("long l2 = " + l2);
        int result = factorial(6);
        System.out.println("result = " + result);
    }

    private static int factorial(int number) {
        if (number < 1) {
            throw new RuntimeException("number cannot less than 1");
        }
        if (number == 1) {//基线条件
            return number;
        } else {//递归条件
            return number * factorial(number - 1);
        }
    }
}
