package com.jzg.framework.log.aop;

/**
 * 保存当前线程业务ID
 */
public final class LogContext {
    /**
     * 线程本地变量
     */
    public static final ThreadLocal<String> LOG_LOCAL = new ThreadLocal<String>();

    /**
     * 私有构造函数
     */
    private LogContext(){

    }

    /**
     * 绑定到当前线程的业务ID
     *
     * @param bizId 业务ID
     */
    public static void setBizId(String bizId) {
        LOG_LOCAL.set(bizId);
    }

    /**
     * 获取当前线程的业务ID
     *
     * @return
     */
    public static String getBizId() {
        return LOG_LOCAL.get();
    }
}
