package com.jzg.framework.eventbus;

import com.google.common.eventbus.SubscriberExceptionHandler;
import com.jzg.framework.core.event.Event;

/**
 * EventBus
 */
@Deprecated
public class EventBus {
    private com.google.common.eventbus.EventBus eventBus;

    /**
     * 事件总线
     */
    public EventBus() {
        this.eventBus = new com.google.common.eventbus.EventBus();
    }

    /**
     * 事件总线
     * @param identifier 事件总线的标识，默认default
     */
    public EventBus(String identifier) {
        this.eventBus = new com.google.common.eventbus.EventBus(identifier);
    }


    /**
     * Creates a new EventBus with the given {@link com.google.common.eventbus.SubscriberExceptionHandler}.
     *
     * @param exceptionHandler Handler for subscriber exceptions.
     * @since 16.0
     */
    public EventBus(SubscriberExceptionHandler exceptionHandler) {
        this.eventBus = new com.google.common.eventbus.EventBus(exceptionHandler);
    }


    /**
     * 接收订阅者对象作为参数并建立Event跟Subscriber的关联关系
     * @param object 订阅者Subscriber
     */
    public void register(Object object) {
        eventBus.register(object);
    }


    /**
     * 移除针对当前订阅者的所有Subscriber实例
     * @param object
     */
    public void unregister(Object object) {
        eventBus.unregister(object);
    }

    /**
     *
     * @param event
     */
    public void post(Event event) {
        eventBus.post(event);
    }


    public final String identifier() {
        return eventBus.identifier();
    }


    public String toString() {
        return eventBus.toString();
    }

}
