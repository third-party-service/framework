package com.jzg.framework.cache.local;

import com.jzg.framework.cache.AbstractCacheFactory;
import com.jzg.framework.cache.Cache;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @Description: 默认本地内存缓存
 * @Author: JZG
 * @Date: 2016/11/21 10:22
 * @param <T>
 */
public class DefaultLocalCacheFactory<T extends Cache> extends AbstractCacheFactory {
    /**
     * 默认实例
     */
    private static DefaultCacheProvider defaultCacheProvider = new DefaultCacheProvider();

    /**
     * 创建Cache
     *
     * @param cacheName
     * @return
     */
    @Override
    protected T createCache(String cacheName) {
        DefaultCache defaultCache = null;
        ServiceLoader<DefaultCache> serviceLoader = ServiceLoader.load(DefaultCache.class, this.getClass().getClassLoader());

        Iterator<DefaultCache> caches = serviceLoader.iterator();
        if (caches.hasNext()) {
            defaultCache = caches.next();
        }
        return (T) defaultCache;
//        return (T) defaultCacheProvider;
    }
}
