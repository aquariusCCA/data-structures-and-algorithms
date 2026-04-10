# LeetCode 206 反转链表

> 所属章节：[二、基础数据结构](../README.md) / [链表](./README.md)  
> 关键字：LeetCode 206、反转链表、头插法、递归、双指针、指针反转  
> 建議回查情境：要比較反轉鏈表的多種寫法，或卡在 `p.next.next = p`、頭插法、雙指針反轉時

## 本节导读

這一節整理 LeetCode 206 反轉鏈表的多種解法，包含新鏈表頭插、容器輔助、遞歸、局部重排與雙指針。第一次閱讀時建議先掌握方法一的頭插直覺，再細讀方法三和方法五的指針變化。

## 你會在這篇學到什麼

- 反轉鏈表的多種實作思路
- 遞歸反轉中 `p.next.next = p` 與 `p.next = null` 的作用
- 迭代反轉時各個指針的角色與更新順序

---

> [206. 反转链表](https://leetcode.cn/problems/reverse-linked-list/description/)

## 方法一

### 這個解法的核心思路

這個解法的想法是：

**不在原本鏈表上直接反轉，而是另外建立一條新鏈表。**

做法如下：

1. 從舊鏈表的頭開始，依序遍歷每個節點
2. 每讀到一個節點，就建立一個新的節點
3. 將這個新節點插入到 **新鏈表的頭部**
4. 全部處理完後，新鏈表自然就是反轉後的結果

### 為什麼插入頭部就能反轉？

假設舊鏈表資料依序是：

```text
1, 2, 3, 4, 5
```

我們依次讀取並插入到新鏈表頭部：

#### 讀到 1

新鏈表：

```text
1
```

#### 讀到 2

把 2 插到前面：

```text
2 -> 1
```

#### 讀到 3

把 3 插到前面：

```text
3 -> 2 -> 1
```

#### 讀到 4

```text
4 -> 3 -> 2 -> 1
```

#### 讀到 5

```text
5 -> 4 -> 3 -> 2 -> 1
```

所以最後結果就是倒序。

### 指針角色

這個解法中主要有兩個指針：

* `p`：用來遍歷 **舊鏈表**
* `n1`：指向 **新鏈表的頭節點**

### 流程圖解

#### 初始狀態

* `p` 指向舊鏈表頭
* `n1 = null`，表示新鏈表一開始是空的

```text
舊鏈表：p  -> 1 -> 2 -> 3 -> 4 -> 5 -> null
新鏈表：n1 -> null
```

#### 第 1 輪：讀到 1

建立新節點 `1`，插到新鏈表頭部：

```text
舊鏈表：     1 -> 2 -> 3 -> 4 -> 5 -> null
            p
新鏈表：n1 -> 1 -> null
```

#### 第 2 輪：讀到 2

建立新節點 `2`，插到新鏈表頭部：

```text
舊鏈表：     1 -> 2 -> 3 -> 4 -> 5 -> null
                 p
新鏈表：n1 -> 2 -> 1 -> null
```

#### 第 3 輪：讀到 3

```text
舊鏈表：     1 -> 2 -> 3 -> 4 -> 5 -> null
                      p
新鏈表：n1 -> 3 -> 2 -> 1 -> null
```

#### 第 4 輪：讀到 4

```text
舊鏈表：     1 -> 2 -> 3 -> 4 -> 5 -> null
                           p
新鏈表：n1 -> 4 -> 3 -> 2 -> 1 -> null
```

#### 第 5 輪：讀到 5

```text
舊鏈表：     1 -> 2 -> 3 -> 4 -> 5 -> null
                                p
新鏈表：n1 -> 5 -> 4 -> 3 -> 2 -> 1 -> null
```

遍歷結束後：

```text
p == null
```

回傳 `n1` 即可。

### 程式碼

```java
/**
 * 反转链表
 */
public class E01Leetcode206 {
    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(3, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);

        System.out.println(o1);

        ListNode n1 = new E01Leetcode206().reverseList(o1);
        System.out.println(n1);
    }

    public ListNode reverseList(ListNode o1) {
        ListNode n1 = null; // 建立新鏈表頭指針，初始為空。
        ListNode p = o1; // 使用 p 來遍歷舊鏈表，從頭節點開始走訪。

        // 只要舊鏈表還有節點，就持續處理。
        while (p != null) {
            n1 = new ListNode(p.val, n1);
            p = p.next; // 繼續處理舊鏈表下一個節點。
        }

        return n1;
    }
}
```

`n1 = new ListNode(p.val, n1);`

這是整題最核心的一行。

意思是：

1. 用 `p.val` 建立一個新節點
2. 讓這個新節點的 `next` 指向目前的新鏈表頭 `n1`
3. 再讓 `n1` 指向這個新節點

也就是把新節點插到新鏈表最前面。

```java
public class ListNode {
    public Integer val; // 節點值
    public ListNode next; // 指向下一個節點

    public ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
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

### 核心思路

這個方法和「建立新鏈表、建立新節點」的思路很像，
但有一個重要差別：

**這一版不是建立新節點**

而是：

* 從 **舊鏈表頭部** 拿出節點
* 直接插入到 **新鏈表頭部**

因此整個過程可以理解成：

> **把舊鏈表的節點一個一個拆下來，搬到新鏈表前面。**

最後新鏈表的順序自然就反過來了。

### 方法二和方法的差別

#### 方法一

* 每讀到一個值，就 `new ListNode(...)`
* 會建立新的節點
* 原鏈表不動

### 方法二

* 不建立新節點
* 直接使用原本的節點
* 從舊鏈表移除後，加到新鏈表頭部

所以這版更接近「真正搬節點」的概念。

### 為什麼需要 List 容器類？

`ListNode`，沒有提供完整的鏈表類別。
但這個解法想做兩個動作：

1. 從鏈表頭部移除節點
2. 在鏈表頭部加入節點

所以這裡額外包了一個 `List` 類別，讓操作更清楚：

* `removeFirst()`：刪除並回傳頭節點
* `addFirst(node)`：把節點插到頭部

這樣整個流程就會變得很直觀。

### 整體流程

準備兩個鏈表：

* `list1`：舊鏈表
* `list2`：新鏈表

一開始：

```text
list1: 1 -> 2 -> 3 -> 4 -> 5 -> null
list2: null
```

接著重複做：

1. 從 `list1` 頭部移除一個節點
2. 把這個節點插入 `list2` 頭部

直到 `list1` 為空。

### 流程圖解

#### 初始狀態

```text
list1: 1 -> 2 -> 3 -> 4 -> 5 -> null
list2: null
```

#### 第 1 輪

從 `list1` 移除頭節點 `1`：

```text
first = 1
list1: 2 -> 3 -> 4 -> 5 -> null
```

把 `1` 插入 `list2` 頭部：

```text
list2: 1 -> null
```

#### 第 2 輪

從 `list1` 移除頭節點 `2`：

```text
first = 2
list1: 3 -> 4 -> 5 -> null
```

插入 `list2` 頭部：

```text
list2: 2 -> 1 -> null
```

#### 第 3 輪

```text
first = 3
list1: 4 -> 5 -> null
list2: 3 -> 2 -> 1 -> null
```

#### 第 4 輪

```text
first = 4
list1: 5 -> null
list2: 4 -> 3 -> 2 -> 1 -> null
```

#### 第 5 輪

```text
first = 5
list1: null
list2: 5 -> 4 -> 3 -> 2 -> 1 -> null
```

#### 結束

當 `list1.removeFirst()` 回傳 `null`，表示舊鏈表已經空了，迴圈結束。

最後回傳：

```java
list2.head
```

### 程式碼

```java
/**
 * 反转链表
 */
public class E01Leetcode206 {
    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(3, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);

        System.out.println(o1);

        ListNode n1 = new E01Leetcode206().reverseList(o1);
        System.out.println(n1);
    }

    public ListNode reverseList(ListNode o1) {
        List list1 = new List(o1); // 原鏈表
        List list2 = new List(null); // 新鏈表，一開始是空的

        while (true) {
            // 從 list1 取出頭節點
            ListNode first = list1.removeFirst();
            // 判斷是否取完
            // 如果取出的是 null，代表鏈表空了，停止迴圈。
            if (first == null) {
                break;
            }
            list2.addFirst(first);
        }

        return list2.head;
    }

    static class List {
        ListNode head;

        public List(ListNode head) {
            this.head = head;
        }

        /**
         * 讓新節點指向原本頭節點
         * 再把 head 改成這個新節點
         */
        public void addFirst(ListNode first) {
            first.next = head;
            head = first;
        }

        public ListNode removeFirst() {
            // 先記住頭節點
            ListNode first = head;
            if (first != null) {
                // 再讓 head 往後移
                head = first.next;
            }
            // 回傳原本的頭節點
            return first;
        }
    }
}
```

```java
public class ListNode {
    public Integer val; //節點值
    public ListNode next; // 下一個節點

