package com.jzg.framework.cache.redis.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: Create By IDEA
 * @author: JZG
 * @date: 2017/12/4 22:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-cacheaop.xml")
public class PropertyUtilTest {
    @Test
    public void getPropValue() throws Exception {
        System.out.println(PropertyUtil.getPropValue("redis.cache.prefix.default"));
    }

}