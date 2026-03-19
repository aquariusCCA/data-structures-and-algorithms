package com.practice.dsa.leetcode.stack;

import java.util.LinkedList;

/**
 * 后缀表达式求值
 */
public class Leetcode150 {
    public int evalRPN(String[] tokens) {
        LinkedList<Integer> stack = new LinkedList<>();
        for (String t : tokens) {
            switch (t) {
                case "+" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a + b);
                }
                case "-" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a - b);
                }
                case "*" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a * b);
                }
                case "/" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a / b);
                }
                default -> { // 数字
                    stack.push(Integer.parseInt(t));
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String[] tokens = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println(new Leetcode150().evalRPN(tokens));

        tokens = new String[]{"2", "1", "+", "3", "*"};
        System.out.println(new Leetcode150().evalRPN(tokens));
    }
}