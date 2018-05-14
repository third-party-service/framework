package com.jzg.framework.cache.redis.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: Create By IDEA
 * @author: JZG
 * @date: 2017/12/12 16:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-cacheaop.xml")
public class RedisUtilsTest {
    @Test
    public void get() throws Exception {
        String result = RedisUtils.get("test");
        System.out.println("*******************开始*********************");
        System.out.println(result);
        System.out.println("*******************结束*********************");
    }

    @Test
    public void get1() throws Exception {

    }

    @Test
    public void set() throws Exception {

    }

    @Test
    public void set1() throws Exception {

    }

    @Test
    public void set2() throws Exception {

    }

    @Test
    public void incr() throws Exception {
        long nRet = RedisUtils.incr("count");
        System.out.println("*******************开始*********************");
        System.out.println(nRet);
        nRet = RedisUtils.incr("count");
        System.out.println(nRet);
        nRet = RedisUtils.incr("count", 2);
        System.out.println(nRet);
        System.out.println("*******************结束*********************");
    }

    @Test
    public void incr1() throws Exception {

    }

    @Test
    public void decr() throws Exception {
        incr();
        long nRet = RedisUtils.decr("count");
        System.out.println("*******************开始*********************");
        System.out.println(nRet);
        nRet = RedisUtils.decr("count");
        System.out.println(nRet);
        nRet = RedisUtils.decr("count", 2);
        System.out.println(nRet);
        System.out.println("*******************结束*********************");
    }

    @Test
    public void decr1() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void delete1() throws Exception {

    }

    @Test
    public void touch() throws Exception {

    }

    @Test
    public void getAndTouch() throws Exception {

    }

    @Test
    public void setObject() throws Exception {

    }

    @Test
    public void getObject() throws Exception {

    }

    @Test
    public void getSet() throws Exception {

    }

    @Test
    public void setnx() throws Exception {

    }

    @Test
    public void mset() throws Exception {

    }

    @Test
    public void msetnx() throws Exception {

    }

    @Test
    public void keys() throws Exception {

    }

    @Test
    public void hmset() throws Exception {

    }

    @Test
    public void hset() throws Exception {

    }

    @Test
    public void hmget() throws Exception {

    }

    @Test
    public void hget() throws Exception {

    }

    @Test
    public void hlen() throws Exception {

    }

    @Test
    public void hexists() throws Exception {

    }

    @Test
    public void hkeys() throws Exception {

    }

    @Test
    public void hvals() throws Exception {

    }

    @Test
    public void hdel() throws Exception {

    }

    @Test
    public void lpush() throws Exception {

    }

    @Test
    public void rpush() throws Exception {

    }

    @Test
    public void lset() throws Exception {

    }

    @Test
    public void lrange() throws Exception {

    }

    @Test
    public void lindex() throws Exception {

    }

    @Test
    public void lpop() throws Exception {

    }

    @Test
    public void rpop() throws Exception {

    }

    @Test
    public void lrem() throws Exception {

    }

    @Test
    public void ltrim() throws Exception {

    }

    @Test
    public void sadd() throws Exception {

    }

    @Test
    public void smembers() throws Exception {

    }

    @Test
    public void scard() throws Exception {

    }

    @Test
    public void spop() throws Exception {

    }

    @Test
    public void spop1() throws Exception {

    }

    @Test
    public void srem() throws Exception {

    }

    @Test
    public void zadd() throws Exception {

    }

    @Test
    public void zadd1() throws Exception {

    }

    @Test
    public void zrange() throws Exception {

    }

    @Test
    public void zcard() throws Exception {

    }

    @Test
    public void zrem() throws Exception {

    }

    @Test
    public void exprie() throws Exception {

    }

    @Test
    public void ttl() throws Exception {

    }

    @Test
    public void persist() throws Exception {

    }

}