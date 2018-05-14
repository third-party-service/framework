package com.jzg.framework.log.aop;

import com.alibaba.fastjson.JSON;
import com.jzg.framework.log.Logable;
import com.jzg.framework.utils.RandomUitls;
import com.jzg.framework.utils.string.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 统一日志记录，添加业务ID
 */
@Aspect
@Component
public class LogAspect {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 默认字符串长度
     */
    private static final int BIZ_ID_LENGTH = 20;

    /**
     * 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     */
    @Pointcut("@annotation(com.jzg.framework.log.Logable)")
    public void logAspect() {
    }


    /**
     * 在方法执行之前生成业务ID
     *
     * @param joinPoint 连接点
     */
    @Before("logAspect()")
    public void before(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Object target = joinPoint.getTarget();
            Object[] args = joinPoint.getArgs();
            String className = target.getClass().getName();
            String methodName = signature.getMethod().getName();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append("args[" + i + "]: ");
                sb.append(args[i]);
                sb.append(",");
            }


            Logable logable = signature.getMethod().getAnnotation(Logable.class);
            if (logable == null) {
                logable = target.getClass().getAnnotation(Logable.class);
            }

            if (StringUtils.isEmpty(LogContext.getBizId())) {
                String bizId = logable.prefix() + RandomUitls.generateInteger(BIZ_ID_LENGTH);
                LogContext.setBizId(bizId);
            }
            logger.info("==>进入 [{}] [{}] [{}] 业务ID：{} 参数值：{} ",
                    logable.bizName(), className, methodName, LogContext.getBizId(), sb.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    /**
     * 异常记录日志
     *
     * @param ex 异常
     */
    @AfterThrowing(pointcut = "logAspect()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        logger.error(String.format("==>异常 业务ID：%s 异常：", LogContext.getBizId()), ex);
    }


    /**
     * 方法执行之后记录返回值
     *
     * @param joinPoint 连接点
     * @param obj       方法返回值
     */
    @AfterReturning(pointcut = "logAspect()", returning = "obj")
    public void afterReturning(JoinPoint joinPoint, Object obj) {
        try {

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Object target = joinPoint.getTarget();
            Object[] args = joinPoint.getArgs();
            String className = target.getClass().getName();
            String methodName = signature.getMethod().getName();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append("args[" + i + "]: ");
                sb.append(args[i]);
                sb.append(",");
            }

            Logable logable = signature.getMethod().getAnnotation(Logable.class);
            if (logable == null) {
                logable = target.getClass().getAnnotation(Logable.class);
            }

            logger.info("==>完成 [{}] [{}] [{}] 业务ID：{} 参数值：{} 返回值：{}",
                    logable.bizName(), className, methodName, LogContext.getBizId(), sb.toString(), JSON.toJSONString(obj));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

    }
}
