package com.practice.dsa.algorithms.binarysearch;

import java.util.Arrays;

public final class BinarySearch {
    private BinarySearch() {
    }

    /**
     * 二分查找基础版：闭区间 [i, j]
     */
    public static int binarySearchBasic(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    /**
     * 二分查找改动版：左闭右开区间 [i, j)
     */
    public static int binarySearchAlternative(int[] a, int target) {
        int i = 0;
        int j = a.length;
        while (i < j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    /**
     * 二分查找平衡版：循环只负责缩小范围，最后统一判断。
     */
    public static int binarySearchBalance(int[] a, int target) {
        if (a.length == 0) {
            return -1;
        }

        int i = 0;
        int j = a.length;
        while (1 < j - i) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m;
            } else {
                i = m;
            }
        }
        return a[i] == target ? i : -1;
    }

    /**
     * Java 标准库版本，找不到时返回 -(插入点 + 1)。
     */
    public static int javaBinarySearch(int[] a, int target) {
        return Arrays.binarySearch(a, target);
    }

    /**
     * 从 Arrays.binarySearch 的负数结果还原插入点。
     */
    public static int insertionPointFromJavaResult(int result) {
        if (result >= 0) {
            throw new IllegalArgumentException("result must be negative when target is not found");
        }
        return -result - 1;
    }

    /**
     * Leftmost 基础版：找到最左命中位置，找不到返回 -1。
     */
    public static int binarySearchLeftmost1(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        int candidate = -1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                candidate = m;
                j = m - 1;
            }
        }
        return candidate;
    }

    /**
     * Rightmost 基础版：找到最右命中位置，找不到返回 -1。
     */
    public static int binarySearchRightmost1(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        int candidate = -1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                candidate = m;
                i = m + 1;
            }
        }
        return candidate;
    }

    /**
     * Leftmost 进阶版：返回第一个 >= target 的位置。
     */
    public static int binarySearchLeftmost(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target <= a[m]) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return i;
    }

    /**
     * Rightmost 进阶版：返回最后一个 <= target 的位置。
     */
    public static int binarySearchRightmost(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
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

    /**
     * LeetCode 35：搜索插入位置。
     */
    public static int searchInsert(int[] nums, int target) {
        return binarySearchLeftmost(nums, target);
    }

    /**
     * LeetCode 34：搜索目标值的开始和结束位置。
     */
    public static int[] searchRange(int[] nums, int target) {
        int left = binarySearchLeftmost1(nums, target);
        if (left == -1) {
            return new int[]{-1, -1};
        }
        return new int[]{left, binarySearchRightmost1(nums, target)};
    }

    /**
     * 前任：最后一个 < target 的索引，不存在时返回 -1。
     */
    public static int predecessorIndex(int[] a, int target) {
        return binarySearchLeftmost(a, target) - 1;
    }

    /**
     * 后任：第一个 > target 的索引，不存在时返回 a.length。
     */
    public static int successorIndex(int[] a, int target) {
        return binarySearchRightmost(a, target) + 1;
    }
}
