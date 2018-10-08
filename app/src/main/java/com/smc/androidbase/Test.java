package com.smc.androidbase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/21
 * @description
 */

public class Test {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(2);

        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                //return > 0 o1和o2替换位置
                return o1 - o2;
            }
        });
        System.out.print(list);

    }
}
