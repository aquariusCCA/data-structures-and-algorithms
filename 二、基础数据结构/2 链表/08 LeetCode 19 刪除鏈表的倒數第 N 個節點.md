# LeetCode 19 刪除鏈表的倒數第 N 個節點

> 所属章节：[二、基础数据结构](../README.md) / [链表](./README.md)  
> 关键字：LeetCode 19、倒数第 N 个节点、递归、快慢指针、虚拟头节点  
> 建議回查情境：要回查倒數第 N 個節點的遞歸解法、快慢指針距離設計或虛擬頭節點作用時

## 本节导读

這一節整理 LeetCode 19 的兩種思路：遞歸回退計數與快慢指針。第一次閱讀時建議先看遞歸如何取得倒數位置，再看快慢指針如何用固定間距定位待刪節點的前驅。

## 你會在這篇學到什麼

- 遞歸回傳值如何表示倒數位置
- 快慢指針為什麼能定位倒數第 N 個節點
- 虛擬頭節點如何處理刪除頭節點的情況

---

- [19. 删除链表的倒数第 N 个结点](https://leetcode.cn/problems/remove-nth-node-from-end-of-list/description/)

## 方法一：遞歸

**偽碼**

```java
recursion(ListNode p=1, int n=2) {
    recursion(ListNode p=2, int n=2) {
        recursion(ListNode p=3, int n=2) {
            recursion(ListNode p=4, int n=2) {
                recursion(ListNode p=5, int n=2) {
                    recursion(ListNode p=null, int n=2) {
                        return 0;
                    }
                    return 1;
                }
                return 2;
            }
            if(返回值 == n == 2) {
                删除
            }
            return 3;
        }
        return 4;
    }
    return 5;
}
```

```java
public class Leetcode19 {
    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 3, 4, 5);
        // ListNode head = ListNode.of(1,2);
        System.out.println(head);
        System.out.println(new Leetcode19()
                .removeNthFromEnd(head, 5));
    }

    // 方法1
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode s = new ListNode(-1, head);
        recursion(s, n);
        return s.next;
    }

    private int recursion(ListNode p, int n) {
        if (p == null) {
            return 0;
        }
        int nth = recursion(p.next, n); // 下一个节点的倒数位置
        if (nth == n) {
            // p=3  p.next=4 p.next.next=5
            p.next = p.next.next;
        }
        return nth + 1;
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

## 方法二：快慢指針法

### 核心觀念

這題真正要做的，不是直接找到「要刪除的節點」，
而是要找到：

> **要被刪除節點的前一個節點 `prev`**

因為單向鏈表刪除節點時，標準做法是：

```text
prev.next = prev.next.next
```

也就是：

* `prev.next` 原本指向要刪除的節點
* 改成指向「要刪除節點的下一個節點」

### 為什麼用快慢指針？

如果要刪除倒數第 `n` 個節點，
等價於找到「倒數第 `n+1` 個節點」，也就是它的前一個節點。

所以可以使用兩個指針：

* `p1`：慢指針
* `p2`：快指針

做法是：

1. 先讓 `p2` 比 `p1` 先走 `n + 1` 步
2. 然後 `p1`、`p2` 一起往前走
3. 當 `p2` 走到 `null` 時，`p1` 剛好停在「要刪除節點的前一個節點」

### 為什麼要設置虛擬頭節點 s？

#### 麻煩的地方在哪？

##### 情況 1：刪除的不是頭節點

可以正常找前一個節點，再做：

```java
prev.next = prev.next.next;
```

##### 情況 2：刪除的就是頭節點

不能用上面那套，必須改成：

```java
head = head.next;
```

也就是說：

* 刪一般節點：一種寫法
* 刪頭節點：另一種寫法

程式就會多出特判，邏輯不統一。

##### 舉個例子

假設：

```text
1 -> 2 -> 3 -> 4 -> 5
n = 5
```

倒數第 5 個就是頭節點 `1`。

如果沒有虛擬頭節點，你最後其實要做的是：

```java
head = head.next;
```

而不是：

```java
prev.next = prev.next.next;
```

因為 `1` 前面沒有 `prev`。

#### 有虛擬頭節點後就不麻煩了

加上一個假的頭節點：

```text
s -> 1 -> 2 -> 3 -> 4 -> 5
```

這時如果要刪掉 `1`，
其實 `1` 的前一個節點就是 `s`。

所以仍然可以用同一套寫法：

```java
p1.next = p1.next.next;
```

也就是：

```text
s -> 1 -> 2 -> 3
```

變成：

```text
s -> 2 -> 3
```

最後回傳 `s.next` 就好。

### 圖解流程

假設：

```text
鏈表：1 -> 2 -> 3 -> 4 -> 5
n = 2
```

加上虛擬頭節點後：

```text
s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
p1
p2
```

#### 第一步：先讓 p2 走 n + 1 = 3 步

走 1 步：

```text
s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
     p2
p1
```

走 2 步：

```text
s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
          p2
p1
```

走 3 步：

```text
s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
               p2
p1
```

此時 `p1` 和 `p2` 的距離固定為 `n + 1`。

#### 第二步：p1、p2 一起往前走

一起移動，直到 `p2 == null`

最後會變成：

```text
s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
          p1                  p2
```

這時候：

* `p2 == null`
* `p1` 停在節點 `3`
* 而節點 `4` 正是要刪除的節點

#### 第三步：刪除節點

原本：

```text
3 -> 4 -> 5
```

做這一行：

```java
p1.next = p1.next.next;
```

就會變成：

```text
3 -> 5
```

整條鏈表變成：

```text
1 -> 2 -> 3 -> 5
```

### 程式碼

```java
public class Leetcode19 {
    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 3, 4, 5);
        System.out.println(head);
        System.out.println(new Leetcode19().removeNthFromEnd(head, 2));
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 建立虛擬頭節點，方便統一處理刪除頭節點的情況
        ListNode s = new ListNode(-1, head);

        ListNode p1 = s;
        ListNode p2 = s;

        // 讓 p2 先走 n + 1 步，和 p1 拉開距離
        for (int i = 0; i < n + 1; i++) {
            p2 = p2.next;
        }

        // p1、p2 一起走，直到 p2 走到 null
        while (p2 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }

        // 刪除 p1 的下一個節點
        p1.next = p1.next.next;

        return s.next;
    }
}
```

```java
public class ListNode {
    public Integer val;
    public ListNode next;

    public ListNode(int val, ListNode next) {
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

- 上一篇：[LeetCode 203 移除链表元素](./07%20LeetCode%20203%20移除链表元素.md)
- 返回：[链表入口](./README.md)
- 下一篇：[LeetCode 83 删除排序链表中的重复元素](./09%20LeetCode%2083%20删除排序链表中的重复元素.md)
