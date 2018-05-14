package com.jzg.framework.exception.test.impl;

import com.jzg.framework.core.exception.BizException;
import com.jzg.framework.core.vo.ResultVo;
import com.jzg.framework.core.vo.ResultObj;
import com.jzg.framework.exception.ExceptionHandling;
import com.jzg.framework.exception.test.UserService;
import com.jzg.framework.exception.test.model.User;
import org.springframework.stereotype.Service;

@Service("user2Service")
public class User2ServiceImpl implements UserService {

    /**
     * 缓存数据
     *
     * @param userName
     * @return
     */
    @ExceptionHandling
    @Override
    public ResultObj findUserByName(String userName) throws java.lang.Exception {
        System.out.println("enter findUserByName");

        ResultObj resultObj = new ResultObj();

        User user = new User();
        //user.setUserName("lisi");
        user.setUserName(userName);
        user.setPassword("123456");
        user.setAge(21);

        resultObj.setData(user);
        resultObj.setStatus(200);

        throw new BizException("自定义错误");

        //throw new java.lang.Exception("自抛出异常");


        //return retViewModel;
    }

    @ExceptionHandling
    @Override
    public ResultVo<User> findUserByNameI(String userName) throws java.lang.Exception {
        System.out.println("enter findUserByNameI ......");
        ResultVo<User> retViewModel = new ResultVo<User>();

        User user = new User();
        //user.setUserName("lisi");
        user.setUserName(userName);
        user.setPassword("123456");
        user.setAge(21);

        retViewModel.setData(user);
        retViewModel.setStatus(200);


        throw new java.lang.Exception("自抛出异常");

    }


}
