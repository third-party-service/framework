package com.jzg.framework.cache;

import java.lang.annotation.*;

/**
 * Cache缓存
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
    /**
     * 缓存名称
     * @return
     */
    String cacheName() default "";

    /**
     * 缓存时间     0：默认不过期
     * @return
     */
    int expire() default 0;


    /**
     * 主键
     * @return
     */
    String key() default "";


    /**
     * 条件
     * @return
     */
    String condition() default "";


    /**
     * 是否强制更新
     * @return
     */
    boolean refresh() default false;


}
