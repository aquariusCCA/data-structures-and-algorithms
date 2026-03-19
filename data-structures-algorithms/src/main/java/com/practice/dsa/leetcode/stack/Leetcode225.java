package com.practice.dsa.leetcode.stack;

import com.practice.dsa.structures.queue.ArrayQueue3;

/**
 * 单队列模拟栈
 * <ol>
 *     <li>调用 push、pop 等方法的次数最多 100</li>
 *     <li>每次调用 pop 和 top 都能保证栈不为空</li>
 * </ol>
 */
public class Leetcode225 {

    ArrayQueue3<Integer> queue = new ArrayQueue3<>(100);
    private int size = 0;

    public void push(int x) {
        queue.offer(x);
        for (int i = 0; i < size; i++) {
            queue.offer(queue.poll());
        }
        size++;
    }

    public int pop() {
        size--;
        return queue.poll();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}