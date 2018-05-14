package com.jzg.framework.notify;

import com.google.common.eventbus.AsyncEventBus;

import java.util.concurrent.Executors;

/**
 * @description: 异步发送邮件
 * @author: JZG
 * @date: 2016/12/30 12:15
 */
public final class AsyncEmail {
    /**
     * 初始化线程池数量
     */
    public static final int DEFAULT_INIT_THREAD_COUNT = 10;
    /**
     * 异步消息总线
     */
    private static AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(DEFAULT_INIT_THREAD_COUNT));

    private AsyncEmail() {
    }

    static {
        EmailListener listener = new EmailListener();
        eventBus.register(listener);
    }

    /**
     * 异步发送邮件
     *
     * @param emailInfo 邮件信息
     */
    public static void sendMail(EmailInfo emailInfo) {
        eventBus.post(emailInfo);
    }
}
