package com.jzg.framework.utils.sort;

import java.util.Comparator;

/**
 * 快速排序
 */
public class QuickSort {

    /**
     * 快速排序
     *
     * @param arr
     * @param left
     * @param right
     * @param <T>
     */
    public static <T extends Comparable> void sort(T[] arr, int left, int right) {
        if (left < right) {
            int mid = partition(arr, left, right);
            sort(arr, left, mid - 1);
            sort(arr, mid + 1, right);
        }
    }


    /**
     * 快速排序
     *
     * @param arr
     * @param left
     * @param right
     * @param comparator
     * @param <T>
     */
    public static <T> void sort(T[] arr, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int mid = partition(arr, left, right, comparator);
            sort(arr, left, mid - 1, comparator);
            sort(arr, mid + 1, right, comparator);
        }
    }

    /**
     * 快速排序
     *
     * @param arr
     * @param left
     * @param right
     */
    public static void sort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = partition(arr, left, right);
            sort(arr, left, mid - 1);
            sort(arr, mid + 1, right);
        }
    }


    public static void sort(long[] arr, int left, int right) {
        if (left < right) {
            int mid = partition(arr, left, right);
            sort(arr, left, mid - 1);
            sort(arr, mid + 1, right);
        }
    }


    private static int partition(int[] arr, int left, int right) {
        int tmp = arr[left];
        while (left < right) {
            while (left < right && arr[right] < tmp) {
                right--;
            }

            if (left < right) {
                arr[left] = arr[right];
                left++;
            }

            while (left < right && arr[left] > tmp) {
                left++;
            }

            if (left < right) {
                arr[right] = arr[left];
                right--;
            }
        }
        arr[left] = tmp;
        return left;
    }

    private static int partition(long[] arr, int left, int right) {
        long tmp = arr[left];
        while (left < right) {
            while (left < right && arr[right] < tmp) {
                right--;
            }

            if (left < right) {
                arr[left] = arr[right];
                left++;
            }

            while (left < right && arr[left] > tmp) {
                left++;
            }

            if (left < right) {
                arr[right] = arr[left];
                right--;
            }
        }
        arr[left] = tmp;
        return left;
    }

    private static <T> int partition(T[] arr, int left, int right, Comparator<T> comparator) {
        T tmp = arr[left];
        while (left < right) {
            while (left < right && comparator.compare(arr[right], tmp) > 0) {
                right--;
            }

            if (left < right) {
                arr[left] = arr[right];
                left++;
            }

            while (left < right && comparator.compare(arr[left], tmp) < 0) {
                left++;
            }

            if (left < right) {
                arr[right] = arr[left];
                right--;
            }
        }
        arr[left] = tmp;
        return left;
    }

    private static <T extends Comparable> int partition(T[] arr, int left, int right) {
        T tmp = arr[left];
        while (left < right) {
            while (left < right && arr[right].compareTo(tmp) > 0) {
                right--;
            }

            if (left < right) {
                arr[left] = arr[right];
                left++;
            }

            while (left < right && arr[left].compareTo(tmp) < 0) {
                left++;
            }

            if (left < right) {
                arr[right] = arr[left];
                right--;
            }
        }
        arr[left] = tmp;
        return left;
    }


}
