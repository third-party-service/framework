package com.jzg.framework.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 缓存管理器
 */
public final class CacheManager {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    /**
     * 缓存工厂
     */
    private static CacheFactory cacheFactory;

    /**
     * 缓存实例
     */
    private static Cache cache;

    private CacheManager() {

    }

    /**
     * 静态代码
     */
    static {
        //加载CacheFactory
        ServiceLoader<CacheFactory> serviceLoader = ServiceLoader.load(CacheFactory.class, CacheManager.class.getClassLoader());
        Iterator<CacheFactory> cacheFactorys = serviceLoader.iterator();
        if (cacheFactorys.hasNext()) {
            cacheFactory = cacheFactorys.next();
        }
        if (cacheFactory == null) {
            logger.error("请检查CacheFactory配置是否正常，classpath下META-INF.services下com.jzg.framework.cache.CacheFactory");
        } else {

            cache = cacheFactory.getCache("");

            if (cache == null) {
                logger.error("请检查Cache配置是否正常，classpath下META-INF.services下com.jzg.framework.cache.Cache");
            }
        }

        //加载Cache
        /*ServiceLoader<Cache> cacheServiceLoader = ServiceLoader.load(Cache.class, Thread.currentThread().getClass().getClassLoader());
        Iterator<Cache> caches = cacheServiceLoader.iterator();
        if (caches.hasNext()) {
            cache = caches.next();
        }
        if (cache == null) {
            logger.error("请检查CacheFactory配置是否正常，classpath下META-INF.services下com.jzg.framework.cache.CacheFactory");
        }*/
    }


    /**
     * 删除Key对应的缓存
     *
     * @param key 键
     * @return 是否成功
     * @throws Exception 异常
     */
    public static boolean delete(String key) throws Exception {
        return cache.delete(key);
    }


    /**
     * 刷新缓存
     *
     * @param key 键
     * @param val json字符串
     * @return 是否成功刷新缓存
     * @throws Exception 异常
     */
    public static boolean refresh(String key, String val) throws Exception {
        return cache.set(key, val);
    }


    /**
     * 设置缓存
     *
     * @param key 键
     * @param val json字符串
     * @return 是否设置成功
     * @throws Exception 异常
     */
    public static boolean set(String key, String val) throws Exception {
        return cache.set(key, val);
    }


    /**
     * 设置缓存
     *
     * @param key    键
     * @param expire 缓存时间,单位秒(s)
     * @param val    字符串，对象使用json
     * @return 是否设置成功
     * @throws Exception 异常
     */
    public static boolean set(String key, int expire, String val) throws Exception {
        return cache.set(key, expire, val);
    }

    /**
     * 获取缓存字符串
     *
     * @param key 键
     * @return 缓存字符串
     * @throws Exception 异常
     */
    public static String get(String key) throws Exception {
        return cache.get(key);
    }

    /**
     * 批量获取键值
     *
     * @param keys 键的集合
     * @return 键值对MAP
     * @throws Exception 异常
     */
    public static Map<String, String> get(List<String> keys) throws Exception {
        return cache.get(keys);
    }


    /**
     * 批量获取键值
     *
     * @param keys 键的数组
     * @return 键值对MAP
     * @throws Exception 异常
     */
    public static Map<String, String> get(String[] keys) throws Exception {
        return cache.get(Arrays.asList(keys));
    }
}
