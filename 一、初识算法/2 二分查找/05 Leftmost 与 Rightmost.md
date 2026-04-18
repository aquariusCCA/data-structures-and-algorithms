# Leftmost 与 Rightmost

> - 所属章节：[一、初识算法 / 二分查找](./README.md)
> - 关键字：Leftmost、Rightmost、重复元素、最左位置、最右位置、插入位置、排名、前任、后任、范围查询
> - 建议回查情境：数组中有重复元素时、需要找最左或最右目标位置时、想理解插入位置与二分查找关系时、需要做范围查询时

---

## 本节导读

前面几节讲的二分查找，默认目标值只要找到一个位置就够了。
但在很多实际场景中，这还不够。

例如数组：

```java
int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
```

如果查找 `4`，普通二分查找可能返回：

* `2`
* `3`
* `4`

这些结果都不算错，因为它们都指向值 `4`。
但如果题目要求的是：

* 返回最左边那个 `4`
* 返回最右边那个 `4`

那普通二分查找就不够用了。

所以这一节要讲的是二分查找的两个重要变形：

* **Leftmost**：找最左侧目标位置
* **Rightmost**：找最右侧目标位置

它们不仅能处理重复元素，还能进一步延伸到：

* 插入位置
* 排名
* 前任（predecessor）
* 后任（successor）
* 范围查询

---

## 你会在这篇学到什么

* 为什么普通二分查找遇到重复元素时不够用
* 如何返回最左侧目标位置
* 如何返回最右侧目标位置
* 为什么需要 `candidate`
* 为什么进阶版 Leftmost 可以直接返回 `i`
* 为什么进阶版 Rightmost 可以直接返回 `i - 1`
* 如何用 Leftmost / Rightmost 做排名、前任、后任与范围查询

---

## 一眼先记住本节的本质

> 当数组中存在重复元素时，普通二分查找只能保证“找到某一个目标位置”，而 Leftmost / Rightmost 则分别用来定位“最左的那个目标”与“最右的那个目标”。

如果这句话记住了，后面的所有变形都会更顺。

---

# 一、为什么普通二分查找不够用？

先看这个数组：

```java
int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
```

如果用基础版二分查找去找 `4`，返回值可能是：

```text
2、3、4
```

这取决于：

* 数组长度
* 中间索引的取法
* 二分过程中区间如何收缩

也就是说，普通二分查找只能保证：

> **如果目标存在，返回某一个合法位置。**

但它不能保证：

* 一定返回最左边那个
* 一定返回最右边那个

所以一旦题目中出现：

* 第一个等于某值的位置
* 最后一个等于某值的位置
* 大于等于某值的第一个位置
* 小于等于某值的最后一个位置

你就该想到：
**普通二分查找不够了，要改成 Leftmost / Rightmost。**

---

# 二、Leftmost 是什么？

Leftmost 的目标是：

> **在所有等于 `target` 的元素中，返回最靠左的那个索引。**

例如：

```java
int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
```

查找 `4` 时：

* 普通二分查找可能返回 `2`、`3`、`4`
* Leftmost 必须返回：

```text
2
```

因为索引 `2` 才是最左边那个 `4`。

---

# 三、Rightmost 是什么？

Rightmost 的目标是：

> **在所有等于 `target` 的元素中，返回最靠右的那个索引。**

同样对于：

```java
int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
```

查找 `4` 时，Rightmost 必须返回：

```text
4
```

因为索引 `4` 才是最右边那个 `4`。

---

# 四、Leftmost 的基础写法：记录 `candidate`

先看代码：

```java
/**
 * 二分查找 Leftmost：找到则返回最左索引，找不到返回 -1
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
            candidate = m; // 先记下当前命中的位置
            j = m - 1;     // 继续向左找
        }
    }

    return candidate;
}
```

---

## 为什么这里要用 `candidate`？

因为当你第一次找到 `target` 时，还不能马上返回。

例如：

```java
int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
```

第一次命中时，`m` 可能是 `3`。
但这并不代表 `3` 就是最左边那个 `4`，因为左边还有可能存在另一个 `4`。

所以正确做法是：

1. 先把当前命中的位置记下来
2. 再继续往左边缩区间
3. 如果后面又找到更靠左的 `4`，就更新 `candidate`
4. 最后循环结束时，`candidate` 中留下的就是最左位置

也就是说：

