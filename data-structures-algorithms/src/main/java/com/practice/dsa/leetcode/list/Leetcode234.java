package com.practice.dsa.leetcode.list;

/**
 * 判断回文链表
 */
public class Leetcode234 {
    /*
        步骤1. 找中间点的同时反转前半个链表
        步骤2. 反转后的前半个链表 与 中间点开始的后半个链表 逐一比较
     */
    public boolean isPalindrome(ListNode head) {
        ListNode p1 = head; // 慢
        ListNode p2 = head; // 快
        ListNode n1 = null; // 新头
        ListNode o1 = head; // 旧头
        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;

            // 反转链表
            // ListNode o2 = o1.next;
            // o1.next = n1;
            // n1 = o1;
            // o1 = o2;
            // 我們使用 o2 紀錄舊鏈表中排名第二的節點，仔細觀察可以發現慢指針是和 o2 同步的
            // 既然它倆都一樣，也就可以調整為如下這樣
            o1.next = n1;
            n1 = o1;
            o1 = p1;
        }
        System.out.println(n1);
        System.out.println(p1);

        if (p2 != null) { // 奇数节点
            p1 = p1.next;
        }

        while (n1 != null) {
            if (n1.val != p1.val) {
                return false;
            }
            n1 = n1.next;
            p1 = p1.next;
        }
        return true;
    }

    public static void main(String[] args) {
//        System.out.println(new E09Leetcode234()
//                .isPalindrome(ListNode.of(1, 2, 2, 1)));

        System.out.println(new Leetcode234()
                .isPalindrome(ListNode.of(1, 2, 3, 2, 1)));
    }
}