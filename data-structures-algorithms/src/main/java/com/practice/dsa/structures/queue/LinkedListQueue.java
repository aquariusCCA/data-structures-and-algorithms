package com.practice.dsa.structures.queue;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * 基于单向环形链表实现
 *
 * @param <E> 队列中元素类型
 */
public class LinkedListQueue<E> implements Queue<E>, Iterable<E> {

    private static class Node<E> {
        E value;
        Node<E> next;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    // 哨兵節點，不存放真正資料
    private final Node<E> sentinel = new Node<>(null, null);

    // 空隊列時 tail == sentinel，否則 tail 指向最後一個有效節點
    private Node<E> tail = sentinel;

    // 當前元素個數
    private int size;

    // 隊列容量
    private int capacity = Integer.MAX_VALUE;

    {
        // 初始化成環
        sentinel.next = sentinel;
    }

    public LinkedListQueue() {
    }

    public LinkedListQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean offer(E value) {
        if (isFull()) {
            return false;
        }
        Node<E> added = new Node<>(value, sentinel);
        tail.next = added;
        tail = added;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }

        Node<E> first = sentinel.next;
        sentinel.next = first.next;

        // 如果原本只有一個元素，刪除後要恢復空隊列狀態
        if (first == tail) {
            tail = sentinel;
        }

        size--;
        return first.value;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return sentinel.next.value;
    }

    @Override
    public boolean isEmpty() {
        return tail == sentinel;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> p = sentinel.next;

            @Override
            public boolean hasNext() {
                return p != sentinel;
            }

            @Override
            public E next() {
                E value = p.value;
                p = p.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (E e : this) {
            sj.add(String.valueOf(e));
        }
        return sj.toString();
    }
}