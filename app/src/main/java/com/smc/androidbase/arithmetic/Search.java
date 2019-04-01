package com.smc.androidbase.arithmetic;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Search {

    public static void main(String[] args) {
        int[] array = {1, 3, 5, 9, 10, 21, 43, 56};
        int key = 10;
        System.out.println("---bSearch");
        int result = bSearch(array, key);
        System.out.println(result);
        System.out.println("---bSearchRec");
        int result1 = bSearchRec(array, array.length - 1, 0, key);
        System.out.println(result1);
        System.out.println("---searchBTS");
        BSTree tree = searchBTS(buildTree(), 53, null);
        if (null == tree) {
            System.out.println("not find");
        } else {
            System.out.println(tree.d + "");
        }
    }

    static BSTree searchBTS(BSTree tree, int key, BSTree father) {
        father = tree;
        if (tree.d == key) {
            return tree;
        } else if (key > tree.d) {
            if (tree.r == null) {
                return null;
            } else {
                return searchBTS(tree.r, key, father);
            }
        } else {
            if (tree.l == null) {
                return null;
            } else {
                return searchBTS(tree.l, key, father);
            }
        }
    }

    static int insertBST(BSTree root, int e) {
        BSTree father = null;
        BSTree target = searchBTS(root, e, father);
        if (target != null) {
            return -1;
        } else {
            BSTree insert = new BSTree(e, null, null);
            if (e > father.d) {
                father.r = insert;
            } else {
                father.l = insert;
            }
            return 1;
        }
    }

    static int bSearch(int[] array, int key) {
        if (null == array || array.length == 0) {
            return -1;
        }
        int h = array.length - 1;
        int l = 0;
        int m = 0;
        while (h >= l) {
            m = (h + l) / 2;
            if (key == array[m]) {
                return m;
            } else if (key > array[m]) {
                l = m + 1;
            } else {
                h = m - 1;
            }
        }
        return -1;
    }

    static BSTree buildTree() {
        BSTree b9 = new BSTree(91, null, null);
        BSTree b8 = new BSTree(62, null, b9);
        BSTree b7 = new BSTree(25, null, null);
        BSTree b6 = new BSTree(101, b8, null);
        BSTree b5 = new BSTree(38, b7, null);
        BSTree b4 = new BSTree(4, null, null);
        BSTree b3 = new BSTree(54, null, b6);
        BSTree b2 = new BSTree(13, b4, b5);
        BSTree b1 = new BSTree(46, b2, b3);
        return b1;
    }

    static int bSearchRec(int[] array, int h, int l, int key) {
        if (h < l) {
            return -1;
        }
        int m = (h + l) / 2;
        if (key == array[m]) {
            return m;
        } else if (key > array[m]) {
            return bSearchRec(array, h, m + 1, key);
        } else {
            return bSearchRec(array, m - 1, l, key);
        }
    }

    public static class BSTree {
        int d;
        BSTree l;
        BSTree r;

        public BSTree(int d, BSTree l, BSTree r) {
            this.d = d;
            this.l = l;
            this.r = r;
        }
    }
}
