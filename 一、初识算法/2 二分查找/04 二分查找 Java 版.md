# 二分查找 Java 版

> - 所属章节：[一、初识算法 / 二分查找](./README.md)
> - 关键字：Java、`Arrays.binarySearch`、插入点、`-(low + 1)`、标准库、查找失败、插入位置
> - 建议回查情境：忘了 Java 二分查找找不到时为什么返回负数时、需要根据返回值还原插入点时、想理解 `-(low + 1)` 设计动机时、需要把新元素插入有序数组时

---

## 本节导读

这一节讲的是 **Java 标准库里的二分查找写法**。

前面几节你学到的，主要是我们自己手写二分查找。
这一节开始，我们把视角切到 Java 标准库，看它是怎么做的。

Java 版最值得理解的，不只是“它也用了二分查找”，而是：

> **当查找失败时，它没有简单返回 `-1`，而是返回 `-(low + 1)`，借此同时表达“没找到”和“应该插入的位置”。**

这也是这篇最核心的主题。

---

## 你会在这篇学到什么

* Java 标准库二分查找的大致实现方式
* `low`、`high`、`mid` 在 Java 版中的含义
* 为什么找不到时返回 `-(low + 1)`
* `low` 为什么正好就是插入点
* 如何从返回值还原插入位置
* 为什么标准还原公式是 `-result - 1`
* 如何根据插入点把元素插入到一个新的有序数组中

---

## 一眼先记住 Java 版的本质

> Java 版二分查找找不到目标时，不是只告诉你“没找到”，而是进一步告诉你“如果想保持有序，这个元素应该插在哪个位置”。

如果这句话记住了，后面的 `-(low + 1)` 就不会只是死记公式。

---

# 一、先看 Java 标准库的典型实现

Java 标准库里，二分查找的典型实现大致如下：

```java
private static int binarySearch0(long[] a, int fromIndex, int toIndex, long key) {
    int low = fromIndex;
    int high = toIndex - 1;

    while (low <= high) {
        int mid = (low + high) >>> 1;
        long midVal = a[mid];

        if (midVal < key) {
            low = mid + 1;
        } else if (midVal > key) {
            high = mid - 1;
        } else {
            return mid;
        }
    }

    return -(low + 1);
}
```

这段代码和前面学过的基础版其实很接近：

* 使用闭区间思想
* 条件是 `low <= high`
* 小了往右，大了往左
* 找到就返回索引

真正不同的地方在最后这句：

```java
return -(low + 1);
```

这就是 Java 版最值得理解的设计。

---

# 二、为什么找不到时不直接返回 `-1`？

如果只是为了表示“没找到”，返回 `-1` 当然够用。

但 Java 标准库想做得更有用一点：

> **除了告诉你“没找到”，还顺便告诉你“应该插入到哪里”。**

因为在很多实际场景中，我们并不只是关心“有没有找到”，还关心：

* 如果没有这个值，应该插在哪个位置？
* 插进去之后，怎样还能保持数组有序？
* 这个值如果插入，它会排在第几个？

如果只是返回 `-1`，这些信息就全丢了。

所以 Java 版选择返回一个更有信息量的值。

---

# 三、什么是插入点（insertion point）？

插入点，就是：

> **如果当前数组里没有 `target`，那么为了保持数组仍然有序，`target` 应该插入的位置。**

例如：

```java
int[] a = {2, 5, 8};
```

如果查找：

```java
target = 4
```

那么 `4` 不在数组里。
但如果想把它插进去，并保持数组有序：

```text
{2, 4, 5, 8}
```

那它应该插在索引 `1` 的位置。

所以这里的插入点就是：

```text
1
```

---

## 插入点更本质的定义

当查找失败时，插入点就是：

> **第一个大于目标值的位置**

或者你也可以理解成：

> **所有小于目标值的元素都在它左边，所有大于目标值的元素都在它右边**

这两个说法本质上是一样的。

---

# 四、为什么查找失败后，`low` 正好就是插入点？

这是整篇最关键的地方。

先看循环：

```java
while (low <= high) {
    int mid = (low + high) >>> 1;
    long midVal = a[mid];

    if (midVal < key) {
        low = mid + 1;
    } else if (midVal > key) {
        high = mid - 1;
    } else {
        return mid;
    }
}
```

如果循环结束还没返回，说明什么？

说明：

* 数组里没有找到 `key`
* 并且最终出现了：

```java
low > high
```

这时查找区间已经为空。

但更重要的是，此时 `low` 的位置刚好满足：

* `low` 左边的元素都小于 `key`
* `low` 右边及其后的元素都大于 `key`

所以：

> **`low` 恰好就是把 `key` 插进去后仍能保持有序的位置。**

这不是巧合，而是二分查找不断收缩边界后的自然结果。

---

## 用一句话记住

> 找不到时，`low` 停下来的位置，就是“第一个大于目标值的位置”，也就是插入点。

