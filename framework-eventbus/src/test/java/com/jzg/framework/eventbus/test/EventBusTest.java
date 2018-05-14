package com.jzg.framework.eventbus.test;


import com.jzg.framework.core.event.Event;
import com.jzg.framework.eventbus.AsyncEventBus;
import com.jzg.framework.eventbus.EventBus;
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
        EventListner listner = new EventListner();
        eventBus.register(listner);
        EventListner2 listner2 = new EventListner2();
        eventBus.register(listner2);

        Event event = new Event<String>();
        event.setHeader("encoding", "utf-8");
        event.setBody("hello");
        eventBus.post(event);

        Event event1 = new Event();
        event1.setHeader("encoding", "utf-8");
        event1.setBody("the second message");
        eventBus.post(event1);


        Event event2 = new Event();
        event2.setHeader("encoding", "utf-8");
        event2.setBody("the third message");
        eventBus.post(event2);

        System.out.println("get last message: " + listner.getMessage());
    }

    @Test
    public void testAsync() {
        AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(3));
        EventListner listner = new EventListner();
        eventBus.register(listner);
        Event<String> event = new Event<>();
        event.setBody("async message");

        eventBus.post(event);


        Event<Integer> event1 = new Event<>();
        event1.setBody(1);

        eventBus.post(event);
        System.out.println("==");
    }
}
