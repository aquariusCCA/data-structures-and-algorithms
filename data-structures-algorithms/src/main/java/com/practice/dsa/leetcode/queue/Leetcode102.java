package com.practice.dsa.leetcode.queue;

import com.practice.dsa.structures.binarytree.TreeNode;
import com.practice.dsa.structures.queue.LinkedListQueue;

import java.util.ArrayList;
import java.util.List;

public class Leetcode102 {
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

        List<List<Integer>> lists = new Leetcode102().levelOrder(root);

        for (List<Integer> list : lists) {
            System.out.println(list);
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        LinkedListQueue<TreeNode> queue = new LinkedListQueue<>();
        queue.offer(root);
        int currentLevelSize = 1; // 当前层节点数
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>(); // 保存每一层结果
            int nextLevelSize = 0; // 下一层节点数
            for (int i = 0; i < currentLevelSize; i++) {
                TreeNode n = queue.poll();
                level.add(n.val);
                if (n.left != null) {
                    queue.offer(n.left);
                    nextLevelSize++;
                }
                if (n.right != null) {
                    queue.offer(n.right);
                    nextLevelSize++;
                }
            }
            result.add(level);
            currentLevelSize = nextLevelSize;
        }

        return result;
    }
}