---

# 五、为什么返回 `-(low + 1)`？

既然 `low` 就是插入点，那为什么不直接返回 `low`？

因为这样会和“找到了”的情况冲突。

例如：

* 如果找到了索引 `1`，返回 `1`
* 如果没找到，但插入点也是 `1`，也返回 `1`

那调用者根本分不清：

* 到底是找到了索引 `1`
* 还是没找到，只是应该插在索引 `1`

所以 Java 版需要一种编码方式，满足两个目标：

1. **找到时**：返回非负索引
2. **找不到时**：返回一个负数，但还能还原插入点

于是它选择：

```java
-(low + 1)
```

---

## 为什么还要加 `1`？

因为如果只返回：

```java
-low
```

那当插入点是 `0` 时，会得到：

```java
-0
```

也就是：

```text
0
```

这会和“找到索引 0”直接冲突。

所以必须再额外减一格，让所有“查找失败”的结果都落在负数区间里：

* 插入点 `0` → 返回 `-1`
* 插入点 `1` → 返回 `-2`
* 插入点 `2` → 返回 `-3`

这样就完全不会和合法索引 `0, 1, 2, ...` 混淆。

---

## 这个设计的两个目的

```java
return -(low + 1);
```

同时完成了两件事：

* 用“负数”表示没找到
* 用数值本身编码插入点

这就是 Java 版这个设计漂亮的地方。

---

# 六、怎么从返回值还原插入点？

这是实际使用时最重要的操作。

如果：

```java
int result = Arrays.binarySearch(a, target);
```

那么正确写法是：

```java
if (result < 0) {
    int insertIndex = -result - 1;
}
```

也就是：

> **插入点 = `-result - 1`**

这是标准、最稳、最推荐的还原方式。

---

## 为什么不是 `Math.abs(result + 1)`？

有些人会写：

```java
int insertIndex = Math.abs(result + 1);
```

在“查找失败”时，这种写法常常也能算对。

但它不是最标准的写法，原因有两个：

### 1）它依赖前提太强

只有在：

```java
result < 0
```

也就是“确定没找到”时，这个公式才有意义。

### 2）可读性不如标准公式直观

`-result - 1` 直接对应：

```text
result = -(插入点 + 1)
```

一眼就能反推回来。

而 `Math.abs(result + 1)` 看起来更像是“凑出来”的，不利于理解原设计。

所以高质量笔记里，最好写成：

```java
int insertIndex = -result - 1;
```

---

# 七、举个完整例子：查找 `4`

数组如下：

```java
int[] a = {2, 5, 8};
int target = 4;
```

执行：

```java
int result = Arrays.binarySearch(a, target);
```

因为 `4` 不存在，所以会返回负数。

---

## 手动分析插入点

如果把 `4` 插入数组，并保持有序：

```text
{2, 4, 5, 8}
```

可以看到它应该插在索引：

```text
1
```

所以插入点是 `1`。

那么 Java 返回值就是：

```text
-(1 + 1) = -2
```

也就是说：

```java
result == -2
```

---

## 如何还原插入点

```java
int insertIndex = -result - 1;
```

代入：

```java
int insertIndex = -(-2) - 1 = 1;
```

得到的正好就是插入点。

---

# 八、再举几个例子，把规律看清楚

假设数组仍然是：

```java
int[] a = {2, 5, 8};
```

---

## 情况 1：查找 `1`

`1` 比所有元素都小，所以插入点是：

```text
0
```

返回值：

```text
-(0 + 1) = -1
```

---

## 情况 2：查找 `4`

插入点是：

```text id="jrktvi"
1
```

返回值：

```text id="3n3efa"
-(1 + 1) = -2
```

---

## 情况 3：查找 `6`

插入点是：

```text
2
```

返回值：

```text
-(2 + 1) = -3
```

---

## 情况 4：查找 `10`

插入点是：

```text
3
```

返回值：

```text
-(3 + 1) = -4
```

---

## 规律总结

* 找到：返回 `0` 或正整数索引
* 找不到：返回负数
* 插入点越靠后，返回值越小

你可以直接记成：

```text
result >= 0   => 找到了
result < 0    => 没找到，插入点 = -result - 1
```

---

# 九、如何根据插入点把元素插进去？

如果已经拿到插入点，就可以把新元素插入一个新的数组。

示例：

```java
@Test
@DisplayName("测试 binarySearch Java 版")
public void testBinarySearchJavaVersion() {
    int[] a = {2, 5, 8};
    int target = 4;

    int result = Arrays.binarySearch(a, target);

    if (result < 0) {
        int insertIndex = -result - 1;

        int[] b = new int[a.length + 1];

        System.arraycopy(a, 0, b, 0, insertIndex);
        b[insertIndex] = target;
        System.arraycopy(a, insertIndex, b, insertIndex + 1, a.length - insertIndex);

        for (int e : b) {
            System.out.print(e + "\t");
        }
    }
}
```

