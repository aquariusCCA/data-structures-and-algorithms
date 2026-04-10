# LeetCode 234 回文链表

> 所属章节：[二、基础数据结构](../README.md) / [链表](./README.md)  
> 关键字：LeetCode 234、回文链表、快慢指针、反转前半段、链表中点  
> 建議回查情境：要判斷鏈表是否回文，或想看找中點和反轉半段如何結合時

## 本节导读

這一節整理 LeetCode 234，核心是找到中點後比較前後兩段，也可以在找中點時同步反轉前半段。第一次閱讀時建議先看直接做法，再看優化後如何處理奇數長度的中間節點。

## 你會在這篇學到什麼

- 回文鏈表的基本判斷流程
- 如何在找中點時同步反轉前半段
- 奇數與偶數節點在比較時的差異

---

- [234. 回文链表](https://leetcode.cn/problems/palindrome-linked-list/description/)

> **解題思路：**
> - 步骤1. 找中间点
> - 步驟2. 中間點後半個鏈表反轉
> - 步骤3. 反转后链表与原鏈表逐一比较

```java
/**
 * 判断回文链表
 */
public class Leetcode234 {

    public boolean isPalindrome(ListNode head) {
        ListNode middle = middle(head);
        System.out.println(middle);
        ListNode newHead = reverse(middle);
        System.out.println(newHead);
        while (newHead != null) {
            if (newHead.val != head.val) {
                return false;
            }
            newHead = newHead.next;
            head = head.next;
        }
        return true;
    }

    private ListNode reverse(ListNode o1) {
        ListNode n1 = null;
        while(o1 != null) {
            ListNode o2 = o1.next;
            
            o1.next = n1;
            n1 = o1;
            
            o1 = o2;
        }
        return n1;
    }

    private ListNode middle(ListNode head) {
        ListNode p1 = head; // 慢
        ListNode p2 = head; // 快
        while(p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
        }
        return p1;
    }

    public static void main(String[] args) {
        // System.out.println(new Leetcode234()
        //        .isPalindrome(ListNode.of(1, 2, 2, 1)));
        System.out.println(new Leetcode234()
                .isPalindrome(ListNode.of(1, 2, 3, 2, 1)));
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

## 優化

先把原本拆成三個步驟的寫法還原回來：

```java
/**
 * 判断回文链表
 */
public class Leetcode234 {

    public boolean isPalindrome(ListNode head) {
        ListNode p1 = head; // 慢指針
        ListNode p2 = head; // 快指針
        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
        }

        ListNode middle = p1;
        System.out.println(middle);

        ListNode o1 = middle;
        ListNode n1 = null;
        while (o1 != null) {
            ListNode o2 = o1.next;
            o1.next = n1;
            n1 = o1;
            o1 = o2;
        }

        ListNode newHead = n1;
        System.out.println(newHead);

        while (newHead != null) {
            if (newHead.val != head.val) {
                return false;
            }
            newHead = newHead.next;
            head = head.next;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Leetcode234()
                .isPalindrome(ListNode.of(1, 2, 3, 2, 1)));
    }
}
```

### 原始作法的流程

這個版本總共做了三件事：

1. **利用快慢指針找到中間節點**
2. **從中間節點開始，反轉後半段鏈表**
3. **將反轉後的鏈表與原鏈表前半段逐一比較**

### 哪裡可以優化？

觀察後可以發現，程式中有三個 `while` 迴圈：

1. **第一個迴圈**：找中間節點

    * 快慢指針一起走
    * 時間大約是整個鏈表長度的一半到一倍

2. **第二個迴圈**：反轉鏈表

    * 反轉後半段
    * 時間大約是半個鏈表長度

3. **第三個迴圈**：比較兩段鏈表

    * 時間大約也是半個鏈表長度

其中，**第三個迴圈幾乎無法省略**，因為回文判斷一定要逐一比較節點值。
但前面兩件事其實可以進一步思考：

> 能不能在「找中間點」的同時，就順便把前半段鏈表反轉？

如果可以，就能把原本的：

* 找中點
* 再反轉一段鏈表

合併成同一個迴圈完成。

---

## 核心想法：找中點的同時反轉前半段

當快慢指針往前走時，慢指針 `p1` 每次只走一步。
我們可以利用這個過程，**把慢指針走過的前半段節點逐步反轉**。

這樣當迴圈結束時：

* `n1` 會指向「反轉後的前半段鏈表」
* `p1` 會停在中間位置附近
* 接著只要比較 `n1` 和 `p1` 後面的鏈表即可

也就是說，流程可以改成：

1. **找中間點的同時，反轉前半段鏈表**
2. **將反轉後的前半段，與後半段逐一比較**

---

## 優化後程式碼

```java
/**
 * 判断回文链表
 */
