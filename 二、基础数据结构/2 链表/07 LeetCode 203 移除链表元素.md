# LeetCode 203 移除链表元素

> 所属章节：[二、基础数据结构](../README.md) / [链表](./README.md)  
> 关键字：LeetCode 203、移除链表元素、哨兵节点、删除节点、指针跟随  
> 建議回查情境：要刪除特定值節點，或想看如何用哨兵節點簡化頭節點刪除時

## 本节导读

這一節整理 LeetCode 203 移除鏈表元素，重點是刪除節點時要拿到前驅節點，並處理連續刪除與頭節點刪除。第一次閱讀時建議從方法一看清楚指針關係，再看逐步優化。

## 你會在這篇學到什麼

- 如何刪除值等於 `val` 的鏈表節點
- 為什麼哨兵節點能簡化頭節點刪除
- 如何讓 `p2` 始終跟隨 `p1.next`

---

- [203. 移除链表元素](https://leetcode.cn/problems/remove-linked-list-elements/description/)

## 方法一
圖中 s 代表 sentinel 哨兵（如果不加哨兵，則刪除第一個節點要特殊處理），例如要刪除 6

```text
p1     p2
s  ->  1  ->  2  ->  6  ->  3 ->  6  ->  null
```

如果 p2 不等於目標，則 p1、p2 不斷後移
```text
       p1     p2
s  ->  1  ->  2  ->  6  ->  3 ->  6  ->  null

              p1     p2
s  ->  1  ->  2  ->  6  ->  3 ->  6  ->  null
```

`p2 == p6` 刪除它，注意 p1 此時保持不變
```text
              p1     p2
s  ->  1  ->  2  ->  3 ->  6  ->  null
```

p2 不等於目標，則 p1、p2 不斷後移
```text
                     p1    p2
s  ->  1  ->  2  ->  3 ->  6  ->  null
```

`p2 == p6` 刪除它，注意 p1 此時保持不變
```text
                     p1     p2
s  ->  1  ->  2  ->  3  ->  null
```

`p2 == null` 退出循環

```java
public class Leetcode203 {
    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 6, 3, 6);
        // ListNode head = ListNode.of(7, 7, 7, 7);
        System.out.println(head);
        System.out.println(new Leetcode203()
                .removeElements(head, 6));
    }

    /**
     * @param head 链表头
     * @param val  目标值
     * @return 删除后的链表头
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode s = new ListNode(-1, head);
        ListNode p1 = s;
        ListNode p2 = s.next;
        while (p2 != null) {
            if (p2.val == val) {
                // 删除, p2 向后平移
                p1.next = p2.next;
                p2 = p2.next;
            } else {
                // p1 p2 向后平移
                p1 = p1.next;
                p2 = p2.next;
            }
        }
        return s.next;
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

### 優化一：讓 p2 始終跟隨 p1.next

```java
public ListNode removeElements(ListNode head, int val) {
    ListNode s = new ListNode(-1, head);
    ListNode p1 = s;
    ListNode p2 = s.next;
    while (p2 != null) {
        if (p2.val == val) {
            p1.next = p2.next;
            p2 = p1.next;
        } else {
            p1 = p1.next;
            p2 = p1.next;
        }
    }
    return s.next;
}
```

**為什麼要這樣改？**

原始版本中，在兩個分支裡都是寫：

```java
p2 = p2.next;
```

這樣雖然也對，但它隱含了一件事：
你必須一直思考「目前的 `p2` 移動完之後，是否還和 `p1.next` 保持同步？」
而改成：

```java
p2 = p1.next;
```

之後，意思就更明確了：

> 每輪處理完後，重新讓 p2 指向 p1 的下一個節點。

這樣做的好處是：

* p2 的語意更清楚：它不是獨立亂跑的指標，而是 p1 的觀察視角
* 刪除節點後，不容易因為鏈結改變而搞混 p2 應該往哪裡走

### 優化二：抽出共同的 p2 更新邏輯

```java
public ListNode removeElements(ListNode head, int val) {
    ListNode s = new ListNode(-1, head);
    ListNode p1 = s;
    ListNode p2 = s.next;
    while (p2 != null) {
        if (p2.val == val) {
            p1.next = p2.next;
        } else {
            p1 = p1.next;
        }
        p2 = p1.next;
    }
    return s.next;
}
```

**為什麼要這樣改？**

你會發現上一版的兩個分支最後都做了同一件事：

```java
p2 = p1.next;
```

既然這是共同邏輯，就應該抽出來。

### 優化三：刪除多餘的初始賦值

```java
public ListNode removeElements(ListNode head, int val) {
    ListNode s = new ListNode(-1, head);
    ListNode p1 = s;
    ListNode p2 = p1.next;
    while (p2 != null) {
        if (p2.val == val) {
            p1.next = p2.next;
        } else {
            p1 = p1.next;
        }
        p2 = p1.next;
    }
    return s.next;
}
```

**為什麼要這樣改？**

這裡的重點是：

```java
ListNode p2 = s.next;
```

其實可以寫成：

```java
ListNode p2 = p1.next;
```

因為初始化時：

```java
p1 = s;
```

所以 `s.next` 和 `p1.next` 完全等價。

> **這樣改的意義是什麼？**
> - 這是在讓程式的表達更加一致。
> - 整個方法裡，核心關係是： `p2` 就是 `p1.next`
> - 那初始化時也應該維持同樣的語意，而不是一開始特別寫成 `s.next`。

> **好處**
> - 前後邏輯一致
> - 讀的人更容易理解 p2 的來源
> - 程式概念更統一

### 優化四：把 p2 的取得合併進循環條件

```java
public ListNode removeElements(ListNode head, int val) {
    ListNode s = new ListNode(-1, head);
    ListNode p1 = s;
    ListNode p2;
    while ((p2 = p1.next) != null) {
        if (p2.val == val) {
            p1.next = p2.next;
        } else {
            p1 = p1.next;
        }
    }
    return s.next;
}
```

**為什麼要這樣改？**

前一版中有兩個地方在做同一件事：

1. 進入循環前初始化 p2
2. 每輪循環結束後再次更新 p2

既然每次進入循環前都必須重新取得 `p1.next`，那就可以把這件事直接寫進 while 條件：

```java
while ((p2 = p1.next) != null)
```

意思就是：

> 每次循環開始時，先取出 p1 的下一個節點，如果不是空，才進入循環。

## 方法二

> **思路：遞歸函數負責返回：從當前節點（我）開始，完成刪除的鏈表**
> 1. 若我與 `v` 相等，應該返回下一個節點遞歸結果
> 2. 若我與 `v` 不相等，應該返回我，但我的 `next` 應該更新

偽代碼
```java
removeElements(ListNode p = 1, int v = 6) {  // 我與 v 不等，返回我及後續
    removeElements(ListNode p = 2, int v = 6) {
        removeElements(ListNode p = 6, int v = 6) { // 我與 v 相等，返回後續
            removeElements(ListNode p = 3, int v = 6) {
                removeElements(ListNode p = 6, int v = 6) {
                    removeElements(ListNode p = null, int v = 6) {
                        // 沒有節點，返回
                    }
                }
            }
        }
    }
}
```

```java
public class Leetcode203 {
    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 6, 3, 6);
        // ListNode head = ListNode.of(7, 7, 7, 7);
        System.out.println(head);
        System.out.println(new Leetcode203()
                .removeElements(head, 6));
    }

    /**
     * @param p   链表头
     * @param val 目标值
     * @return 删除后的链表头
     */
    public ListNode removeElements(ListNode p, int val) {
        if (p == null) {
            return null;
        }
        if (p.val == val) {
            return removeElements(p.next, val);
        } else {
            p.next = removeElements(p.next, val);
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

- 上一篇：[LeetCode 206 反转链表](./06%20LeetCode%20206%20反转链表.md)
- 返回：[链表入口](./README.md)
- 下一篇：[LeetCode 19 刪除鏈表的倒數第 N 個節點](./08%20LeetCode%2019%20刪除鏈表的倒數第%20N%20個節點.md)
