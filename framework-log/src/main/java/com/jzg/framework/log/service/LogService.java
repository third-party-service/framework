package com.jzg.framework.log.service;

/**
 * @description: 日志实现接口
 * @author: JZG
 * @date: 2016/12/29 13:28
 */
public interface LogService {
    /**
     * DEBUG 日志
     * @param message 信息
     */
    void debug(Object message);


    /**
     * INFO日志
     * @param message 信息
     */
    void info(Object message);


    /**
     * ERROR日志
     * @param message 信息
     */
    void error(Object message);


    /**
     * FATAL日志
     * @param message 信息
     */
    void fatal(Object message);

}
