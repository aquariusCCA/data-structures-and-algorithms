# AVL 樹概述

## 為什麼需要 AVL 樹？

我們先思考一個問題：

**如果一棵二叉搜索樹長得很不平衡，查詢效率會不會變差？**

答案是：**會。**

例如下面這棵樹：

```text
         4
        /
       3
      /  
     2
    /
   1
```

這雖然還是一棵二叉搜索樹，但它已經嚴重偏向左邊，幾乎像一條鏈。

假設我們要查找節點 `1`：

* 先比較 `4`
* 再比較 `3`
* 再比較 `2`
* 最後才找到 `1`

總共要比較 4 次。

可以發現，這樣的查詢效率已經退化，最差情況下會接近線性查找，也就是 **元素越多，查找越慢**。

## 怎麼解決不平衡問題？

解法之一就是：**旋轉（Rotation）**

旋轉的目的是讓樹的形狀更平衡，從而提升查詢效率。

例如剛才那棵左偏的樹：

```text
         4
        /
       3
      /  
     2
    /
   1
```

對它做一次**右旋**後，會變成：

```text
         3
        / \
       2   4
      /
     1
```

這時候再查找 `1`：

* 先比較 `3`
* 再比較 `2`
* 再找到 `1`

查找次數就減少了。

## 為什麼旋轉後仍然是二叉搜索樹？

因為旋轉只是**調整節點之間的位置關係**，並沒有改變節點本身的值，也沒有破壞二叉搜索樹原本的規則：

* 左子樹的值都比根小
* 右子樹的值都比根大

所以旋轉之後，它仍然是一棵合法的二叉搜索樹。

## 怎麼判斷一棵樹是否失衡？

判斷方式是看某個節點的：

**左子樹高度 - 右子樹高度**

或

**右子樹高度 - 左子樹高度**

只要**左右子樹高度差的絕對值超過 1**，就表示這個節點失衡，需要調整。

## 高度怎麼看？

還是看這棵樹：

```text
         4
        /
       3
      /  
     2
    /
   1
```

各節點高度如下：

* 節點 `1` 高度是 `1`
* 節點 `2` 高度是 `2`
* 節點 `3` 高度是 `3`
* 節點 `4` 高度是 `4`

對根節點 `4` 來說：

* 左子樹高度是 `3`
* 右子樹高度是 `0`

高度差為 `3`

因為 `3 > 1`，所以節點 `4` 失衡，需要旋轉。

## 旋轉後為什麼就不用再轉了？

旋轉後變成：

```text
         3
        / \
       2   4
      /
     1
```

現在來看根節點 `3`：

* 左子樹高度是 `2`
* 右子樹高度是 `1`

高度差是 `1`

因為沒有超過 `1`，所以這棵樹已經算平衡，不需要再旋轉。

## 為什麼不是越平衡越好？

有人可能會想，既然旋轉可以平衡，那能不能再轉一次，變成這樣？

```text
         2
        / \
       1   3
            \
             4
```

這棵樹看起來也沒問題，但其實沒有必要。

因為：

* 查找 `1` 時，仍然是 2 次左右
* 查找 `4` 時，卻要 3 次

也就是說，它沒有本質上比前一棵更好。

所以 AVL 樹的目標不是「拼命旋轉到最漂亮」，而是：

**只要左右高度差不超過 1，就算平衡。**

## 什麼情況會讓樹失衡？

主要有兩種情況：

1. **插入新節點**
2. **刪除節點**

### 情況一：插入導致失衡

原本這棵樹是平衡的：

```text
         8
        / 
       6   
```

現在插入 `3`：

```text
         8
        / 
       6   
      /
     3
```

此時：

* 節點 `8` 的左子樹高度是 `2`
* 右子樹高度是 `0`

高度差為 `2`

已經失衡，所以要旋轉。

旋轉後：

```text
         6
        / \
       3   8
```

這樣就重新平衡了。

### 情況二：刪除導致失衡

原本這棵樹是平衡的：

```text
         6
        / \
       3   8
      /
     1
```

現在刪除 `8`：

```text
         6
        / 
       3
      /
     1
```

此時：

* 節點 `6` 的左子樹高度是 `2`
* 右子樹高度是 `0`

高度差為 `2`

因此失衡，需要旋轉。

旋轉後：

```text
         3
        / \
       1   6
```

又重新恢復平衡。

## AVL 樹的核心概念

經過上面的例子，可以整理出 AVL 樹的重點：

### 1. 二叉搜索樹在插入或刪除時，可能會失衡

也就是左右子樹高度差過大。

### 2. 失衡時，可以透過旋轉讓它恢復平衡

這樣可以避免樹退化成鏈狀，維持較好的查詢效率。

### 3. 這種能自動維持平衡的二叉搜索樹，稱為自平衡二叉搜索樹

AVL 樹就是其中一種實作。

## AVL 樹的定義

**AVL 樹（AVL Tree）** 是一種**自平衡二叉搜索樹**。

它的特點是：

* 本質上仍然是二叉搜索樹
* 在插入或刪除節點後，若發現某個節點失衡
* 就會透過旋轉操作，讓整棵樹重新保持平衡

因此，AVL 樹能讓查詢、插入、刪除都維持較好的效率。

# AVL 樹的高度與平衡因子

在實作 AVL 樹時，**高度（height）** 是非常重要的資訊。

因為 AVL 樹必須隨時判斷一個節點是否平衡，而判斷平衡的依據，就是看它左右子樹的高度差。

## 1. 先定義 AVL 節點

首先，我們先定義 AVL 樹中的節點類別：

```java
public class AVLTree {
    static class AVLNode {
        int key;
        Object value;
        AVLNode left;
        AVLNode right;
        int height = 1; // 高度

        public AVLNode(int key) {
            this.key = key;
        }

        public AVLNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public AVLNode(int key, Object value, AVLNode left, AVLNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
```

### 節點中的 height 是什麼？

這個 `height` 代表：**以目前節點為根的子樹高度**。

而且在這裡，節點一建立時，預設高度就是 `1`。

這是因為：

* 一個剛建立的節點，本身就算一層
* 它還沒有子節點，所以高度是 `1`

例如：

```text
   9
```

節點 `9` 的高度就是 `1`。

## 2. 取得節點高度的方法

雖然節點本身已經有 `height` 屬性，但我們仍然需要額外提供一個方法來取得高度：