    public ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
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

## 方法三

### 核心思路

這個解法利用了 **遞歸在返回時會倒著處理節點** 的特性。

正常往下遞歸時，節點順序是：

```text
1 -> 2 -> 3 -> 4 -> 5
```

但當遞歸開始返回時，處理順序會變成：

```text
5 -> 4 -> 3 -> 2 -> 1
```

剛好就是我們想要的反轉順序。

所以這題可以這樣想：

1. 一路遞歸到最後一個節點
2. 把最後一個節點當成新鏈表頭
3. 在遞歸返回的過程中，讓後一個節點指回前一個節點
4. 同時把原本的 `next` 斷開，避免形成環

### 第一步：先找到最後一個節點

先寫出最基本的遞歸框架：

```java
public ListNode reverseList(ListNode p) {
    if (p == null || p.next == null) { // 不足兩個節點
        return p; // 最後一個節點
    }
    ListNode last = reverseList(p.next);
    return last;
}
```

這段程式的作用是：

* 不斷往下遞歸
* 直到走到最後一個節點
* 然後把最後一個節點一層一層往上傳回去

### 遞歸終止條件

```java
if (p == null || p.next == null) {
    return p;
}
```

這裡有兩個情況要注意。

#### 1. p == null

代表空鏈表，直接回傳 `null`

#### 2. p.next == null

代表目前已經來到最後一個節點，這時候就停止遞歸，並把這個節點當作反轉後的新頭節點回傳

### 先理解遞歸呼叫過程

假設原鏈表是：

```text
1 -> 2 -> 3 -> 4 -> 5 -> null
```

那麼呼叫過程可以想成：

```java
reverseList(1)
    -> reverseList(2)
        -> reverseList(3)
            -> reverseList(4)
                -> reverseList(5)
```

當 `p = 5` 時：

```java
p.next == null
```

成立，所以回傳 `5`。

接著開始一層一層往回退。

### 回來時才是真正反轉的關鍵

當遞歸從最深層開始返回時：

* `p = 4`，`p.next = 5`
* 我們希望變成 `5 -> 4`

所以要寫：

```java
p.next.next = p;
```

這句話非常重要。

#### p.next.next = p 是什麼意思？

假設現在：

```text
4 -> 5 -> null
```

其中：

* `p = 4`
* `p.next = 5`

那麼：

```java
p.next.next = p;
```

等價於：

```java
5.next = 4;
```

也就是讓 `5` 指回 `4`。

這樣方向就反過來了。

#### 為什麼還要寫 p.next = null？

如果只寫：

```java
p.next.next = p;
```

會得到：

```text
5 -> 4
^    |
|____|
```

因為原本 `4.next` 還是指向 `5`，就會形成環，造成死循環。

所以還必須補上：

```java
p.next = null;
```

把原本的正向連結斷掉。

這樣才會變成：

```text
5 -> 4 -> null
```

### 完整反轉過程圖解

原鏈表：

```text
1 -> 2 -> 3 -> 4 -> 5 -> null
```

#### 第 1 階段：一路遞歸到底

```text
reverseList(1)
    reverseList(2)
        reverseList(3)
            reverseList(4)
                reverseList(5)
```

當走到 `5` 時，回傳 `5`。

#### 第 2 階段：開始回退並反轉

##### 回到 p = 4

原本：

```text
4 -> 5 -> null
```

執行：

```java
p.next.next = p; // 5.next = 4
p.next = null;   // 4.next = null
```

結果：

```text
5 -> 4 -> null
```

##### 回到 p = 3

原本此時概念上變成：

```text
3 -> 4
5 -> 4 -> null
```

執行：

```java
p.next.next = p; // 4.next = 3
p.next = null;   // 3.next = null
```

結果：

```text
5 -> 4 -> 3 -> null
```

##### 回到 p = 2

執行後：

```text
5 -> 4 -> 3 -> 2 -> null
```

##### 回到 p = 1

執行後：

```text
5 -> 4 -> 3 -> 2 -> 1 -> null
```

### 程式碼

```java
/**
 * 反转链表
 */
public class E01Leetcode206 {
    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(3, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);

        System.out.println(o1);

        ListNode n1 = new E01Leetcode206().reverseList(o1);
        System.out.println(n1);
    }

    public ListNode reverseList(ListNode p) {
        if (p == null || p.next == null) { // 空鏈表或已到最後節點
            return p;
        }

        ListNode last = reverseList(p.next);
        p.next.next = p;
        p.next = null;

        return last;
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

## 方法四

### 核心思路

每次都從原鏈表中拿出 **第二個節點 `o2`**，
把它從原本位置斷開，再插入到鏈表最前面。
一直做到 `o2 == null` 為止。

### 初始狀態

設定三個指針：

* `o1`：固定指向原本的第一個節點
* `o2`：指向 `o1` 的下一個節點，也就是原本的第二個節點
* `n1`：指向反轉後鏈表的新頭節點，初始時和 `o1` 一樣

初始鏈表：

```text
  n1
  o1
  1 -> 2 -> 3 -> 4 -> 5 -> null
       o2
```

### 每一輪做的事情

#### 第 1 步：先把 o2 從原鏈表中斷開

執行：

```java
o1.next = o2.next;
```

此時變成：

```text
  n1
  o1
  1 -> 3 -> 4 -> 5 -> null

