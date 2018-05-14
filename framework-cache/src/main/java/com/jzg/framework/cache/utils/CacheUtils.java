package com.jzg.framework.cache.utils;

import com.alibaba.fastjson.JSON;
import com.jzg.framework.cache.Cache;
import com.jzg.framework.cache.CacheManager;
import com.jzg.framework.cache.local.DefaultCacheProvider;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 缓存工具类
 */
public final class CacheUtils {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    /**
     * 缓存KEY的限制长度
     */
    private static final int CACHE_KEY_LIMIT_LENGTH = 250;

    /**
     * 默认缓存NULL值时间(s)
     */
    protected static final int DEFAULT_CACHE_NULL_TIME = 6;

    private CacheUtils() {

    }

    /**
     * 获取形参名称
     *
     * @param target    target
     * @param signature signature
     * @param arguments arguments
     * @return String[]
     */
    public static String[] getNames(Object target, Signature signature, Object[] arguments) {
        Method method = getMethod(target, signature, arguments);

        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);

        return paramNames;
    }

    /**
     * 获取形参名称
     *
     * @param method 方法
     * @return String[]
     */
    public static String[] getNames(Method method) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);

        return paramNames;
    }

    /**
     * 获取Method
     *
     * @param target    target
     * @param signature signature
     * @param arguments arguments
     * @return Method
     */
    private static Method getMethod(Object target, Signature signature, Object[] arguments) {
        Class[] argTypes = new Class[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argTypes[i] = arguments[i].getClass();
        }
        Method method = null;
        try {
            method = target.getClass().getMethod(signature.getName(), argTypes);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (SecurityException e) {
            logger.error(e.getMessage(), e);
        }
        return method;

    }

    /**
     * 获取缓存，若查找不到，使用本地缓存
     *
     * @param cacheName 缓存名
     * @return Cache
     */
    @Deprecated
    public static Cache getCache(String cacheName) {
        Cache cache;
        if (com.jzg.framework.utils.string.StringUtils.isEmpty(cacheName)) {
            cache = (Cache) ApplicationContextUtils.getApplicationContext().getBean(DefaultCacheProvider.class);
        } else {
            cache = (Cache) ApplicationContextUtils.getApplicationContext().getBean(cacheName);
            if (cache == null) {
                cache = (Cache) ApplicationContextUtils.getApplicationContext().getBean(DefaultCacheProvider.class);
            }
        }
        return cache;
    }


    /**
     * 匹配简写缓存提供方，返回完整缓存名称
     *
     * @param cacheName
     * @return
     */
    /*public static String getCacheName(String cacheName) {
        switch (cacheName) {
            case "":
            case "default":
            case "defaultCache":
            case "local":
            case "localCache":
                //return "localCache";
                //return "defaultCache";
            case "guava":
            case "guavaCache":
                //return "guavaCache";
                return "defaultCache";
            case "mc":
            case "mcache":
            case "memcache":
            case "memcached":
            case "memcachedCache":
            case "memcachedCached":
                return "memcachedCache";
            case "redis":
            case "redisCache":
                return "redisCache";
            default:
                //return cacheName;
                return "defaultCache";
        }
    }*/

    /**
     * 获取Cache
     * @param cacheName
     * @return
     */
    /*public static Cache getCache(String cacheName) {
        Cache cache = null;

         *//*if (!StringUtils.isEmpty(cacheName)) {*//*
            //cacheName = getCacheName(cacheName);
            //logger.debug("get cacheName:" + cacheName);
            cache = (Cache) ApplicationContextUtils.getApplicationContext().getBean(cacheName);
        *//*} else {
            cache = (Cache) ApplicationContextUtils.getApplicationContext().getBean("defaultCache");
             logger.debug("get default cache:" + cache);
        }*//*

        return cache;
    }*/

    /**
     * 生成缓存主键（SpEL表达式）
     * 若 cacheKey 为空，主键：类名.方法名
     * 若 cacheKey 不为空，主键：cacheKey[解析具体参数]
     *
     * @param cacheKey  缓存名称
     * @param condition 条件
     * @param target    target
     * @param signature signature
     * @param arguments 参数值
     * @return 缓存主键
     */
    public static String getKey(String cacheKey, String condition, Object target, Signature signature, Object[] arguments) {
        String key = null;
        String simpleName = target.getClass().getSimpleName();
        String methodName = signature.getName();

        key = getCacheKey(cacheKey, simpleName, methodName);

        String[] paramNames = ParamNameMap.get(key);
        if (paramNames == null) {
            paramNames = CacheUtils.getNames(target, signature, arguments);
            ParamNameMap.put(key, paramNames);
        }

        key = SpelUtils.getKey(key, condition, paramNames, arguments);

        //若长度大于250，强制截断，注意！！！
        if (key.length() > CACHE_KEY_LIMIT_LENGTH) {
            key = key.substring(0, CACHE_KEY_LIMIT_LENGTH);
            logger.warn("+++cache key length over max 250!");
        }
        return key;
    }


    /**
     * 生成缓存主键（SpEL表达式）
     * 若 cacheKey 为空，主键：类名.方法名
     * 若 cacheKey 不为空，主键：cacheKey[解析具体参数]
     *
     * @param cacheKey  缓存Key
     * @param target    调用方法对象
     * @param method    方法
     * @param arguments 方法参数值
     * @return 缓存主键
     */
    public static String getKey(String cacheKey, Object target, Method method, Object[] arguments) {
        String key = null;

        key = getCacheKey(cacheKey, target.getClass().getSimpleName(), method.getName());

        String[] paramNames = ParamNameMap.get(key);
        if (paramNames == null) {
            paramNames = CacheUtils.getNames(method);
            ParamNameMap.put(key, paramNames);
        }

        key = SpelUtils.getKey(key, "", paramNames, arguments);

        //若长度大于250，强制截断，注意！！！
        if (key.length() > CACHE_KEY_LIMIT_LENGTH) {
            key = key.substring(0, CACHE_KEY_LIMIT_LENGTH);
            logger.warn("+++cache key length over max 250!");
        }
        return key;
    }


    /**
     * 生成缓存主键（SpEL表达式）
     *
     * @param cacheKey    缓存Key，不允许空
     * @param keyAndValus cacheKey中用到的"参数值"
     * @return 缓存主键
     */
    public static String getKey(String cacheKey, Map<String, Object> keyAndValus) {
        Assert.notNull(cacheKey);

        String key = null;
        String[] keys = new String[keyAndValus.keySet().size()];
        key = SpelUtils.getKey(cacheKey, "", keyAndValus.keySet().toArray(keys), keyAndValus.values().toArray());

        //若长度大于250，强制截断，注意！！！
        if (key.length() > CACHE_KEY_LIMIT_LENGTH) {
            key = key.substring(0, CACHE_KEY_LIMIT_LENGTH);
            logger.warn("+++cache key length over max 250!");
        }
        return key;
    }

    /**
     * 获取默认CacheKey
     * 若 cacheKey 为空默认生成，主键：类名.方法名
     * 若 cacheKey 不为空，主键：cacheKey[解析具体参数]
     *
     * @param cacheKey   键
     * @param simpleName 类名
     * @param methodName 方法名
     * @return 缓存主键
     */
    private static String getCacheKey(String cacheKey, String simpleName, String methodName) {
        String key;
        if (!StringUtils.isEmpty(cacheKey)) {
            key = cacheKey;
        } else {
            key = simpleName + "." + methodName;
        }
        return key;
    }


    /**
     * 获取缓存值
     *
     * @param signature signature
     * @param refresh   是否刷新
     * @param key       键
     * @param cache     缓存
     * @return 缓存对象
     * @throws Exception 异常
     */
    @Deprecated
    public static Object getCacheValue(MethodSignature signature, boolean refresh, String key, Cache cache) throws Exception {
        Object object = null;
        String str = cache.get(key);
        if (!StringUtils.isEmpty(str) && !refresh) {
            Class returnType = signature.getReturnType();
            object = JSON.parseObject(str, returnType);
        }
        return object;
    }

    /**
     * 获取缓存值
     *
     * @param signature signature
     * @param refresh   是否刷新
     * @param key       键
     * @return 缓存对象
     * @throws Exception 异常
     */
    public static Object getCacheValue(MethodSignature signature, boolean refresh, String key) throws Exception {
        Object object = null;
        String str = CacheManager.get(key);
        if (!StringUtils.isEmpty(str) && !refresh) {
            Class returnType = signature.getReturnType();
            object = JSON.parseObject(str, returnType);
        }
        return object;
    }

    /**
     * 获取缓存对象
     * @param object 要缓存对象
     * @param signature signature
     * @param expire 过期时间
     * @param key 键
     * @return 是否成功
     * @throws Exception Exception
     */
    public static boolean setCacheValue(Object object, MethodSignature signature, int expire, String key) throws Exception {
        boolean bRet;
        String json = null;
        if (object == null) {
            Class clazz = signature.getReturnType();
            object = BeanUtils.instantiateClass(clazz);
            json = JSON.toJSONString(object);
            //bRet = cache.set(key, DEFAULT_CACHE_NULL_TIME, json);
            bRet = CacheManager.set(key, DEFAULT_CACHE_NULL_TIME, json);
        } else {
            json = JSON.toJSONString(object);
            if (expire <= 0) {
                //bRet = cache.set(key, json);
                bRet = CacheManager.set(key, json);
            } else {
                //bRet = cache.set(key, expire, json);
                bRet = CacheManager.set(key, expire, json);
            }
        }

        return bRet;
    }


    /**
     * 清除缓存
     * @param key 键
     * @return 是否成功
     * @throws Exception Exception
     */
    public static boolean delCacheValue(String key) throws Exception {
        boolean bRet = false;
        //if (cache.get(key) != null) {
        if (CacheManager.get(key) != null) {
            //删除缓存
            //boolean bRet = cache.delete(key);
            bRet = CacheManager.delete(key);
        }
        return bRet;
    }
}
