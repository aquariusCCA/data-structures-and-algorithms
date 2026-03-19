package com.practice.dsa.structures.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSinglyLinkedList {
    @Test
    @DisplayName("测试 recursion loop 方法")
    public void test1() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addFirst(5);

        list.loop(value -> {
            System.out.println("before:" + value);
        }, value -> {
            System.out.println("after:" + value);
        });
    }

    @Test
    @DisplayName("测试 addLast")
    public void test2() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);

        for (Integer value : list) {
            System.out.println(value);
        }

        System.out.println("==========");

        SinglyLinkedList list2 = new SinglyLinkedList();
        list2.addLast(1);
        list2.addLast(2);
        list2.addLast(3);
        list2.addLast(4);

        for (Integer value : list2) {
            System.out.println(value);
        }
    }

    @Test
    @DisplayName("测试 addLast")
    public void test3() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addFirst(1);
        list.addLast(2, 3, 4, 5);

        for (Integer value : list) {
            System.out.println(value);
        }
    }

    @Test
    @DisplayName("测试 get 方法")
    public void test4() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addFirst(1);
        list.addLast(2, 3, 4, 5);

        int num = list.get(2);
        System.out.println(num);
    }

    @Test
    @DisplayName("测试 insert 方法")
    public void test5() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3); // 2
        list.addLast(4);

        list.insert(2, 5);
        for (int i : list) {
            System.out.println(i);
        }
    }

    @Test
    @DisplayName("测试 removeFirst 方法")
    public void test6() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);

        list.removeFirst();

        for (int i : list) {
            System.out.println(i);
        }
    }

    @Test
    @DisplayName("测试 remove 方法")
    public void test7() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);

        list.remove(2);

        for (int i : list) {
            System.out.println(i);
        }
    }
}
