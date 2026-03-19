package com.practice.dsa.algorithms.recursion.single;

import java.util.Arrays;

public class E04BubbleSort {
    public static void main(String[] args) {
        int[] a = { 3, 2, 6, 1, 5, 4, 7 };
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        bubble(a, a.length - 1);
    }

    private static void bubble(int[] a, int j) {
        if (j == 0) {
            return;
        }
        int x = 0;
        for (int i = 0; i < j; i++) {
            if(a[i] > a[i+1]) {
                swap(a, i, i + 1);
                x = i;
            }
        }
        bubble(a, x);
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
