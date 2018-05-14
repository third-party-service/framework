package com.jzg.framework.cache.local;


import com.jzg.framework.cache.annotation.ArgName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认本地缓存
 */
public class DefaultCacheProvider implements DefaultCache {
    /**
     * 通过Key获取缓存
     *
     * @param key
     * @return
     */
    @Override
    public <T extends Serializable> T get(String key) throws Exception {
        CacheEntity cacheEntity = CacheHandler.get(key);

        if (cacheEntity != null) {
            return (T) cacheEntity.getT();
        } else {
            return null;
        }
    }

    /**
     * 通过keys获取列表
     *
     * @param keys
     * @return
     */
    @Override
    public <T extends Serializable> Map<String, T> get(List<String> keys) throws Exception {
        Map<String, T> map = new HashMap<>();
        for (String key : keys) {
            T t = get(key);
            map.put(key, t);
        }
        return map;
    }

    /**
     * 设置缓存 对象
     *
     * @param key
     * @param t
     */
    @Override
    public <T extends Serializable> boolean set(String key, T t) throws Exception {
        CacheEntity cacheEntity = new CacheEntity(key, t);
        CacheHandler.add(key, cacheEntity);

        return true;
    }

    /**
     * 设置缓存
     *
     * @param key    键
     * @param expire 缓存时间(s)
     * @param t      缓存值
     * @return
     * @throws Exception
     */
    @Override
    public <T extends Serializable> boolean set(String key, int expire, T t) throws Exception {
        CacheEntity cacheEntity = new CacheEntity(key, t, expire);
        CacheHandler.add(key, cacheEntity, expire);

        return true;
    }

    /**
     * 设置缓存
     *
     * @param key    键
     * @param t      缓存值
     * @param expire 缓存时间(s)
     * @return 缓存对象
     * @throws Exception 异常
     */
    @Override
    public <T extends Serializable> boolean set(@ArgName("key") String key, T t, int expire) throws Exception {
        return set(key, expire, t);
    }

    /**
     * 增加数量
     *
     * @param key
     * @param val
     * @return
     * @throws Exception
     */
    @Override
    public long incr(String key, long val) throws Exception {
        throw new Exception("尚未实现");
    }

    /**
     * 增加数量 +1
     *
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public long incr(String key) throws Exception {
        throw new Exception("尚未实现");
    }

    /**
     * 减少数量
     *
     * @param key
     * @param val
     * @return
     * @throws Exception
     */
    @Override
    public long decr(String key, long val) throws Exception {
        throw new Exception("尚未实现");
    }

    /**
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public long decr(String key) throws Exception {
        throw new Exception("尚未实现");
    }

    /**
     * 移除（删除）缓存
     *
     * @param key
     */
    @Override
    public boolean delete(String key) throws Exception {
        CacheHandler.delete(key);
        return true;
    }

    /**
     * 批量删除键值
     *
     * @param keys 多个键值
     * @return 成功或失败
     * @throws Exception 异常
     */
    @Override
    public boolean delete(@ArgName("keys") String... keys) throws Exception {
        for (String key : keys) {
            CacheHandler.delete(key);
        }
        return true;
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() throws Exception {
        CacheHandler.clear();
    }
}
