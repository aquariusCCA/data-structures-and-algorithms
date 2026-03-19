package com.practice.dsa.algorithms.recursion.single;

public class E03BinarySearch {
    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 4, 5, 6, 7 };
        System.out.println(binarySearch(a, 1));
    }

    public static int binarySearch(int[] a, int target) {
        return recursion(a, target, 0, a.length - 1);
    }

    public static int recursion(int[] a, int target, int i, int j) {
        if (i > j) {
            return -1;
        }
        // 中點（無號右移避免溢位符號問題）
        int m = (i + j) >>> 1;
        if (target < a[m]) {
            // 縮小到左半區間
            return recursion(a, target, i, m - 1);
        } else if (a[m] < target) {
            // 縮小到右半區間
            return recursion(a, target, m + 1, j);
        } else {
            return m;
        }
    }
}
