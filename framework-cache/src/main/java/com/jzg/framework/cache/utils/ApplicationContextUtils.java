package com.jzg.framework.cache.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring应用上下文环境工具类
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     * 通过传递applicationContext参数初始化成员变量applicationContext
     *
     * @param applicationContext Spring应用上下文环境
     * @throws org.springframework.beans.BeansException 异常
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (this) {
            if (ApplicationContextUtils.applicationContext == null) {
                ApplicationContextUtils.applicationContext = applicationContext;
            }
        }
    }

    /**
     * 获取Spring应用上下文环境
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
