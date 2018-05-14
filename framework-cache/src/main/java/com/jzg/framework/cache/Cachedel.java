package com.jzg.framework.cache;

import java.lang.annotation.*;

/**
 * Cache删除
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cachedel {
    /**
     * 主键
     *
     * @return
     */
    String key() default "";


    /**
     * 条件
     *
     * @return
     */
    String condition() default "";

    /**
     * 缓存名称
     *
     * @return
     */
    String cacheName() default "";
}
