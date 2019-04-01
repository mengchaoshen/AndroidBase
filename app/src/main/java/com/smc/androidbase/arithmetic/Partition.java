package com.smc.androidbase.arithmetic;

/**
 * @author shenmengchao
 * @version 1.0.0
 *          分治算法汇总
 */

public class Partition {

    public static void main(String[] args) {

        int[] a = {22, 11, -9, 3, 5, 2};
        int maxSubSum = maxSubSum(a, 0, a.length - 1);
        System.out.println(maxSubSum);
    }

    /**
     * 最大子段和算法
     *
     * @param a 需要计算的数组
     * @param l 数组左边界
     * @param r 数组右边界
     * @return
     */
    static int maxSubSum(int[] a, int l, int r) {
        if (l == r) {
            if (a[l] > 0) {
                return a[l];
            } else {
                return 0;
            }
        }
        int c = (l + r) / 2;
        int left = maxSubSum(a, l, c);
        int right = maxSubSum(a, c + 1, r);

        int maxLeft = 0;
        int sumLeft = 0;
        for (int i = c; i >= l; i--) {
            sumLeft += a[i];
            maxLeft = Math.max(sumLeft, maxLeft);
        }
        int maxRight = 0;
        int sumRight = 0;
        for (int i = c + 1; i <= r; i++) {
            sumRight += a[i];
            maxRight = Math.max(sumRight, maxRight);
        }
        int maxCenter = maxLeft + maxRight;
        return Math.max(Math.max(left, right), maxCenter);
    }
}
