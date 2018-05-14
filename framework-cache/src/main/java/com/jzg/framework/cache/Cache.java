package com.jzg.framework.cache;

import com.jzg.framework.cache.annotation.ArgName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 缓存接口
 */
public interface Cache {
    /**
     * 通过Key获取缓存
     *
     * @param key 键
     * @param <T> 缓存对象类型
     * @return 缓存对象
     * @throws Exception 异常
     */
    <T extends Serializable> T get(@ArgName("key") String key) throws Exception;


    /**
     * 通过keys获取列表
     *
     * @param <T>  缓存对象类型
     * @param keys 多个键
     * @return 缓存对象
     * @throws Exception 异常
     */
    <T extends Serializable> Map<String, T> get(@ArgName("keys") List<String> keys) throws Exception;


    /**
     * 设置缓存 对象
     *
     * @param key 键
     * @param t   缓存对象
     * @param <T> 缓存对象类型
     * @return 缓存对象
     * @throws Exception 异常
     */
    <T extends Serializable> boolean set(@ArgName("key") String key, T t) throws Exception;


    /**
     * 设置缓存
     *
     * @param key    键
     * @param expire 缓存时间(s)
     * @param t      缓存值
     * @param <T>    缓存对象类型
     * @return 缓存对象
     * @throws Exception 异常
     */
    <T extends Serializable> boolean set(@ArgName("key") String key, int expire, T t) throws Exception;

    /**
     * 设置缓存
     *
     * @param key    键
     * @param expire 缓存时间(s)
     * @param t      缓存值
     * @param <T>    缓存对象类型
     * @return 缓存对象
     * @throws Exception 异常
     */
    <T extends Serializable> boolean set(@ArgName("key") String key, T t, int expire) throws Exception;

    /**
     * 增加数量
     *
     * @param key 键
     * @param val 值
     * @return 缓存对象
     * @throws Exception 异常
     */
    long incr(@ArgName("key") String key, long val) throws Exception;

    /**
     * 增加数量 +1
     *
     * @param key 键
     * @return 数量
     * @throws Exception 异常
     */
    long incr(@ArgName("key") String key) throws Exception;


    /**
     * 减少数量
     *
     * @param key 键
     * @param val 减少数值
     * @return
     * @throws Exception 异常
     */
    long decr(@ArgName("key") String key, long val) throws Exception;

    /**
     * @param key 键
     * @return
     * @throws Exception 异常
     */

    long decr(@ArgName("key") String key) throws Exception;

    /**
     * 移除（删除）缓存
     *
     * @param key 键
     * @throws Exception 异常
     */
    boolean delete(@ArgName("key") String key) throws Exception;

    /**
     * 批量删除键值
     *
     * @param keys 多个键值
     * @return 成功或失败
     * @throws Exception 异常
     */
    boolean delete(@ArgName("keys") String... keys) throws Exception;

    /**
     * 清空缓存
     * @throws Exception 异常
     */
    void clear() throws Exception;
}