```java
// 求节点的高度
private int height(AVLNode node) {
   return node == null ? 0 : node.height;
}
```

### 為什麼不能直接用 node.height？

因為傳進來的節點有可能是 `null`。

例如某個節點沒有左孩子或右孩子時，子節點就是 `null`。
如果直接寫：

```java
node.height
```

當 `node == null` 時，就會發生 **空指針異常（NullPointerException）**。

所以我們規定：

* 如果節點是 `null`，高度就是 `0`
* 如果節點存在，高度就是它自己的 `height`

這樣可以讓後面的高度計算更安全，也更方便。

## 3. 更新節點高度的方法

在 AVL 樹中，節點高度不是固定不變的。

只要樹的結構發生改變，就要重新更新高度，例如：

* **新增節點**
* **刪除節點**
* **旋轉節點**

更新高度的方法如下：

```java
// 更新节点高度（新增，删除，旋转）
private void updateHeight(AVLNode node) {
   node.height = Integer.max(
      height(node.left), 
      height(node.right)
   ) + 1;
}
```

### 節點高度是怎麼計算的？

規則很簡單：

> **節點高度 = 左右子樹中較大的高度 + 1**

也就是說：

* 先看左子樹高度
* 再看右子樹高度
* 取較大值
* 最後再加上自己這一層

### 用例子理解高度更新

#### 情況一：只有一個節點

```text
   9
```

這時候 `9` 沒有左右孩子，所以它的高度是 `1`。

#### 情況二：插入 7

`7 < 9`，所以 `7` 會放到 `9` 的左邊：

```text
   9
  /
 7
```

此時：

* `7` 的高度是 `1`
* `9` 的左子樹高度是 `1`
* `9` 的右子樹高度是 `0`

所以 `9` 的高度變成：

```text
max(1, 0) + 1 = 2
```

#### 情況三：再插入 10

`10 > 9`，所以 `10` 會放到 `9` 的右邊：

```text
   9
  / \
 7  10
```

此時：

* `7` 的高度是 `1`
* `10` 的高度是 `1`

所以 `9` 的高度是：

```text
max(1, 1) + 1 = 2
```

因此 `9` 的高度仍然是 `2`，不需要改變。

#### 情況四：再插入 5

`5 < 9`，先往左走；`5 < 7`，所以它會成為 `7` 的左孩子：

```text
     9
    / \
   7   10
  /
 5
```

此時：

* `5` 的高度是 `1`
* `7` 的高度要更新
* `9` 的高度也要更新

先看 `7`：

* 左子樹高度是 `1`
* 右子樹高度是 `0`

所以：

```text
7.height = max(1, 0) + 1 = 2
```

再看 `9`：

* 左子樹高度是 `2`
* 右子樹高度是 `1`

所以：

```text
9.height = max(2, 1) + 1 = 3
```

## 6. 平衡因子（Balance Factor）

AVL 樹要判斷一個節點是否平衡，不是只看高度本身，而是看：

> **左子樹高度 - 右子樹高度**

這個值就叫做 **平衡因子（balance factor）**。

公式如下：

```text
平衡因子 = 左子樹高度 - 右子樹高度
```

對應的方法是：

```java
private int bf(AVLNode node) {
   return height(node.left) - height(node.right);
}
```

### 平衡因子的意義

平衡因子的結果主要分成三種情況：

#### 1）0、1、-1

表示這個節點是**平衡的**。

也就是左右子樹高度差沒有超過 1。

#### 2）大於 1

表示這個節點**不平衡**，而且是**左子樹太高**。

#### 3）小於 -1

表示這個節點**不平衡**，而且是**右子樹太高**。

### 平衡因子的例子

#### 例子一：平衡因子為 0

```text
          2
         / \
        1   4
           / \
          3   5
```

看節點 `4`：

* 左子樹高度 = `1`
* 右子樹高度 = `1`

所以：

```text
bf(4) = 1 - 1 = 0
```

代表節點 `4` 是平衡的。

#### 例子二：平衡因子大於 1

```text
         6
        / \
       2   7
      / \   
     1   4
        / \
       3   5
```

看根節點 `6`：

* 左子樹高度 = `3`
* 右子樹高度 = `1`

所以：

```text
bf(6) = 3 - 1 = 2
```

因為 `2 > 1`，表示節點 `6` 已經不平衡，而且是**左邊太高**。

#### 例子三：平衡因子小於 -1

```text
         2
        / \
       1   6
          / \  
         4   7
        / \
       3   5
```

看根節點 `2`：

* 左子樹高度 = `1`
* 右子樹高度 = `3`

所以：

```text
bf(2) = 1 - 3 = -2
```

因為 `-2 < -1`，表示節點 `2` 已經不平衡，而且是**右邊太高**。

# AVL 樹的四種失衡情況

在 AVL 樹中，當某個節點的左右子樹高度差超過 1 時，這個節點就會**失衡**。

但是，失衡並不是只有一種樣子。
實際上，AVL 樹的失衡一共分成 **四種情況**：

* LL
* LR
* RL
* RR

理解這四種情況非常重要，因為 **不同的失衡類型，要用不同的旋轉方式處理**。

## 一、為什麼不能只看「左高右低」或「右高左低」？

先看下面這棵樹：

```text
         5
        / \ 
       3   6
      / \
     2   4
    /
   1
```

節點 `5`：

* 左子樹高度 = 3
* 右子樹高度 = 1

所以它的平衡因子：

```text
bf(5) = 3 - 1 = 2
```

表示節點 `5` 已經失衡，而且是**左邊太高**。

這種情況下，只要對 `5` 做一次**右旋**，就能恢復平衡。

旋轉後：

```text
         3
        / \ 
       2   5
      /   / \
     1   4   6
```

這說明：
有些「左高右低」的情況，**一次右旋就能解決**。

但是再看另一棵樹：

```text
         6
        / \
       2   7
      / \
     1   4
        / \
       3   5
```

節點 `6`：

* 左子樹高度 = 3
* 右子樹高度 = 1

所以它的平衡因子也是：

```text
bf(6) = 3 - 1 = 2
```

同樣是**左高右低**。

如果這時候也直接對 `6` 做一次右旋，會變成：

```text
         2
        / \ 
       1   6
          / \
         4   7
        / \
       3   5
```

你會發現：**樹還是不平衡**。

這就說明一件事：

> 不能只看失衡節點是左高還是右高，還要繼續看它的子樹是偏左還是偏右。

