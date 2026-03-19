package com.practice.dsa.algorithms.recursion.single;

public class E02ReversePrintString {
    public static void main(String[] args) {
        reversePrint("abc", 0);
    }

    public static void reversePrint(String str, int index) {
        if (index == str.length()) {
            return;
        }
        reversePrint(str, index + 1);
        System.out.println(str.charAt(index));
    }
}
