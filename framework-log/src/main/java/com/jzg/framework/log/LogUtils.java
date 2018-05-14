package com.jzg.framework.log;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @description: 记录日志信息
 * @author: JZG
 * @date: 2017/1/12 16:15
 */
public class LogUtils {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LogUtils.class);

    /**
     * 私有构造函数
     */
    private LogUtils() {

    }

    /**
     * 记录debug日志
     *
     * @param msg debug信息
     */
    public static void debug(String msg) {
        logger.debug(msg);
    }

    /**
     * 记录debug日志
     *
     * @param obj 可序列化对象
     */
    public static void debug(Object obj) {
        logger.debug(JSON.toJSONString(obj));
    }

    /**
     * 记录info日志
     *
     * @param msg 日志消息
     */
    public static void info(String msg) {
        logger.info(msg);
    }

    /**
     * 记录info日志
     *
     * @param obj 日志对象
     */
    public static void info(Object obj) {
        logger.info(JSON.toJSONString(obj));
    }

    /**
     * 记录error日志
     *
     * @param msg 异常日志信息
     */
    public static void error(String msg) {
        logger.error(msg);
    }

    /**
     * 记录详细异常信息
     *
     * @param ex 异常
     */
    public static void error(Throwable ex) {
        logger.error(ex.getMessage(), ex);
    }

    /**
     * 记录详细异常信息
     *
     * @param msg 异常日志信息
     * @param ex  异常
     */
    public static void error(String msg, Throwable ex) {
        logger.error(ex.getMessage(), ex);
    }

    /**
     * 记录警告日志
     *
     * @param msg 日志信息
     */
    public static void warn(String msg) {
        logger.warn(msg);
    }

    /**
     * 记录warn日志
     *
     * @param obj 日志对象
     */
    public static void warn(Object obj) {
        logger.warn(JSON.toJSONString(obj));
    }


    /**
     * 记录fatal日志
     *
     * @param msg 日志信息
     */
    public static void fatal(String msg) {
        logger.error(msg);
    }


    /**
     * 记录fatal日志
     *
     * @param obj 日志对象
     */
    public static void fatal(Object obj) {
        logger.error(JSON.toJSONString(obj));
    }
}
