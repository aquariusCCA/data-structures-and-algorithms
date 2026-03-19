package com.practice.dsa.algorithms.recursion.multi;

public class E03PascalTriangle {
    /**
     * <h3>记忆化递归：使用二维数组缓存</h3>
     *
     * @param triangle 缓存表：triangle[i][j] 表示第 i 行第 j 列的值
     * @param i        行坐标
     * @param j        列坐标
     * @return 该坐标元素值
     */
    private static int element(int[][] triangle, int i, int j) {
        // 命中缓存：该位置已经计算过
        if (triangle[i][j] > 0) {
            return triangle[i][j];
        }

        // 边界：两侧都是 1
        if (j == 0 || i == j) {
            triangle[i][j] = 1;
            return 1;
        }

        // 递归 + 写入缓存
        triangle[i][j] = element(triangle, i - 1, j - 1) + element(triangle, i - 1, j);
        return triangle[i][j];
    }

    public static void print(int n) {
        int[][] triangle = new int[n][];
        for (int i = 0; i < n; i++) {
            triangle[i] = new int[i + 1];
            for (int j = 0; j <= i; j++) {
                System.out.printf("%-4d", element(triangle, i, j));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        print(6);
    }
}