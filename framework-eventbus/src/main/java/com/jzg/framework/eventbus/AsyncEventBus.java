package com.jzg.framework.eventbus;

import com.google.common.eventbus.SubscriberExceptionHandler;

import java.util.concurrent.Executor;

/**
 * AsyncEventBus
 */
@Deprecated
public class AsyncEventBus extends EventBus {
    private com.google.common.eventbus.AsyncEventBus asyncEventBus;

    public AsyncEventBus(String identifier, Executor executor) {
        this.asyncEventBus = new com.google.common.eventbus.AsyncEventBus(identifier, executor);
    }

    public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {

        this.asyncEventBus = new com.google.common.eventbus.AsyncEventBus(executor, subscriberExceptionHandler);
    }

    public AsyncEventBus(Executor executor) {
        this.asyncEventBus = new com.google.common.eventbus.AsyncEventBus(executor);

    }
}
