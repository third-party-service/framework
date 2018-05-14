package com.jzg.framework.exception;

import com.jzg.framework.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * Created by JZG on 2016/11/9.
 */
public class ExceptionInterceptorLog implements ThrowsAdvice {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ExceptionInterceptorLog.class);


    /**
     * 对未知异常的处理
     *
     * @param method 执行的方法
     * @param args   方法参数
     * @param target 目标对象
     * @param ex     代理的目标对象 Throwable BizException 产生的异常
     */
    public void afterThrowing(Method method, Object[] args, Object target, BizException ex) {

        /*logger.info("==>ExceptionInterceptorLog.BizException");
        logger.info("==>errCode:" + ex.getCode() + " errMsg:" + ex.getMsg());
        logger.info("==>" + ex.fillInStackTrace());*/

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append("args[" + i + "]: ");
            sb.append(args[i]);
            sb.append(",");
        }
        //logger.error("==>[{}] [{}] 参数值：{} 异常：{}", target.getClass().getName(), method.getName(), sb.toString(), ex.fillInStackTrace());
        logger.error(String.format("==>[%s] [%s] 参数值：%s 异常：", target.getClass().getName(), method.getName(), sb.toString()), ex);
    }

    /**
     * @param method 执行的方法
     * @param args   方法参数
     * @param target 目标对象
     * @param ex     异常
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {

        /*logger.error("==>ExceptionInterceptorLog.Exception");

        logger.error("==>Error class: " + target.getClass().getName());
        logger.error("==>Error method: " + method.getName());

        for (int i = 0; i < args.length; i++) {
            logger.error("==>args[" + i + "]: " + args[i]);
        }

        logger.error("==>Exception class: " + ex.getClass().getName());
        logger.error("==>" + ex.fillInStackTrace());*/

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append("args[" + i + "]: ");
            sb.append(args[i]);
            sb.append(",");
        }
        logger.error(String.format("==>[%s] [%s] 参数值：%s 异常：", target.getClass().getName(), method.getName(), sb.toString()), ex);
        //logger.error("==>[{}] [{}] 参数值：{} 异常：{}", target.getClass().getName(), method.getName(), sb.toString(), ex.fillInStackTrace());
    }
}