输出后数组就是：

```text
2    4    5    8
```

---

## `System.arraycopy` 参数含义

```java
System.arraycopy(src, srcPos, dest, destPos, length);
```

含义如下：

* `src`：来源数组
* `srcPos`：从来源数组哪个索引开始复制
* `dest`：目标数组
* `destPos`：复制到目标数组哪个索引
* `length`：复制多少个元素

在上面的插入逻辑中：

### 第一次复制

```java
System.arraycopy(a, 0, b, 0, insertIndex);
```

表示把插入点左边的元素，原样复制过去。

### 手动放入目标值

```java
b[insertIndex] = target;
```

### 第二次复制

```java
System.arraycopy(a, insertIndex, b, insertIndex + 1, a.length - insertIndex);
```

表示把插入点右边原本的元素整体后移一格。

---

# 十、Java 版和前面手写版的关系

其实 Java 版并没有脱离前面的基础版思想。

它仍然是：

* 二分查找
* 使用中间索引
* 根据大小关系收缩区间
* 找到时返回索引

它真正增加的，是：

> **查找失败时，返回一个能恢复插入点的编码结果。**

所以这篇最值得掌握的，不只是代码，而是这个设计思想。

---

# 十一、这个设计有什么实际价值？

`-(low + 1)` 的价值在于：

## 1）它比单纯返回 `-1` 信息更多

不仅知道“没找到”，还知道“该插在哪里”。

## 2）适合有序插入场景

例如：

* 把元素插入有序数组
* 维护有序列表
* 判断某个值应处于第几个位置

## 3）查找与插入逻辑自然衔接

你不需要额外再做一次扫描去找位置。
一次二分查找，就把“找没找到”和“该插哪里”都解决了。

---

# 十二、测试代码

```java
@Test
@DisplayName("测试 Arrays.binarySearch 返回值含义")
public void testBinarySearchReturnValue() {
    int[] a = {2, 5, 8};

    assertEquals(0, Arrays.binarySearch(a, 2));
    assertEquals(1, Arrays.binarySearch(a, 5));
    assertEquals(2, Arrays.binarySearch(a, 8));

    assertEquals(-1, Arrays.binarySearch(a, 1));
    assertEquals(-2, Arrays.binarySearch(a, 4));
    assertEquals(-3, Arrays.binarySearch(a, 6));
    assertEquals(-4, Arrays.binarySearch(a, 10));
}
```

---

# 十三、常见错误总结

## 错误 1：把“找不到”一律理解成 `-1`

问题：

* 在 Java 标准库里，找不到不一定返回 `-1`
* 还可能是 `-2`、`-3`、`-4`……
* 因为这些值还承担了“编码插入点”的作用

---

## 错误 2：不知道 `low` 为什么是插入点

问题：

* 只记公式，不懂含义
* 之后很容易忘记 `-(low + 1)` 为什么成立

---

## 错误 3：用 `Math.abs(result + 1)` 作为主要讲法

问题：

* 不是最标准的还原方式
* 可读性也不如 `-result - 1`

---

## 错误 4：把“插入点”理解成“最后一个小于目标的位置”

这不够准确。

更准确地说，插入点是：

> **第一个大于目标值的位置**

或者在允许重复值的更一般表述里：

> **第一个大于等于目标值的位置**
> 具体要看实现和场景定义

对于当前这类“查找失败”的直观理解，你先记成“应该插入的位置”最稳。

---

# 十四、一句话抓核心

> Java 标准库的二分查找在找不到目标时，会返回 `-(low + 1)`；其中 `low` 正好是插入点，这样一个返回值就能同时表达“没找到”和“应该插入的位置”。

---

# 十五、极简回查版

如果你以后只想用 20 秒回忆起 Java 版，可以只看这段：

```java
int result = Arrays.binarySearch(a, target);

if (result >= 0) {
    // 找到了，result 就是索引
} else {
    int insertIndex = -result - 1;
    // 没找到，insertIndex 就是插入点
}
```

配套记忆句：

* 找到：返回索引
* 找不到：返回 `-(插入点 + 1)`
* 还原插入点：`-result - 1`

---

# 十六、常见回查问题

* Java 的 `Arrays.binarySearch` 为什么找不到时不返回 `-1`？
* `-(low + 1)` 到底是什么意思？
* 为什么 `low` 就是插入点？
* 插入点应该怎么还原？
* 为什么推荐写 `-result - 1`，而不是 `Math.abs(result + 1)`？
* 这个返回值设计在实际开发中有什么用？

---

# 十七、延伸阅读

* [上一節：二分查找平衡版](./03%20二分查找平衡版.md)
* [下一節：Leftmost 与 Rightmost](./05%20Leftmost%20与%20Rightmost.md)
* [回到二分查找目录](./README.md)

---