package com.jzg.framework.utils.test;

import com.jzg.framework.utils.sort.HeapSort;
import com.jzg.framework.utils.sort.QuickSort;
import org.junit.Test;

import java.util.Arrays;

public class QuickSortTest {

    @Test
    public void test() {
        System.out.println("start int sort...");
        int[] arr = {10, 78, 54, 26, 32, 49, 85, 3, 43};
        QuickSort.sort(arr, 0, 8);
        System.out.print(Arrays.toString(arr));


        System.out.println("");
        System.out.println("start string sort...");
        String[] arrStrs = {"10", "78", "54", "26", "32", "49", "85", "3", "43"};
        QuickSort.sort(arrStrs, 0, 8);
        System.out.print(Arrays.toString(arrStrs));
    }


    @Test
    public void testI() {
        System.out.println("start sort...");
        Integer[] arr = {10, 78, 54, 26, 32, 49, 85, 3, 43};
        QuickSort.sort(arr, 0, 8);
        System.out.print(Arrays.toString(arr));

        System.out.println("");
        System.out.println("start sort...");

        System.out.println("start int sort...");
        Long[] arr1 = {10L, 78L, 54L, 26L, 32L, 49L, 85L, 3L, 43L};
        QuickSort.sort(arr1, 0, 8);
        System.out.print(Arrays.toString(arr1));
    }

    @Test
    public void testHeap(){
        System.out.println("start sort...");
        int[] arr = {10, 78, 54, 26, 32, 49, 85, 3, 43};
        HeapSort.sort(arr);
        System.out.println("end sort...");
        System.out.print(Arrays.toString(arr));
    }


    @Test
    public void testab(){
        int a = 10, b = 5;
        System.out.printf("a:%s  b:%s", a ,b);
        a = a + b;
        b = a - b;
        a = a - b;
        System.out.println();
        System.out.printf("a:%s  b:%s", a, b);
    }
}
