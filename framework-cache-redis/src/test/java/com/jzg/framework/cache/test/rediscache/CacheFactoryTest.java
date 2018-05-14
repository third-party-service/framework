package com.jzg.framework.cache.test.rediscache;

import com.jzg.framework.cache.Cache;
import com.jzg.framework.cache.CacheFactory;
import com.jzg.framework.cache.redis.DefaultRedisCacheFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-redis.xml")
public class CacheFactoryTest {
    @Test
    public void test() {
        //RedisCache cache = CacheFactory.getRedisCache();
        //RedisCache cache = (RedisCache)CacheFactory.getCache("redis");
        //Cache cache = CacheFactory.getCache();

        CacheFactory factory = new DefaultRedisCacheFactory();
        //RedisCache cache = (RedisCache)factory.getCache();
        Cache cache = factory.getCache("redisCache");
        try {
            System.out.println(cache.get("test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
