package com.jzg.framework.task;

import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;

public class HandlerTest {

    @Test
    public void test() {
        final BaseTaskor baseTaskor = new BaseTaskor("test", 1, 5) {
            @Override
            public void run() {
                DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                System.out.println(dateFormat.format(new Date()) + " the task is running.");
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(dateFormat.format(new Date()) + " the task end.");
            }
        };

        Task.start(baseTaskor);

        Runnable stopRunable = new Runnable() {
            @Override
            public void run() {
                DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    Thread.sleep(10000L);
                    /*Task taskHandler = new Task("test", task);
                    taskHandler.stop(false);*/
                    Task.stop("test", false);

                    System.out.println(dateFormat.format(new Date()) + " the task stop.");

                    Thread.sleep(10000L);
                    Task.start(baseTaskor);
                    System.out.println(dateFormat.format(new Date()) + " the task restart.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(stopRunable);
        thread.start();


        try {
            Thread.sleep(1000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }







    @Test
    public void test1() {
        TaskII.start("test", new Runnable() {
            @Override
            public void run() {
                DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                System.out.println(dateFormat.format(new Date()) + " the task is running.");
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(dateFormat.format(new Date()) + " the task end.");
            }
        }, 1, 5);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    Thread.sleep(10000L);
                    TaskII.stop("test");
                    System.out.println(dateFormat.format(new Date()) + " the task stop.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        try {
            Thread.sleep(100000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