  2
  o2
```

意思是：

* `1` 不再指向 `2`
* `1` 改成直接指向 `3`
* `2` 被單獨拆出來了

#### 第 2 步：把 o2 插到最前面

執行：

```java
o2.next = n1;
```

此時變成：

```text
     n1
     o1
2 -> 1 -> 3 -> 4 -> 5 -> null
o2
```

但這時候要注意：

* 目前真正的新頭其實應該是 `2`
* 所以接下來要更新 `n1`

#### 第 3 步：更新 n1

執行：

```java
n1 = o2;
```

此時變成：

```text
  n1
  2 -> 1 -> 3 -> 4 -> 5 -> null
       o1
            o2
```

注意：

* `n1` 現在指向 `2`
* `o1` 還是指向 `1`
* 下一個要搬的節點，會是 `o1.next`

#### 第 4 步：更新 o2

執行：

```java
o2 = o1.next;
```

此時變成：

```text
  n1
  2 -> 1 -> 3 -> 4 -> 5 -> null
       o1
            o2
```

也就是：

* `n1` 在 `2`
* `o1` 在 `1`
* `o2` 在 `3`

### 第二輪

現在再重複一次同樣操作。

#### 1. 斷開 o2

```java
o1.next = o2.next;
```

```text
  n1
  2 -> 1 -> 4 -> 5 -> null
       o1

