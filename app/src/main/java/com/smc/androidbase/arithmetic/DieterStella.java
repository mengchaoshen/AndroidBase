package com.smc.androidbase.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/25
 * @description 狄克斯特拉算法 找出两点间加权最短路径
 * TODO 疑惑1：书上说道，有边的权重是负数时，狄克斯特拉会不适用，需要使用贝尔曼-福德算法，但是我现在测试负数边权重，计算结果还是正确的
 * TODO 疑惑1理解：如书上例子，如果出现负边时，跟新终点的costs表只能是在处理B节点时，而B节点处理时到达B节点最短距离是0，
 * TODO 故得出结论，到达终点的最短距离是0+35=35，而后面处理A节点时，得到前往B节点最短距离却是-2，但是此时B节点已经处理过了，
 * TODO 所以到达终点的距离被锁定在35
 * TODO 疑惑1小结：如果出现负边，costs表计算会错误，但是parent表计算是正确的，所以通过parent表再计算一遍最短距离，最短距离就正确了
 *
 */

public class DieterStella {

    public static void main(String[] args) {
        DieterStella dieterStella = new DieterStella();
        String result = dieterStella.findLowest();
        System.out.println("result = " + result);
    }

    private String findLowest() {
        Map<String, List<Point>> graph = buildGraph();
        Map<String, Integer> costs = buildCosts(graph);
        Map<String, String> parent = buildParent(graph);
        List<String> processedList = new ArrayList<>();

        //先从路径表中，获取起点可以到达路径最短的节点
        String node = findLowestCostNode(costs, processedList);
        //如果这个节点为空，就表示所有cost表中都遍历完毕
        while (null != node && !node.equals("")) {
            //找出最短距离的所有子节点
            List<Point> neighbors = graph.get(node);
            for (Point neighbor : neighbors) {
                //如果从这个节点，到达子节点，比直接从启动去子节点距离要短，就跟新去子节点cost表和子节点parent表
                if (costs.get(neighbor.getName()) > costs.get(node) + neighbor.getCost()) {
                    costs.put(neighbor.getName(), costs.get(node) + neighbor.getCost());
                    parent.put(neighbor.getName(), node);
                }
            }
            //处理完这个节点，就把它加入处理完的列表中
            processedList.add(node);
            node = findLowestCostNode(costs, processedList);
        }
        return buildResult(parent);
    }

    private String findLowestCostNode(Map<String, Integer> costs, List<String> processedList) {
        int lowestCost = Integer.MAX_VALUE;
        String lowestCostName = "";
        for (Map.Entry<String, Integer> key : costs.entrySet()) {
            if (key.getValue() < lowestCost && !processedList.contains(key.getKey())) {
                lowestCost = key.getValue();
                lowestCostName = key.getKey();
            }
        }
        return lowestCostName;
    }

    private String buildResult(Map<String, String> parent) {
        List<String> list = new ArrayList<>();
        list.add("end");
        String p = parent.get("end");
        list.add(0, p);
        while (!"start".equals(p)) {
            p = parent.get(p);
            list.add(0, p);
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            if (!"end".equals(s)) {
                sb.append("->");
            }
        }
        return sb.toString();
    }

    /**
     * 用Map来表示有向图，key是当前节点的名字，value是它的邻居的列表
     * @return
     */
//    private Map<String, List<Point>> buildGraph() {
//        Map<String, List<Point>> graph = new HashMap<>();
//
//        List<Point> listStart = new ArrayList<>();
//        listStart.add(new Point("A", 6));
//        listStart.add(new Point("B", 3));
//        graph.put("start", listStart);
//
//        List<Point> listA = new ArrayList<>();
//        listA.add(new Point("end", 1));
//        graph.put("A", listA);
//
//        List<Point> listB = new ArrayList<>();
//        listB.add(new Point("A", 2));
//        listB.add(new Point("end", 5));
//        graph.put("B", listB);
//
//        List<Point> listEnd = new ArrayList<>();
//        graph.put("end", listEnd);
//        return graph;
//    }

