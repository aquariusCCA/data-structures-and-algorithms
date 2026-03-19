package com.practice.dsa.leetcode.list;

public class Leetcode82 {
    // 方法2
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode s = new ListNode(-1, head);
        ListNode p1 = s;
        ListNode p2, p3;
        while ((p2 = p1.next) != null
                && (p3 = p2.next) != null) {
            if (p2.val == p3.val) {
                while ((p3 = p3.next) != null
                        && p3.val == p2.val) {
                }
                // p3 找到了不重复的值
                p1.next = p3;
            } else {
                p1 = p1.next;
            }
        }
        return s.next;
    }

    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 3, 3, 4, 4, 5);
        // ListNode head = ListNode.of(1, 1, 1, 2, 3);
        System.out.println(head);
        System.out.println(new Leetcode82().deleteDuplicates(head));
    }
}