也因此，AVL 樹才會把失衡分成四種類型。

## 二、AVL 樹四種失衡類型

### 1. LL 型

#### 判斷方式

* 失衡節點的 `bf > 1`，代表**左邊太高**
* 失衡節點的左孩子 `bf >= 0`，代表左孩子也是**左邊高**或**左右等高**

#### 樹形特徵

就是「**左邊的左邊太高**」。

例如：

```text
         5
        / \ 
       3   6
      / \
     2   4
    /
   1
```

這裡：

* `5` 是失衡節點
* `5` 的左孩子是 `3`
* `3` 也是偏左

所以這是 **LL 型失衡**。

#### 解法

**對失衡節點做一次右旋** 即可。

旋轉後：

```text
         3
        / \ 
       2   5
      /   / \
     1   4   6
```

#### 結論

> **LL 型：一次右旋**

### 2. RR 型

#### 判斷方式

* 失衡節點的 `bf < -1`，代表**右邊太高**
* 失衡節點的右孩子 `bf <= 0`，代表右孩子也是**右邊高**或**左右等高**

#### 樹形特徵

就是「**右邊的右邊太高**」。

例如：

```text
         2
        / \ 
       1   4
          / \
         3   5
              \
               6
```

這裡：

* `2` 是失衡節點
* `2` 的右孩子是 `4`
* `4` 也是偏右

所以這是 **RR 型失衡**。

#### 解法

**對失衡節點做一次左旋** 即可。

旋轉後：

```text
         4
        / \ 
       2   5
      / \   \
     1   3   6
```

#### 結論

> **RR 型：一次左旋**

### 3. LR 型

#### 判斷方式

* 失衡節點的 `bf > 1`，代表**左邊太高**
* 失衡節點的左孩子 `bf < 0`，代表左孩子是**右邊太高**

#### 樹形特徵

就是「**左邊高，但左子樹的右邊更高**」，也就是「**左-右**」。

例如：

```text
         6
        / \
       2   7
      / \
     1   4
        / \
       3   5
```

這裡：

* `6` 是失衡節點，左邊高
* `6` 的左孩子 `2`，卻是右邊高

所以這是 **LR 型失衡**。

#### 為什麼不能直接右旋？

因為這不是單純的 LL。
如果直接對 `6` 右旋，樹不一定會恢復平衡。

所以 LR 型需要**兩次旋轉**。

#### 解法

分兩步：

##### 第一步：先對失衡節點的左子樹做一次左旋

也就是先對 `2` 左旋。

旋轉後：

```text
         6
        / \
       4   7
      / \
     2   5
    / \
   1   3
```

這時整棵樹已經轉成 **LL 型**。

##### 第二步：再對失衡節點做一次右旋

對 `6` 再做右旋。

結果變成：

```text
          4
        /   \ 
       2     6
      / \   / \
     1   3 5   7
```

#### 結論

> **LR 型：先左旋左子樹，再右旋失衡節點**

也可以記成：

> **先左後右**


### 4. RL 型

#### 判斷方式

* 失衡節點的 `bf < -1`，代表**右邊太高**
* 失衡節點的右孩子 `bf > 0`，代表右孩子是**左邊太高**

#### 樹形特徵

就是「**右邊高，但右子樹的左邊更高**」，也就是「**右-左**」。

例如：

```text
         2
        / \ 
       1   6
          / \
         4   7
        / \
       3   5
```

這裡：

* `2` 是失衡節點，右邊高
* `2` 的右孩子 `6`，卻是左邊高

所以這是 **RL 型失衡**。

#### 解法

同樣需要兩步：

##### 第一步：先對失衡節點的右子樹做一次右旋

也就是先對 `6` 做右旋。

旋轉後：

```text
         2
        /  \ 
       1    4
          /  \
         3    6
             / \
            5   7
```

這時整棵樹變成 **RR 型**。

##### 第二步：再對失衡節點做一次左旋

對 `2` 做左旋。

最後得到：

```text
         4
        / \ 
       2   6
      / \ / \
     1  3 5  7
```

#### 結論

> **RL 型：先右旋右子樹，再左旋失衡節點**

也可以記成：

> **先右後左**

## 三、四種失衡的核心判斷規則

可以整理成下面這張表：

| 失衡類型 | 失衡節點 | 子節點特徵     | 解法      |
| ---- | ---- | --------- | ------- |
| LL   | 左高   | 左孩子也偏左或等高 | 一次右旋    |
| RR   | 右高   | 右孩子也偏右或等高 | 一次左旋    |
| LR   | 左高   | 左孩子偏右     | 先左旋，再右旋 |
| RL   | 右高   | 右孩子偏左     | 先右旋，再左旋 |

# AVL 樹的左旋與右旋

前面我們已經知道，AVL 樹失衡時有四種情況：

* LL
* RR
* LR
* RL

雖然失衡情況有四種，但**基本旋轉操作其實只有兩種**：

* **右旋（Right Rotate）**
* **左旋（Left Rotate）**

至於 LR、RL，都是由這兩種基本旋轉組合出來的。

## 一、右旋（Right Rotate）

### 1. 右旋的核心概念

右旋可以理解成：

> **把左子樹旋轉上來，原本的根節點往右下移。**

方法宣告如下：

```java
// 參數：要旋轉的節點
// 返回值：新的根節點
private AVLNode rightRotate(AVLNode node) {

}
```

### 2. 先看最簡單的例子

原本的樹：

```text
      8
     /
    6
```

現在對 `8` 做右旋。

右旋後會變成：

```text
      6
       \
        8
```

意思是：

* `8` 原本是根
* `6` 是 `8` 的左孩子
* 右旋之後，`6` 上升成新的根
* `8` 變成 `6` 的右孩子

### 3. 對應的基本想法

用偽代碼表示：

```java
private AVLNode rightRotate(AVLNode node) {
    AVLNode leftChild = node.left;
    leftChild.right = node;
    return leftChild;
}
```

這個版本先幫我們建立一個概念：

* 找到要旋轉節點的左孩子
* 左孩子上升
* 原節點下降成它的右孩子

但是，這還不完整。

### 4. 為什麼上面的寫法還不夠？

因為它沒有處理「中間子樹」的去向。

來看更完整的情況：

```text
       8
     /   \   
    6     9
   / \
  5   7
```

這次對 `8` 右旋：

* `8` 要往下
* `6` 要往上
* `7` 的位置就必須重新安排

如果只寫：

