package com.jzg.framework.log;

import java.lang.annotation.*;

/**
 * Logable
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Logable {
    /**
     * 业务ID前缀
     *
     * @return
     */
    String prefix() default "B";


    /**
     * 业务名称
     * @return
     */
    String bizName() default "";
}
