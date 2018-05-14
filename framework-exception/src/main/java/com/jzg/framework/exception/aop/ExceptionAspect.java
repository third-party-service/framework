package com.jzg.framework.exception.aop;

import com.jzg.framework.core.exception.BizException;
import com.jzg.framework.core.vo.ResultListVo;
import com.jzg.framework.core.vo.ResultObj;
import com.jzg.framework.core.vo.ResultPageVo;
import com.jzg.framework.core.vo.ResultVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 异常统一处理（返回值按类型包装）
 */
@Aspect
@Component
public class ExceptionAspect {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    /**
     * 内部错误编码
     */
    private static final int ERROR_CODE = 500;

    /**
     * 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     */
    @Pointcut("@annotation(com.jzg.framework.exception.ExceptionHandling)")
    public void exceptionAspect() {
    }


    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint 连接点
     * @return 返回值
     * @throws Throwable 异常
     */
    @Around("exceptionAspect()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Object target = proceedingJoinPoint.getTarget();

        Object[] arguments = proceedingJoinPoint.getArgs();

        String className = target.getClass().getName();
        String methodName = signature.getMethod().getName();

        Object object = null;
        try {
            object = proceedingJoinPoint.proceed();
        } catch (BizException be) {
            write(methodName, className, arguments, be);

            Class clazz = signature.getReturnType();
            object = getRetObject(clazz, be.getCode(), be.getMessage());

        } catch (Throwable ex) {
            write(methodName, className, arguments, ex);

            Class clazz = signature.getReturnType();
            object = getRetObject(clazz, ERROR_CODE, "内部错误");
        }

        return object;
    }


    /**
     * 记录错误日志
     *
     * @param className  抛出异常目标类的类名
     * @param methodName 方法名
     * @param args       方法参数值
     * @param ex         异常
     */
    private void write(String className, String methodName, Object[] args, Throwable ex) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append("args[" + i + "]: ");
                sb.append(args[i]);
                sb.append(",");
            }
            logger.error(String.format("==>[%s] [%s] 参数值：%s", className, methodName, sb.toString()), ex);

            /*logger.error("==>Error class: " + className);
            logger.error("==>Error method: " + methodName);

            for (int i = 0; i < args.length; i++) {
                logger.error("==>args[" + i + "]: " + args[i]);
            }

            logger.error("==>Exception class: " + ex.getClass().getName());
            logger.error("==>" + ex.fillInStackTrace());*/
        } catch (Exception e) {
            logger.error("==>打印日志异常：", e);
        }

    }


    /**
     * 获取异常返回对象
     *
     * @param clazz  返回值类型
     * @param code   错误码
     * @param errMsg 错误信息
     * @return 返回值
     */
    private Object getRetObject(Class clazz, int code, String errMsg) {
        Object object = null;

        if (code <= 0) {
            code = ERROR_CODE;
        }

        if (clazz.isAssignableFrom(ResultObj.class)) {
            object = new ResultObj(code, errMsg);
        } else if (clazz.isAssignableFrom(ResultVo.class)) {
            object = new ResultVo<>(code, errMsg);
        } else if (clazz.isAssignableFrom(ResultListVo.class)) {
            object = new ResultListVo<>(code, errMsg);
        } else if (clazz.isAssignableFrom(ResultPageVo.class)) {
            object = new ResultPageVo<>(code, errMsg);
        } else {
            try {
                object = clazz.newInstance();
            } catch (InstantiationException e) {
                //e.printStackTrace();
                logger.error(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                //e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
        }

        return object;
    }
}
