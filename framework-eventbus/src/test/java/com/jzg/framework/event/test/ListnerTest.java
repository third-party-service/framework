package com.jzg.framework.event.test;

import com.google.common.eventbus.Subscribe;
import com.jzg.framework.event.test.model.User;

public class ListnerTest {

    @Subscribe
    public void listen(String event) {
        System.out.println("string:" + event.toString());
    }


    @Subscribe
    public void listen(Integer event) throws Exception {
        System.out.println("int:" + event.toString());
        throw new Exception("");
    }

    @Subscribe
    public void listen(Long event) {
        System.out.println("long:" + event.toString());
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Subscribe
    public void getUser(User user) {
        System.out.println("user:" + user.toString());
    }




}
