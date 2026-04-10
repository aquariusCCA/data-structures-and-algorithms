# System.arraycopy 的使用

> 所属章节：[二、基础数据结构](../README.md) / [數組](./README.md)
> 关键字：System.arraycopy、src、srcPos、dest、destPos、length、數組複製
> 建議回查情境：忘記 arraycopy 參數順序，或需要確認如何把一段數組複製到另一個位置時

## 本节导读

這一節集中整理 Java 的 System.arraycopy 用法。重點是記住五個參數各自代表的來源、目標、起點與複製長度，並透過簡單範例確認複製結果。

## 你會在這篇學到什麼

- System.arraycopy 的用途與語法
- 五個參數 src、srcPos、dest、destPos、length 的意義
- 如何判斷複製後目標數組的內容

---

`System.arraycopy` 是 Java 提供的一种高效数组复制方法，用于在数组之间快速复制元素。

该方法是静态的，属于 `System` 类，可以比手动循环复制数组元素更快，因为它直接利用底层的内存操作。

语法：

```java
System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
```

参数说明：

- `src`：源数组，表示从哪个数组中复制数据。
- `srcPos`：源数组的起始位置，表示从源数组的哪个索引位置开始复制。
- `dest`：目标数组，表示数据复制到哪个数组。
- `destPos`：目标数组的起始位置，表示从目标数组的哪个索引位置开始写入。
- `length`：复制的元素数量。

以下是一个简单的示例，展示如何使用 `System.arraycopy` 将一个数组的一部分复制到另一个数组的特定位置。

```java
public class ArrayCopyExample {
    public static void main(String[] args) {
        // 源数组
        int[] sourceArray = {1, 2, 3, 4, 5};

        // 目标数组
        int[] destinationArray = new int[10];

        // 从源数组的索引 0 开始，复制 5 个元素到目标数组的索引 3 开始的位置
        System.arraycopy(sourceArray, 0, destinationArray, 3, 5);

        // 输出目标数组
        for (int i : destinationArray) {
            System.out.print(i + " ");
        }
    }
}
```

输出结果：

```
0 0 0 1 2 3 4 5 0 0
```

在这个例子中，`System.arraycopy` 将 `sourceArray` 的前 5 个元素复制到 `destinationArray` 从索引 `3` 开始的位置。未复制到的元素位置会保持目标数组的默认值（例如 `0`）。

---

## 導航

- 上一篇：[局部性原理](./04%20局部性原理.md)
- 返回：[數組入口](./README.md)
- 下一篇：[LeetCode 88 合并两个有序数组](./06%20LeetCode%2088%20合并两个有序数组.md)
