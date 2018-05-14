package com.jzg.framework.utils.test;

import com.jzg.framework.utils.bean.BeanUtils;
import com.jzg.framework.utils.test.model.User;
import org.junit.Test;

import java.util.Map;

public class BeanUtilsTest {

    @Test
    public void getFields() {
        User user = new User();


        user.setUserName("zhangsan");
        user.setPassword("123456");
        user.setAge(20);


        try {
            Map<String, String> map = BeanUtils.getProperties(user);

            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*@Test
    public void getObject() {
        Map<String, String> map = new HashMap<>();

        map.put("userName", "zhangsan");
        map.put("password", "123456");
        map.put("age", "20");


        try {
            User user = (User) BeanUtils.getObject(map, User.class);

            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
