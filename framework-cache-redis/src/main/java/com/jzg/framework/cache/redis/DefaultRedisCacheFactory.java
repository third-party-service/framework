package com.jzg.framework.cache.redis;

import com.jzg.framework.cache.AbstractCacheFactory;
import com.jzg.framework.cache.Cache;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @param <T>
 * @Description: framework
 * @Author: JZG
 * @Date: 2016/11/21 10:21
 */
public class DefaultRedisCacheFactory<T extends Cache> extends AbstractCacheFactory {
    /**
     * 创建Cache
     *
     * @param cacheName
     * @return
     */
    @Override
    protected T createCache(String cacheName) {
        RedisCache redisCache = null;
        ServiceLoader<RedisCache> serviceLoader = ServiceLoader.load(RedisCache.class, this.getClass().getClassLoader());

        Iterator<RedisCache> searchs = serviceLoader.iterator();
        if (searchs.hasNext()) {
            redisCache = searchs.next();
        }
        return (T) redisCache;

        //return (T) new RedisCacheProvider(cacheName);
    }
}
