package com.jzg.framework.exception.test;

import com.alibaba.fastjson.JSON;
import com.jzg.framework.core.vo.ResultVo;
import com.jzg.framework.core.vo.ResultObj;
import com.jzg.framework.exception.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-aop.xml")
public class ExceptionTest {
    @Resource
    private UserService user2Service;

    @Test
    public void test() {
        try {
            String userName = "zhangsan";
            ResultObj user = user2Service.findUserByName(userName);
            System.out.println(JSON.toJSONString(user));
            System.out.println("****************************");



            ResultVo<User> user1 = user2Service.findUserByNameI(userName);
            System.out.println(JSON.toJSONString(user1));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
