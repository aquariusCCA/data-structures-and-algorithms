package com.practice.dsa.leetcode.list;

public class Leetcode19 {
    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 3, 4, 5);
        // ListNode head = ListNode.of(1,2);
        System.out.println(head);
        System.out.println(new Leetcode19()
                .removeNthFromEnd(head, 2));
    }

    // 方法2
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || head.next == null) return head;
        ListNode s = new ListNode(-1, head);
        ListNode p1 = s;
        ListNode p2 = s;
        for (int i = 0; i <= n; i++) {
            p1 = p1.next;
            p2 = p2.next;
        }
        while (p2 != null){
            p2 = p2.next;
        }
        p1.next = p1.next.next;
        return s.next;
    }
}