```java
AVLNode leftChild = node.left;
leftChild.right = node;
```

那原本 `6.right` 的 `7` 就會被覆蓋掉。

所以我們必須先把 `7` 暫存起來。

### 5. 右旋時，中間子樹要怎麼接？

原本：

```text
       8
     /   \   
    6     9
   / \
  5   7
```

右旋之後：

```text
        6
      /   \   
     5     8
          / \
         7   9
```

可以看到：

* `6` 上升
* `8` 下降
* 原本 `6` 的右子樹 `7`，改成 `8` 的左子樹

這樣才不會遺失節點，而且仍然符合二叉搜索樹規則。

### 6. 完整的右旋程式碼

```java
private AVLNode rightRotate(AVLNode node) {
    AVLNode leftChild = node.left;
    AVLNode leftRightChild = leftChild.right;

    leftChild.right = node;
    node.left = leftRightChild;

    return leftChild;
}
```

### 7. 右旋時要注意的細節

#### 細節一：node.left 不需要判空嗎？

通常不用。

因為既然要做右旋，表示這個節點一定是**左邊比較高**，那麼左孩子理論上不可能是 `null`。

#### 細節二：程式碼順序很重要

下面這種寫法有問題：

```java
AVLNode leftChild = node.left;
leftChild.right = node;
AVLNode leftRightChild = leftChild.right;
node.left = leftRightChild;
```

因為當你先執行：

```java
leftChild.right = node;
```

```text
       8
     /   \   
    6     9
   / \
  5   7
```

此時 `leftChild.right` 已經不再是原本的那個節點了，而變成 `node`。
也就是說，你原本想保存的 `7` 已經拿不到了。

所以一定要先暫存：

```java
AVLNode leftChild = node.left;
AVLNode leftRightChild = leftChild.right;
```

然後才能修改指標。

## 二、左旋（Left Rotate）

左旋和右旋是完全對稱的。

### 1. 左旋的核心概念

左旋可以理解成：

> **把右子樹旋轉上來，原本的根節點往左下移。**

方法宣告：

```java
private AVLNode leftRotate(AVLNode node) {

}
```

### 2. 例子

原本的樹：

```text
          2
        /  \
       1    4 
           /  \
          3    5
                \
                 6
```

對 `2` 做左旋後：

```text
         4
        / \ 
       2   5
      / \    \
     1   3    6
```

這裡：

* `2` 是要旋轉的節點
* `4` 是要上升的新根
* `3` 是中間子樹，要重新換父節點

### 3. 完整的左旋程式碼

```java
private AVLNode leftRotate(AVLNode node) {
    AVLNode rightChild = node.right;
    AVLNode rightLeftChild = rightChild.left;

    rightChild.left = node;
    node.right = rightLeftChild;

    return rightChild;
}
```

### 4. 左旋的理解方式

對照右旋來記：

#### 右旋

* `node.left` 上升
* `leftChild.right` 改接給 `node.left`

#### 左旋

* `node.right` 上升
* `rightChild.left` 改接給 `node.right`

## 三、LR：先左旋，再右旋

### 1. LR 是什麼？

LR 的意思是：

* 失衡節點是**左邊高**
* 但它的左子樹卻是**右邊高**

例如：

```text
            6
           / \
          2   7
         / \
        1   4
           /  \
          3    5    
```

這時不能直接右旋，因為它不是 LL，而是 LR。

### 2. LR 的處理方式

要分兩步：

#### 第一步：先對左子樹做左旋

也就是對 `2` 左旋：

```text
            6
           / \
          4   7
         / \ 
        2   5
       / \
      1   3    
```

#### 第二步：再對根節點做右旋

對 `6` 右旋：

```text
            4
           / \
          2   6
         / \ / \
        1  3 5  7    
```

### 3. LR 的程式碼

```java
private AVLNode leftRightRotate(AVLNode node) {
    node.left = leftRotate(node.left);
    return rightRotate(node);
}
```

### 4. 為什麼這樣寫？

因為：

1. 原本 `node.left` 是左子樹根節點
2. 左旋之後，左子樹的根節點改變了
3. 所以要把結果重新接回 `node.left`
4. 最後再對 `node` 本身做右旋

## 四、RL：先右旋，再左旋

### 1. RL 是什麼？

RL 的意思是：

* 失衡節點是**右邊高**
* 但它的右子樹卻是**左邊高**

例如：

```text
            2
           / \
          1   6
             / \
            4   7
           / \
          3   5    
```

這時不能直接左旋，因為它不是 RR，而是 RL。

### 2. RL 的處理方式

分兩步：

#### 第一步：先對右子樹做右旋

也就是對 `6` 做右旋：

```text
            2
           / \
          1   4
             / \
            3   6
               / \
              5   7    
```

#### 第二步：再對根節點做左旋

對 `2` 左旋：

```text
         4
        / \ 
       2   6
      / \ / \
     1  3 5  7
```

### 3. RL 的程式碼

```java
private AVLNode rightLeftRotate(AVLNode node) {
    node.right = rightRotate(node.right);
    return leftRotate(node);
}
```

## 五、旋轉後為什麼還要更新高度？

旋轉不只是改變節點位置，還會改變子樹高度。

例如右旋前：

```text
          5
         / \ 
        3   6
       / \
      2   4
     /
    1
```

右旋後：

```text
          3
         / \ 
        2   5
       /   / \
      1   4   6
```

原本：

* `5` 的高度較高

旋轉後：

* `5` 被旋轉下去，高度變小了
* `3` 被旋轉上來，高度也可能改變

所以旋轉完成後，一定要重新計算高度。

## 六、右旋時的高度更新

右旋完成後，節點的高度可能會發生變化，所以也要重新更新高度。

```java
private AVLNode rightRotate(AVLNode node) {
    AVLNode leftChild = node.left;
    AVLNode leftRightChild = leftChild.right;

    leftChild.right = node;
    node.left = leftRightChild;

    // 先更新旋轉下去的節點
    updateHeight(node);
    // 再更新旋轉上來的節點
    updateHeight(leftChild);

    return leftChild;
}
```

### 為什麼先更新 node，再更新 leftChild？

因為右旋之後：

* `node` 會被旋轉下去
* `leftChild` 會旋轉上來，成為新的根

而 `leftChild` 的高度，必須根據它最新的左右子樹高度來計算。
其中，它的右子樹正是剛剛旋轉下去的 `node`。

也就是說：

