package com.smc.androidbase.arithmetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/6
 * @description
 */

public class KNN {

    public static void main(String args[]) {
        double breadNumber = forecastBreadNumber(new int[]{4, 1, 0});
        System.out.println(breadNumber);
    }

    /**
     * 使用最邻近算法，根据今天的条件，计算出今天应该准备多少面包
     * 首先会给定6天，有每天的天气指数，是否周末，有没有活动，以及最终卖出去面包数量
     * 然后根据今天的这些调节，计算哪四天跟今天更接近，取这四天的平均值，就是今天大概卖出面包数量
     *
     * @param condition
     * @return
     */
    private static double forecastBreadNumber(int[] condition) {
        List<DailyBread> recordList = buildRecord();
        Map<Double, Integer> checkedMap = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o1 > o2 ? 1 : -1;
            }
        });
        for (DailyBread dailyBread : recordList) {
            double calculateDistance = calculateDistance(dailyBread.getCondition(), condition);
            checkedMap.put(calculateDistance, dailyBread.getBreadNumber());
        }
        return getAverageBreadNumber(checkedMap);
    }

    private static double calculateDistance(int[] condition1, int[] condition2) {
        int sum = 0;
        for (int i = 0; i < condition1.length; i++) {
            sum += Math.pow((condition1[i] - condition2[i]), 2);
        }
        double result = Math.sqrt(sum);
        return result;
    }

    private static double getAverageBreadNumber(Map<Double, Integer> map) {
        int sum = 0;
        int i = 0;
        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
            if (i > 3) {
                break;
            }
            i++;
            sum += entry.getValue();
        }
        double result = sum / 4.0;
        return result;
    }

    private static List<DailyBread> buildRecord() {
        List<DailyBread> list = new ArrayList<>();
        list.add(new DailyBread(new int[]{5, 1, 0}, 300));
        list.add(new DailyBread(new int[]{3, 1, 1}, 225));
        list.add(new DailyBread(new int[]{1, 1, 0}, 75));
        list.add(new DailyBread(new int[]{4, 0, 1}, 200));
        list.add(new DailyBread(new int[]{4, 0, 0}, 150));
        list.add(new DailyBread(new int[]{2, 0, 0}, 50));
        return list;
    }

    static class DailyBread {
        int[] condition = new int[3];
        int breadNumber;

        public DailyBread(int[] condition, int breadNumber) {
            this.condition = condition;
            this.breadNumber = breadNumber;
        }

        public int[] getCondition() {
            return condition;
        }

        public void setCondition(int[] condition) {
            this.condition = condition;
        }

        public int getBreadNumber() {
            return breadNumber;
        }

        public void setBreadNumber(int breadNumber) {
            this.breadNumber = breadNumber;
        }
    }

}
