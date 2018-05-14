package com.jzg.framework.cache.aop;


import com.jzg.framework.cache.Cacheable;
import com.jzg.framework.cache.Cachedel;
import com.jzg.framework.cache.utils.CacheUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 缓存切面
 */
@Aspect
@Component
public class CacheAspect {
    /**
     * 日志记录
     */
    private static Logger logger = LoggerFactory.getLogger(CacheAspect.class);


    /**
     * 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     */
    @Pointcut("@annotation(com.jzg.framework.cache.Cacheable)")
    public void aspect() {
    }

    /**
     *
     */
    @Pointcut("@annotation(com.jzg.framework.cache.Cachedel)")
    public void cacheDel() {
    }

    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint proceedingJoinPoint
     * @return Object
     * @throws Throwable 异常
     */
    @Around("aspect()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.debug("around start method...  threadId:" + Thread.currentThread().getId());
        Object object = null;
        try {

            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            Object target = proceedingJoinPoint.getTarget();
            Object[] arguments = proceedingJoinPoint.getArgs();

            String cacheKey = null;
            int expire = 0;
            boolean refresh = false;
            String cacheName = null;
            String condition = null;


            Cacheable cacheable = signature.getMethod().getAnnotation(Cacheable.class);
            if (cacheable != null) {
                expire = cacheable.expire();
                cacheKey = cacheable.key();
                refresh = cacheable.refresh();
                cacheName = cacheable.cacheName();
                condition = cacheable.condition();
            }

            //类名.方法名.key中参数值
            String key = CacheUtils.getKey(cacheKey, condition, target, signature, arguments);
            Object objLock = KeyMap.put(key);

            if (logger.isDebugEnabled()) {
                logger.debug("key:" + key);
            }

            //获取缓存
            //缓存名称暂时不使用，统一使用公用缓存
            //Cache cache = CacheUtils.getCache(cacheName);

            //有些缓存不支持直接存储对象，需要序列化，如redis
            //Object object = cacheProvider.get(key);

            //序列化为json  各种缓存都支持
            //object = CacheUtils.getCacheValue(signature, refresh, key, cache);
            object = CacheUtils.getCacheValue(signature, refresh, key);


            if (object == null || refresh) {
                boolean bRet = false;
                synchronized (objLock) {
                    logger.debug(String.format("start synchronized-%s ...", key));
                    //object = CacheUtils.getCacheValue(signature, refresh, key, cache);
                    object = CacheUtils.getCacheValue(signature, refresh, key);
                    if (object == null) {
                        //执行该方法
                        object = proceedingJoinPoint.proceed();
                        bRet = CacheUtils.setCacheValue(object, signature, expire, key);
                        if (logger.isDebugEnabled()) {
                            logger.debug("around set cache: " + bRet);
                        }
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("around synchronized get cache: true");
                        }
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("end synchronized-%s ...", key));
                    }
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("around get cache: true");
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            //失败后直接执行原操作，此时缓存无效
            object = proceedingJoinPoint.proceed();
        }
        logger.debug("around end method...");

        return object;
    }


    /**
     * 后置返回结果通知
     *
     * @param joinPoint joinPoint
     */
    @AfterReturning("cacheDel()")
    public void afterReturn(JoinPoint joinPoint) {
        logger.debug("afterReturn begin method...");
        Object object = null;
        try {

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Object target = joinPoint.getTarget();
            Object[] arguments = joinPoint.getArgs();

            String cacheKey = null;
            String cacheName = null;
            String condition = null;


            Cachedel cachedel = signature.getMethod().getAnnotation(Cachedel.class);
            if (cachedel != null) {
                cacheKey = cachedel.key();
                cacheName = cachedel.cacheName();
                condition = cachedel.condition();
            }

            //类名.方法名.key中参数值
            String key = CacheUtils.getKey(cacheKey, condition, target, signature, arguments);
            logger.debug("del key:" + key);

            //获取缓存
            //Cache cache = CacheUtils.getCache(cacheName);

            boolean bRet = CacheUtils.delCacheValue(key);
            if (bRet) {
                logger.debug(String.format("del cache key[%s] : %s", key, bRet));
                KeyMap.remove(key);
            }
            logger.debug(String.format("del cache key[%s] : %s", key, bRet));

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        logger.debug("afterReturn end method...");
    }

    /**
     * 异常通知
     *
     * @param joinPoint joinPoint
     * @param ex        ex
     */
    @AfterThrowing(pointcut = "aspect()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("method " + joinPoint.getTarget().getClass().getName()
                + "." + joinPoint.getSignature().getName() + " throw exception");
        logger.error(ex.getMessage(), ex);
    }
}
