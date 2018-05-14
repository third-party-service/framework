package com.jzg.framework.exception.test;


import com.jzg.framework.core.vo.ResultVo;
import com.jzg.framework.core.vo.ResultObj;
import com.jzg.framework.exception.test.model.User;

public interface UserService {



    /**
     * 缓存数据
     *
     * @param userName
     * @return
     */
    public ResultObj findUserByName(String userName) throws Exception;

    public ResultVo<User> findUserByNameI(String userName) throws Exception;

}
