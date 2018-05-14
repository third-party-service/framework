package com.jzg.framework.task;

import java.util.HashMap;
import java.util.concurrent.*;

/**
 * 计划任务，每个任务一个线程，停止时关闭线程。暂时不用
 */

public class TaskII {
    /**
     * 首次执行延迟时间 0s
     */
    private static final long INIT_DELAY_TIME = 0;
    /**
     * 间隔时间  60秒
     */
    private static final int DEFAULT_PERIOD_TIME = 60;


    private static final ConcurrentHashMap<String, ScheduledExecutorService> tasks;

    static {
        tasks = new ConcurrentHashMap<String, ScheduledExecutorService>(new HashMap<String, ScheduledExecutorService>(1<<18));
    }

    /**
     * 执行计划任务 首次执行时间默认0s  间隔时间默认60s
     *
     * @param taskName 计划任务名称
     * @param runnable
     */
    public static void start(String taskName, Runnable runnable) {
        start(taskName, runnable, INIT_DELAY_TIME, DEFAULT_PERIOD_TIME);
    }

    /**
     * 执行计划任务
     *
     * @param taskName 计划任务名称
     * @param runnable
     * @param period   间隔时间 s
     */
    public static void start(String taskName, Runnable runnable, long period) {
        start(taskName, runnable, INIT_DELAY_TIME, period);
    }


    /**
     * 执行计划任务
     *
     * @param runnable
     * @param taskName     计划任务名称
     * @param initialDelay 首次执行延迟时间 s
     * @param period       间隔时间 s
     */
    public static void start(String taskName, Runnable runnable, long initialDelay, long period) {
        if (period <= 0 || initialDelay <= 0)
            throw new IllegalArgumentException();

        ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(1);
        service.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);

        tasks.put(taskName, service);
    }


    /**
     * 停止计划任务
     * @param taskName
     */
    public static void stop(String taskName){
        if (tasks.containsKey(taskName)) {
            ScheduledThreadPoolExecutor service = (ScheduledThreadPoolExecutor)tasks.get(taskName);
            service.shutdown();
            tasks.remove(taskName);
        }
    }
}