package com.practice.dsa.structures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyCircularLinkedListSentinel implements Iterable<Integer> {
    static class Node {
        Node prev;
        int value;
        Node next;

        public Node(Node prev, int value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private final Node sentinel = new Node(null, Integer.MIN_VALUE, null); // 哨兵

    public DoublyCircularLinkedListSentinel() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    private Node findNode(int index) {
        // 讓 index=-1 回傳哨兵，方便找 prev
        if (index == -1) return sentinel;
        if (index < -1) return null;

        int i = 0;
        for (Node curr = sentinel.next; curr != sentinel; curr = curr.next, i++) {
            if (i == index) return curr;
        }
        return null;
    }

    public void insert(int index, int value) {
        Node prev = findNode(index - 1);
        if (prev == null) {
            throw illegalIndex(index);
        }
        Node next = prev.next;
        Node inserted = new Node(prev, value, next);
        prev.next = inserted;
        next.prev = inserted;
    }

    /**
     * 添加到第一个
     * @param value 待添加值
     */
    public void addFirst(int value) {
        insert(0, value);
    }

    /**
     * 添加到最后一个
     * @param value 待添加值
     */
    public void addLast(int value) {
        Node prev = sentinel.prev;
        Node next = sentinel;
        Node added = new Node(prev, value, next);
        prev.next = added;
        next.prev = added;
    }

    public void remove(int index) {
        Node prev = findNode(index - 1);
        if (prev == null) {
            throw illegalIndex(index);
        }
        Node removed = prev.next;
        if (removed == sentinel) {
            throw illegalIndex(index);
        }
        Node next = removed.next;
        prev.next = next;
        next.prev = prev;
        // 斷開 removed，避免誤用 & 幫助 GC
        removed.prev = null;
        removed.next = null;
    }

    /**
     * 删除第一个
     */
    public void removeFirst() {
        remove(0);
    }

    /**
     * 删除最后一个
     */
    public void removeLast() {
        // 請幫我檢查
        Node removed = sentinel.prev;
        if (removed == sentinel) { // 空表
            throw new IllegalArgumentException("非法");
        }
        Node prev = removed.prev;
        prev.next = sentinel;
        sentinel.prev = prev;
        // 斷開 removed，避免誤用 & 幫助 GC
        removed.prev = null;
        removed.next = null;
    }

    /**
     * 根据值删除节点
     * <p>假定 value 在链表中作为 key, 有唯一性</p>
     * @param value 待删除值
     */
    public void removeByValue(int value) {
        Node removed = findNodeByValue(value);
        if (removed != null) {
            Node prev = removed.prev;
            Node next = removed.next;
            prev.next = next;
            next.prev = prev;
        }
    }

    private Node findNodeByValue(int value) {
        Node p = sentinel.next;
        while (p != sentinel) {
            if (p.value == value) {
                return p;
            }
            p = p.next;
        }
        return null;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            Node p = sentinel.next;

            @Override
            public boolean hasNext() {
                return p != sentinel;
            }

            @Override
            public Integer next() {
                if (p == sentinel) throw new NoSuchElementException();
                int value = p.value;
                p = p.next;
                return value;
            }
        };
    }

    private IllegalArgumentException illegalIndex(int index) {
        return new IllegalArgumentException(
                String.format("index [%d] 不合法%n", index));
    }
}