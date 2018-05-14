package com.jzg.framework.cache.test.impl;

import com.jzg.framework.cache.Cacheable;
import com.jzg.framework.cache.Cachedel;
import com.jzg.framework.cache.test.UserService;
import com.jzg.framework.cache.test.model.User;
import org.springframework.stereotype.Service;

@Service("user2Service")
public class User2ServiceImpl implements UserService {

    @Override
    public boolean put(User user) {
        return true;
    }

    @Cachedel(key = "'User.'+ #userName")
    @Override
    public void remove(String userName) {
        System.out.println(String.format("清除（{%s}）的用户缓存", userName));
    }


    /**
     * 缓存数据
     *
     * @param userName 用户名
     * @return
     */
    @Cacheable(key = "'User.'+ #userName", expire = 10)
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



    /**
     * 缓存数据I
     *
     * @param userName
     * @return
     */
    @Cacheable(expire = 72000, key = "'UserI.'+ #userName")
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



    @Cacheable(key="'findUserByName'")
    @Override
    public User findUserByName() {
        System.out.println("enter findUserByName");

        User user = new User();
        user.setUserName("lisi");
        user.setPassword("123456");
        user.setAge(21);

        System.out.println("leave findUserByName");
        //return null;
        return user;
    }

}