    /**
     * 用Map来表示有向图,稍微复杂的图
     *
     * @return
     */
//    private Map<String, List<Point>> buildGraph() {
//        Map<String, List<Point>> graph = new HashMap<>();
//
//        List<Point> listStart = new ArrayList<>();
//        listStart.add(new Point("A", 5));
//        listStart.add(new Point("B", 2));
//        graph.put("start", listStart);
//
//        List<Point> listA = new ArrayList<>();
//        listA.add(new Point("C", 4));
//        listA.add(new Point("D", 2));
//        graph.put("A", listA);
//
//        List<Point> listB = new ArrayList<>();
//        listB.add(new Point("A", 8));
//        listB.add(new Point("D", 7));
//        graph.put("B", listB);
//
//        List<Point> listC = new ArrayList<>();
//        listC.add(new Point("end", 3));
//        listC.add(new Point("D", 6));
//        graph.put("C", listC);
//
//        List<Point> listD = new ArrayList<>();
//        listD.add(new Point("end", 1));
//        graph.put("D", listD);
//
//        List<Point> listEnd = new ArrayList<>();
//        graph.put("end", listEnd);
//        return graph;
//    }

    /**
     * 带循环的图
     * @return
     */
//    private Map<String, List<Point>> buildGraph() {
//        Map<String, List<Point>> graph = new HashMap<>();
//
//        List<Point> listStart = new ArrayList<>();
//        listStart.add(new Point("A", 10));
//        graph.put("start", listStart);
//
//        List<Point> listA = new ArrayList<>();
//        listA.add(new Point("B", 20));
//        graph.put("A", listA);
//
//        List<Point> listB = new ArrayList<>();
//        listB.add(new Point("C", 1));
//        listB.add(new Point("end", 30));
//        graph.put("B", listB);
//
//        List<Point> listC = new ArrayList<>();
//        listC.add(new Point("A", 1));
//        graph.put("C", listB);
//
//        List<Point> listEnd = new ArrayList<>();
//        graph.put("end", listEnd);
//        return graph;
//    }

    /**
     * 带负数的图
     * @return
     */
//    private Map<String, List<Point>> buildGraph() {
//        Map<String, List<Point>> graph = new HashMap<>();
//
//        List<Point> listStart = new ArrayList<>();
//        listStart.add(new Point("A", 2));
//        listStart.add(new Point("B", 2));
//        graph.put("start", listStart);
//
//        List<Point> listA = new ArrayList<>();
//        listA.add(new Point("C", 2));
//        listA.add(new Point("end", 2));
//        graph.put("A", listA);
//
//        List<Point> listB = new ArrayList<>();
//        listB.add(new Point("A", 2));
//        graph.put("B", listB);
//
//        List<Point> listC = new ArrayList<>();
//        listC.add(new Point("B", -1));
//        listC.add(new Point("end", 2));
//        graph.put("C", listB);
//
//        List<Point> listEnd = new ArrayList<>();
//        graph.put("end", listEnd);
//        return graph;
//    }

    private Map<String, List<Point>> buildGraph() {
        Map<String, List<Point>> graph = new HashMap<>();

        List<Point> listStart = new ArrayList<>();
        listStart.add(new Point("A", 5));
        listStart.add(new Point("B", 0));
        graph.put("start", listStart);

        List<Point> listA = new ArrayList<>();
        listA.add(new Point("B", -7));
        graph.put("A", listA);

        List<Point> listB = new ArrayList<>();
        listB.add(new Point("end", 35));
        graph.put("B", listB);

        List<Point> listEnd = new ArrayList<>();
        graph.put("end", listEnd);
        return graph;
    }

    /**
     * 起点去各个节点需要的路程，如果不是直接到达的，使用无穷大表示
     *
     * @return
     */
    private Map<String, Integer> buildCosts(Map<String, List<Point>> group) {
        Map<String, Integer> costs = new HashMap<>();
        for (Map.Entry<String, List<Point>> entry : group.entrySet()) {
            String name = entry.getKey();
            List<Point> list = entry.getValue();
            for (Point point : list) {
                if (!costs.containsKey(point)) {
                    int cost = "start".equals(name) ? point.getCost() : Integer.MAX_VALUE;
                    costs.put(point.getName(), cost);
                }
            }
        }
        return costs;
    }


    /**
     * 各个节点的父节点
     *
     * @return
     */
    private Map<String, String> buildParent(Map<String, List<Point>> group) {
        Map<String, String> parent = new HashMap<>();
        for (Map.Entry<String, List<Point>> entry : group.entrySet()) {
            String name = entry.getKey();
            List<Point> list = entry.getValue();
            for (Point point : list) {
                if (!parent.containsKey(point)) {
                    parent.put(point.getName(), name);
                }
            }
        }
        return parent;
    }


    static class Point {
        String startName;
        String name;
        int cost;

        public Point(String name, int cost) {
            this.name = name;
            this.cost = cost;
        }

        public Point(String startName, String name, int cost) {
            this.startName = startName;
            this.name = name;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }
    }


}
