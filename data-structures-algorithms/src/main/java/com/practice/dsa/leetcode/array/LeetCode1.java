package com.practice.dsa.leetcode.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// 題目：https://leetcode.cn/problems/two-sum/
// 目標：在陣列中找出兩個數字，使它們的和等於 target，並回傳索引。
public class LeetCode1 {
    public static void main(String[] args) {
        int[] nums = { 2, 7, 11, 15 };
        int target = 18;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }

    /*
     * 解題思路：
     * 使用 HashMap 記錄「之前出現過的數字」以及「該數字對應的索引」。
     *
     * Map 結構：
     * key   -> 數字值
     * value -> 該數字在陣列中的索引
     *
     * 對於每一個 nums[i]：
     * 1. 先計算目前還差多少：need = target - nums[i]
     * 2. 檢查 map 中是否已經出現過 need
     * 3. 如果出現過，代表答案已找到
     * 4. 如果還沒出現，就把目前數字與索引放進 map
     *
     * 這裡的重點是：
     * 先查找，再放入。
     * 這樣可以避免同一個元素被使用兩次。
     *
     * 時間複雜度：O(n)
     * 空間複雜度：O(n)
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];

            // 如果 need 已經出現在 map 中，代表配對成功。
            if (map.containsKey(need)) {
                return new int[] { i, map.get(need) };
            }

            // 將目前數字存入 map，提供給後面的元素配對使用。
            map.put(nums[i], i);
        }

        // LeetCode 原題保證一定有解，這裡保留 return null 作為防禦性寫法。
        return null;
    }
}
