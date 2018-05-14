package com.jzg.framework.eventbus.test;

import com.google.common.eventbus.Subscribe;
import com.jzg.framework.core.event.Event;

public class EventListner2 {
    private Integer message = null;

    @Subscribe
    public void listen(Event<Integer> event) {
        this.message = event.getBody();
        System.out.println("Message:" + message.toString());
    }

    public Integer getMessage() {
        return message;
    }
}
