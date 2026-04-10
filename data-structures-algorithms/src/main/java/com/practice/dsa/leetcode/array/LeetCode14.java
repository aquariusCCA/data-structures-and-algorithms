package com.practice.dsa.leetcode.array;

// 題目：https://leetcode.cn/problems/longest-common-prefix/?envType=problem-list-v2&envId=array
public class LeetCode14 {
    public static void main(String[] args) {
        String[] strs1 = { "flower","flow","flight" };
        System.out.println(longestCommonPrefix(strs1));

        String[] strs2 = { "a" };
        System.out.println(longestCommonPrefix(strs2));
    }

    public static String longestCommonPrefix(String[] strs) {
        String minStr = strs[0];
        for (int i = 1; i < strs.length; i++) {
            if (minStr.length() > strs[i].length()) {
                minStr = strs[i];
            }
        }
        int i = 0;
        String res = "";
        while (i < minStr.length()) {
            char c = minStr.charAt(i);
            boolean flag = true;
            for (int j = 0; j < strs.length; j++) {
                if (c != strs[j].charAt(i)) {
                    flag = false;
                    break;
                }
            }
            if (flag){
                res += c;
                i++;
            } else {
                break;
            }
        }
        return res;
    }
}
