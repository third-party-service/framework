package com.jzg.framework.cache.test.impl;


import com.jzg.framework.cache.test.UserService;
import com.jzg.framework.cache.test.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @CachePut(value = "userCache", key = "targetClass + '.' + methodName + '.' + #user.userName")
    @Override
    public boolean put(User user) {
        return true;
    }


    @CacheEvict(value = "userCache", key = "targetClass + '.' + methodName + '.' + #userName")
    @Override
    public void remove(String userName) {
        System.out.println(String.format("清除（{}）的用户缓存", userName));
    }


    /**
     * 缓存数据【使用的Spring缓存】
     *
     * @param userName
     * @return
     */
    @Cacheable(value = "userCache", key = "targetClass + '.' + methodName + '.' + #userName")
    @Override
    public User findUserByName(String userName) {
        System.out.println("enter findUserByName");

        User user = new User();
        user.setUserName(userName);
        user.setPassword("123456");
        user.setAge(21);

        System.out.println("leave findUserByName");
        return user;
    }

    /**
     * 缓存数据I
     *
     * @param userName
     * @return
     */
    @Override
    public User findUserByNameI(String userName) {
        System.out.println("enter findUserByName");

        User user = new User();
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
        //return null;
        return user;
    }


}
