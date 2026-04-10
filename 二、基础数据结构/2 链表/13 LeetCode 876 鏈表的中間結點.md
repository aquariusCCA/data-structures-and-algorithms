# LeetCode 876 鏈表的中間結點

> 所属章节：[二、基础数据结构](../README.md) / [链表](./README.md)  
> 关键字：LeetCode 876、中间结点、快慢指针、slow、fast  
> 建議回查情境：要找鏈表中點，或想確認偶數長度時返回哪一個中間節點時

## 本节导读

這一節整理 LeetCode 876，核心是快指針一次走兩步、慢指針一次走一步。第一次閱讀時建議對照奇數與偶數節點範例，看迴圈結束時 `slow` 停在哪裡。

## 你會在這篇學到什麼

- 快慢指針如何找到鏈表中點
- 奇數與偶數長度下的停止條件
- LeetCode 876 對偶數長度返回第二個中間節點的規則

---

題目連結：
[876. 鏈表的中間結點](https://leetcode.cn/problems/middle-of-the-linked-list/description/)

題目說明：

給定一個單向鏈表的頭節點 `head`，請找出並回傳這個鏈表的**中間節點**。

## 解題核心思路：快慢指針

這題最經典的做法，就是使用：

* **慢指針 `slow`**：每次走 1 步
* **快指針 `fast`**：每次走 2 步

### 為什麼這樣可以找到中間？

因為快指針走得比慢指針快：

* 當快指針走到鏈表尾端時
* 慢指針剛好會走到中間位置

## 過程示意

### 範例一：奇數個節點

鏈表：`1 -> 2 -> 3 -> 4 -> 5`

| 回合 | slow | fast |
| -- | ---- | ---- |
| 初始 | 1    | 1    |
| 1  | 2    | 3    |
| 2  | 3    | 5    |

此時 `fast.next == null`，停止迴圈。
所以中間節點是：`3`

### 範例二：偶數個節點

鏈表：`1 -> 2 -> 3 -> 4 -> 5 -> 6`

| 回合 | slow | fast |
| -- | ---- | ---- |
| 初始 | 1    | 1    |
| 1  | 2    | 3    |
| 2  | 3    | 5    |
| 3  | 4    | null |

此時 `fast == null`，停止迴圈。
所以回傳的是：`4`

這也符合題目要求：
偶數長度時，回傳**第二個中間節點**。

## 程式碼

```java
/**
 * 查找鏈表中間節點
 */
public class Leetcode876 {
    /**
     * @param head 頭節點
     * @return 中間節點
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;           // 慢指針走一步
            fast = fast.next.next;      // 快指針走兩步
        }

        return slow;
    }

    public static void main(String[] args) {
        ListNode head1 = ListNode.of(1, 2, 3, 4, 5);
        System.out.println(new Leetcode876().middleNode(head1)); // 3 -> 4 -> 5

        ListNode head2 = ListNode.of(1, 2, 3, 4, 5, 6);
        System.out.println(new Leetcode876().middleNode(head2)); // 4 -> 5 -> 6
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

- 上一篇：[LeetCode 23 合并 K 个升序链表](./12%20LeetCode%2023%20合并%20K%20个升序链表.md)
- 返回：[链表入口](./README.md)
- 下一篇：[LeetCode 234 回文链表](./14%20LeetCode%20234%20回文链表.md)