public class Leetcode234 {
    /*
        步骤1. 找中间点的同时反转前半个链表
        步骤2. 反转后的前半个链表 与 后半个链表 逐一比较
     */
    public boolean isPalindrome(ListNode head) {
        ListNode p1 = head; // 慢指針
        ListNode p2 = head; // 快指針
        ListNode n1 = null; // 反轉後的新頭
        ListNode o1 = head; // 尚未反轉部分的當前節點

        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;

            // 反轉前半段鏈表
            o1.next = n1;
            n1 = o1;
            o1 = p1;
        }

        System.out.println(n1);
        System.out.println(p1);

        // 如果節點數為奇數，跳過中間節點
        if (p2 != null) {
            p1 = p1.next;
        }

        // 比較反轉後的前半段與後半段
        while (n1 != null) {
            if (n1.val != p1.val) {
                return false;
            }
            n1 = n1.next;
            p1 = p1.next;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Leetcode234()
                .isPalindrome(ListNode.of(1, 2, 3, 2, 1)));
    }
}
```

---

## 為什麼可以這樣寫？

先看一般的反轉鏈表寫法：

```java
ListNode o2 = o1.next;
o1.next = n1;
n1 = o1;
o1 = o2;
```

在這題中，`o2` 其實就是「舊鏈表的下一個節點」。
而在快慢指針向前推進的過程中，可以觀察到：

* `p1` 每次往前走一步後，
* 它剛好就會來到 `o1.next` 原本要去的位置

所以原本的：

```java
ListNode o2 = o1.next;
o1.next = n1;
n1 = o1;
o1 = o2;
```

可以簡化成：

```java
o1.next = n1;
n1 = o1;
o1 = p1;
```

這樣就不需要額外宣告 `o2`。

---

## 為什麼奇數節點會出問題？

先看這個例子：

```java
1 -> 2 -> 3 -> 2 -> 1
```

當 `while (p2 != null && p2.next != null)` 結束時，指標位置會像這樣：

```text
        p1
                p2
1 -> 2 -> 3 -> 2 -> 1 -> null
```

此時：

* `n1` 反轉後會是前半段：`2 -> 1`
* `p1` 仍停在中間節點 `3`
* 如果直接拿 `n1` 和 `p1` 比較，就會變成：

```text
2 -> 1
3 -> 2 -> 1
```

第一個節點就不相等，因此錯誤地回傳 `false`。

### 問題本質

當鏈表長度是奇數時，**中間節點不需要參與比較**。
因為回文的中心元素左右對稱，但它自己不用和任何節點配對。

所以在奇數長度時，必須先讓 `p1` 往後移一格，跳過中間節點。

---

## 如何判斷鏈表長度是奇數？

迴圈結束條件是：

```java
p2 == null || p2.next == null
```

* 如果最後 `p2 == null`，表示節點數是 **偶數**
* 如果最後 `p2 != null`，但 `p2.next == null`，表示節點數是 **奇數**

因此可以寫成：

```java
if (p2 != null) {
    p1 = p1.next;
}
```

意思就是：

> 如果快指針最後沒有走到 `null`，代表鏈表長度是奇數，此時慢指針要再往後移一步，跳過中間節點。

---

## 優化後的整體流程

### 偶數長度

例如：

```text
1 -> 2 -> 2 -> 1
```

* `n1`：反轉後前半段 `2 -> 1`
* `p1`：後半段起點 `2 -> 1`

直接比較即可。

### 奇數長度

例如：

```text
1 -> 2 -> 3 -> 2 -> 1
```

* `n1`：反轉後前半段 `2 -> 1`
* `p1`：先停在中間節點 `3`
* 因為中間節點不用比較，所以先 `p1 = p1.next`
* 再拿 `n1` 和 `2 -> 1` 比較

---

---

## 導航

- 上一篇：[LeetCode 876 鏈表的中間結點](./13%20LeetCode%20876%20鏈表的中間結點.md)
- 返回：[链表入口](./README.md)
- 下一篇：[LeetCode 141 / 142 環形鏈表](./15%20LeetCode%20141%20142%20環形鏈表.md)
