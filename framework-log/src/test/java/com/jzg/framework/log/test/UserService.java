package com.jzg.framework.log.test;


import com.jzg.framework.log.test.model.User;

public interface UserService {


    public boolean put(User user) ;



    /**
     * 测试删除缓存
     * @param userName
     */
    public void remove(String userName);


    /**
     * 缓存数据
     *
     * @param userName
     * @return
     */
    public User findUserByName(String userName);


    /**
     * 缓存数据I
     *
     * @param userName
     * @return
     */
    public User findUserByNameI(String userName);


    public User findUserByName();


}
