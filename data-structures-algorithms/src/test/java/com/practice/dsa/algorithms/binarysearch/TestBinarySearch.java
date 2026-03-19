package com.practice.dsa.algorithms.binarysearch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.practice.dsa.algorithms.binarysearch.BinarySearch.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBinarySearch {
    @Test
    @DisplayName("测试 binarySearchBasic")
    public void test1() {
        int[] a = { 7, 13, 21, 30, 38, 44, 52, 53 };
        assertEquals(0, binarySearchBasic(a, 7));
        assertEquals(1, binarySearchBasic(a, 13));
        assertEquals(2, binarySearchBasic(a, 21));
        assertEquals(3, binarySearchBasic(a, 30));
        assertEquals(4, binarySearchBasic(a, 38));
        assertEquals(5, binarySearchBasic(a, 44));
        assertEquals(6, binarySearchBasic(a, 52));
        assertEquals(7, binarySearchBasic(a, 53));

        assertEquals(-1, binarySearchBasic(a, 0));
        assertEquals(-1, binarySearchBasic(a, 15));
        assertEquals(-1, binarySearchBasic(a, 60));
    }

    @Test
    @DisplayName("问题 1：为什么是 i <= j 意味着区间内有未比较的元素，而不是 i < j")
    public void test2(){
        // 範例一
        // int[] a = { 5 };
        // assertEquals(0, binarySearchQ1(a, 5));

        // 範例二
        int[] a = { 1, 3 };
        assertEquals(1, binarySearchQ1(a, 3));

        // 範例三
        // int[] a = { 6, 7, 10, 15, 20, 29, 36, 38 };
        // assertEquals(0, binarySearchQ1(a, 6));
    }

    @Test
    @DisplayName("问题 2: (i + j) / 2 有没有问题 ?")
    public void test3(){
        // 第一次
        int i = 0;
        int j = Integer.MAX_VALUE - 1; // 我們假設這個數組非常的大，它的數組長度是整數的最大值
        int m = (i + j) / 2;
        System.out.println(m);

        // 第二次(目標值大於中間值)
        i = m + 1;
        m = (i + j) / 2;
        System.out.println(m);
        // 為什麼是負數？
        // 因為 i + j 的值超過了 int 的最大值，所以 m 的值是負數
    }

    @Test
    @DisplayName("测试 binarySearchAlternative")
    public void test4() {
        int[] a = {7, 13, 21, 30, 38, 44, 52, 53};
        assertEquals(0, binarySearchAlternative(a, 7));
        assertEquals(1, binarySearchAlternative(a, 13));
        assertEquals(2, binarySearchAlternative(a, 21));
        assertEquals(3, binarySearchAlternative(a, 30));
        assertEquals(4, binarySearchAlternative(a, 38));
        assertEquals(5, binarySearchAlternative(a, 44));
        assertEquals(6, binarySearchAlternative(a, 52));
        assertEquals(7, binarySearchAlternative(a, 53));

        assertEquals(-1, binarySearchAlternative(a, 0));
        assertEquals(-1, binarySearchAlternative(a, 15));
        assertEquals(-1, binarySearchAlternative(a, 60));
    }

    @Test
    @DisplayName("問題：如果 while (i < j){ … } 會陷入死循環")
    public void test5(){
        int[] a = { 7, 13, 21, 30, 38, 44, 52, 53 };
        // 示範：死循環
        assertEquals(-1, binarySearchAlternative(a, 35));
    }

    @Test
    @DisplayName("测试 binarySearchBalance")
    public void test6() {
        int[] a = {7, 13, 21, 30, 38, 44, 52, 53};
        assertEquals(0, binarySearchBalance(a, 7));
        assertEquals(1, binarySearchBalance(a, 13));
        assertEquals(2, binarySearchBalance(a, 21));
        assertEquals(3, binarySearchBalance(a, 30));
        assertEquals(4, binarySearchBalance(a, 38));
        assertEquals(5, binarySearchBalance(a, 44));
        assertEquals(6, binarySearchBalance(a, 52));
        assertEquals(7, binarySearchBalance(a, 53));

        assertEquals(-1, binarySearchBalance(a, 0));
        assertEquals(-1, binarySearchBalance(a, 15));
        assertEquals(-1, binarySearchBalance(a, 60));
    }

    @Test
    @DisplayName("测试 binarySearch java 版")
    public void test7() {
        int[] a = {2, 5, 8};
        int target = 4;
        int i = Arrays.binarySearch(a, target);
        // 沒找到返回的是 => -插入点 - 1
        // -2 = -插入点 - 1
        // -2 + 1 = -插入点
        // -1 = -插入点
        // 1 = 插入點
        System.out.println(i);
        // i = -插入点 - 1  因此有 插入点 = abs(i+1)
        int insertIndex = Math.abs(i + 1); // 插入点索引
        int[] b = new int[a.length + 1];
        // System.arraycopy 參數意思：
        // src：來源陣列
        // srcPos：從來源陣列哪個 index 開始複製（含）
        // dest：目標陣列
        // destPos：貼到目標陣列哪個 index 開始（含）
        // length：複製幾個元素
        System.arraycopy(a, 0, b, 0, insertIndex);
        b[insertIndex] = target;
        System.arraycopy(a, insertIndex, b, insertIndex + 1, a.length - insertIndex);
        for (int e : b) {
            System.out.print(e + "\t");
        }
    }

    @Test
    @DisplayName("测试 binarySearchLeftmost 返回 -1")
    public void test8() {
        int[] a = { 1, 2, 4, 4, 4, 5, 6, 7 };
        assertEquals(0, binarySearchLeftmost1(a, 1));
        assertEquals(1, binarySearchLeftmost1(a, 2));
        assertEquals(2, binarySearchLeftmost1(a, 4));
        assertEquals(5, binarySearchLeftmost1(a, 5));
        assertEquals(6, binarySearchLeftmost1(a, 6));
        assertEquals(7, binarySearchLeftmost1(a, 7));

        assertEquals(-1, binarySearchLeftmost1(a, 0));
        assertEquals(-1, binarySearchLeftmost1(a, 3));
        assertEquals(-1, binarySearchLeftmost1(a, 8));
    }

    @Test
    @DisplayName("测试 binarySearchRightmost 返回 -1")
    public void test9() {
        int[] a = { 1, 2, 4, 4, 4, 5, 6, 7 };
        assertEquals(0, binarySearchRightmost1(a, 1));
        assertEquals(1, binarySearchRightmost1(a, 2));
        assertEquals(4, binarySearchRightmost1(a, 4));
        assertEquals(5, binarySearchRightmost1(a, 5));
        assertEquals(6, binarySearchRightmost1(a, 6));
        assertEquals(7, binarySearchRightmost1(a, 7));

        assertEquals(-1, binarySearchRightmost1(a, 0));
        assertEquals(-1, binarySearchRightmost1(a, 3));
        assertEquals(-1, binarySearchRightmost1(a, 8));
    }

    @Test
    @DisplayName("测试 Leftmost、Rightmost 的几个名词")
    public void test10() {
        int[] a = { 1, 2, 4, 4, 4, 7, 7 };

        // System.out.println(binarySearchLeftmost(a, 4));
        // System.out.println(binarySearchLeftmost(a, 5));
        // System.out.println(binarySearchRightmost(a, 4));
         System.out.println(binarySearchRightmost(a, 5));

        // 求排名
        // assertEquals(6, binarySearchLeftmost(a, 5) + 1);
        // assertEquals(3, binarySearchLeftmost(a, 4) + 1);

        // 求前任
        // assertEquals(1, binarySearchLeftmost(a, 3) - 1);
        // assertEquals(1, binarySearchLeftmost(a, 4) - 1);

        // 求後任
        // assertEquals(5, binarySearchRightmost(a, 5) + 1);
        // assertEquals(5, binarySearchRightmost(a, 4) + 1);
    }
}