* 如果 `node` 的高度還沒更新
* 你就先去更新 `leftChild`
* 那麼 `leftChild` 會拿到一個**舊的 `node.height`**
* 算出來的高度就可能是錯的

所以正確順序一定是：

1. **先更新旋轉下去的節點 `node`**
2. **再更新旋轉上來的節點 `leftChild`**

這樣 `leftChild` 在計算高度時，才能取得正確的子樹高度。

### 用圖來理解

例如下面這棵樹做右旋：

```text
      5                    3
     / \                  / \    
    3   6      ->        2   5
   / \                  /   / \
  2   4                1   4   6
 /
1
```

右旋後：

* `5` 被旋轉下去
* `3` 旋轉上來

此時 `3` 的右子樹變成了 `5`。
所以如果 `5` 的高度還沒先更新，`3` 的高度就會建立在錯誤的基礎上。

因此：

```java
updateHeight(node);
updateHeight(leftChild);
```

這個順序不能顛倒。

## 七、左旋時的高度更新

左旋和右旋一樣，旋轉完成後，也要更新節點高度。

```java
private AVLNode leftRotate(AVLNode node) {
    AVLNode rightChild = node.right;
    AVLNode rightLeftChild = rightChild.left;

    rightChild.left = node;
    node.right = rightLeftChild;

    updateHeight(node);
    updateHeight(rightChild);

    return rightChild;
}
```

### 為什麼一定要先更新 node，再更新 rightChild？

因為左旋之後：

* `node` 會被旋轉下去
* `rightChild` 會旋轉上來，成為新的根

而 `rightChild` 的高度，必須根據它最新的左右子樹高度來計算。
其中，它的左子樹正是剛剛旋轉下去的 `node`。

也就是說：

* 如果 `node` 的高度還沒更新
* 你就先去更新 `rightChild`
* 那麼 `rightChild` 會拿到一個**舊的 `node.height`**
* 算出來的高度就可能是錯的

所以正確順序一定是：

1. **先更新旋轉下去的節點 `node`**
2. **再更新旋轉上來的節點 `rightChild`**

這樣 `rightChild` 在計算高度時，才能取得正確的子樹高度。

### 用圖來理解

例如下面這棵樹做左旋：

```text
       2                          4
      / \                       /   \
     1   4                     2     6
        / \           ->      / \   / \
       3   6                 1   3 5   7 
          / \
         5   7
```

左旋後：

* `2` 被旋轉下去
* `4` 旋轉上來

此時 `4` 的左子樹變成了 `2`。
所以如果 `2` 的高度還沒先更新，`4` 的高度就會建立在錯誤的基礎上。

因此：

```java
updateHeight(node);
updateHeight(rightChild);
```

這個順序不能顛倒。

## 八、旋轉後，哪些節點需要更新高度？

旋轉之後，並不是整棵樹的所有節點都要重新更新高度。

真正需要更新的，其實只有兩類節點：

1. **要旋轉下去的節點**
2. **要旋轉上來的節點**

除此之外，其他節點的子樹結構並沒有改變，所以它們的高度也不會改變。

### 為什麼只需要更新這兩個節點？

因為一次基本旋轉，真正改變父子關係的核心節點只有這兩個：

* 一個往下掉
* 一個往上升

至於中間那棵「換父節點的子樹」，雖然它的父節點變了，但它自己的左右子樹並沒有變，所以它本身的高度通常不變。

### 看起來「旋轉上來的節點」好像不用更新？

乍看之下，會覺得只有旋轉下去的節點高度會變，旋轉上來的節點高度好像不一定會變。

例如下面這個右旋例子中，`3` 是旋轉上來的節點：

```text
      5                    3
     / \                  / \    
    3   6      ->        2   5
   / \                  /   / \
  2   4                1   4   6
 /
1
```

在這個例子裡，`3` 旋轉上來前後，看起來高度似乎沒有明顯改變，所以容易讓人誤以為：

> 只有旋轉下去的節點需要更新，旋轉上來的節點不用更新。

但這個想法其實不對。

### 為什麼旋轉上來的節點也要更新？

我們來看另一個例子，也就是 LR 情況中的第一步：
先對左子樹做一次左旋。

原本：

```text
      6
     / \
    2   7
   / \
  1   4
      / \
     3   5
```

先對 `2` 做左旋後：

```text
       6                6
      / \              / \
     2   7     ->     4   7
    / \              / \ 
   1   4            2   5
      / \          / \
     3   5        1   3
```

這裡：

* `2` 是旋轉下去的節點
* `4` 是旋轉上來的節點
* `3` 是換父節點的子樹

注意看 `4`：

* 旋轉前，`4` 的高度是 `2`
* 旋轉後，`4` 的高度變成 `3`

也就是說：

> **旋轉上來的節點，它的高度也可能改變。**

所以不能只更新旋轉下去的節點，
**旋轉上來的節點也一定要更新。**

### 右旋的高度更新寫法

```java
private AVLNode rightRotate(AVLNode node) {
    AVLNode leftChild = node.left;
    AVLNode leftRightChild = leftChild.right;

    leftChild.right = node;
    node.left = leftRightChild;

    // 先更新旋轉下去的節點
    updateHeight(node);
    // 再更新旋轉上來的節點
    updateHeight(leftChild);

    return leftChild;
}
```

這裡也是同樣道理：

* `node` 先旋轉下去
* `leftChild` 再旋轉上來

所以更新順序一定是：

```java
updateHeight(node);
updateHeight(leftChild);
```

不能顛倒。

### 雙旋轉需要額外處理高度嗎？

通常不需要單獨再寫一套高度更新邏輯。

因為：

* LR = 左旋 + 右旋
* RL = 右旋 + 左旋

而每次基本旋轉 `leftRotate()`、`rightRotate()` 內部都已經做好高度更新了，所以直接組合呼叫即可。

例如：

```java
private AVLNode leftRightRotate(AVLNode node) {
    node.left = leftRotate(node.left);
    return rightRotate(node);
}

private AVLNode rightLeftRotate(AVLNode node) {
    node.right = rightRotate(node.right);
    return leftRotate(node);
}
```

## 九、四種旋轉程式碼總整理

