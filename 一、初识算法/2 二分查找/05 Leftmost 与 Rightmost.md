# Leftmost 与 Rightmost

> 所属章节：[一、初识算法 / 二分查找](./README.md)
> 关键字：Leftmost、Rightmost、重复元素、插入位置、排名、前任、后任、范围查询
> 建議回查情境：数组有重复元素时、需要找最左或最右目标位置时、需要处理插入位置或范围查询时

## 本节导读

这一节说明二分查找在重复元素场景中的两个重要变形：Leftmost 和 Rightmost。它们不仅可以返回重复目标的最左或最右索引，还可以扩展为插入位置、排名、前任、后任和范围查询的基础工具。

第一次读时建议先理解候选位置 `candidate` 的写法；复习时可以直接看「应用」中的返回值含义。

## 你會在這篇學到什麼

- 如何用二分查找返回重复元素的最左侧位置
- 如何用二分查找返回重复元素的最右侧位置
- Leftmost / Rightmost 返回值的额外含义
- 如何用二分查找处理排名、前任、后任、最近邻居与范围查询

有时我们希望返回的是最左侧的重复元素，如果用 Basic 二分查找

- 对于数组 `[1, 2, 4, 4, 4, 5, 6, 7]`，查找元素 `4`，结果是索引 `3`
- 对于数组 `[1, 2, 4, 4, 4, 5, 6, 7]`，查找元素 `4`，结果也是索引 `3`，并不是最左侧的元素

```java
/**
 * 二分查找 Leftmost
 *
 * @param a      待查找的升序数组
 * @param target 待查找的目标值
 * @return
 *         找到则返回最靠左索引
 *         找不到返回 -1
 */
public static int binarySearchLeftmost1(int[] a, int target) {
    int i = 0, j = a.length - 1;
    int candidate = -1;
    while (i <= j) {
        int m = (i + j) >>> 1;
        if (target < a[m]) {
            j = m - 1;
        } else if (a[m] < target) {
            i = m + 1;
        } else {
            candidate = m; // 记录候选位置
            j = m - 1; // 继续向左
        }
    }
    return candidate;
}
```

測試如下：

```java
@Test
@DisplayName("测试 binarySearchLeftmost 返回 -1")
public void test() {
    int[] a = { 1, 2, 4, 4, 4, 5, 6, 7 };
    assertEquals(0, binarySearchLeftmost1(a, 1));
    assertEquals(1, binarySearchLeftmost1(a, 2));
    assertEquals(2, binarySearchLeftmost1(a, 4));
    assertEquals(5, binarySearchLeftmost1(a, 5));
    assertEquals(6, binarySearchLeftmost1(a, 6));
    assertEquals(7, binarySearchLeftmost1(a, 7));

    assertEquals(-1, binarySearchLeftmost1(a, 0));
    assertEquals(-1, binarySearchLeftmost1(a, 3));
    assertEquals(-1, binarySearchLeftmost1(a, 8));
}
```

如果希望返回的是最右侧元素

```java
/**
 * 二分查找 Rightmost
 *
 * @param a      待查找的升序数组
 * @param target 待查找的目标值
 * @return
 *         找到则返回最靠右索引
 *         找不到返回 -1
 */
public static int binarySearchRightmost1(int[] a, int target) {
    int i = 0, j = a.length - 1;
    int candidate = -1;
    while (i <= j) {
        int m = (i + j) >>> 1;
        if (target < a[m]) {
            j = m - 1;
        } else if (a[m] < target) {
            i = m + 1;
        } else {
            candidate = m; // 记录候选位置
            i = m + 1; // 继续向右
        }
    }
    return candidate;
}
```

測試如下：

