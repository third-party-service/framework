package com.jzg.framework.exception;

import java.lang.annotation.*;

/**
 * 统一异常处理
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExceptionHandling {

    /**
     * 视图，如果是页面，则此处为异常显示的View路径
     * @return
     */
    String view() default "";


}

