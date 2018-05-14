package com.jzg.framework.cache.test.rediscache;

import com.jzg.framework.cache.test.rediscache.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-cacheaop.xml")
public class CacheableTest {

    @Resource
    private UserService user4Service;

    @Test
    public void cache(){
        String userName = "zhangsan";
        User user = user4Service.findUserByName(userName);

        System.out.println("****************************");
        user = user4Service.findUserByName(userName);

        System.out.println(user.toString());
    }

    @Test
    public void cacheDel(){
        String userName = "zhangsan";
        User user = user4Service.findUserByName(userName);

        System.out.println("****************************");
        user4Service.remove(userName);

        user = user4Service.findUserByName(userName);
        System.out.println(user.toString());
    }

    public List<User> getUserList(User user){
        return getUserList();
    }

    private List<User> getUserList() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            User user = new User();
            user.setUserName("userName_" + i);
            user.setPassword("password_" + i);
            user.setAge(i);

            users.add(user);
        }

        return users;
    }


    @Test
    public void cacheUserList(){
        List<User> users = user4Service.findUserList();
        System.out.println("****************************");

        System.out.println(users.toString());
    }

}