```java
private AVLNode rightRotate(AVLNode node) {
    AVLNode leftChild = node.left;
    AVLNode leftRightChild = leftChild.right;

    leftChild.right = node;
    node.left = leftRightChild;

    updateHeight(node);
    updateHeight(leftChild);

    return leftChild;
}

private AVLNode leftRotate(AVLNode node) {
    AVLNode rightChild = node.right;
    AVLNode rightLeftChild = rightChild.left;

    rightChild.left = node;
    node.right = rightLeftChild;

    updateHeight(node);
    updateHeight(rightChild);

    return rightChild;
}

private AVLNode leftRightRotate(AVLNode node) {
    node.left = leftRotate(node.left);
    return rightRotate(node);
}

private AVLNode rightLeftRotate(AVLNode node) {
    node.right = rightRotate(node.right);
    return leftRotate(node);
}
```

# AVL 樹的 balance 方法

前面我們已經知道，AVL 樹在**新增**或**刪除**節點後，可能會失衡。
所以我們需要一個方法，在節點失衡時，自動做對應的旋轉，讓它重新恢復平衡。

這個方法就叫做 `balance`。

```java
private AVLNode balance(AVLNode node) {

}
```

它的作用可以簡單理解成：

> **檢查目前節點是否失衡，如果失衡，就做正確的旋轉；如果沒有失衡，就直接返回原節點。**

## 1. 先處理 node == null 的情況

如果目前節點本身就是 `null`，那就沒有必要再判斷是否平衡，直接返回 `null` 即可。

```java
private AVLNode balance(AVLNode node) {
    if (node == null) {
        return null;
    }
}
```

## 2. 怎麼判斷節點是否失衡？

我們前面已經寫過 `bf(node)` 方法，用來求一個節點的**平衡因子**。

平衡因子的公式是：

```text
平衡因子 = 左子樹高度 - 右子樹高度
```

它的結果有三種主要情況：

### 1）0、1、-1

表示節點是**平衡的**。
也就是左右子樹高度差沒有超過 1。

### 2）大於 1

表示節點**不平衡**，而且是**左子樹太高**。

### 3）小於 -1

表示節點**不平衡**，而且是**右子樹太高**。

所以在 `balance` 方法中，我們第一步就是先求出目前節點的平衡因子：

```java
private AVLNode balance(AVLNode node) {
    if (node == null) {
        return null;
    }
    int bf = bf(node);
}
```

## 3. 左邊高時，要繼續判斷是 LL 還是 LR

如果 `bf > 1`，表示目前節點左邊太高。
但這時還不能立刻決定怎麼旋轉，因為左高又分成兩種情況：

* **LL**
* **LR**

要怎麼分辨呢？

就要再看：

> **失衡節點的左孩子，它自己的平衡因子是多少？**

### LL 的判斷方式

如果：

* 目前節點左邊高
* 而且左孩子也是左邊高，或者左右等高

那就是 **LL**

```java
bf(node) > 1 && bf(node.left) >= 0
```

這時做一次**右旋**即可。

### LR 的判斷方式

如果：

* 目前節點左邊高
* 但左孩子是右邊高

那就是 **LR**

```java
bf(node) > 1 && bf(node.left) < 0
```

這時不能直接右旋，而要：

* 先對左子樹做左旋
* 再對根節點做右旋

也就是 **leftRightRotate(node)**

## 4. 右邊高時，要繼續判斷是 RL 還是 RR

如果 `bf < -1`，表示目前節點右邊太高。
這時同樣也不能直接決定旋轉方式，還要再看右孩子的平衡因子。

### RL 的判斷方式

如果：

* 目前節點右邊高
* 但右孩子是左邊高

那就是 **RL**

```java
bf(node) < -1 && bf(node.right) > 0
```

這時要：

* 先對右子樹做右旋
* 再對根節點做左旋

也就是 **rightLeftRotate(node)**

### RR 的判斷方式

如果：

* 目前節點右邊高
* 而且右孩子也是右邊高，或者左右等高

那就是 **RR**

```java
bf(node) < -1 && bf(node.right) <= 0
```

這時做一次**左旋**即可。

## 5. balance 方法的完整邏輯

整理後的程式碼如下：

```java
private AVLNode balance(AVLNode node) {
    if (node == null) {
        return null;
    }

    int bf = bf(node);

    // 左子樹過高
    if (bf > 1 && bf(node.left) >= 0) { // LL
        return rightRotate(node);
    } else if (bf > 1 && bf(node.left) < 0) { // LR
        return leftRightRotate(node);
    }

    // 右子樹過高
    else if (bf < -1 && bf(node.right) > 0) { // RL
        return rightLeftRotate(node);
    } else if (bf < -1 && bf(node.right) <= 0) { // RR
        return leftRotate(node);
    }

    // 本身平衡，不需要旋轉
    return node;
}
```

## 6. 為什麼最後要 return node？

因為不是每個節點都會失衡。

如果平衡因子是 `0`、`1`、`-1`，表示這個節點本來就平衡，根本不需要旋轉。

所以最後要把原本的節點直接返回：

```java
return node;
```

這樣整個方法的意義才完整：

* 失衡 → 旋轉後返回新根
* 平衡 → 直接返回原節點

## 7. 為什麼插入和刪除都可以用 balance？

因為不論是插入還是刪除，只要造成某個節點的左右高度差超過 1，就要重新調整。

所以 `balance` 可以當成一個通用方法：

* 插入完，往上回溯時可以呼叫它
* 刪除完，往上回溯時也可以呼叫它

也就是說：

> **只要某個節點可能失衡，就交給 `balance` 處理。**

## 8. 刪除時的特殊情況

前面的四種情況，已經可以處理大多數失衡問題。
但是在**刪除節點**時，還有兩種特別容易漏掉的情況。

這兩種情況的特點是：

* 失衡節點的某一邊太高
* 但是它孩子節點的平衡因子剛好是 `0`

這種情況在插入時通常不會出現，但在刪除時很常見。

## 9. 刪除時，左孩子平衡因子為 0 的情況

先看這棵樹：

```text
         6
        / \
       3   8
      / \
     1   5
```

這棵樹目前是平衡的。

現在把 `8` 刪掉：

```text
         6
        / 
       3   
      / \
     1   5
```

此時節點 `6` 失衡了：

* 左子樹高度 = 2
* 右子樹高度 = 0

所以它是左邊太高。

但問題是，節點 `3` 的左右子樹高度相同，所以：

```text
bf(3) = 0
```

這時它不符合原本簡化版的 LL 判斷：

```java
bf(node.left) > 0
```

因為這裡不是 `> 0`，而是 `== 0`。

但實際上，這種情況還是應該做一次**右旋**：

