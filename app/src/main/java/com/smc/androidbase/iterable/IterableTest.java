package com.smc.androidbase.iterable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/27
 * @description
 */

public class IterableTest {

    public static void main(String args[]) {

        MyIterable<String> iterable = new MyIterable<>();
        iterable.add("a");
        iterable.add("b");

        for (String i : iterable) {
            System.out.println(i);
        }

        Map<String, String> map = new HashMap<>();
        map.entrySet();

        //数组快速转list
        List<String> list = Arrays.asList("1", "2");

        //List快速转数组
        String[] array = (String[]) list.toArray();

        int[] i1 = new int[10];
        i1[0] = 1;
        int[] i2 = new int[10];
        i2[1] = 2;

        //是将src数组从srcPosition开始复制到dest数组的destPos位置，复制长度是length
        System.arraycopy(i1, 0, i2, 0, 1);
        System.out.println(i1);
        System.out.println(i2);

    }
}
