package com.smc.androidbase.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/14
 * @description 使用分而治之的思路来解决问题
 */
public class DivideAndConquer {

    public static void main(String args[]) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(8);
        list.add(10);

        System.out.println("max square = " + findMaxSquare(1680, 640));
        System.out.println("sum = " + sum(list));
        System.out.println("max = " + findMax(list));
        System.out.println("binarySearch index = " + binarySearch(list, 10));
    }

    /**
     * 给出一个矩形，计算出可以分割的最大方形边长
     * 其实就是计算两个数的最大公约数
     *
     * @param width
     * @param height
     * @return
     */
    private static int findMaxSquare(int width, int height) {
        if (width < height) {//保证width > height
            int trim = width;
            width = height;
            height = trim;
        }
        if (width % height == 0) {//基线条件
            return Math.min(width, height);
        } else {//递归条件
            return findMaxSquare(height, width % height);
        }
    }

    /**
     * 使用递归的方法，来求一个list的和
     *
     * @param list
     * @return
     */
    private static int sum(List<Integer> list) {
        if (null == list || list.size() == 0) {
            return 0;
        }
        if (list.size() == 1) {//基线条件
            return list.get(0);
        } else {//递归条件
            return list.get(0) + sum(list.subList(1, list.size()));
        }
    }

    /**
     * 使用递归的方法，找出列表的最大值
     *
     * @param list
     * @return
     */
    private static int findMax(List<Integer> list) {
        if (null == list || list.size() == 0) {
            throw new RuntimeException("list is null or list size is 0");
        }
        if (list.size() == 1) {//基线
            return list.get(0);
        } else {//递归条件
            return Math.max(list.get(0), findMax(list.subList(1, list.size())));
        }
    }

    /**
     * 使用递归的方法，二分查找列表中符合条件的数字
     *
     * @param list
     * @param target
     * @return
     */
    private static int binarySearch(List<Integer> list, int target) {
        if (null == list || list.size() == 0) {
            throw new RuntimeException("list is null or list size is 0");
        }
        if (list.size() == 1 && list.get(0) != target) {
            throw new RuntimeException("can not find target");
        }
        int index = list.size() / 2;
        if (target == list.get(index)) {//基线
            return index;
        } else {//递归
            if (target > list.get(index)) {
                return index + 1 + binarySearch(list.subList(index + 1, list.size()), target);
            } else {
                return binarySearch(list.subList(0, index), target);
            }
        }
    }
}
