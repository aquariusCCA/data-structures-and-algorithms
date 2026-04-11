package com.practice.dsa.leetcode.array;

// 題目：https://leetcode.com/problems/search-insert-position/description/?envType=problem-list-v2&envId=array
public class LeetCode35 {
    public static void main(String[] args) {
        int[] arr = new int[] { 1, 3, 5, 6 };
        System.out.println(searchInsert(arr, 5)); // 2
        System.out.println(searchInsert(arr, 2)); // 1
        System.out.println(searchInsert(arr, 7)); // 4
    }

    public static int searchInsert(int[] a, int target) {
        int i = 0, j = a.length - 1;

        // 在區間 [i, j] 中找「第一個 >= target 的位置」
        while (i <= j) {
            // 無符號右移，避免 (i + j) 溢位
            int m = (i + j) >>> 1;

            // 如果 target 小於等於中間值
            // 代表答案可能是 m，或在 m 左邊
            if (target <= a[m]) {
                j = m - 1;
            } else {
                // 如果 target 大於中間值
                // 代表答案一定在 m 右邊
                i = m + 1;
            }
        }

        // 迴圈結束時，i 就是 target 應插入的位置
        return i;
    }
}