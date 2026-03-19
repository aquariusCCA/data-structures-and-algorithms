package com.practice.dsa.leetcode.deque;

import com.practice.dsa.structures.binarytree.TreeNode;
import com.practice.dsa.structures.queue.LinkedListQueue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉樹 Z 字層序遍歷
 */
public class Leetcode103 {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        LinkedListQueue<TreeNode> queue = new LinkedListQueue<>();
        queue.offer(root);

        int c1 = 1;           // 當前層節點數
        boolean odd = true;   // 是否為奇數層

        while (!queue.isEmpty()) {
            LinkedList<Integer> level = new LinkedList<>(); // 保存當前層結果
            int c2 = 0; // 下一層節點數

            for (int i = 0; i < c1; i++) {
                TreeNode n = queue.poll();

                if (odd) {
                    level.offerLast(n.val);   // 奇數層：尾部加入
                } else {
                    level.offerFirst(n.val);  // 偶數層：頭部加入
                }

                if (n.left != null) {
                    queue.offer(n.left);
                    c2++;
                }

                if (n.right != null) {
                    queue.offer(n.right);
                    c2++;
                }
            }

            result.add(level);
            odd = !odd; // 切換下一層方向
            c1 = c2;    // 更新當前層節點數
        }

        return result;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(
                new TreeNode(
                        new TreeNode(4),
                        2,
                        new TreeNode(5)
                ),
                1,
                new TreeNode(
                        new TreeNode(6),
                        3,
                        new TreeNode(7)
                )
        );

        List<List<Integer>> lists = new Leetcode103().zigzagLevelOrder(root);
        for (List<Integer> list : lists) {
            System.out.println(list);
        }
    }
}