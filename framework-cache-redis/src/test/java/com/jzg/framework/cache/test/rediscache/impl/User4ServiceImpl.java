package com.jzg.framework.cache.test.rediscache.impl;

import com.jzg.framework.cache.Cacheable;
import com.jzg.framework.cache.Cachedel;
import com.jzg.framework.cache.test.rediscache.UserService;
import com.jzg.framework.cache.test.rediscache.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("user4Service")
public class User4ServiceImpl implements UserService {

    @Override
    public boolean put(User user) {
        return true;
    }

    @Cachedel(cacheName = "redis",  key = "'User.'+ #userName")
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
    @Cacheable(cacheName = "redis", expire = 72000, key = "'User.'+ #userName")
    @Override
    public User findUserByName(String userName) {
        System.out.println("enter findUserByName");

        User user = new User();
        user.setUserName("lisi");
        user.setPassword("123456");
        user.setAge(21);

        System.out.println("leave findUserByName");
        return user;
    }

    /**
     * 获取订单列表
     *
     * @return
     */
    @Cacheable(cacheName = "redis", expire = 72000, key = "'User1'")
    @Override
    public List<User> findUserList() {
        System.out.println("enter findUserList");

        List<User> users = new ArrayList<>();

        User user = new User();
        user.setUserName("lisi");
        user.setPassword("123456");
        user.setAge(21);

        users.add(user);

        System.out.println("leave findUserList");
        return users;
        //return null;
    }


}
