package com.smc.androidbase.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/15
 * @description
 */
public class Sort {

    public static void main(String args[]) {

        int number = 10000;
        System.out.println("time1 " + System.currentTimeMillis());
        System.out.println("quickSort " + quickSort(buildList(number)));//快速排序100位->3ms 1000位->15ms 10000位->25ms
        System.out.println("time2 " + System.currentTimeMillis());
        System.out.println("selectSort " + selectSort(buildList(number)));//选择排序100位->1ms 1000位->16ms 10000位->94ms
        System.out.println("time3 " + System.currentTimeMillis());
        System.out.println("boboSort " + boboSort(buildList(number)));//冒泡排序100位->6ms 1000位->51ms 10000位->94ms
        System.out.println("time4 " + System.currentTimeMillis());

    }

    /**
     * 快速排序
     * 原理：先以第一个参数作为基准，大于的放前面，小于的放后面，再分别已快速排序排列这大小两个列表，依次循环
     * 直到列表长度为1
     * @param list
     * @return
     */
    private static List<Integer> quickSort(List<Integer> list) {
        if (null == list) {
            return new ArrayList<>();
        }
        if (list.size() < 2) {
            return list;
        }
        List<Integer> less = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();
        int target = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (target < list.get(i)) {
                greater.add(list.get(i));
            } else {
                less.add(list.get(i));
            }
        }
        List<Integer> result = new ArrayList<>();
        result.addAll(quickSort(greater));
        result.add(target);
        result.addAll(quickSort(less));
        return result;
    }

    /**
     * 选择排序 先从队列中遍历出最大值，放入新队列中，再从剩下队列中，获取最大值，放入队列中，循环到最后一个
     * 时间复杂度就是O(n^2)
     *
     * @param list
     * @return
     */
    private static List<Integer> selectSort(List<Integer> list) {
        List<Integer> sortedList = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int index = selectMax(list);
            sortedList.add(list.get(index));
            list.remove(index);
        }
        return sortedList;
    }

    private static List<Integer> boboSort(List<Integer> list) {
        if (null == list) {
            throw new RuntimeException("list is null or length == 0");
        }
        if (list.size() < 2) {//基线条件
            return list;
        } else {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) < list.get(i + 1)) {
                    int trim = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, trim);
                }
            }
            List<Integer> result = new ArrayList<>();
            result.addAll(boboSort(list.subList(0, list.size() - 1)));
            result.add(list.get(list.size() - 1));
            return result;
        }
    }

    private static int selectMax(List<Integer> list) {
        if (null == list || list.size() < 1) {
            throw new RuntimeException("array is null or length == 0");
        }
        int max = list.get(0);
        int index = 0;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return index;
    }

    private static List<Integer> buildList(int number) {
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(3);
        list.add(1);
        list.add(2);
        list.add(7);
        for (int i = 0; i < number; i++) {
            list.add((int) (Math.random() * 10000));
        }
        return list;
    }

}
