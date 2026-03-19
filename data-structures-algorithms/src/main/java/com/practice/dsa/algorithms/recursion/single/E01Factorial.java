package com.practice.dsa.algorithms.recursion.single;

public class E01Factorial {
    public static void main(String[] args) {
        System.out.println(f(3));
    }

    private static int f(int n) {
        if (n == 1) {
            return 1;
        }
        return n * f(n - 1);
    }
}
