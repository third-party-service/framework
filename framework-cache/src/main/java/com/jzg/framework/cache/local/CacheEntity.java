package com.jzg.framework.cache.local;

import java.io.Serializable;

/**
 * 缓存实体
 *
 * @param <T>
 */
public class CacheEntity<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -3971709196436977492L;
    /**
     * 默认过期时间
     */
    private static final int DEFAULT_EXPIRE_TIME = 20; //默认过期时间 20秒

    /**
     * 毫秒转化为秒单位
     */
    private static final int SECOND_TIME = 1000;

    /**
     *
     */
    private String cacheKey;
    /**
     * 缓存对象泛型
     */
    private T t;
    /**
     * 有效期时长，单位：秒
     */
    private int expireTime;
    /**
     * 过期时间戳
     */
    private long timeoutStamp;

    private CacheEntity() {
        this.timeoutStamp = System.currentTimeMillis() + DEFAULT_EXPIRE_TIME * SECOND_TIME;
        this.expireTime = DEFAULT_EXPIRE_TIME;
    }

    /**
     * 构建缓存实体
     *
     * @param cacheKey
     * @param t
     */
    public CacheEntity(String cacheKey, T t) {
        this();
        this.cacheKey = cacheKey;
        this.t = t;
    }

    /**
     * 构建缓存实体
     *
     * @param cacheKey
     * @param t
     * @param timeoutStamp
     */
    public CacheEntity(String cacheKey, T t, long timeoutStamp) {
        this(cacheKey, t);
        this.timeoutStamp = timeoutStamp;
    }

    /**
     * 构建缓存实体
     *
     * @param cacheKey 键
     * @param t 值
     * @param expireTime 过期时间
     */
    public CacheEntity(String cacheKey, T t, int expireTime) {
        this(cacheKey, t);
        this.expireTime = expireTime;
        this.timeoutStamp = System.currentTimeMillis() + expireTime * SECOND_TIME;
    }

    /**
     * 获取缓存KEY
     *
     * @return 缓存KEY
     */
    public String getCacheKey() {
        return cacheKey;
    }

    /**
     * 设置缓存KEY
     *
     * @param cacheKey 键
     */
    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    /**
     * 获取缓存对象
     *
     * @return 缓存对象
     */
    public Object getT() {
        return t;
    }

    /**
     * 设置缓存对象
     *
     * @param t 缓存对象
     */
    public void setT(T t) {
        this.t = t;
    }

    /**
     * 获取过期绝对时间戳
     *
     * @return 过期绝对时间戳
     */
    public long getTimeoutStamp() {
        return timeoutStamp;
    }

    /**
     * 设置过期绝对时间戳
     *
     * @param timeoutStamp 过期绝对时间戳
     */
    public void setTimeoutStamp(long timeoutStamp) {
        this.timeoutStamp = timeoutStamp;
    }

    /**
     * 获取过期时间
     *
     * @return 过期时间
     */
    public int getExpireTime() {
        return expireTime;
    }

    /**
     * 设置过期时间 s
     *
     * @param expireTime 过期时间秒
     */
    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
