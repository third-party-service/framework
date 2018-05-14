package com.jzg.framework.cache.test.rediscache;

import com.jzg.framework.cache.CacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: Create By IDEA
 * @author: JZG
 * @date: 2017/10/26 10:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-redis.xml")
public class CacheManagerTest {

    /**
     *
     */
    @Test
    public void testManange(){
        try {
            boolean bRet = CacheManager.set("test", "1234");
            String val = CacheManager.get("test");
            System.out.println(val);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
