package com.jzg.framework.task;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 计划任务
 */
public class Task {
    /**
     * 锁
     */
    private static final Object objLock = new Object();
    /**
     * 计划任务列表
     */
    private static final ConcurrentHashMap<String, ScheduledFuture> tasks;
    /**
     * 线程池
     */
    private static final ScheduledThreadPoolExecutor service;

    /**
     * 所有计划任务列表
     */
    private static final ConcurrentHashMap<String, BaseTaskor> taskors;


    static {
        tasks = new ConcurrentHashMap<String, ScheduledFuture>(new HashMap<String, ScheduledFuture>(1 << 18));
        taskors = new ConcurrentHashMap<String, BaseTaskor>(new HashMap<String, BaseTaskor>(1 << 18));
        service = new ScheduledThreadPoolExecutor(25);
    }

    private Task(){

    }

    /**
     * 执行计划任务  根据Taskor中参数确定  首次执行时间默认0s  间隔时间默认60s
     */
    public static void start(BaseTaskor baseTaskor) {
        synchronized (objLock) {
            if (baseTaskor == null) {
                throw new NullPointerException();
            }

            if (baseTaskor.getPeriod() <= 0)
                throw new IllegalArgumentException();


            if (baseTaskor.getTaskName() == null || baseTaskor.getTaskName().equals("")) {
                throw new IllegalArgumentException();
            }

            if (!taskors.containsKey(baseTaskor.getTaskName())) {
                taskors.put(baseTaskor.getTaskName(), baseTaskor);
            }

            if (baseTaskor.getIsStart()) {
                if (tasks.containsKey(baseTaskor.getTaskName())) {
                    System.out.println(baseTaskor.getTaskName() + " has already running.. tasks size:" + tasks.size() + " taskors size:" + taskors.size());
                    return;
                } else {
                    ScheduledFuture<?> scheduledFuture = service.scheduleAtFixedRate(baseTaskor, baseTaskor.getInitialDelay(), baseTaskor.getPeriod(), TimeUnit.SECONDS);
                    tasks.put(baseTaskor.getTaskName(), scheduledFuture);

                    System.out.println(baseTaskor.getTaskName() + " start running.. tasks size:" + tasks.size() + " taskors size:" + taskors.size());
                }
            }else {
                System.out.println(baseTaskor.getTaskName() + " isstart is false.. ");
            }
        }
    }


    /**
     * 终止执行计划任务，强制中断正在执行任务
     */
    public static void stop(String taskName) {
        stop(taskName, true);
    }


    /**
     * @param mayInterruptIfRunning 是否强制中断正在执行任务
     */
    public static void stop(String taskName, boolean mayInterruptIfRunning) {
        synchronized (objLock) {
            if (tasks.containsKey(taskName)) {
                // true表示如果定时任务在执行，立即中止，false则等待任务结束后再停止。
                ScheduledFuture<?> scheduledFuture = tasks.get(taskName);
                scheduledFuture.cancel(mayInterruptIfRunning);
                tasks.remove(taskName);
            }
        }
    }

    /**
     * 返回正在运行的计划任务名称列表
     *
     * @return
     */
    public static Set<String> getTaskNameList() {
        return tasks.keySet();
    }

    /**
     * 返回所有初始化的计划任务列表
     *
     * @return
     */
    public static ConcurrentHashMap<String, BaseTaskor> getTaskorList() {
        return taskors;
    }
}