package com.jzg.framework.cache.aop;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 键对象
 */
class KeyMap {
    private static ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();

    /**
     * 获取key对应的锁对象
     * 如果key存在，返回对应的锁对象；
     * 如果key不存在，添加锁对象，并返回
     * @param key
     * @return
     */
    public static Object put(String key) {
        Object objLock = new Object();
        Object obj = map.putIfAbsent(key, objLock);
        if (obj == null) {
            obj = objLock;
        }
        return obj;
    }

    /**
     * 获取key对应的锁对象
     * @param key
     * @return
     */
    public static Object get(String key) {
        return map.get(key);
    }

    /**
     * 移除key对应的锁
     * @param key
     */
    public static void remove(String key) {
        map.remove(key);
    }

    /**
     * 获取key是否存在
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return map.containsKey(key);
    }
}
