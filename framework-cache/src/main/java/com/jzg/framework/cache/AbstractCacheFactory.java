package com.jzg.framework.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description: 抽象缓存工厂类
 * @author: JZG
 * @date: 2016/12/19 13:46
 *
 * @param <T> 缓存接口
 */
public abstract class AbstractCacheFactory<T extends Cache> implements CacheFactory {
    /**
     * 缓存列表
     */
    private final ConcurrentMap<String, T> caches = new ConcurrentHashMap<String, T>();

    /**
     * 获取缓存
     *
     * @param cacheName
     * @return
     */
    @Override
    public synchronized T getCache(String cacheName) {
        //T cache = (T) ApplicationContextUtils.getApplicationContext().getBean(cacheName);
        T cache = caches.get(cacheName);
        if (cache == null) {
            T t = createCache(cacheName);
            caches.put(cacheName, t);
            cache = this.caches.get(cacheName);
        }
        return cache;
    }

    /**
     * 创建Cache
     *
     * @param cacheName 缓存名称
     * @return 缓存实例
     */
    protected abstract T createCache(String cacheName);
}
