package com.practice.dsa.structures.queue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestArrayQueue3 {
    @Test
    public void generic() {
        ArrayQueue3<String> queue =
                new ArrayQueue3<>(4);
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        assertFalse(queue.offer("e"));

        assertIterableEquals(List.of("a", "b", "c", "d"), queue);
    }

    @Test
    public void offerLimit() {
        ArrayQueue3<Integer> queue =
                new ArrayQueue3<>(4);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        assertFalse(queue.offer(5));

        assertIterableEquals(List.of(1, 2, 3, 4), queue);
    }

    @Test
    @DisplayName("测试删除只剩一个节点时")
    public void poll1() {
        ArrayQueue3<Integer> queue = new ArrayQueue3<>(8);
        queue.offer(1);
        assertEquals(1, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void offer() {
        ArrayQueue3<Integer> queue = new ArrayQueue3<>(8);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);

        assertIterableEquals(List.of(1, 2, 3, 4, 5), queue);
    }

    @Test
    public void peek() {
        ArrayQueue3<Integer> queue = new ArrayQueue3<>(8);
        assertNull(queue.peek());
        queue.offer(1);
        assertEquals(1, queue.peek());
        queue.offer(2);
        assertEquals(1, queue.peek());
    }

    @Test
    public void poll() {
        ArrayQueue3<Integer> queue = new ArrayQueue3<>(8);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);

        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
        assertNull(queue.poll());

        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        assertIterableEquals(List.of(4, 5, 6), queue);
    }

    @Test
    public void boundary() {
        ArrayQueue3<Integer> queue = new ArrayQueue3<>(10);
        // 2147483647 正整数的最大值 int
        queue.head = 2147483640;
        queue.tail = queue.head;

        for (int i = 0; i < 10; i++) {
            queue.offer(i);
        }

        for (Integer value : queue) {
            System.out.println(value);
        }
    }

    @Test
    public void test() {
        int capacity = 32;
        int n = (int)(Math.log10(capacity - 1) / Math.log10(2)) + 1;
        System.out.println(n);
        System.out.println(1 << n);
    }
}