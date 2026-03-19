package com.practice.dsa.algorithms.binarysearch;

public class BinarySearch {
    /**
     * 二分查找基础版
     *
     * @param a      待查找的升序数组
     * @param target 待查找的目标值
     * @return 找到则返回索引 找不到返回 -1
     */
    public static int binarySearchBasic(int[] a, int target) {
        int i = 0, j = a.length - 1; // 设置指针和初值
        while (i <= j) { // i~j 范围内有东西
            int m = (i + j) / 2;
            if (target < a[m]) { // 目标在左边
                j = m - 1;
            } else if (a[m] < target) { // 目标在右边
                i = m + 1;
            } else { // 找到了
                return m;
            }
        }
        return -1;
    }


    public static int binarySearchQ1(int[] a, int target) {
        int i = 0, j = a.length - 1; // 设置指针和初值
        while (i < j) {
            int m = (i + j) / 2;
            if (target < a[m]) { // 目标在左边
                j = m - 1;
            } else if (a[m] < target) { // 目标在右边
                i = m + 1;
            } else { // 找到了
                return m;
            }
        }
        return -1;
    }

    /**
     * 二分查找改动版
     *
     * @param a      待查找的升序数组
     * @param target 待查找的目标值
     * @return 找到则返回索引 找不到返回 -1
     */
    public static int binarySearchAlternative(int[] a, int target) {
        int i = 0, j = a.length; // 第一处
        while (i < j) { // 第二处
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m; // 第三处
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    /**
     * 二分查找平衡版
     *
     * @param a      待查找的升序数组
     * @param target 待查找的目标值
     * @return 找到则返回索引 找不到返回 -1
     */
    public static int binarySearchBalance(int[] a, int target) {
        int i = 0, j = a.length;
        while (1 < j - i) { // 表示的是「范围内待查找的元素个数 > 1」 时，當範圍內只剩下一個元素的時候就退出循環
            int m = (i + j) >>> 1;
            if (target < a[m]) { // 目标在左边
                j = m;
            } else { // 目标在 m 或右边
                i = m;
            }
        }
        return (target == a[i]) ? i : -1;
    }

    /**
     * 二分查找 Leftmost
     *
     * @param a      待查找的升序数组
     * @param target 待查找的目标值
     * @return
     *         找到则返回最靠左索引
     *         找不到返回 -1
     */
    public static int binarySearchLeftmost1(int[] a, int target) {
        int i = 0, j = a.length - 1;
        int candidate = -1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                candidate = m; // 记录候选位置
                j = m - 1; // 继续向左
            }
        }
        return candidate;
    }

    public static int binarySearchLeftmost(int[] a, int target) {
        int i = 0, j = a.length - 1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            // 當目標小于等于中间值，都要向左找
            if (target <= a[m]) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return i;
    }

    /**
     * 二分查找 Rightmost
     *
     * @param a      待查找的升序数组
     * @param target 待查找的目标值
     * @return
     *         找到则返回最靠右索引
     *         找不到返回 -1
     */
    public static int binarySearchRightmost1(int[] a, int target) {
        int i = 0, j = a.length - 1;
        int candidate = -1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                candidate = m; // 记录候选位置
                i = m + 1; // 继续向右
            }
        }
        return candidate;
    }

    public static int binarySearchRightmost(int[] a, int target) {
        int i = 0, j = a.length - 1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return i - 1;
    }
}
