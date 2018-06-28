package com.smc.androidbase.iterable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/27
 * @description
 */

public class IterableTest {

    public static void main(String args[]){

        MyIterable<String> iterable = new MyIterable<>();
        iterable.add("a");
        iterable.add("b");

        for(String i : iterable){
            System.out.println(i);
        }

        Map<String, String> map = new HashMap<>();
        map.entrySet();
    }
}
