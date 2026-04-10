# LeetCode 82 删除排序链表中的所有重复元素 II

> 所属章节：[二、基础数据结构](../README.md) / [链表](./README.md)  
> 关键字：LeetCode 82、排序链表、删除所有重复元素、哨兵节点、三指针  
> 建議回查情境：要把所有出現重複的值整段刪掉，或比較 LeetCode 82 和 83 差異時

## 本节导读

這一節整理 LeetCode 82，和 83 不同的是只要某個值重複出現，就要把整段全部刪掉。第一次閱讀時建議先看和 83 的差異，再看三指針如何定位重複區間。

## 你會在這篇學到什麼

- LeetCode 82 與 83 的刪除規則差異
- 如何找出一整段重複值
- 三指針 `p1`、`p2`、`p3` 在刪除中的角色

---

- [82. 删除排序链表中的重复元素 II](https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/description/)

## 題目要做什麼？

給你一個 **已排序的鏈表**，請你刪除所有出現重複的節點，
最後只保留 **原本就沒有重複出現的節點**。

### 例子 1

```text
1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5
```

刪除所有重複值後：

```text
1 -> 2 -> 5
```

因為：

* `3` 出現兩次，要全部刪掉
* `4` 出現兩次，要全部刪掉

### 例子 2

```text
1 -> 1 -> 1 -> 2 -> 3
```

結果是：

```text
2 -> 3
```

因為 `1` 重複出現，所以 `1` 要整段刪掉，不留任何一個。

## 這題和 LeetCode 83 的差別

這題很容易和另一題搞混：

### LeetCode 83：刪除排序鏈表中的重複元素

* 重複元素只保留一個
* 例如：

```text
1 -> 1 -> 2 -> 3 -> 3
```

變成：

```text
1 -> 2 -> 3
```

### LeetCode 82：刪除排序鏈表中的所有重複元素 II

* 只要某個值重複出現，這個值就要全部刪掉
* 例如：

```text
1 -> 1 -> 2 -> 3 -> 3
```

變成：

```text
2
```

## 方法一

遞歸函數負責返回：從當前節點（我）開始，完成去重的鏈表

1. 若我與 `next` 重複，一直找到下一個不重複的節點，已它的返回結果為準
2. 若我與 `next` 不重複，返回我，同時更新 `next`

```java
deleteDuplicates(ListNode p = 1) {
    deleteDuplicates(ListNode p = 1) {
        deleteDuplicates(ListNode p = 1) {
            deleteDuplicates(ListNode p = 2) {
                deleteDuplicates(ListNode p = 3) {
                    // 只有一個節點，返回
                }
            }
        }
    }
}
```

```java
public class Leetcode82 {
    // 方法1
    public ListNode deleteDuplicates(ListNode p) {
        if (p == null || p.next == null) {
            return p;
        }
        if (p.val == p.next.val) {
            ListNode x = p.next.next;
            while (x != null && x.val == p.val) {
                x = x.next;
            }
            // 我們要以 x 的結果為準，因為前面重複的都不需要了
            return deleteDuplicates(x); // x 就是与 p 取值不同的节点
        } else {
            p.next = deleteDuplicates(p.next);
            return p;
        }
    }

    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 3, 3, 4, 4, 5);
        // ListNode head = ListNode.of(1, 1, 1, 2, 3);
        System.out.println(head);
        System.out.println(new Leetcode82().deleteDuplicates(head));
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

### 三個指針各自代表什麼？

- `p1`: 永遠指向「目前待處理區段的前一個節點」

- `p2`: 指向目前要判斷是否重複的節點

- `p3`: 指向 `p2` 的下一個節點，用來和 `p2` 比較

### 每輪迴圈要做什麼？

每次都比較 `p2.val` 和 `p3.val`：

#### 情況 1：p2.val == p3.val

代表遇到重複值

此時不能只刪掉其中一個，
而是要讓 `p3` 一直往後走，直到找到第一個 **和 `p2.val` 不同的節點**

然後做：

```java
p1.next = p3;
```

表示把整段重複值全部跳過。

### 情況 2：p2.val != p3.val

代表 `p2` 目前不是重複值，可以保留

此時只要讓 `p1` 往後移一格：

```java
p1 = p1.next;
```

接著繼續下一輪判斷。

#### 情況 3`p2 == null 或 p3 == null

表示已經走到尾端，迴圈結束。

### 圖解流程

以這個例子說明：

```text
1 -> 1 -> 1 -> 2 -> 3
```

加上虛擬頭節點：

```text
s -> 1 -> 1 -> 1 -> 2 -> 3 -> null
p1   p2   p3
```

#### 第一步：比較 p2 和 p3

```text
s -> 1 -> 1 -> 1 -> 2 -> 3 -> null
p1   p2   p3
```

`p2.val == p3.val`，表示值 `1` 有重複。

#### 第二步：讓 p3 繼續往後找

```text
s -> 1 -> 1 -> 1 -> 2 -> 3 -> null
p1   p2        p3
```

還是 `1`，繼續走。

再往後：

```text
s -> 1 -> 1 -> 1 -> 2 -> 3 -> null
p1   p2             p3
```

現在 `p3` 來到值 `2`，這是第一個和 `1` 不同的節點。

#### 第三步：刪除整段重複值

執行：

```java
p1.next = p3;
```

鏈表變成：

```text
s -> 2 -> 3 -> null
p1
```

也就是把前面的 `1 -> 1 -> 1` 全部跳過。

#### 第四步：繼續處理後面

現在：

```text
s -> 2 -> 3 -> null
p1   p2   p3
```

因為 `2 != 3`，表示 `2` 不重複，可以保留。

所以：

```java
p1 = p1.next;
```

變成：

```text
s -> 2 -> 3 -> null
     p1   p2   p3
```

接著因為後面沒有更多節點，迴圈結束。

### 整理後的程式碼

```java
public class Leetcode82 {

    public ListNode deleteDuplicates(ListNode head) {
        // 空鏈表或只有一個節點，不可能有重複
        if (head == null || head.next == null) {
            return head;
        }

        // 虛擬頭節點，方便處理開頭就重複的情況
        ListNode s = new ListNode(-1, head);

        // p1：待刪除區間的前一個節點
        ListNode p1 = s;

        // p2、p3：每輪動態取得
        ListNode p2, p3;

        while ((p2 = p1.next) != null && (p3 = p2.next) != null) {

            // 發現重複值
            if (p2.val == p3.val) {

                // p3 一直往後走，直到找到第一個不等於 p2.val 的節點
                while ((p3 = p3.next) != null && p3.val == p2.val) {
                }

                // 將整段重複值跳過
                p1.next = p3;

            } else {
                // p2 不重複，保留它，p1 往後移
                p1 = p1.next;
            }
        }

        return s.next;
    }

    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 3, 3, 4, 4, 5);
        // ListNode head = ListNode.of(1, 1, 1, 2, 3);

        System.out.println(head);
        System.out.println(new Leetcode82().deleteDuplicates(head));
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

- 上一篇：[LeetCode 83 删除排序链表中的重复元素](./09%20LeetCode%2083%20删除排序链表中的重复元素.md)
- 返回：[链表入口](./README.md)
- 下一篇：[LeetCode 21 合并两个有序链表](./11%20LeetCode%2021%20合并两个有序链表.md)
