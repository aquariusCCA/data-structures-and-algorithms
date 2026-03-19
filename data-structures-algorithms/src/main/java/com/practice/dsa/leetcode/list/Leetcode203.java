package com.practice.dsa.leetcode.list;

public class Leetcode203 {
    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 6, 3, 6);
        // ListNode head = ListNode.of(7, 7, 7, 7);
        System.out.println(head);
        System.out.println(new Leetcode203()
                .removeElements(head, 6));
    }

    /**
     * @param p   链表头
     * @param val 目标值
     * @return 删除后的链表头
     */
    public ListNode removeElements(ListNode p, int val) {
        // 1 -> 2 -> 6 -> 3 -> null
        if (p == null) {
            return null;
        }

        if (p.val == val) {
            return removeElements(p.next, val);
        } else {
            ListNode node = removeElements(p.next, val);
            p.next = node;
            return p;
        }
    }
}