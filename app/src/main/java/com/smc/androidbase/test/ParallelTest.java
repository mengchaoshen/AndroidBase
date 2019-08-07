package com.smc.androidbase.test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ParallelTest {

    public static void main(String[] args) {

        long[] arrayOfLong = new long[2000000];
        //使用parallelSetAll生成20000个1000000以内的随机数
        Arrays.parallelSetAll(arrayOfLong, index -> ThreadLocalRandom.current().nextInt(1000000));
        System.out.println("Unsorted:");
        //打印出前10个（未排序）
        Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + ","));

        System.out.println("");
        System.out.println("Sorted:");
        //使用parallelSort进行并行排序
        long c1 = System.currentTimeMillis();
        //sort 498ms  parallelSort 238ms
        //使用parallelSort排序在大量数据多核cpu的情况下，可以提升排序的性能
        Arrays.parallelSort(arrayOfLong);
        long c2 = System.currentTimeMillis();
        System.out.println("duration:" + (c2 - c1));
        Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + ","));
    }
}
