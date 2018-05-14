package com.jzg.framework.log;

import org.aopalliance.intercept.Joinpoint;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * 日志执行时间记录
 */
public class LogTimeInterceptor implements MethodInterceptor {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LogTimeInterceptor.class);

    /**
     * Implement this method to perform extra treatments before and
     * after the invocation. Polite implementations would certainly
     * like to invoke {@link Joinpoint#proceed()}.
     *
     * @param invocation the method invocation joinpoint
     * @return the result of the call to {@link
     * Joinpoint#proceed()}, might be intercepted by the
     * interceptor.
     * @throws Throwable if the interceptors or the
     *                   target-object throws an exception.
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();


        //Object[] arguments = invocation.getArguments();

        String simpleName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        String functionName = simpleName + "." + methodName;

        Object object = invocation.proceed();

        stopWatch.stop();
        //logger.info(String.format("%s running time is %s ms. args : %s     result:%s", functionName, stopWatch.getTotalTimeMillis(), arguments == null ? "" : JSON.toJSONString(arguments), JSON.toJSONString(object)));
        logger.debug(String.format("%s running time is %s ms.", functionName, stopWatch.getTotalTimeMillis()));
        return object;
    }
}