```java
@Test
@DisplayName("测试 binarySearchRightmost 返回 -1")
public void test() {
    int[] a = { 1, 2, 4, 4, 4, 5, 6, 7 };
    assertEquals(0, binarySearchRightmost1(a, 1));
    assertEquals(1, binarySearchRightmost1(a, 2));
    assertEquals(4, binarySearchRightmost1(a, 4));
    assertEquals(5, binarySearchRightmost1(a, 5));
    assertEquals(6, binarySearchRightmost1(a, 6));
    assertEquals(7, binarySearchRightmost1(a, 7));

    assertEquals(-1, binarySearchRightmost1(a, 0));
    assertEquals(-1, binarySearchRightmost1(a, 3));
    assertEquals(-1, binarySearchRightmost1(a, 8));
}
```

## 應用

> 对于 Leftmost 与 Rightmost，可以返回一个比 `-1` 更有用的值

### Leftmost 改为

```java
public static int binarySearchLeftmost(int[] a, int target) {
    int i = 0, j = a.length - 1;
    while (i <= j) {
        int m = (i + j) >>> 1;
        // 當目標小于等于中间值，都要向左找
        if (target <= a[m]) {
            j = m - 1;
        } else {
            i = m + 1;
        }
    }
    return i;
}
```

- leftmost 返回值的另一层含义：返回  ≥ target 的最靠左索引。
- 小于等于中间值，都要向左找。

### Rightmost 改为

```java
public static int binarySearchRightmost(int[] a, int target) {
    int i = 0, j = a.length - 1;
    while (i <= j) {
        int m = (i + j) >>> 1;
        if (target < a[m]) {
            j = m - 1;
        } else {
            i = m + 1;
        }
    }
    // 迴圈結束後 i 會停在「第一個 > target 的位置」，所以回傳 i - 1 等於「最後一個 <= target 的位置（最靠右）」。
    return i - 1;
}
```

- rightmost 返回值的另一层含义：返回 ≤ target 的最靠右索引
- 大于等于中间值，都要向右找。

### 几个名词

![](../images/1772124418427_CaFB7vJ5QQ.png)

> **求排名**：$leftmost(target) + 1$
> - **排名**：我有一個 target 值，我想知道這個 target 值在這個數組排名第幾。
> - target 可以不存在，如：$leftmost(5)+1 = 6$
> - target 也可以存在，如：$leftmost(4)+1 = 3$

> **求前任（predecessor）**：$leftmost(target) - 1$
> - **前任**：就是比 target 小並且更靠右的。
> - $leftmost(3) - 1 = 1$，前任 $a_1 = 2$
> - $leftmost(4) - 1 = 1$，前任 $a_1 = 2$

> **求后任（successor）**：$rightmost(target)+1$
> - **後任**：比 target 大並且更靠左的。
> - $rightmost(5) + 1 = 5$，后任 $a_5 = 7$
> - $rightmost(4) + 1 = 5$，后任 $a_5 = 7$

> **求最近邻居**：
> - 前任和后任距离更近者
> - 比如 4 跟 5 中間距離了一個位置、7 跟 5 中間距離了兩個位置；所以我們說 4 是 5 的最近鄰居。

> **范围查询**：
> - 查询所有小於 4 的元素：$x \lt 4$，$0 \sim leftmost(4) - 1$
> - 查询所有小於等於 4 的元素： $x \leq 4$，$0 \sim rightmost(4)$
> - 查询所有大於 4 的元素： $4 \lt x$，$rightmost(4) + 1 \sim \infty$
> - 查询所有大於等於 4 的元素： $4 \leq x$， $leftmost(4) \sim \infty$
> - 查询所有大於等於 4 並且小於等於 7 的元素： $4 \leq x \leq 7$，$leftmost(4) \sim rightmost(7)$
> - 查询查询所有大於 4 並且小於 7 的元素： $4 \lt x \lt 7$，$rightmost(4)+1 \sim leftmost(7)-1$

## 常見回查問題

- 有重复元素时，如何返回最左侧目标位置？
- 有重复元素时，如何返回最右侧目标位置？
- Leftmost 为什么也可以代表插入位置？
- 如何用 Leftmost / Rightmost 做范围查询？

## 延伸閱讀

- [上一節：二分查找 Java 版](./04%20二分查找%20Java%20版.md)
- [下一節：习题](./06%20习题.md)
- [回到二分查找目錄](./README.md)
