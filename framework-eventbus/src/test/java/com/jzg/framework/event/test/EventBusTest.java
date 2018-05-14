package com.jzg.framework.event.test;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.jzg.framework.event.test.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;

public class EventBusTest {
    @Before
    public void before() {

    }

    @Test
    public void test() {
        EventBus eventBus = new EventBus();
        ListnerTest listner = new ListnerTest();
        eventBus.register(listner);


        eventBus.post("hello");
        eventBus.post(1000000000000L);
        eventBus.post(1);
        eventBus.post(new User("xxxx"));
    }

    @Test
    public void testAsync() {
        AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(3));
        ListnerTest listner = new ListnerTest();
        eventBus.register(listner);

        eventBus.post("hello");
        eventBus.post(1);
        eventBus.post(new User("xxxx"));
        eventBus.post(1000000000000L);
    }
}
