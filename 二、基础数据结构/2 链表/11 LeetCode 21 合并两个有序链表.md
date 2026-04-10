# LeetCode 21 合并两个有序链表

> 所属章节：[二、基础数据结构](../README.md) / [链表](./README.md)  
> 关键字：LeetCode 21、合并有序链表、迭代、递归、哨兵节点  
> 建議回查情境：要合併兩條有序鏈表，或想比較迭代法與遞歸法時

## 本节导读

這一節整理 LeetCode 21，核心是每次從兩條鏈表的頭部挑出較小節點接到結果鏈表。第一次閱讀時建議先看迭代法的哨兵節點，再看遞歸法如何把「挑小的」拆成子問題。

## 你會在這篇學到什麼

- 合併兩條有序鏈表的核心規則
- 迭代法中哨兵節點與尾指針的作用
- 遞歸法如何定義終止條件與返回結果

---

- [21. 合并两个有序链表](https://leetcode.cn/problems/merge-two-sorted-lists/description/)

## 方法一：迭代法
### 解題思路

因為兩個鏈表本來就已經是遞增排序，所以每次只要比較兩個鏈表目前節點的值：

* 哪個值比較小，就先把哪個節點接到新鏈表後面
* 然後該鏈表指標往後移動一格
* 新鏈表的尾指標 `p` 也往後移動一格

一直做，直到其中一條鏈表走到 `null` 為止。

最後，另一條還沒走完的鏈表，直接接到新鏈表尾端即可，因為它本身已經有序，不需要再逐一比較。

### 指標說明

* `p1`：指向第一條有序鏈表目前比較的位置
* `p2`：指向第二條有序鏈表目前比較的位置
* `s`：虛擬頭節點（dummy node），方便操作
* `p`：指向新鏈表的尾端，負責把較小的節點接上去

### 範例

#### 初始狀態

```text
p1
1 -> 3 -> 8 -> 9 -> null

p2
2 -> 4 -> null

s -> null
p
```

#### 第一次比較

比較 `p1.val = 1` 和 `p2.val = 2`

* `1 < 2`
* 所以把 `1` 接到 `p` 後面
* `p1` 往後移
* `p` 也往後移

```text
s -> 1 -> null
     p

p1
3 -> 8 -> 9 -> null

p2
2 -> 4 -> null
```

#### 第二次比較

比較 `3` 和 `2`

* `2` 比較小
* 把 `2` 接到 `p` 後面
* `p2` 往後移
* `p` 往後移

```text
s -> 1 -> 2 -> null
          p

p1
3 -> 8 -> 9 -> null

p2
4 -> null
```

#### 第三次比較

比較 `3` 和 `4`

* `3` 比較小
* 接上 `3`

```text
s -> 1 -> 2 -> 3 -> null
               p

p1
8 -> 9 -> null

p2
4 -> null
```

#### 第四次比較

比較 `8` 和 `4`

* `4` 比較小
* 接上 `4`

```text
s -> 1 -> 2 -> 3 -> 4 -> null
                    p

p1
8 -> 9 -> null

p2
null
```

#### 結束

此時 `p2 == null`，比較結束。
直接把剩下的 `p1` 接到 `p` 後面：

```text
s -> 1 -> 2 -> 3 -> 4 -> 8 -> 9 -> null
```

最後回傳 `s.next`，也就是：

```text
1 -> 2 -> 3 -> 4 -> 8 -> 9
```

### 核心規則

1. 比較 `p1` 和 `p2` 當前節點值
2. 誰小，就把誰接到 `p.next`
3. 被接上的那條鏈表往後移一格
4. `p` 也往後移一格
5. 當 `p1` 或 `p2` 其中一個為 `null` 時，停止比較
6. 把剩下不為 `null` 的那條鏈表直接接到 `p.next`

### 程式碼

```java
public class Leetcode21 {
    public static void main(String[] args) {
        ListNode p1 = ListNode.of(1, 3, 8, 9);
        ListNode p2 = ListNode.of(2, 4);

        System.out.println(new Leetcode21().mergeTwoLists(p1, p2));
    }

    public ListNode mergeTwoLists(ListNode p1, ListNode p2) {
        // 虛擬頭節點，方便統一處理
        ListNode s = new ListNode(-1, null);
        // p 指向新鏈表尾端
        ListNode p = s;

        // 只要兩條鏈表都還有節點，就持續比較
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                p.next = p1;   // 接上較小的 p1
                p1 = p1.next;  // p1 往後移
            } else {
                p.next = p2;   // 接上較小的 p2
                p2 = p2.next;  // p2 往後移
            }
            p = p.next;        // 新鏈表尾端往後移
        }

        // 把剩餘未處理完的鏈表直接接上
        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
        }

        // 回傳真正的頭節點
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

## 方法二：遞迴法

### 遞迴的核心想法

迭代法是每次挑較小的節點接到結果後面。
遞迴法本質上也是一樣，只是把「接下來剩下的部分怎麼合併」交給遞迴去做。

#### 遞迴函式在做什麼？

`mergeTwoLists(p1, p2)` 的意思是：

> **把以 `p1` 為開頭的鏈表** 和 **以 `p2` 為開頭的鏈表** 合併，並回傳合併後的新頭節點。

所以這個函式每次都要決定：

1. 目前 `p1` 和 `p2` 誰比較小
2. 較小的那個節點，應該作為本次合併後的頭節點
3. 它的 `next`，要接上「剩下部分合併後的結果」

### 遞迴規則

#### 1. 遞迴終止條件

當其中一條鏈表已經走完時，就不用再比較了，直接回傳另一條鏈表即可。

```java
if (p2 == null) {
    return p1;
}
if (p1 == null) {
    return p2;
}
```

意思是：

* 如果 `p2` 已經沒了，剩下的就是 `p1`
* 如果 `p1` 已經沒了，剩下的就是 `p2`

#### 2. 遞迴處理邏輯

如果 `p1.val < p2.val`：

* 代表 `p1` 比較小
* 那麼本次合併後的頭節點就是 `p1`
* 但 `p1.next` 應該接誰？
  應該接上 `p1.next` 與 `p2` 合併後的結果

```java
p1.next = mergeTwoLists(p1.next, p2);
return p1;
```

如果 `p2.val <= p1.val`：

* 代表 `p2` 比較小
* 那麼本次合併後的頭節點就是 `p2`
* `p2.next` 應該接上 `p1` 與 `p2.next` 合併後的結果

```java
p2.next = mergeTwoLists(p1, p2.next);
return p2;
```

### 可以這樣理解整個遞迴過程

假設：

```text
p1 = 1 -> 3 -> 8 -> 9
p2 = 2 -> 4
```

我們呼叫：

```java
mergeTwoLists(p1, p2)
```

#### 第一次呼叫

比較 `1` 和 `2`

* `1` 比較小
* 所以回傳的頭節點應該是 `1`
* 但是 `1.next` 要接上什麼？
* 要接上：

```java
mergeTwoLists([3, 8, 9], [2, 4])
```

也就是：

```java
1.next = mergeTwoLists([3, 8, 9], [2, 4])
return 1
```

#### 第二次呼叫

比較 `3` 和 `2`

* `2` 比較小
* 所以這一層回傳 `2`
* `2.next` 要接上：

```java
mergeTwoLists([3, 8, 9], [4])
```

也就是：

```java
2.next = mergeTwoLists([3, 8, 9], [4])
return 2
```

#### 第三次呼叫

比較 `3` 和 `4`

* `3` 比較小
* 所以這一層回傳 `3`
* `3.next` 要接上：

```java
mergeTwoLists([8, 9], [4])
```

也就是：

```java
3.next = mergeTwoLists([8, 9], [4])
return 3
```

#### 第四次呼叫

比較 `8` 和 `4`

* `4` 比較小
* 所以這一層回傳 `4`
* `4.next` 要接上：

```java
mergeTwoLists([8, 9], null)
```

也就是：

```java
4.next = mergeTwoLists([8, 9], null)
return 4
```

#### 第五次呼叫

此時 `p2 == null`

所以直接回傳：

```java
return [8, 9]
```

#### 開始往回返回

第五層先回傳 `[8, 9]`

所以第四層變成：

```java
4.next = [8, 9]
return 4
```

得到：

```text
4 -> 8 -> 9
```

第三層變成：

```java
3.next = [4 -> 8 -> 9]
return 3
```

得到：

```text
3 -> 4 -> 8 -> 9
```

第二層變成：

```java
2.next = [3 -> 4 -> 8 -> 9]
return 2
```

得到：

```text
2 -> 3 -> 4 -> 8 -> 9
```

第一層變成：

```java
1.next = [2 -> 3 -> 4 -> 8 -> 9]
return 1
```

最後結果就是：

```text
1 -> 2 -> 3 -> 4 -> 8 -> 9
```

#### 遞迴過程整理版

可以把它整理成下面這樣：

```text
mergeTwoLists([1, 3, 8, 9], [2, 4])
= 1 -> mergeTwoLists([3, 8, 9], [2, 4])

mergeTwoLists([3, 8, 9], [2, 4])
= 2 -> mergeTwoLists([3, 8, 9], [4])

mergeTwoLists([3, 8, 9], [4])
= 3 -> mergeTwoLists([8, 9], [4])

mergeTwoLists([8, 9], [4])
= 4 -> mergeTwoLists([8, 9], null)

mergeTwoLists([8, 9], null)
= [8, 9]
```

再一路往回接起來：

```text
4 -> 8 -> 9
3 -> 4 -> 8 -> 9
2 -> 3 -> 4 -> 8 -> 9
1 -> 2 -> 3 -> 4 -> 8 -> 9
```

### 程式碼

```java
public class Leetcode21 {
    public static void main(String[] args) {
        ListNode p1 = ListNode.of(1, 3, 8, 9);
        ListNode p2 = ListNode.of(2, 4);

        System.out.println(new Leetcode21().mergeTwoLists(p1, p2));
    }

    // 方法2：遞迴
    public ListNode mergeTwoLists(ListNode p1, ListNode p2) {
        // 遞迴終止條件
        if (p2 == null) {
            return p1;
        }
        if (p1 == null) {
            return p2;
        }

        // 誰小，就讓誰作為目前這一層的頭節點
        if (p1.val < p2.val) {
            p1.next = mergeTwoLists(p1.next, p2);
            return p1;
        } else {
            p2.next = mergeTwoLists(p1, p2.next);
            return p2;
        }
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

- 上一篇：[LeetCode 82 删除排序链表中的所有重复元素 II](./10%20LeetCode%2082%20删除排序链表中的所有重复元素%20II.md)
- 返回：[链表入口](./README.md)
- 下一篇：[LeetCode 23 合并 K 个升序链表](./12%20LeetCode%2023%20合并%20K%20个升序链表.md)
