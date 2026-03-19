package com.practice.dsa.algorithms.recursion.single;

import java.util.Arrays;

public class E05InsertionSort {
    public static void main(String[] args) {
        int[] a = { 5, 2, 4, 6, 1, 3 };
        System.out.println("Before sort: " + Arrays.toString(a));
        sort(a);
        System.out.println("After sort: " + Arrays.toString(a));
    }

    public static void sort(int[] a) {
        insertion(a, 1);
    }
    // a: 待排序數組
// low: 未排序區域的邊界
    private static void insertion(int[] a, int low) {
        if (low == a.length) {
            return;
        }

        int temp = a[low];
        int i = low - 1; // 已排序區域指針

        while (i >= 0 && a[i] > temp) { // 沒有找到插入位置
            a[i + 1] = a[i]; // 空出插入位置
            i--;
        }

        a[i + 1] = temp; // 找到插入位置，插入元素

        insertion(a, low + 1); // 插入下一個元素
    }
}
