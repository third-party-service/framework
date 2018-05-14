package com.jzg.framework.cache.test.rediscache;

import com.jzg.framework.cache.redis.RedisCache;
import com.jzg.framework.cache.test.rediscache.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-redis.xml")
public class RedisTest {

    @Resource
    private RedisCache redisCache;

    @Test
    public void get() {
        try {
            String str = this.redisCache.get("test");
            System.out.println(String.format("get cache: %s", str));

            List keys = new ArrayList();
            keys.add("test");
            keys.add("test1");
            Map keyMap = this.redisCache.get(keys);
            System.out.println(keyMap);

            boolean bRet = this.redisCache.setObjectList("users", getUserList());
            System.out.println("设置缓存List:" + bRet);
            List<User> users = this.redisCache.getObjectList("users");
            System.out.println(String.format("getObjectList cache: %s", users.toString()));

            User user = this.redisCache.getObject("user_0");
            System.out.println(String.format("setObject cache: %s", user == null ? "" : user.toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<User> getUserList() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            User user = new User();
            user.setUserName("userName_" + i);
            user.setPassword("password_" + i);
            user.setAge(i);

            users.add(user);
        }

        return users;
    }


    @Test
    public void set() {
        try {
            boolean bSet = this.redisCache.set("test", "hello");
            System.out.println(String.format("set cache: %s", bSet));


            List<User> users = getUserList();
            bSet = this.redisCache.setObjectList("users", users);
            System.out.println(String.format("setObjectList cache: %s", bSet));

            bSet = this.redisCache.setObject("user_0", users.get(0));
            System.out.println(String.format("setObject cache: %s", bSet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void touch() {
        try {
            boolean bSet = this.redisCache.touch("test", 12000);
            System.out.println(String.format("touch cache: %s", bSet));


            String str = this.redisCache.getAndTouch("test", 12000);
            System.out.println(String.format("getAndTouch cache: %s", str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void incr() {
        try {
            long nRet = this.redisCache.incr("num", 1);
            System.out.println(String.format("incr num cache: %s", nRet));

            nRet = this.redisCache.incr("num", 1);
            System.out.println(String.format("incr num cache: %s", nRet));

            nRet = this.redisCache.incr("num", 1);
            System.out.println(String.format("incr num cache: %s", nRet));

            nRet = this.redisCache.decr("num", 1);
            System.out.println(String.format("decr num cache: %s", nRet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void del() {
        try {
            this.redisCache.delete("test");
            System.out.println(String.format("delete cache: %s", "ok"));

            get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * list 相关操作
     */
    @Test
    public void list() {
        List<String> list = new ArrayList<String>();

        list.add("zhangsan");
        list.add("lisi");
        list.add("wangwu");

        try {


            //设置列表
            boolean bRet = this.redisCache.lpush("test_list", list.toArray(new String[list.size()]));
            //boolean bRet = this.redisCache.lpush("test_list", "zhangsan", "lisi", "wangwu");
            System.out.println(String.format("lpush cache: %s", bRet));

            //获取列表
            List<String> strs = this.redisCache.lrange("test_list", 0, 1);
            System.out.println(String.format("lrange cache: %s", strs.toString()));

            //获取全部列表
            strs = this.redisCache.lrange("test_list", 0, -1);
            System.out.println(String.format("lrange cache: %s", strs.toString()));

            //通过索引设置单个元素值
            bRet = this.redisCache.lset("test_list", 1, "lisi_1");
            strs = this.redisCache.lrange("test_list", 0, -1);
            System.out.println(String.format("lset cache: %s", strs.toString()));

            //从列表移除头部值
            String popStr = this.redisCache.lpop("test_list");
            System.out.println(String.format("lpop cache: %s", popStr));
            strs = this.redisCache.lrange("test_list", 0, -1);
            System.out.println(String.format("after lpop cache: %s", strs.toString()));

            popStr = this.redisCache.rpop("test_list");
            System.out.println(String.format("rpop cache: %s", popStr));
            strs = this.redisCache.lrange("test_list", 0, -1);
            System.out.println(String.format("after rpop cache: %s", strs.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hash相关操作
     */
    @Test
    public void hash() {

        try {
            Map<String, String> map = new HashMap<>();
            map.put("username", "zhangsan");
            map.put("password", "132");
            map.put("age", "1");

            this.redisCache.delete("test_hash");
            //设置列表
            boolean bRet = this.redisCache.hmset("test_hash", map);
            System.out.println(String.format("hmset cache: %s", bRet));

            //设置单个值
            bRet = this.redisCache.hset("test_hash", "birth", "1900-01-01");
            System.out.println(String.format("hmset cache: %s", bRet));

            //获取单个或多个field值(未查到值返回null 例如wangwu)
            List<String> strs = this.redisCache.hmget("test_hash", "username", "password", "age", "wangwu");
            System.out.println(String.format("hmget cache: %s", strs.toString()));

            //获取field列表
            Set<String> keys = this.redisCache.hkeys("test_hash");
            System.out.println(String.format("hkeys cache: %s", keys.toString()));

            //获取value列表
            List<String> vals = this.redisCache.hvals("test_hash");
            System.out.println(String.format("hkeys cache: %s", vals.toString()));

            //通过索引设置单个元素值
            bRet = this.redisCache.hset("test_hash", "age", "50");
            strs = this.redisCache.hmget("test_hash", "username", "password", "age");
            System.out.println(String.format("lset cache: %s", strs.toString()));

            //从列表移除头部值
            bRet = this.redisCache.hdel("test_hash", "wangwu");
            System.out.println(String.format("hdel cache: %s", bRet));
            strs = this.redisCache.hmget("test_hash", "username", "password", "age");
            System.out.println(String.format("after hdel cache: %s", strs.toString()));

            long nRet = this.redisCache.hlen("test_hash");
            System.out.println(String.format("hlen cache: %s", nRet));
            strs = this.redisCache.hmget("test_hash", "username", "password", "age");
            System.out.println(String.format("after hlen cache: %s", strs.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
