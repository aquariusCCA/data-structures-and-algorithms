# 二分查找 Java 版

> 所属章节：[一、初识算法 / 二分查找](./README.md)
> 关键字：Java、Arrays.binarySearch、插入点、`-(low + 1)`、System.arraycopy
> 建議回查情境：忘了 Java 二分查找找不到时的返回值含义、需要根据插入点插入元素时

## 本节导读

这一节说明 Java 标准库中二分查找的典型实现，以及找不到目标值时为什么返回 `-(low + 1)`。重点是理解 `low` 在查找失败后代表插入点。

如果你只想回查 `Arrays.binarySearch` 找不到时如何还原插入位置，可以直接看「`return -(low + 1)` 有什麼含義？」。

## 你會在這篇學到什麼

- Java 二分查找的标准库写法
- `low`、`high`、`mid` 在 Java 版中的含义
- 找不到时为什么返回负数
- 如何从返回值还原插入点

```java
private static int binarySearch0(long[] a, int fromIndex, int toIndex, long key) {
    int low = fromIndex;
    int high = toIndex - 1;

    while (low <= high) {
        int mid = (low + high) >>> 1;
        long midVal = a[mid];

        if (midVal < key)
            low = mid + 1;
        else if (midVal > key)
            high = mid - 1;
        else
            return mid; // key found
    }
    return -(low + 1);  // key not found.
}
```

## return -(low + 1) 有什麼含義？

```java
@Test
@DisplayName("测试 binarySearch java 版")
public void test5() {
    int[] a = {2, 5, 8};
    int target = 4;
    int i = Arrays.binarySearch(a, target);
    // 沒找到返回的是 => -插入点 - 1
    // -2 = -插入点 - 1
    // -2 + 1 = -插入点
    // -1 = -插入点
    // 1 = 插入點
    System.out.println(i);
    // i = -插入点 - 1  因此有 插入点 = abs(i+1)
    int insertIndex = Math.abs(i + 1); // 插入点索引
    int[] b = new int[a.length + 1];
    // System.arraycopy 參數意思：
    // src：來源陣列
    // srcPos：從來源陣列哪個 index 開始複製（含）
    // dest：目標陣列
    // destPos：貼到目標陣列哪個 index 開始（含）
    // length：複製幾個元素
    System.arraycopy(a, 0, b, 0, insertIndex);
    b[insertIndex] = target;
    System.arraycopy(a, insertIndex, b, insertIndex + 1, a.length - insertIndex);
    for (int e : b) {
        System.out.print(e + "\t");
    }
}
```

- 例如 `[1,3,5,6]` 要插入 `2` 那么就是找到一个位置，这个位置左侧元素都比它小
    - 等循环结束，若没找到，`low` 左侧元素肯定都比 `target` 小，因此 `low` 即插入点
- 插入点取负是为了与找到情况区分
- `-1` 是为了把索引 `0` 位置的插入点与找到的情况进行区分

## 一句話抓核心

Java 版二分查找找不到目标时，`low` 就是插入点；返回 `-(low + 1)` 是为了同时表达「没找到」和「应该插入的位置」。

## 延伸閱讀

- [上一節：二分查找平衡版](./03%20二分查找平衡版.md)
- [下一節：Leftmost 与 Rightmost](./05%20Leftmost%20与%20Rightmost.md)
- [回到二分查找目錄](./README.md)