> `candidate` 的作用不是“第一次找到就结束”，而是“先记住一个答案，再尝试找更好的答案”。

---

## Leftmost 的核心动作

当：

```java
a[m] == target
```

时，不是直接返回，而是：

```java
candidate = m;
j = m - 1;
```

因为：

* 当前 `m` 是一个合法答案
* 但左边可能还有更左的答案
* 所以要继续往左找

---

# 五、Leftmost 测试代码

```java
@Test
@DisplayName("测试 binarySearchLeftmost1")
public void testLeftmost1() {
    int[] a = {1, 2, 4, 4, 4, 5, 6, 7};

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

---

# 六、Rightmost 的基础写法：记录 `candidate`

代码如下：

```java
/**
 * 二分查找 Rightmost：找到则返回最右索引，找不到返回 -1
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
            candidate = m; // 先记下当前命中的位置
            i = m + 1;     // 继续向右找
        }
    }

    return candidate;
}
```

---

## Rightmost 的核心动作

当：

```java
a[m] == target
```

时，不是直接返回，而是：

```java
candidate = m;
i = m + 1;
```

因为：

* 当前 `m` 是一个合法答案
* 但右边可能还有更靠右的答案
* 所以要继续往右找

---

# 七、Rightmost 测试代码

```java
@Test
@DisplayName("测试 binarySearchRightmost1")
public void testRightmost1() {
    int[] a = {1, 2, 4, 4, 4, 5, 6, 7};

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

---

# 八、为什么进阶版 Leftmost 可以直接返回 `i`？

上面的基础写法适合初学，因为它直观。
但进一步看，Leftmost 其实还可以写成一个更有用的版本：

```java
public static int binarySearchLeftmost(int[] a, int target) {
    int i = 0, j = a.length - 1;
    while (i <= j) {
        int m = (i + j) >>> 1;
        if (target <= a[m]) {
            j = m - 1;
        } else {
            i = m + 1;
        }
    }
    return i;
}
```

这个版本不再显式记录 `candidate`，但它更强大。

---

## 这个版本到底在找什么？

它找的其实不是“某个等于 target 的位置”，而是：

> **第一个大于等于 `target` 的位置**

也可以写成：

```text
第一个 >= target 的索引
```

这就是 Leftmost 更本质的定义。

---

## 为什么最后会返回 `i`？

因为循环结束时：

* 所有 `< target` 的元素都已经被排到 `i` 左边
* `i` 停在第一个可能满足 `>= target` 的位置

所以：

```java
return i;
```

返回的就是：

> 第一个大于等于 `target` 的位置

---

## 它和“最左等于 target”有什么关系？

如果数组中真的存在 `target`，那么：

* 第一个 `>= target` 的位置
* 就正好是第一个 `== target` 的位置

所以它自然就是最左命中位置。

如果数组中不存在 `target`，那它返回的就不是 `-1`，而是：

> `target` 应该插入的位置

这就是为什么这个版本更有价值。

---

# 九、为什么进阶版 Rightmost 可以返回 `i - 1`？

Rightmost 也有对应的进阶写法：

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
    return i - 1;
}
```

这个版本找的其实不是“某个等于 target 的位置”，而是：

> **第一个大于 `target` 的位置的前一个位置**

更本质地说，它返回的是：

```text
最后一个 <= target 的索引
```

---

## 为什么是 `i - 1`？

因为循环结束时：

* `i` 会停在第一个 `> target` 的位置
* 所以它前一个位置 `i - 1`
* 就是最后一个 `<= target` 的位置

因此：

```java
return i - 1;
```

就非常自然。

---

## 它和“最右等于 target”有什么关系？

如果数组中存在 `target`，那么：

* 最后一个 `<= target` 的位置
* 就正好是最后一个 `== target` 的位置

所以它自然就是最右命中位置。

如果数组中不存在 `target`，那它返回的仍然很有价值，因为它代表：

> 最后一个小于等于 `target` 的位置

---

# 十、Leftmost / Rightmost 的本质总结

你可以这样记：

## Leftmost（进阶版）

返回：

```text
第一个 >= target 的位置
```

## Rightmost（进阶版）

返回：

```text
最后一个 <= target 的位置
```

这两个定义比“找最左 / 找最右”更本质，因为它们还能延伸出很多应用。

---

# 十一、几个重要名词

仍然以数组：

```java
int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
```

为例。

---

## 1）排名（rank）

排名通常可以理解为：

> `target` 如果插入到数组中，它应该排在第几个（从 1 开始）

对应公式：

```text
leftmost(target) + 1
```

例如：

### `target = 4`

```text
leftmost(4) = 2
```

所以排名是：

```text
2 + 1 = 3
```

表示 `4` 处在第 3 名的位置。

### `target = 3`

```text
leftmost(3) = 2
```

所以排名是：

```text
2 + 1 = 3
```

表示如果把 `3` 插进来，它会排在第 3 名。

---

## 2）前任（predecessor）

前任指的是：

> **严格小于 `target` 的元素里，最靠右的那个**

公式可以写成：

```text
leftmost(target) - 1
```

因为：

* `leftmost(target)` 是第一个 `>= target` 的位置
* 它左边那个位置
* 就是最后一个 `< target` 的位置

例如：

### `target = 4`

```text
leftmost(4) = 2
```

所以前任索引是：

```text
2 - 1 = 1
```

对应元素：

```text
a[1] = 2
```

### `target = 3`

```text
leftmost(3) = 2
```

所以前任索引也是：

```text
1
```

对应元素同样是：

```text
2
```

---

## 3）后任（successor）

后任指的是：

> **严格大于 `target` 的元素里，最靠左的那个**

公式可以写成：

```text
rightmost(target) + 1
```

因为：

* `rightmost(target)` 是最后一个 `<= target` 的位置
* 它右边那个位置
* 就是第一个 `> target` 的位置

例如：

### `target = 4`

```text
rightmost(4) = 4
```

所以后任索引是：

```text
4 + 1 = 5
```

对应元素：

```text
a[5] = 5
```

### `target = 5`

```text
rightmost(5) = 5
```

所以后任索引是：

```text
5 + 1 = 6
```

对应元素：

```text
a[6] = 6
```

**一定要分清楚「索引」和「元素值」**，不然後任例子很容易寫亂。

---

## 4）最近邻居

最近邻居不是 Leftmost / Rightmost 的直接返回值，而是它们的一个延伸应用。

它的意思通常是：

> 在 `target` 不存在时，分别找出它的前任与后任，再比较谁离 `target` 更近。

也就是说，最近邻居的求法通常分成三步：

1. 找前任：最后一个 `< target` 的元素
2. 找后任：第一个 `> target` 的元素
3. 比较两者与 `target` 的距离

例如对于数组：

```java
int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
```

如果查找 `target = 3`：

* 前任是 `2`
* 后任是 `4`

两边距离分别是：

```text
|3 - 2| = 1
|4 - 3| = 1
```

说明两者距离相同。
这种情况下，最近邻居到底取左边还是右边，**要看题目如何规定**。常见做法有：

* 距离相同时取较小者
* 距离相同时取较大者
* 距离相同时任选其一

再例如，如果查找 `target = 5.4`（这里只是为了说明“距离比较”的概念）：

* 前任是 `5`
* 后任是 `6`

两边距离分别是：

```text
|5.4 - 5| = 0.4
|6 - 5.4| = 0.6
```

所以最近邻居是 `5`。

> 因此，最近邻居问题的关键不在于额外记住一种新的二分模板，而在于：
> **先用 Leftmost / Rightmost 找到前任和后任，再根据题目要求比较距离。**

---

# 十二、范围查询

Leftmost / Rightmost 很适合做范围查询。

仍以数组：

```java
int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
```

为例。

---

## 查询所有 `< 4` 的元素

范围是：

```text
0 ~ leftmost(4) - 1
```

因为 `leftmost(4)` 是第一个 `>= 4` 的位置。

这里：

```text
leftmost(4) = 2
```

所以范围是：

```text
0 ~ 1
```

对应元素：

```text
1, 2
```

---

## 查询所有 `<= 4` 的元素

范围是：

```text
0 ~ rightmost(4)
```

这里：

```text
rightmost(4) = 4
```

所以范围是：

```text
0 ~ 4
```

对应元素：

```text
1, 2, 4, 4, 4
```

---

## 查询所有 `> 4` 的元素

范围是：

```text
rightmost(4) + 1 ~ 末尾
```

这里：

```text
rightmost(4) + 1 = 5
```

所以范围是：

```text
5 ~ 7
```

对应元素：

```text
5, 6, 7
```

---

## 查询所有 `>= 4` 的元素

范围是：

```text
leftmost(4) ~ 末尾
```

这里：

```text
leftmost(4) = 2
```

所以范围是：

```text
2 ~ 7
```

对应元素：

```text
4, 4, 4, 5, 6, 7
```

---

## 查询所有 `4 <= x <= 7` 的元素

范围是：

```text
leftmost(4) ~ rightmost(7)
```

这里：

```text
leftmost(4) = 2
rightmost(7) = 7
```

所以范围是：

```text
2 ~ 7
```

---

## 查询所有 `4 < x < 7` 的元素

范围是：

```text
rightmost(4) + 1 ~ leftmost(7) - 1
```

这里：

```text
rightmost(4) + 1 = 5
leftmost(7) - 1 = 6
```

所以范围是：

```text
5 ~ 6
```

对应元素：

```text
5, 6
```

---

# 十三、代码总整理

## Leftmost：基础版（返回最左命中位置，找不到返回 -1）

```java
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
            candidate = m;
            j = m - 1;
        }
    }

    return candidate;
}
```

## Rightmost：基础版（返回最右命中位置，找不到返回 -1）

```java
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
            candidate = m;
            i = m + 1;
        }
    }

    return candidate;
}
```

## Leftmost：进阶版（返回第一个 `>= target` 的位置）

```java
public static int binarySearchLeftmost(int[] a, int target) {
    int i = 0, j = a.length - 1;
    while (i <= j) {
        int m = (i + j) >>> 1;
        if (target <= a[m]) {
            j = m - 1;
        } else {
            i = m + 1;
        }
    }
    return i;
}
```

## Rightmost：进阶版（返回最后一个 `<= target` 的位置）

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
    return i - 1;
}
```

---

# 十四、常见错误总结

## 错误 1：以为普通二分查找能稳定返回最左或最右

问题：

* 普通二分查找只能保证找到一个合法位置
* 不能保证是最左或最右

---

## 错误 2：找到目标就立刻返回

问题：

* 在 Leftmost / Rightmost 里，第一次找到并不代表已经是最优答案
* 必须继续往左或往右缩区间

---

## 错误 3：分不清基础版与进阶版的返回值语义

问题：

* 基础版：找不到返回 `-1`
* 进阶版：即使找不到，也返回有意义的位置

---

## 错误 4：把 `Leftmost` / `Rightmost` 只当成“重复元素题目”

问题：

* 它们不只是找重复元素
* 更重要的是，它们本质上在找边界
* 所以还能延伸到插入点、范围查询、排名等问题

---

## 错误 5：混淆索引与元素值

问题：

* 例如前任、后任、范围查询时
* 很容易把“某个索引”直接写成“某个值”
* 一定要先算索引，再映射到元素

---

# 十五、一句话抓核心

> Leftmost 的本质是找第一个 `>= target` 的位置，Rightmost 的本质是找最后一个 `<= target` 的位置；当目标存在时，它们分别对应最左命中位置和最右命中位置，当目标不存在时，它们仍然能提供插入位置、前任、后任与范围边界等信息。

---

# 十六、极简回查版

如果你以后只想用 20 秒回忆起这一节，可以记这两句话：

* `Leftmost(target)`：第一个 `>= target` 的位置
* `Rightmost(target)`：最后一个 `<= target` 的位置

以及这两段代码：

```java
public static int binarySearchLeftmost(int[] a, int target) {
    int i = 0, j = a.length - 1;
    while (i <= j) {
        int m = (i + j) >>> 1;
        if (target <= a[m]) {
            j = m - 1;
        } else {
            i = m + 1;
        }
    }
    return i;
}
```

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
    return i - 1;
}
```

---

# 十七、常见回查问题

* 为什么普通二分查找不能稳定处理重复元素？
* Leftmost 为什么要继续向左找？
* Rightmost 为什么要继续向右找？
* 为什么进阶版 Leftmost 可以直接返回 `i`？
* 为什么进阶版 Rightmost 返回 `i - 1`？
* Leftmost / Rightmost 和插入位置、前任、后任有什么关系？
* 如何用它们做范围查询？

---

# 十八、延伸阅读

* [上一節：二分查找 Java 版](./04%20二分查找%20Java%20版.md)
* [下一節：LeetCode 习题](./06%20LeetCode%20习题.md)
* [回到二分查找目录](./README.md)

---
