package com.smc.androidbase.test;

/**
 * @author shenmengchao
 * @version 1.0.0
 */
public class Test {
    /**
     * 结点对象
     */
    public static class ListNode {
        public ListNode(int val, ListNode nxt) {
            this.val = val;
            this.nxt = nxt;
        }

        int val; // 结点的值
        ListNode nxt; // 下一个结点
    }

    public static void main(String[] args) {
        ListNode n3 = new ListNode(3, null);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(1, n2);

        get(n1);
    }

    public static void get(ListNode node) {
        if (node != null) {
            get(node.nxt);
            System.out.println("value:" + node.val);
        }
    }
}
