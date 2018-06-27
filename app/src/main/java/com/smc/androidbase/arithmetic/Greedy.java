package com.smc.androidbase.arithmetic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/27
 * @description 贪婪算法
 */

public class Greedy {

    public static void main(String[] args) {
        Greedy greedy = new Greedy();
        Set<String> result = greedy.greedy(buildStationMap(), buildNeedState());
        System.out.println(result);
    }

    /**
     * 使用贪婪算法，计算集合填满问题
     * @param stationMap
     * @param needStateSet
     * @return
     */
    private Set<String> greedy(Map<String, Set<String>> stationMap, Set<String> needStateSet) {
        Set<String> selectStation = new HashSet<>();
        while (needStateSet.size() > 0) {
            int maxSize = 0;
            String station = "";
            for (Map.Entry<String, Set<String>> entry : stationMap.entrySet()) {
                if (getIntersectionSize(needStateSet, entry.getValue()) > maxSize) {
                    maxSize = getIntersectionSize(needStateSet, entry.getValue());
                    station = entry.getKey();
                }
            }
            needStateSet.removeAll(stationMap.get(station));
            stationMap.remove(station);
            selectStation.add(station);
        }
        return selectStation;
    }

    private static Set<String> buildNeedState() {
        Set<String> set = new HashSet<>();
        set.add("mt");
        set.add("wa");
        set.add("or");
        set.add("id");
        set.add("nv");
        set.add("ut");
        set.add("ca");
        set.add("az");
        return set;
    }

    private static Map<String, Set<String>> buildStationMap() {
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> oneSet = new HashSet<>();
        oneSet.add("id");
        oneSet.add("nv");
        oneSet.add("ut");
        map.put("kone", oneSet);

        Set<String> twoSet = new HashSet<>();
        twoSet.add("wa");
        twoSet.add("id");
        twoSet.add("mt");
        map.put("ktwo", twoSet);

        Set<String> threeSet = new HashSet<>();
        threeSet.add("or");
        threeSet.add("nv");
        threeSet.add("ca");
        map.put("kthree", threeSet);

        Set<String> fourSet = new HashSet<>();
        fourSet.add("nv");
        fourSet.add("ut");
        map.put("kfour", fourSet);

        Set<String> fiveSet = new HashSet<>();
        fiveSet.add("ca");
        fiveSet.add("az");
        map.put("kfive", fiveSet);
        return map;
    }

    /**
     * 计算两个Set交集的个数
     *
     * @param set1
     * @param set2
     * @return
     */
    private int getIntersectionSize(Set<String> set1, Set<String> set2) {
        Set<String> set = new HashSet<>();
        set.addAll(set1);
        set.retainAll(set2);
        return set.size();
    }

}
