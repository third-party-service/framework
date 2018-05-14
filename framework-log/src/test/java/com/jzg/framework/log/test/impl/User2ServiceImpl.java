package com.jzg.framework.log.test.impl;

import com.jzg.framework.log.test.UserService;
import com.jzg.framework.log.test.model.User;
import org.springframework.stereotype.Service;

@Service("user2Service")
public class User2ServiceImpl implements UserService {

    @Override
    public boolean put(User user) {
        return true;
    }

    @Override
    public void remove(String userName) {
        System.out.println(String.format("清除（{%s}）的用户缓存", userName));
    }


    /**
     * 缓存数据
     *
     * @param userName
     * @return
     */
    @Override
    public User findUserByName(String userName) {
        System.out.println("enter findUserByName");

        User user = new User();
        //user.setUserName("lisi");
        user.setUserName(userName);
        user.setPassword("123456");
        user.setAge(21);

        System.out.println("leave findUserByName");
        //return null;
        return user;
    }


    @Override
    public User findUserByNameI(String userName) {
        System.out.println("enter findUserByName");

        User user = new User();
        //user.setUserName("lisi");
        user.setUserName(userName);
        user.setPassword("123456");
        user.setAge(21);

        System.out.println("leave findUserByName");


        return user;
    }


    @Override
    public User findUserByName() {
        System.out.println("enter findUserByName");

        User user = new User();
        user.setUserName("lisi");
        user.setPassword("123456");
        user.setAge(21);

        System.out.println("leave findUserByName");
        double d = 1/0;
        return user;
    }

}
