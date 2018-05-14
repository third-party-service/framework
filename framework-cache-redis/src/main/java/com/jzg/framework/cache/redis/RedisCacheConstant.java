package com.jzg.framework.cache.redis;

/**
 * @description: Create By IDEA
 * @author: JZG
 * @date: 2017/12/4 23:04
 */
public final class RedisCacheConstant {
    /**
     * 默认Redis文件地址，classPath根路径地址
     */
    public static final String REDIS_CONFIG_LOCATION = "/conf/redis.properties";

    /**
     * 默认根目录Redis文件地址，classPath根路径地址
     */
    public static final String REDIS_CONFIG_LOCATION_ROOT = "/redis.properties";


    /**
     * 默认超时时间
     */
    public static final int DEFAULT_POOL_CONNECT_TIMEOUT = 2000;

    /**
     * 默认缓存时间，单位秒 设置成一个钟
     */
    public static final int DEFAULT_CACHE_SECONDS = 60 * 60 * 1;

    /**
     * 默认缓存名称
     */
    public static final String DEFAULT_CACHE_NAME = "redisCache";

    private RedisCacheConstant() {

    }
}
