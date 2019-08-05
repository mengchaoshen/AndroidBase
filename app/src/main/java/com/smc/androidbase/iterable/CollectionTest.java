package com.smc.androidbase.iterable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class CollectionTest {

    public static void main(String[] args) {

        Map<String, Integer> map = new LinkedHashMap<>(10, 0.75f, true);
        map.put("数学", 1);
        map.put("语文", 2);
        map.put("英语", 3);
        map.put("生物", 4);

        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println("---");
        //这里如果accessOrder=true的话，会把get()过的参数调整位置到最后
        map.get("英语");
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}
