package com.practice.dsa.algorithms.binarysearch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.dsa.algorithms.binarysearch.BinarySearch.binarySearchAlternative;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.binarySearchBalance;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.binarySearchBasic;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.binarySearchLeftmost;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.binarySearchLeftmost1;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.binarySearchRightmost;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.binarySearchRightmost1;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.insertionPointFromJavaResult;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.javaBinarySearch;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.predecessorIndex;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.searchInsert;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.searchRange;
import static com.practice.dsa.algorithms.binarysearch.BinarySearch.successorIndex;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestBinarySearch {
    @Test
    @DisplayName("基础版二分查找")
    public void testBinarySearchBasic() {
        int[] a = {7, 13, 21, 30, 38, 44, 52, 53};
        assertEquals(0, binarySearchBasic(a, 7));
        assertEquals(4, binarySearchBasic(a, 38));
        assertEquals(7, binarySearchBasic(a, 53));
        assertEquals(-1, binarySearchBasic(a, 15));
        assertEquals(-1, binarySearchBasic(a, 60));
    }

    @Test
    @DisplayName("左闭右开版二分查找")
    public void testBinarySearchAlternative() {
        int[] a = {7, 13, 21, 30, 38, 44, 52, 53};
        assertEquals(0, binarySearchAlternative(a, 7));
        assertEquals(4, binarySearchAlternative(a, 38));
        assertEquals(7, binarySearchAlternative(a, 53));
        assertEquals(-1, binarySearchAlternative(a, 15));
        assertEquals(-1, binarySearchAlternative(a, 60));
    }

    @Test
    @DisplayName("平衡版二分查找")
    public void testBinarySearchBalance() {
        int[] a = {7, 13, 21, 30, 38, 44, 52, 53};
        assertEquals(0, binarySearchBalance(a, 7));
        assertEquals(4, binarySearchBalance(a, 38));
        assertEquals(7, binarySearchBalance(a, 53));
        assertEquals(-1, binarySearchBalance(a, 15));
        assertEquals(-1, binarySearchBalance(a, 60));
        assertEquals(-1, binarySearchBalance(new int[]{}, 1));
    }

    @Test
    @DisplayName("Arrays.binarySearch 返回值与插入点")
    public void testJavaBinarySearchAndInsertionPoint() {
        int[] a = {2, 5, 8};
        assertEquals(0, javaBinarySearch(a, 2));
        assertEquals(1, javaBinarySearch(a, 5));
        assertEquals(2, javaBinarySearch(a, 8));

        assertEquals(-1, javaBinarySearch(a, 1));
        assertEquals(-2, javaBinarySearch(a, 4));
        assertEquals(-3, javaBinarySearch(a, 6));
        assertEquals(-4, javaBinarySearch(a, 10));

        assertEquals(0, insertionPointFromJavaResult(-1));
        assertEquals(1, insertionPointFromJavaResult(-2));
        assertEquals(2, insertionPointFromJavaResult(-3));
        assertEquals(3, insertionPointFromJavaResult(-4));
        assertThrows(IllegalArgumentException.class, () -> insertionPointFromJavaResult(1));
    }

    @Test
    @DisplayName("Leftmost 与 Rightmost 基础版")
    public void testLeftmostAndRightmostWithCandidate() {
        int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
        assertEquals(2, binarySearchLeftmost1(a, 4));
        assertEquals(4, binarySearchRightmost1(a, 4));
        assertEquals(0, binarySearchLeftmost1(a, 1));
        assertEquals(7, binarySearchRightmost1(a, 7));
        assertEquals(-1, binarySearchLeftmost1(a, 3));
        assertEquals(-1, binarySearchRightmost1(a, 3));
    }

    @Test
    @DisplayName("Leftmost 与 Rightmost 进阶版")
    public void testLeftmostAndRightmostBoundarySearch() {
        int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
        assertEquals(2, binarySearchLeftmost(a, 4));
        assertEquals(2, binarySearchLeftmost(a, 3));
        assertEquals(0, binarySearchLeftmost(a, 0));
        assertEquals(8, binarySearchLeftmost(a, 8));

        assertEquals(4, binarySearchRightmost(a, 4));
        assertEquals(1, binarySearchRightmost(a, 3));
        assertEquals(-1, binarySearchRightmost(a, 0));
        assertEquals(7, binarySearchRightmost(a, 8));
    }

    @Test
    @DisplayName("搜索插入位置")
    public void testSearchInsert() {
        int[] nums = {1, 3, 5, 6};
        assertEquals(2, searchInsert(nums, 5));
        assertEquals(1, searchInsert(nums, 2));
        assertEquals(4, searchInsert(nums, 7));
        assertEquals(0, searchInsert(nums, 0));
    }

    @Test
    @DisplayName("搜索开始结束位置")
    public void testSearchRange() {
        assertArrayEquals(new int[]{3, 4}, searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8));
        assertArrayEquals(new int[]{-1, -1}, searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6));
        assertArrayEquals(new int[]{-1, -1}, searchRange(new int[]{}, 0));
    }

    @Test
    @DisplayName("前任与后任索引")
    public void testPredecessorAndSuccessor() {
        int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
        assertEquals(1, predecessorIndex(a, 4));
        assertEquals(1, predecessorIndex(a, 3));
        assertEquals(-1, predecessorIndex(a, 1));

        assertEquals(5, successorIndex(a, 4));
        assertEquals(6, successorIndex(a, 5));
        assertEquals(8, successorIndex(a, 7));
        assertEquals(0, successorIndex(a, 0));
    }
}
