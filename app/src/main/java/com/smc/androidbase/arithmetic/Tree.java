package com.smc.androidbase.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Tree {

    public static void main(String[] args) {
        BiTree tree = buildTree();
        System.out.println("---递归先序遍历");
        preOrder(tree);
        System.out.println("\n---Stack先序遍历");
        stackPreOrder(tree);
        System.out.println("\n---递归中序遍历");
        inOrder(tree);
        System.out.println("\n---Stack中序遍历");
        stackInOrder(tree);
        System.out.println("\n---递归后序遍历");
        postOrder(tree);
        System.out.println("\n---Stack后序遍历");
        stackPostOrder(tree);
        System.out.println("\n---Queue层次遍历");
        queueLevelOrder(tree);
        System.out.println("\n---huffmanCode");
        huffmanCode(buildHuffmanTree());
        System.out.println("\n---huffmanDecoding");
        huffmanDecoding(buildHuffmanTree(), "00");
    }

    static List<String> huffmanCode(HuffmanTree[] array) {
        int n = array.length;
        List<String> huffmanCodeList = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            HuffmanTree h = array[i];
            if (h.c == null) {
                continue;
            }
            String s = new String();
            int j = i;
            while (h.p != 0) {
                if (array[h.p].l == j) {
                    s = '0' + s;
                } else {
                    s = '1' + s;
                }
                j = h.p;
                h = array[h.p];
            }
            if (!s.isEmpty()) {
                huffmanCodeList.add(array[i].c + s);
                System.out.print(array[i].c + "->" + s + ",");
            }
        }
        return huffmanCodeList;
    }

    static void huffmanDecoding(HuffmanTree[] array, String code) {
        if (null == code || code.isEmpty()) {
            return;
        }
        char[] codeArray = code.toCharArray();
        HuffmanTree t = array[1];
        int i = 0;
        while (null == t.c && i < codeArray.length) {
            if (codeArray[i++] == '0') {
                t = array[t.l];
            } else {
                t = array[t.r];
            }
            if (null != t.c) {
                if (i == codeArray.length) {
                    System.out.println(code + "->" + t.c);
                } else {
                    System.out.println("code(" + code + ")->not find code");
                }
                i = 0;
                continue;
            }
        }
    }

    static void queueLevelOrder(BiTree root) {
        Queue<BiTree> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BiTree t = queue.remove();
            System.out.print(t.d);
            if (null != t.l) {
                queue.add(t.l);
            }
            if (null != t.r) {
                queue.add(t.r);
            }
        }
    }

    static void stackPreOrder(BiTree root) {
        Stack<BiTree> stack = new Stack<>();
        BiTree p = root;
        while (null != p || !stack.empty()) {
            if (null != p) {
                System.out.print(p.d);
                stack.push(p);
                p = p.l;
            } else {
                p = stack.pop().r;
            }
        }
    }

    static void stackInOrder(BiTree root) {
        Stack<BiTree> stack = new Stack<>();
        BiTree p = root;
        while (null != p || !stack.empty()) {
            if (null != p) {
                stack.push(p);
                p = p.l;
            } else {
                System.out.print(stack.peek().d);
                p = stack.pop().r;
            }
        }
    }

    static void stackPostOrder(BiTree root) {

    }

    static void preOrder(BiTree biTree) {
        if (null != biTree) {
            System.out.print(biTree.d);
            preOrder(biTree.l);
            preOrder(biTree.r);
        }
    }

    static void inOrder(BiTree biTree) {
        if (null != biTree) {
            inOrder(biTree.l);
            System.out.print(biTree.d);
            inOrder(biTree.r);
        }
    }

    static void postOrder(BiTree biTree) {
        if (null != biTree) {
            postOrder(biTree.l);
            postOrder(biTree.r);
            System.out.print(biTree.d);
        }
    }


    private static BiTree buildTree() {
        BiTree t4 = new BiTree("4", null, null);
        BiTree t5 = new BiTree("5", null, null);
        BiTree t6 = new BiTree("6", null, null);
        BiTree t2 = new BiTree("2", t4, t5);
        BiTree t3 = new BiTree("3", null, t6);
        BiTree t1 = new BiTree("1", t2, t3);
        return t1;
    }

    private static HuffmanTree[] buildHuffmanTree() {
        HuffmanTree t0 = new HuffmanTree(0, 0, 0);
        HuffmanTree t1 = new HuffmanTree(0, 2, 3);
        HuffmanTree t2 = new HuffmanTree(1, 4, 5);
        HuffmanTree t3 = new HuffmanTree(1, 6, 7);
        HuffmanTree t4 = new HuffmanTree("a", 25, 2, 0, 0);
        HuffmanTree t5 = new HuffmanTree("b", 30, 2, 0, 0);
        HuffmanTree t6 = new HuffmanTree(3, 8, 9);
        HuffmanTree t7 = new HuffmanTree("e", 25, 3, 0, 0);
        HuffmanTree t8 = new HuffmanTree("c", 12, 6, 0, 0);
        HuffmanTree t9 = new HuffmanTree("d", 8, 6, 0, 0);
        HuffmanTree[] array = new HuffmanTree[10];
        array[0] = t0;
        array[1] = t1;
        array[2] = t2;
        array[3] = t3;
        array[4] = t4;
        array[5] = t5;
        array[6] = t6;
        array[7] = t7;
        array[8] = t8;
        array[9] = t9;
        return array;
    }

    static class BiTree {
        String d;
        BiTree l;
        BiTree r;

        public BiTree(String d, BiTree l, BiTree r) {
            this.d = d;
            this.l = l;
            this.r = r;
        }
    }

    static class HuffmanTree {
        String c;
        int w;
        int p;
        int l, r;

        public HuffmanTree(int p, int l, int r) {
            this.p = p;
            this.l = l;
            this.r = r;
        }

        public HuffmanTree(String c, int w, int p, int l, int r) {
            this.c = c;
            this.w = w;
            this.p = p;
            this.l = l;
            this.r = r;
        }
    }
}
