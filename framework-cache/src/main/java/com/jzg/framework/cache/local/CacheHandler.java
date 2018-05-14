package com.jzg.framework.cache.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 本地缓存处理类
 */
public final class CacheHandler {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CacheHandler.class);
    /**
     * HASH初始化大小
     */
    private static final int INIT_CAPACITY = 18;
    /**
     * 时间单位，毫秒转化为秒
     */
    private static final long SECOND_TIME = 1000;
    /**
     * 默认缓存时间  20秒
     */
    private static final int DEFAULT_EXPIRE_TIME = 20;
    /**
     * 计时器
     */
    private static Timer timer;
    /**
     * 缓存容器HASH
     */
    private static ConcurrentHashMap<String, CacheEntity> map;

    static {
        timer = new Timer();
        map = new ConcurrentHashMap<String, CacheEntity>(new HashMap<String, CacheEntity>(1 << INIT_CAPACITY));
        logger.info("本地缓存初始化完毕！");
    }

    private CacheHandler() {

    }

    /**
     * 增加缓存对象
     *
     * @param key 键
     * @param cacheEntity 缓存对象
     */
    public static void add(String key, CacheEntity cacheEntity) {
        add(key, cacheEntity, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 增加缓存对象
     *
     * @param key 键
     * @param cacheEntity 缓存对象
     * @param expireTime  有效时间
     */
    public static void add(String key, CacheEntity cacheEntity, int expireTime) {
        map.put(key, cacheEntity);
        //添加过期定时
        timer.schedule(new TimeoutTimerTask(key), expireTime * SECOND_TIME);
    }

    /**
     * 获取缓存对象
     *
     * @param key 键
     * @return 缓存对象
     */
    public static CacheEntity get(String key) {
        return map.get(key);
    }

    /**
     * 检查是否含有制定key的缓冲
     *
     * @param key 键
     * @return 是否包含此键
     */
    public static boolean isConcurrent(String key) {
        return map.containsKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public static void delete(String key) {
        map.remove(key);
    }

    /**
     * 获取缓存大小
     * @return 缓存大小
     */
    public static int getCacheSize() {
        return map.size();
    }

    /**
     * 清除全部缓存
     */
    public static synchronized void clear() {
        if (null != timer) {
            timer.cancel();
        }
        map.clear();
        System.out.println("clear cache");
    }


    /**
     * 清除超时缓存
     */
    static class TimeoutTimerTask extends TimerTask {
        /**
         * 键
         */
        private String cacheKey;

        /**
         * 构造器
         * @param key
         */
        TimeoutTimerTask(String key) {
            this.cacheKey = key;
        }

        @Override
        public void run() {
            CacheHandler.delete(cacheKey);
            System.out.println("delete : " + cacheKey);
        }
    }
}
