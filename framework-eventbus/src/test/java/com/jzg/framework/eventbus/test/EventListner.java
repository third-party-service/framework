package com.jzg.framework.eventbus.test;

import com.google.common.eventbus.Subscribe;
import com.jzg.framework.core.event.Event;

import java.io.Serializable;

public class EventListner {
    private String message = null;

    @Subscribe
    public void listen(Event<String> event) {
        this.message = event.getBody();
        System.out.println("Message:" + message.toString());
    }


    @Subscribe
    public void getUser(Event<User> event) {
        System.out.println("Message:" + event.getBody());
    }

    public String getMessage() {
        return message;
    }


    class User implements Serializable {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