  3
  o2
```

#### 2. o2 插到前面

```java
o2.next = n1;
```

```text
       n1
  3 -> 2 -> 1 -> 4 -> 5 -> null
            o1
  o2
```

#### 3. 更新 n1

```java
n1 = o2;
```

```text
  n1
  3 -> 2 -> 1 -> 4 -> 5 -> null
            o1
                 o2
```

#### 4. 更新 o2

```java
o2 = o1.next;
```

此時 `o2` 會指向 `4`。

### 持續重複

照這樣一直做下去：

* 把 `4` 搬到前面
* 再把 `5` 搬到前面

最後得到：

```text
  n1
  5 -> 4 -> 3 -> 2 -> 1 -> null
```

此時：

```java
o2 == null
```

迴圈結束，回傳 `n1`。

### 對應程式碼

```java
/**
 * 反转链表
 */
public class E01Leetcode206 {
    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(3, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);
        System.out.println(o1);
        ListNode n1 = new E01Leetcode206().reverseList(o1);
        System.out.println(n1);
    }

    public ListNode reverseList(ListNode o1) {
        if (o1 == null || o1.next == null) { // 不足兩個節點
          return o1;
        }
        ListNode o2 = o1.next;
        ListNode n1 = o1;
        while (o2 != null) {
          o1.next = o2.next;  // 2.
          o2.next = n1;       // 3.
          n1 = o2;            // 4.
          o2 = o1.next;       // 5.
        }
        return n1;
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

## 方法五

### 核心思路

**這個做法的重點是：把鏈表看成兩部分**

* **鏈表 1（新鏈表）**：已經完成反轉的部分
* **鏈表 2（原鏈表）**：還沒處理的部分

一開始：

* 新鏈表是空的
* 原鏈表是完整的原始鏈表

之後每一輪都做同一件事：

> **從原鏈表的頭取出一個節點，搬到新鏈表的頭部**

直到原鏈表為 `null`，代表所有節點都搬完了。

### 指針角色

在這個解法中有三個重要指針：

* `n1`：指向 **新鏈表** 的頭節點
* `o1`：指向 **原鏈表** 的頭節點
* `o2`：暫存 `o1.next`，避免搬移時鏈表斷掉後找不到後續節點

### 初始狀態

一開始：

* `n1 = null`，表示新鏈表還沒有任何元素
* `o1` 指向原鏈表的第一個節點

```text
n1 -> null

o1 -> 1 -> 2 -> 3 -> 4 -> 5 -> null
```

也可以理解成：

* 新鏈表：`null`
* 原鏈表：`1 -> 2 -> 3 -> 4 -> 5 -> null`

### 每一輪做什麼

每一輪都分成 3 個動作：

#### 1. 先保存原鏈表下一個節點

```java
ListNode o2 = o1.next;
```

因為等一下要改變 `o1.next`，所以要先把下一個節點記住。

#### 2. 把目前節點搬到新鏈表頭部

```java
o1.next = n1;
```

意思是：

* 讓目前的 `o1` 指向新鏈表頭
* 這樣 `o1` 就接到新鏈表前面了

#### 3. 更新指針位置

```java
n1 = o1;
o1 = o2;
```

意思是：

* `n1` 改成新的頭節點
* `o1` 往後移，繼續處理原鏈表下一個節點

### 流程圖解

#### 第 0 輪：初始狀態

```text
n1 -> null

o1 -> 1 -> 2 -> 3 -> 4 -> 5 -> null
```

#### 第 1 輪

先保存 `o2`

```text
n1 -> null

o1 -> 1 -> 2 -> 3 -> 4 -> 5 -> null
           o2
```

執行搬移：

```java
o1.next = n1;
```

變成：

```text
1 -> null

2 -> 3 -> 4 -> 5 -> null
```

更新指針：

```java
n1 = o1;
o1 = o2;
```

結果：

```text
n1 -> 1 -> null

o1 -> 2 -> 3 -> 4 -> 5 -> null
```

#### 第 2 輪

先保存 `o2`

```text
n1 -> 1 -> null

o1 -> 2 -> 3 -> 4 -> 5 -> null
           o2
```

執行搬移：

```java
o1.next = n1;
```

變成：

```text
2 -> 1 -> null

3 -> 4 -> 5 -> null
```

更新指針：

```text
n1 -> 2 -> 1 -> null

o1 -> 3 -> 4 -> 5 -> null
```

#### 第 3 輪

```text
n1 -> 2 -> 1 -> null

o1 -> 3 -> 4 -> 5 -> null
           o2
```

搬移後：

```text
n1 -> 3 -> 2 -> 1 -> null

o1 -> 4 -> 5 -> null
```

#### 第 4 輪

搬移後：

```text
n1 -> 4 -> 3 -> 2 -> 1 -> null

o1 -> 5 -> null
```

#### 第 5 輪

搬移後：

```text
n1 -> 5 -> 4 -> 3 -> 2 -> 1 -> null

o1 -> null
```

### 結束條件

當：

```java
o1 == null
```

表示原鏈表已經搬空，迴圈結束。

最後回傳 `n1`。


### 程式碼

```java
/**
 * 反转链表
 */
public class E01Leetcode206 {
    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(3, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);
        System.out.println(o1);
        ListNode n1 = new E01Leetcode206().reverseList(o1);
        System.out.println(n1);
    }

    public ListNode reverseList(ListNode o1) {
        if (o1 == null || o1.next == null) { // 不足兩個節點
            return o1;
        }
        ListNode n1 = null;
        while (o1 != null) {
            ListNode o2 = o1.next; // 2.
            o1.next = n1; // 3.
            n1 = o1;      // 4.
            o1 = o2;      // 4.
        }
        return n1;
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

- 上一篇：[环形链表（带哨兵）](./05%20环形链表（带哨兵）.md)
- 返回：[链表入口](./README.md)
- 下一篇：[LeetCode 203 移除链表元素](./07%20LeetCode%20203%20移除链表元素.md)
