package com.jzg.framework.utils.sort;

import java.util.Arrays;

/**
 * 堆排序
 * Key[i]<=key[2i+1]&&Key[i]<=key[2i+2]
 * 或者Key[i]>=Key[2i+1]&&key>=key[2i+2]
 * 即任何一非叶节点的关键字不大于或者不小于其左右孩子节点的关键字。
 * <p/>
 */
public class HeapSort {

    public static void sort(int[] arr) {
        int len = arr.length;
        //循环建堆
        for (int i = 0; i < len - 1; i++) {
            //建堆
            buildMaxHeap(arr, len - 1 - i);
            //交换堆顶和最后一个元素
            swap(arr, 0, len - 1 - i);

            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 数据交换
     *
     * @param arr
     * @param i
     * @param j
     */
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    /**
     * 构建最大堆
     *
     * @param arr
     * @param lastIndex
     */
    private static void buildMaxHeap(int[] arr, int lastIndex) {
        // 从lastIndex处节点（最后一个节点）的父节点开始
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            //k保存正在判断的节点
            int k = i;
            //如果当前k节点的子节点存在
            while (k * 2 + 1 <= lastIndex) {
                //k节点的左子节点的索引
                int biggerIndex = 2 * k + 1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if (biggerIndex < lastIndex) {
                    //若果右子节点的值较大
                    if (arr[biggerIndex] < arr[biggerIndex + 1]) {
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                if (arr[k] < arr[biggerIndex]) {
                    //交换他们
                    swap(arr, k, biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k = biggerIndex;
                } else {
                    break;
                }
            }
        }
    }


    public static void sort() {

    }


    public static void heap(int[] arr, int i, int size) {
        int lchild = 2 * i;
        int rchild = 2 * i + 1;
        int max = i;
        if (i <= size / 2) {
            if (lchild <= size && arr[lchild] > arr[max]) {
                max = lchild;
            }
        }



    }
}


