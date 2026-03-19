package com.practice.dsa.algorithms.recursion.multi;

public class E01Fibonacci {
    // 1, 1, 2, 3, 5, 8, 13
    public static void main(String[] args) {
        System.out.println(f(6));
    }

    public static int f(int n) {
        if (n == 1 || n == 2) return 1;

        int a = f(n - 1);
        int b = f(n - 2);
        return a + b;
    }
}
