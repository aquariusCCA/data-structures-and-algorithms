package com.practice.dsa.leetcode.array;

import java.util.Arrays;

// 題目：Remove Element
// 題意：移除陣列中所有等於 val 的元素，並回傳剩下元素的數量 k
// 要求：必須在原陣列 nums 上原地操作，不可使用額外陣列存結果
public class LeetCode27 {
    public static void main(String[] args) {
        int[] arr = new int[] { 3, 2, 2, 3 };
        System.out.println(removeElement(arr, 3));   // 預期回傳 2
        System.out.println(Arrays.toString(arr));    // 前 2 個位置應為 [2, 2]

        int[] arr2 = new int[] { 0, 1, 2, 2, 3, 0, 4, 2 };
        System.out.println(removeElement(arr2, 2));  // 預期回傳 5
        System.out.println(Arrays.toString(arr2));   // 前 5 個位置應為 [0, 1, 3, 0, 4]
    }

    public static int removeElement(int[] nums, int val) {
        int k = 0; // 慢指標：指向有效元素應該寫入的位置

        for (int i = 0; i < nums.length; i++) { // 快指標：負責掃描陣列
            if (nums[i] != val) {               // 找到不需要刪除的元素
                nums[k] = nums[i];              // 放到前面有效區
                k++;                            // 有效區長度擴大
            }
        }

        return k; // 回傳有效區長度
    }
}