# LeetCode 83 删除排序链表中的重复元素

> 所属章节：[二、基础数据结构](../README.md) / [链表](./README.md)  
> 关键字：LeetCode 83、排序链表、重复元素、去重、相邻比较  
> 建議回查情境：要保留每個重複值的一個節點，或想回查排序鏈表去重邏輯時

## 本节导读

這一節整理 LeetCode 83，題目條件是鏈表已排序，所以重複值一定相鄰。第一次閱讀時建議抓住「保留一個、刪掉後續重複」這條主線，再看不同寫法的指針移動。

## 你會在這篇學到什麼

- 排序鏈表去重的核心前提
- 如何比較相鄰節點並跳過重複值
- 方法一與方法二的指針處理差異

---

- [83. 删除排序链表中的重复元素](https://leetcode.cn/problems/remove-duplicates-from-sorted-list/solutions/)

## 方法一

```text
p1     p2
1  ->  1  ->  2  ->  3  ->  3  ->  null
```

`p1.val == p2.val` 那麼刪除 p2，注意 p1 此時保持不變

```text
p1     p2
1  ->  2  ->  3  ->  3  ->  null
```

`p1.val != p2.val` 那麼 p1、p2 向後移動

```text
       p1     p2
1  ->  2  ->  3  ->  3  ->  null

              p1     p2
1  ->  2  ->  3  ->  3  ->  null
```

`p1.val == p2.val` 那麼刪除 p2

```text
              p1     p2
1  ->  2  ->  3  ->  null
```

當 `p2 == null` 退出循環

```java
public class Leetcode83 {
    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 1, 2, 3, 3);
        System.out.println(head);
        System.out.println(new Leetcode83().deleteDuplicates(head));
    }

    // 方法1
    public ListNode deleteDuplicates(ListNode head) {
        // 节点数 < 2
        if (head == null || head.next == null) {
            return head;
        }
        // 节点数 >= 2
        ListNode p1 = head;
        ListNode p2;
        while ((p2 = p1.next) != null) {
            if (p1.val == p2.val) {
                // 删除 p2
                p1.next = p2.next;
            } else {
                // 向后平移
                p1 = p1.next;
            }
        }
        return head;
    }
}
```

```java
public class ListNode {
    public Integer val;
    public ListNode next;

    public ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }

    public static ListNode of(int... elements) {
        if (elements.length == 0) {
            return null;
        }
        ListNode p = null;
        for (int i = elements.length - 1; i >= 0; i--) {
            p = new ListNode(elements[i], p);
        }
        return p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode curr = this;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(" -> ");
            }
            curr = curr.next;
        }
        return sb.toString();
    }
}
```

## 方法二

遞歸函數負責返回：從當前節點（我）開始，完成去重的鏈表

1. 若我與 `next` 重複，返回 `next`
2. 若我與 `next` 不重複，返回我，但 `next` 應當更新

```java
deleteDuplicates(ListNode p = 1) {
    deleteDuplicates(ListNode p = 1) {
        deleteDuplicates(ListNode p = 2) {
            deleteDuplicates(ListNode p = 3) {
                deleteDuplicates(ListNode p = 3) {
                    // 只剩下一個節點，返回
                }
            }
        }
    }
}
```

```java
public class Leetcode83 {
    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 1, 2, 3, 3);
        System.out.println(head);
        System.out.println(new Leetcode83().deleteDuplicates(head));
    }

    // 方法2
    public ListNode deleteDuplicates(ListNode p) {
        if (p == null || p.next == null) {
            return p;
        }
        if (p.val == p.next.val) {
            return deleteDuplicates(p.next);
        } else {
            p.next = deleteDuplicates(p.next);
            return p;
        }
    }
}
```

```java
public class ListNode {
    public Integer val;
    public ListNode next;

    public ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }

    public static ListNode of(int... elements) {
        if (elements.length == 0) {
            return null;
        }
        ListNode p = null;
        for (int i = elements.length - 1; i >= 0; i--) {
            p = new ListNode(elements[i], p);
        }
        return p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode curr = this;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(" -> ");
            }
            curr = curr.next;
        }
        return sb.toString();
    }
}
```

---

## 導航

- 上一篇：[LeetCode 19 刪除鏈表的倒數第 N 個節點](./08%20LeetCode%2019%20刪除鏈表的倒數第%20N%20個節點.md)
- 返回：[链表入口](./README.md)
- 下一篇：[LeetCode 82 删除排序链表中的所有重复元素 II](./10%20LeetCode%2082%20删除排序链表中的所有重复元素%20II.md)
