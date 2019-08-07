package com.smc.androidbase.test;

import java.util.Optional;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class OptionalTest {

    public static void main(String[] args) {

        //设置一个Optional类型的参数
        Optional<String> optional = Optional.ofNullable("abc");
        //isPresent()返回参数值是否为null
        System.out.println("Full name is set? " + optional.isPresent());
        //orElseGet返回参数值或者返回函数中的值
        System.out.println("Full name: " + optional.orElseGet(() -> "[none]"));
        //map将返回值映射成函数中的表达式，如果为null的话，返回orElse返回的值
        System.out.println(optional.map(s -> "Hey " + s + "!").orElse("Hey Stranger"));
    }
}
