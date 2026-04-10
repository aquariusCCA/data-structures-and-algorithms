# LeetCode 23 合并 K 个升序链表

> 所属章节：[二、基础数据结构](../README.md) / [链表](./README.md)  
> 关键字：LeetCode 23、合并 K 个链表、分治、递归、mergeTwoLists  
> 建議回查情境：要理解 K 條有序鏈表如何用分治拆分，或回查 `split(lists, i, j)` 遞歸過程時

## 本节导读

這一節整理 LeetCode 23 的分治解法，重點是把 K 條鏈表反覆拆成左右兩半，再用合併兩條有序鏈表的邏輯合回來。第一次閱讀時建議先確認 `mergeTwoLists`，再看 `split` 的遞歸拆分。

## 你會在這篇學到什麼

- 為什麼合併 K 條鏈表適合用分治
- `mergeTwoLists` 與 `split` 的分工
- 如何展開理解 `split` 的遞歸返回值

---

- [23. 合并 K 个升序链表](https://leetcode.cn/problems/merge-k-sorted-lists/description/)

## 解題思路：分治法

這題可以看成是 `21. 合并两个有序链表` 的延伸版。

前一題我們已經知道：

> 只要會「合併兩條有序鏈表」，就能把兩條遞增鏈表合成一條新的遞增鏈表。

那麼這一題的關鍵就變成：

> 如果現在不是 2 條，而是 `K` 條，要怎麼把問題拆小？

最直覺的想法是從左到右逐條合併：

```text
((list0 + list1) + list2) + list3 ...
```

這樣可以做，但效率不是最好。

這份程式碼採用的是更常見、也更高效的做法：

> **分治（Divide and Conquer）**

也就是：

1. 先把 `K` 條鏈表分成左半和右半
2. 左半自己合併成一條
3. 右半自己合併成一條
4. 最後再把這兩條結果合併起來

這樣的想法其實很像「合併排序」。

## 為什麼分治比較好？

因為每次不是把所有鏈表都往同一條結果上累加，而是：

* 先兩兩合併
* 再把小結果繼續兩兩合併
* 一層一層往上收斂

這樣每個節點被處理的層數會比較平均，因此整體效率更好。

## 程式分成兩個核心

### 1. mergeTwoLists(p1, p2)

這個函式和第 21 題一樣，負責：

> 合併兩條有序鏈表，並回傳合併後的新頭節點

也就是說，這題真正的新部分不在「怎麼合併兩條鏈表」，而在：

> **怎麼把 K 條鏈表拆成很多個兩條鏈表的問題**

### 2. split(lists, i, j)

這個函式負責：

> 把 `lists[i...j]` 這一段鏈表全部合併，並回傳合併後的結果

它的定義非常重要：

```java
split(lists, i, j)
```

表示：

> 把索引從 `i` 到 `j` 的所有鏈表合併成一條鏈表

## split 的遞迴規則

### 1. 終止條件

如果 `i == j`，表示這個區間裡只剩下一條鏈表。

那就不用再拆了，直接回傳它本身：

```java
if (i == j) {
    return lists[i];
}
```

### 2. 遞迴拆分

先找中間位置：

```java
int m = (i + j) >>> 1;
```

這裡的 `>>> 1` 可以理解成「除以 2」，用來取中間索引。

接著把區間拆成左右兩半：

```java
ListNode left = split(lists, i, m);
ListNode right = split(lists, m + 1, j);
```

此時：

* `left` 是左半部所有鏈表合併後的結果
* `right` 是右半部所有鏈表合併後的結果

最後再呼叫前面已經會的 `mergeTwoLists`：

```java
return mergeTwoLists(left, right);
```

## 範例

假設輸入：

```text
lists = [
  1 -> 4 -> 5,
  1 -> 3 -> 4,
  2 -> 6
]
```

呼叫：

```java
mergeKLists(lists)
```

會先進入：

```java
split(lists, 0, 2)
```

### 第一步：拆成左右兩半

```text
split(0, 2)
├── split(0, 1)
└── split(2, 2)
```

### 第二步：繼續拆左半

```text
split(0, 1)
├── split(0, 0) -> 1 -> 4 -> 5
└── split(1, 1) -> 1 -> 3 -> 4
```

然後把這兩條合併：

```text
mergeTwoLists(
  1 -> 4 -> 5,
  1 -> 3 -> 4
)
```

得到：

```text
1 -> 1 -> 3 -> 4 -> 4 -> 5
```

### 第三步：右半不用拆

```text
split(2, 2) -> 2 -> 6
```

### 第四步：把左右結果再合併一次

左半結果：

```text
1 -> 1 -> 3 -> 4 -> 4 -> 5
```

右半結果：

```text
2 -> 6
```

最後合併：

```text
1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
```

## 如何理解整個遞迴過程？

這樣看遞迴時，重點只有兩件事：

1. 這一層先呼叫了誰
2. 子問題回來後，這一層回傳什麼

這題的 `split(lists, i, j)` 雖然不是沿著單鏈表一路往下走，
但它的閱讀方式完全一樣：

> 先進入更小的子問題，等子問題回傳後，再處理當前這一層。

### 用偽碼展開 split(0, 2)`

假設：

```text
lists[0] = 1 -> 4 -> 5
lists[1] = 1 -> 3 -> 4
lists[2] = 2 -> 6
```

那麼：

```java
split(lists, 0, 2) {
    split(lists, 0, 1) {
        split(lists, 0, 0) {
            return lists[0];   // 1 -> 4 -> 5
        }
        split(lists, 1, 1) {
            return lists[1];   // 1 -> 3 -> 4
        }
        return mergeTwoLists(
            1 -> 4 -> 5,
            1 -> 3 -> 4
        ); // 回傳 A = 1 -> 1 -> 3 -> 4 -> 4 -> 5
    }
    split(lists, 2, 2) {
        return lists[2];       // 2 -> 6
    }
    return mergeTwoLists(
        A,
        2 -> 6
    ); // 回傳 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
}
```

這個展開方式要表達的其實是：

* `split(0, 2)` 不會一進來就直接合併
* 它會先把左邊 `split(0, 1)` 算完
* 再把右邊 `split(2, 2)` 算完
* 等左右結果都拿到之後，才做最後一次 `mergeTwoLists`

### 如果只看「回傳值」會更清楚

也可以再把它壓縮成只看回傳值的版本：

```java
split(0, 2) {
    left = split(0, 1) {
        left = split(0, 0) { return list0; }
        right = split(1, 1) { return list1; }
        return merge(list0, list1);   // 回傳 A
    }
    right = split(2, 2) { return list2; }
    return merge(A, list2);           // 回傳最終答案
}
```

其中：

```text
list0 = 1 -> 4 -> 5
list1 = 1 -> 3 -> 4
list2 = 2 -> 6
A     = 1 -> 1 -> 3 -> 4 -> 4 -> 5
答案   = 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
```

### 這段偽碼真正幫你看懂的是什麼？

它幫你看懂的不是語法，而是遞迴的執行順序：

1. 先一路往下拆，拆到 `i == j`
2. 最底層先回傳單一鏈表
3. 上一層拿到左右結果後才開始合併
4. 一層一層往上回傳，最後得到整體答案

所以這題的核心可以記成一句話：

> `split(i, j)` 的任務，就是回傳 `lists[i...j]` 全部合併後的結果。

## 整體流程整理

可以把這題理解成下面三句話：

1. 先把多條鏈表拆成左右兩半
2. 左右兩半各自遞迴合併
3. 最後把左右結果用 `mergeTwoLists` 合併

所以它的本質其實是：

> **把 K 條鏈表合併問題，不斷拆成「合併兩條鏈表」的子問題**

### mergeKLists 做了什麼？

入口函式：

```java
public ListNode mergeKLists(ListNode[] lists)
```

它只做兩件事：

#### 1. 處理空陣列

如果 `lists.length == 0`，代表根本沒有鏈表可合併，直接回傳 `null`

#### 2. 把整個區間交給 split

```java
return split(lists, 0, lists.length - 1);
```

也就是從整個陣列範圍開始遞迴處理。

## 程式碼

```java
/**
 * 合并多个有序链表
 */
public class Leetcode23 {
    // 合并两个有序链表
    public ListNode mergeTwoLists(ListNode p1, ListNode p2) {
        if (p2 == null) {
            return p1;
        }
        if (p1 == null) {
            return p2;
        }
        if (p1.val < p2.val) {
            p1.next = mergeTwoLists(p1.next, p2);
            return p1;
        } else {
            p2.next = mergeTwoLists(p1, p2.next);
            return p2;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        return split(lists, 0, lists.length - 1);
    }

    // 返回合并后的链表, i, j 代表左右边界
    private ListNode split(ListNode[] lists, int i, int j) {
        if (i == j) { // 数组内只有一个链表
            return lists[i];
        }
        int m = (i + j) >>> 1;
        ListNode left = split(lists, i, m);
        ListNode right = split(lists, m + 1, j);
        return mergeTwoLists(left, right);
    }

    public static void main(String[] args) {
        ListNode[] lists = {
                ListNode.of(1, 4, 5),
                ListNode.of(1, 3, 4),
                ListNode.of(2, 6),
        };
        ListNode m = new Leetcode23().mergeKLists(lists);
        System.out.println(m);
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

- 上一篇：[LeetCode 21 合并两个有序链表](./11%20LeetCode%2021%20合并两个有序链表.md)
- 返回：[链表入口](./README.md)
- 下一篇：[LeetCode 876 鏈表的中間結點](./13%20LeetCode%20876%20鏈表的中間結點.md)
