package com.jzg.framework.task;

import java.io.Serializable;

/**
 * 计划任务信息实体
 */
public abstract class BaseTaskor implements Runnable, Serializable {
    /**
     * 首次执行延迟时间 0s
     */
    private static final long INIT_DELAY_TIME = 0;
    /**
     * 间隔时间  60秒
     */
    private static final int DEFAULT_PERIOD_TIME = 60;

    /**
     * 计划任务名称
     */
    private String taskName;
    /**
     * 首次执行延迟时间
     */
    private long initialDelay;
    /**
     * 间隔时间
     */
    private long period;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否默认自动启动，默认自动启动 true
     */
    private Boolean isStart = true;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsStart() {
        return isStart;
    }

    public void setIsStart(Boolean isStart) {
        this.isStart = isStart;
    }

    /**
     * 计划任务
     * @param taskName  计划名称，不允许重复  默认每隔60s执行一次
     */
    public BaseTaskor(String taskName) {
        this(taskName, INIT_DELAY_TIME, DEFAULT_PERIOD_TIME);
    }

    /**
     * 计划任务
     * @param taskName  计划任务名称，不允许重复
     * @param initialDelay  首次执行延迟时间 s
     * @param period    间隔时间 s
     */
    public BaseTaskor(String taskName, long initialDelay, long period) {
        this.taskName = taskName;
        this.initialDelay = initialDelay;
        this.period = period;
    }


}