```text
      6           3
     /           / \
    3     ->    1   6
   / \             /
  1   5           5
```

所以在刪除情況下，LL 的判斷應該放寬成：

```java
bf(node.left) >= 0
```

而不是只寫 `> 0`。

## 10. 刪除時，右孩子平衡因子為 0 的情況

右邊的情況也是同樣道理。

如果：

* 目前節點右邊太高
* 而且右孩子的平衡因子剛好等於 `0`

那它仍然應該歸類成 **RR**，做一次**左旋**。

所以 RR 的判斷也要放寬成：

```java
bf(node.right) <= 0
```

而不是只寫 `< 0`。

## 11. 最終版 balance 方法

考慮刪除時的特殊情況後，完整版本如下：

```java
private AVLNode balance(AVLNode node) {
    if (node == null) {
        return null;
    }

    int bf = bf(node);

    // 左子樹過高
    if (bf > 1 && bf(node.left) >= 0) { // LL
        return rightRotate(node);
    } else if (bf > 1 && bf(node.left) < 0) { // LR
        return leftRightRotate(node);
    }

    // 右子樹過高
    else if (bf < -1 && bf(node.right) > 0) { // RL
        return rightLeftRotate(node);
    } else if (bf < -1 && bf(node.right) <= 0) { // RR
        return leftRotate(node);
    }

    return node;
}
```

# AVL 樹的新增操作

前面我們已經把 AVL 樹的重要基礎方法都準備好了，例如：

* 節點高度的計算
* 平衡因子的判斷
* 左旋、右旋
* `balance` 平衡調整方法

接下來，就可以開始實作 AVL 樹的**新增操作**。

至於查詢操作，因為查詢並不會改變樹的結構，也不會影響平衡，所以它和普通二叉搜索樹的查詢邏輯一樣，這裡就不重複實作了。

真正需要特別處理的是：

* **新增**
* **刪除**

因為這兩個操作都可能讓樹失衡。

## 一、先定義 put 方法

AVL 樹的新增方法可以叫做 `put`，接收兩個參數：

* `key`
* `value`

```java
public void put(int key, Object value) {

}
```

這個方法是給外部呼叫的入口。

## 二、為什麼要另外寫一個遞迴方法？

為了簡化實作，我們用**遞迴**來完成新增。

原因很簡單：

> 新增節點的過程，本質上就是沿著樹一路往下找，直到找到空位，把新節點放進去。

這種「往左找 / 往右找 / 找到空位就停止」的流程，很適合用遞迴實現。

所以我們再設計一個真正負責遞迴插入的方法：

```java
private AVLNode doPut(AVLNode node, int key, Object value) {

}
```

這個方法多了一個參數：

* `node`：表示目前遞迴到哪個節點

也就是說，它的意思是：

> **從 node 這個節點開始，去找 key 應該插入的位置。**

## 三、doPut 的三種情況

整個新增邏輯，其實可以拆成三種情況：

### 1. 找到空位

如果目前節點是 `null`，表示已經找到插入位置了。
這時直接建立新節點並返回。

### 2. key 已存在

如果目前節點的 key 和要插入的 key 一樣，表示這不是新增，而是更新。
直接更新 value 即可，不需要繼續往下找。

### 3. 繼續查找

如果既不是空位，也不是相同 key，那就依照二叉搜索樹的規則：

* `key < node.key` → 往左找
* `key > node.key` → 往右找

## 四、先補上根節點

因為整棵樹總要有一個入口，所以先定義根節點：

```java
AVLNode root;
```

然後 `put` 方法要做的事，就是從根節點開始呼叫 `doPut`：

```java
AVLNode root;

public void put(int key, Object value) {
    root = doPut(root, key, value);
}
```

這裡的重點是：

```java
root = doPut(root, key, value);
```

為什麼要賦值給 `root`？

因為插入之後，整棵樹的根節點**有可能改變**。

例如：

* 第一次插入時，根原本是 `null`
* 插入後，新的節點要成為根
* 之後如果發生旋轉，新的根也可能改變

所以 `doPut` 的返回值，一定要重新接回 `root`。

## 五、情況一：找到空位，建立新節點

如果遞迴傳進來的 `node == null`，表示已經找到空位了。

這時直接建立新節點：

```java id="4rplsi"
private AVLNode doPut(AVLNode node, int key, Object value) {
    // 1. 找到空位，建立新節點
    if (node == null) {
        return new AVLNode(key, value);
    }

    // 2. key 已存在，更新
    // 3. 繼續查找
}
```

新節點建立後，它的高度預設就是 `1`，因為在 `AVLNode` 類裡已經設定好了。

## 六、情況二：key 已存在，直接更新

如果目前節點的 key 和要插入的 key 一樣，表示這個 key 已經存在了。

這時就不需要再新增節點，而是直接更新 value：

```java
private AVLNode doPut(AVLNode node, int key, Object value) {
    // 1. 找到空位，建立新節點
    if (node == null) {
        return new AVLNode(key, value);
    }

    // 2. key 已存在，更新
    if (node.key == key) {
        node.value = value;
        return node;
    }

    // 3. 繼續查找
}
```

這裡更新完就可以直接 `return node`，因為：

* 樹的結構沒有改變
* 高度不會改變
* 不會失衡
* 所以也不需要重新平衡

## 七、情況三：繼續往下找空位

如果不是空位，也不是相同 key，那就表示要繼續找。

根據二叉搜索樹規則：

* `key < node.key` → 去左子樹
* 否則 → 去右子樹

```java 
private AVLNode doPut(AVLNode node, int key, Object value) {
    // 1. 找到空位，建立新節點
    if (node == null) {
        return new AVLNode(key, value);
    }

    // 2. key 已存在，更新
    if (node.key == key) {
        node.value = value;
        return node;
    }

    // 3. 繼續查找
    if (key < node.key) {
        node.left = doPut(node.left, key, value);
    } else {
        node.right = doPut(node.right, key, value);
    }
}
```

## 八、為什麼要寫成 node.left = doPut(...)？

這一點很重要。

例如往左找時，不是只呼叫：

```java 
doPut(node.left, key, value);
```

而是要寫成：

```java
node.left = doPut(node.left, key, value);
```

原因是：

> 遞迴找到空位後，會返回一個新節點；
> 這個新節點必須接回目前節點的左孩子或右孩子，父子關係才會真正建立起來。

也就是說：

