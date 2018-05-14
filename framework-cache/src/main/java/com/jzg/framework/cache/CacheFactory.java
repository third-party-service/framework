package com.jzg.framework.cache;

/**
 * @Description: framework
 * @Author: JZG
 * @Date: 2016/11/21 10:18
 * @param <T>
 */
public interface CacheFactory<T extends Cache> {
    /**
     * 获取缓存
     * @param cacheName 缓存名称
     * @return 缓存实例
     */
    T getCache(String cacheName);
}
