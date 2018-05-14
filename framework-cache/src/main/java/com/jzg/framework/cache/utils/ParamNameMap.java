package com.jzg.framework.cache.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 参数名HASH
 */
public final class ParamNameMap {
    private ParamNameMap(){

    }
    /**
     * 初始化HASH
     */
    private static ConcurrentHashMap<String, String[]> map = new ConcurrentHashMap<String, String[]>();

    /**
     *
     * @param key 键
     * @param paramNames 参数数组
     */
    public static void put(String key, String[] paramNames) {
        map.putIfAbsent(key, paramNames);
    }

    /**
     * 获取参数列表
     * @param key 键
     * @return
     */
    public static String[] get(String key) {
        return map.get(key);
    }
}
