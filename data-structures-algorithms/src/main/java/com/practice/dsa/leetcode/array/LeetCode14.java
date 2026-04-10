package com.practice.dsa.leetcode.array;

// 題目：https://leetcode.cn/problems/longest-common-prefix/?envType=problem-list-v2&envId=array
public class LeetCode14 {
    public static void main(String[] args) {
        String[] strs1 = {"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix(strs1)); // fl

        String[] strs2 = {"a"};
        System.out.println(longestCommonPrefix(strs2)); // a

        String[] strs3 = {"dog", "racecar", "car"};
        System.out.println(longestCommonPrefix(strs3)); // ""
    }

    public static String longestCommonPrefix(String[] strs) {
        // 邊界處理：
        // 如果陣列是 null，或陣列長度為 0，直接回傳空字串
        if (strs == null || strs.length == 0) {
            return "";
        }

        // 先假設第一個字串是最短字串
        String minStr = strs[0];

        // 找出陣列中最短的字串
        // 因為最長公共前綴的長度，不可能超過最短字串的長度
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].length() < minStr.length()) {
                minStr = strs[i];
            }
        }

        // 逐個字元檢查最短字串的每一個位置
        for (int i = 0; i < minStr.length(); i++) {
            // 取出最短字串當前位置的字元，作為比較基準
            char c = minStr.charAt(i);

            // 拿這個字元去和所有字串同一位置的字元比較
            for (int j = 0; j < strs.length; j++) {
                // 只要有任何一個字串在這個位置的字元不同，
                // 代表公共前綴到 i 之前就結束了
                if (strs[j].charAt(i) != c) {
                    // 回傳從 0 到 i-1 的子字串
                    return minStr.substring(0, i);
                }
            }
        }

        // 如果整個最短字串都比完了，仍然都相同，
        // 代表最短字串本身就是最長公共前綴
        return minStr;
    }
}