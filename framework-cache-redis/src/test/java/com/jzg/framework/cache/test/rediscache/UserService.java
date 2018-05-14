package com.jzg.framework.cache.test.rediscache;

import com.jzg.framework.cache.test.rediscache.model.User;

import java.util.List;

public interface UserService {


    public boolean put(User user) ;


    public void remove(String userName);


    /**
     * 缓存数据
     *
     * @param userName
     * @return
     */
    public User findUserByName(String userName);

    /**
     * 获取订单列表
     * @return
     */
    public List<User> findUserList();
}
