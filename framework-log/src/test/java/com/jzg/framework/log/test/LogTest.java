package com.jzg.framework.log.test;

import com.jzg.framework.log.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-cacheaop.xml")
public class LogTest {
    @Resource
    private UserService user2Service;

    @Test
    public void cache() {
        try {
            String userName = "zhangsan";
            User user = user2Service.findUserByName(userName);


            System.out.println("****************************");
            user = user2Service.findUserByName(userName);
            System.out.println(user.toString());

            //验证20s过期情况
            //Thread.sleep(20000L);


            user = user2Service.findUserByName(userName);
            System.out.println(user.toString());

            //清空缓存
            user2Service.remove(userName);

            user = user2Service.findUserByName(userName);
            System.out.println(user.toString());

            user2Service.findUserByName();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cacheNull() {
        User user = user2Service.findUserByName();
        System.out.println("****************************");
        System.out.println(user.toString());
    }

}
