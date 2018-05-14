package com.jzg.framework.cache.test;

import com.jzg.framework.cache.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-localcache.xml")
public class LocalCacheTest {

    @Resource(name = "defaultCache")
    private Cache defaultCache;

    @Resource
    private UserService userService;

    @Test
    public void test() {
        try {
            boolean bSet = this.defaultCache.set("test", "hello");
            System.out.println(String.format("set cache: %s", bSet));

            String str = this.defaultCache.get("test");
            System.out.println(String.format("get cache: %s", str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test1() {
        /*try {
            boolean bSet = this.guavaCache.set("test", "hello");
            System.out.println(String.format("set guavacache: %s", bSet));

            String str = this.guavaCache.get("test");
            System.out.println(String.format("get guavacache: %s", str));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    @Test
    public void test2() {
        try {
            userService.findUserByName("zhangsan");
            System.out.println(String.format("findUserByName: %s", 1));
            userService.findUserByName("zhangsan");
            System.out.println(String.format("findUserByName: %s", 2));
            userService.findUserByName("zhangsan");
            System.out.println(String.format("findUserByName: %s", 3));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
