package com.jzg.framework.cache.test;

import com.jzg.framework.cache.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-cacheaop.xml")
public class CacheableTest {

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

    public List<User> getUserList(User user) {
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
    public void cacheMulti() {
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Random random = new Random((long) (Math.random() * 1000000000));
                    //String userName = "userName" + String.valueOf(random.nextLong());
                    String userName = "zhangsan";
                    User user = user2Service.findUserByName(userName);


                    System.out.println("****************************");
                    System.out.println(user.toString());
                    System.out.println();
                }
            };

            Runnable runnable1 = new Runnable() {
                @Override
                public void run() {
                    Random random = new Random((long) (Math.random() * 1000000000));
                    String userName = "userName" + String.valueOf(random.nextLong());
                    User user = user2Service.findUserByNameI(userName);

                    System.out.println("****************************");
                    System.out.println(user.getUserName());
                }
            };

            for (int i = 0; i < 10; i++) {
                Thread t = new Thread(runnable);
                t.start();
            }


            for (int i = 0; i < 10; i++) {
                Thread t = new Thread(runnable1);
                t.start();
            }


            Thread.sleep(10000);
            System.out.println("sleep end...");

            for (int i = 0; i < 10; i++) {
                Thread t = new Thread(runnable);
                t.start();
            }

            Thread.sleep(10000);
            System.out.println("sleep end...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