* 如果是往左找，返回值要接到 `node.left`
* 如果是往右找，返回值要接到 `node.right`

這樣整棵樹的結構才會真正被更新。

## 九、插入後還沒結束：還要更新高度

如果只是普通二叉搜索樹，寫到這裡通常就結束了。

但 AVL 樹不一樣。

因為插入節點後，某些祖先節點的高度可能會改變，所以在遞迴返回的路上，要順便更新高度：

```java
private AVLNode doPut(AVLNode node, int key, Object value) {
    if (node == null) {
        return new AVLNode(key, value);
    }

    if (node.key == key) {
        node.value = value;
        return node;
    }

    if (key < node.key) {
        node.left = doPut(node.left, key, value);
    } else {
        node.right = doPut(node.right, key, value);
    }

    updateHeight(node);
}
```

這一行的意思是：

> 當子節點插入完成後，回頭更新目前這個節點的高度。

## 十、更新完高度後，還要檢查是否失衡

更新高度之後，節點有可能會失衡。

所以接下來還要呼叫 `balance(node)`，檢查是否需要旋轉：

```java
private AVLNode doPut(AVLNode node, int key, Object value) {
    if (node == null) {
        return new AVLNode(key, value);
    }

    if (node.key == key) {
        node.value = value;
        return node;
    }

    if (key < node.key) {
        node.left = doPut(node.left, key, value);
    } else {
        node.right = doPut(node.right, key, value);
    }

    updateHeight(node);
    return balance(node);
}
```

這裡一定要 `return balance(node)`，不能只寫：

```java
balance(node);
```

因為：

* 如果節點沒有失衡，`balance(node)` 會返回原本的 `node`
* 如果節點失衡，`balance(node)` 會返回旋轉後的**新根節點**

所以我們必須把它當作返回值傳回去，讓上層正確接住新的子樹根節點。

## 十一、最終版 doPut

整理後，AVL 樹的新增遞迴方法如下：

```java id="ub0s5u"
private AVLNode doPut(AVLNode node, int key, Object value) {
    // 1. 找到空位，建立新節點
    if (node == null) {
        return new AVLNode(key, value);
    }

    // 2. key 已存在，更新
    if (node.key == key) {
        node.value = value;
        return node;
    }

    // 3. 繼續查找
    if (key < node.key) {
        node.left = doPut(node.left, key, value);
    } else {
        node.right = doPut(node.right, key, value);
    }

    // 4. 更新高度
    updateHeight(node);

    // 5. 重新平衡並返回
    return balance(node);
}
```

而對外的 `put` 方法是：

```java
AVLNode root;

public void put(int key, Object value) {
    root = doPut(root, key, value);
}
```

## 十三、用例子理解新增流程

### 例子一：插入第一個節點 9

一開始整棵樹是空的：

```text
root = null
```

呼叫：

```java
put(9, value)
```

這時：

```java
root = doPut(root, key, value);
```

因為 `root` 是 `null`，所以進入：

```java
if (node == null) {
    return new AVLNode(key, value);
}
```

於是建立出新節點 `9`，並返回。

最後：

```text
       9
     /   \
   null  null
```

這時 `9` 就成了根節點。

### 例子二：再插入 5

現在樹是：

```text
       9
```

插入 `5`：

* `5 != 9`
* `5 < 9`
* 所以往左找

左邊是 `null`，表示找到空位，建立新節點 `5`。

插入後變成：

```text
       9
      /
     5
```

接著開始遞迴返回，回到節點 `9` 時會做兩件事：

#### 1. 更新高度

* 左子樹高度 = 1
* 右子樹高度 = 0
* 所以 `9.height = 2`

#### 2. 檢查平衡

* `bf(9) = 1 - 0 = 1`
* 沒有失衡

所以 `balance(9)` 最後返回的還是 `9`。

整棵樹保持不變。

### 例子三：再插入 3

現在樹是：

```text 
       9
      /
     5
```

插入 `3` 的流程是：

* `3 < 9`，往左找
* `3 < 5`，再往左找
* 左邊是 `null`，建立新節點 `3`

插入後暫時會變成：

```text
         9
        /
       5
      /
     3
```

#### 先回到節點 5

`3` 插入完成後，遞迴開始往回走。

先回到節點 `5`：

#### 更新高度

* 左子樹高度 = 1
* 右子樹高度 = 0
* 所以 `5.height = 2`

#### 檢查平衡

* `bf(5) = 1`
* 還平衡，不旋轉

所以節點 `5` 原樣返回。

#### 再回到節點 9

接著回到節點 `9`：

#### 更新高度

* 左子樹高度 = 2
* 右子樹高度 = 0
* 所以 `9.height = 3`

#### 檢查平衡

* `bf(9) = 2`
* 已經失衡

樹形如下：

```text
         9
        /
       5
      /
     3
```

這屬於：

* `9` 左邊高
* `9` 的左孩子 `5` 也左邊高

所以這是 **LL 型失衡**。

#### 做右旋

對 `9` 做右旋後：

```text
       5
      / \
     3   9
```

這時 `balance(9)` 返回的就不再是 `9`，而是旋轉後的新根 `5`。

最後這個 `5` 會一路返回到最外層：

```java id="nzbq5s"
root = doPut(root, key, value);
```

所以根節點被更新成 `5`。

## 十四、為什麼說 AVL 的高度更新是沿著回去的路做的？

這是 AVL 遞迴插入最重要的觀念之一。

當新節點插入完成後，不是一次把整棵樹全部重新算一遍高度，而是：

> **沿著遞迴返回的路徑，一層一層往上更新。**

例如插入 `3` 時，實際順序是：

1. 建立 `3`
2. 回到 `5`，更新 `5`
3. 回到 `9`，更新 `9`
4. 如果 `9` 失衡，就在 `9` 做旋轉

也就是說，只有插入路徑上的節點可能受到影響，其他節點完全不用動。

這也是 AVL 效率高的原因之一。

## 十五、和普通 BST 新增相比，AVL 多了哪兩步？

如果你把 AVL 的新增和普通二叉搜索樹的新增做比較，會發現前面大部分都一樣。

真正多出來的，是最後這兩步：

```java
updateHeight(node);
return balance(node);
```

也就是：

### 1. 更新高度

插入後，祖先節點的高度可能改變。

### 2. 重新平衡

如果高度改變導致失衡，就要透過旋轉恢復平衡。

這就是 AVL 和普通 BST 新增操作最大的差別。