package com.practice.dsa.structures.list;

import java.util.Iterator;
import java.util.function.Consumer;

public class SinglyLinkedListSentinel implements Iterable<Integer> {

    // 哨兵節點（不參與資料存儲）
    private final Node head = new Node(Integer.MIN_VALUE, null);

    // 節點類
    private static class Node {
        int value;
        Node next;

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    /* =========================
       迭代器 / foreach 支援
       ========================= */
    private class NodeIterator implements Iterator<Integer> {
        // 注意：從第一個資料節點開始，而不是哨兵
        Node curr = head.next;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public Integer next() {
            int value = curr.value;
            curr = curr.next;
            return value;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new NodeIterator();
    }

    public void loop(Consumer<Integer> consumer) {
        for (Node curr = head.next; curr != null; curr = curr.next) {
            consumer.accept(curr.value);
        }
    }

    /* =========================
       核心查找方法（哨兵版）
       ========================= */

    /**
     * 根據索引取得節點（包含哨兵語義）：
     * index = -1 -> 回傳哨兵 head
     * index =  0 -> 回傳第一個資料節點
     * index =  1 -> 回傳第二個資料節點
     * ...
     */
    private Node findNode(int index) {
        int i = -1;
        for (Node curr = this.head; curr != null; curr = curr.next, i++) {
            if (i == index) {
                return curr;
            }
        }
        return null;
    }

    /**
     * 取得最後一個節點：
     * - 若鏈表沒有資料節點，最後一個節點就是哨兵 head
     * - 否則回傳最後一個資料節點
     */
    private Node findLast() {
        Node curr = this.head;
        while (curr.next != null) {
            curr = curr.next;
        }
        return curr;
    }

    private IllegalArgumentException illegalIndex(int index) {
        return new IllegalArgumentException(String.format("index [%d] 不合法%n", index));
    }

    /* =========================
       CRUD 操作（哨兵版）
       ========================= */

    public void addFirst(int value) {
        insert(0, value);
    }

    public void addLast(int value) {
        Node last = findLast();          // 永遠不為 null
        last.next = new Node(value, null);
    }

    public void addLast(int first, int... rest) {
        // 建立一段子鏈
        Node sublist = new Node(first, null);
        Node curr = sublist;
        for (int value : rest) {
            curr.next = new Node(value, null);
            curr = curr.next;
        }

        // 接到尾巴
        Node last = findLast();
        last.next = sublist;
    }

    public int get(int index) {
        Node node = findNode(index);
        if (node != null && node != head) { // node != head 等價於 index != -1
            return node.value;
        }
        throw illegalIndex(index);
    }

    public void insert(int index, int value) {
        // 找到 index 的前一個節點（index-1）
        Node prev = findNode(index - 1);
        if (prev == null) {
            throw illegalIndex(index);
        }
        prev.next = new Node(value, prev.next);
    }

    public void removeFirst() {
        remove(0);
    }

    public void remove(int index) {
        Node prev = findNode(index - 1);
        if (prev == null || prev.next == null) {
            throw illegalIndex(index);
        }
        prev.next = prev.next.next;
    }
}