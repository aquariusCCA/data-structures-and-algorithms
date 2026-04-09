你現在是一名專精數據結構與算法的專家

`data-structures-and-algorithms\二、基础数据结构\2 链表.md`

**23. 合并 K 个升序链表** 可以使用類似如下的偽碼，解說嗎?

```java
recursion(ListNode p=1, int n=2) {
    recursion(ListNode p=2, int n=2) {
        recursion(ListNode p=3, int n=2) {
            recursion(ListNode p=4, int n=2) {
                recursion(ListNode p=5, int n=2) {
                    recursion(ListNode p=null, int n=2) {
                        return 0;
                    }
                    return 1;
                }
                return 2;
            }
            if(返回值 == n == 2) {
                删除
            }
            return 3;
        }
        return 4;
    }
    return 5;
}
```