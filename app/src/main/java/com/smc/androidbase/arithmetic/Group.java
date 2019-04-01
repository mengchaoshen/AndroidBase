package com.smc.androidbase.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Group {


    public static void main(String[] args) {
        System.out.println("\n---dfs");
        dfs(new ArrayList<String>(), buildG(), 0);
        System.out.println("\n---bfs");
        bfs(buildG());
    }

    static void dfs(List<String> addedList, G g, int i) {
        VNode vNode = g.vNodeList.get(i);
        String data = vNode.data;
        System.out.print(data);
        addedList.add(data);
        ArcNode arcNode = vNode.firstArcNode;
        while (null != arcNode) {
            if (!addedList.contains(g.vNodeList.get(arcNode.adjVex).data)) {
                dfs(addedList, g, arcNode.adjVex);
            }
            arcNode = arcNode.nextArcNode;
        }
    }

    static void bfs(G g){
        List<String> addedList = new ArrayList<>();
        List<VNode> vNodeList = g.vNodeList;
        VNode vNode = g.vNodeList.get(0);
        Queue<VNode> queue = new ArrayDeque<>();
        queue.add(vNode);
        while (!queue.isEmpty()){
            VNode v = queue.remove();
            System.out.print(v.data);
            addedList.add(v.data);
            ArcNode a = v.firstArcNode;
            while(null != a){
                VNode vt = vNodeList.get(a.adjVex);
                if(!addedList.contains(vt.data) && !queue.contains(vt)){
                    queue.add(vNodeList.get(a.adjVex));
                }
                a = a.nextArcNode;
            }
        }
    }

    private static G buildG() {
        ArcNode a2 = new ArcNode(2, null);
        ArcNode a1 = new ArcNode(1, a2);
        VNode v1 = new VNode("v1", a1);

        ArcNode a5 = new ArcNode(4, null);
        ArcNode a4 = new ArcNode(3, a5);
        ArcNode a3 = new ArcNode(0, a4);
        VNode v2 = new VNode("v2", a3);

        ArcNode a8 = new ArcNode(6, null);
        ArcNode a7 = new ArcNode(5, a8);
        ArcNode a6 = new ArcNode(0, a7);
        VNode v3 = new VNode("v3", a6);

        ArcNode a10 = new ArcNode(7, null);
        ArcNode a9 = new ArcNode(1, a10);
        VNode v4 = new VNode("v4", a9);

        ArcNode a12 = new ArcNode(7, null);
        ArcNode a11 = new ArcNode(1, a12);
        VNode v5 = new VNode("v5", a11);

        ArcNode a13 = new ArcNode(2, null);
        VNode v6 = new VNode("v6", a13);

        ArcNode a14 = new ArcNode(2, null);
        VNode v7 = new VNode("v7", a14);

        ArcNode a16 = new ArcNode(4, null);
        ArcNode a15 = new ArcNode(3, a16);
        VNode v8 = new VNode("v8", a15);

        List<VNode> list = new ArrayList<>();
        list.add(v1);
        list.add(v2);
        list.add(v3);
        list.add(v4);
        list.add(v5);
        list.add(v6);
        list.add(v7);
        list.add(v8);
        G g = new G(8, list);
        return g;
    }

    static class G {
        int num;
        List<VNode> vNodeList;

        public G(int num, List<VNode> vNodeList) {
            this.num = num;
            this.vNodeList = vNodeList;
        }
    }

    static class VNode {
        String data;
        ArcNode firstArcNode;

        public VNode(String data, ArcNode firstArcNode) {
            this.data = data;
            this.firstArcNode = firstArcNode;
        }
    }


    static class ArcNode {

        int adjVex;
        double weight;
        ArcNode nextArcNode;

        public ArcNode(int adjVex, ArcNode nextArcNode) {
            this.adjVex = adjVex;
            this.nextArcNode = nextArcNode;
        }
    }
}
