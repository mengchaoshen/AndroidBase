package com.smc.androidbase.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/20
 * @description 广度优先算法，找出谁是关系最近的芒果经销商
 */
public class BreadthFirstSearch {

    public static void main(String[] args) {
        People people = breadthFirstSearch(buildMap(), 1);
        System.out.println("name:" + people.getName());
    }

    private static People breadthFirstSearch(Map<Integer, List<People>> map, int id) {
        Queue<People> queue = new LinkedBlockingDeque<>();
        List<Integer> checkedList = new ArrayList<>();
        checkedList.add(id);
        queue.addAll(map.get(id));
        while (!queue.isEmpty()) {
            People people = queue.poll();
            if (people.isMango()) {
                return people;
            } else {
                List<People> list = map.get(people.getId());
                if (!checkedList.contains(people.getId())) {
                    queue.addAll(list);
                } else {
                    checkedList.add(people.getId());
                }
            }
        }
        throw new RuntimeException("can not find any mango!!!");
    }

    private static Map<Integer, List<People>> buildMap() {
        People a = buildPeople(1, false, "A");
        People b = buildPeople(2, false, "B");
        People c = buildPeople(3, false, "C");
        People d = buildPeople(4, false, "D");
        People e = buildPeople(5, false, "E");
        People f = buildPeople(6, false, "F");
        People g = buildPeople(7, false, "G");
        People h = buildPeople(8, false, "H");

        Map<Integer, List<People>> map = new HashMap<>();
        map.put(a.getId(), buildList(b, c, d));
        map.put(b.getId(), buildList(a));
        map.put(c.getId(), buildList(e, g));
        map.put(d.getId(), buildList(f));
        map.put(e.getId(), buildList());
        map.put(f.getId(), buildList());
        map.put(g.getId(), buildList(h));
        map.put(h.getId(), buildList());
        return map;
    }

    private static List<People> buildList(People... array) {
        List<People> list = Arrays.asList(array);
        return list;
    }

    private static People buildPeople(int id, boolean isMango, String name) {
        People people = new People();
        people.setId(id);
        people.setMango(isMango);
        people.setName(name);
        return people;
    }

    static class People {
        int id;
        boolean isMango;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isMango() {
            System.out.println("isMango name:" + name);
            return isMango;
        }

        public void setMango(boolean mango) {
            isMango = mango;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
