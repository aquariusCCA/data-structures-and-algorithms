package com.practice.dsa.leetcode.list;

/**
 * 反转链表
 */
public class Leetcode206 {
    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(3, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);
        System.out.println(o1);
        ListNode n1 = new Leetcode206().reverseList(o1);
        System.out.println(n1);
    }

    public ListNode reverseList(ListNode o1) {
        if (o1 == null || o1.next == null) { // 不足兩個節點
            return o1;
        }
        ListNode n1 = null;
        while (o1 != null) {
            ListNode o2 = o1.next; // 2.
            o1.next = n1; // 3.
            n1 = o1;      // 4.
            o1 = o2;      // 4.
        }
        return n1;
    }
}