package com.jzg.framework.cache.redis.aop;

import com.jzg.framework.cache.annotation.ArgName;
import com.jzg.framework.cache.redis.utils.PropertyUtil;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by JZG on 2017/7/29.
 */


/**
 * 系统服务组件Aspect切面Bean
 *
 * @author Shenghany
 *         //@date 2013-5-28
 */
//声明这是一个组件
@Component
//声明这是一个切面Bean
@Aspect
public class RedisPrefixAspect {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(RedisPrefixAspect.class);

    /**
     * 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     */
    @Pointcut("execution(* com.jzg.framework.cache.redis.*.*(..))")
    public void redisPrefixAspect() {
    }

    /*
     * 配置前置通知,使用在方法aspect()上注册的切入点
     * 同时接受JoinPoint切入点对象,可以没有该参数
     */
    //@Before("execution(* com.jzg.framework.cache.redis..*(..))")

    /**
     * 配置前置通知,使用在方法aspect()上注册的切入点
     * 同时接受JoinPoint切入点对象,可以没有该参数
     *
     * @param joinPoint joinPoint
     */
    public void before(JoinPoint joinPoint) {
        if (logger.isInfoEnabled()) {
            logger.info("before " + joinPoint);
        }
    }

    /**
     * 添加前缀
     *
     * @param point point
     * @return Object
     * @throws Throwable Throwable
     */
    //@Around("execution(* com.jzg.framework.cache.redis.RedisCacheProvider.*(..))")
    public Object addPrefix(ProceedingJoinPoint point) throws Throwable {
        logger.debug("@Around：执行目标方法之前...");
        //访问目标方法的参数：
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            String prefix = PropertyUtil.getPropValue("redis.cache.prefix");
            Signature signature = point.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] argNames = methodSignature.getParameterNames();
            //System.out.println(Arrays.toString(argNames));
            logger.debug(Arrays.toString(argNames));
            //System.out.println(Arrays.toString(argNames));

            for (int i = 0; i < args.length; i++) {

                if (argNames[i] != null && argNames[i].equals("key")) {
                    args[i] = prefix + (String) args[i];
                } else if (argNames[i] != null && argNames[i].equals("keys")) {
                    if (args[i] instanceof List) {
                        /*for (Object argTemp : (List)args[i]) {
                            argTemp=prefix+(String)argTemp;
                        }*/
                        List argList = (List) args[i];
                        for (int j = 0; j < argList.size(); j++) {
                            argList.set(j, prefix + argList.get(j));
                        }
                    }
                /*else if(args[i] instanceof Set){
                    for (Object argTemp : (Set)args[i]) {
                        argTemp=prefix+(String)argTemp;
                    }
                }else if(args[i] instanceof Map){
                    for (Object argTemp : (Map)args[i]) {
                        argTemp=prefix+(String)argTemp;
                    }
                }*/
                }
            }
        }
        //用改变后的参数执行目标方法
        Object returnValue = point.proceed(args);
        /*System.out.println("@Around：执行目标方法之后...");
        System.out.println("@Around：被织入的目标对象为：" + point.getTarget());
        return "原返回值：" + returnValue + "，这是返回结果的后缀";*/
        return returnValue;
    }

    /**
     * @param point point
     * @return Object
     * @throws Throwable Throwable
     */
    @Around("redisPrefixAspect()")
    public Object addPrefixAnnotation(ProceedingJoinPoint point) throws Throwable {
        //System.out.println("@Around：执行目标方法之前2...");
        logger.info("@Around：执行目标方法之前2...");
        //访问目标方法的参数：
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            String classType = point.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String methodName = point.getSignature().getName(); //获取方法名称
            //获取参数名称和值
            /*Map<String,Object > nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName,args);
            System.out.println(nameAndArgs.toString());
            args[0] = "改变后的参数1";*/

            Signature signature = point.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            /*String[] strings = methodSignature.getParameterNames();
            System.out.println(Arrays.toString(strings));*/


            String prefix = PropertyUtil.getPropValue("redis.cache.prefix");
            if (prefix == null || prefix.equals("")) {
                prefix = PropertyUtil.getPropValue("redis.cache.prefix.default");
            }

            Annotation parameterAnnotations[][] = method.getParameterAnnotations();
            String[] argNames = new String[parameterAnnotations.length];
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (ArgName.class.equals(annotation.annotationType())) {
                        String argName = ((ArgName) annotation).value();
                        //System.out.print(((ArgName) annotation).value() + ' ');
                        logger.info(argName);
                        if (argName.equals("key")) {
                            argNames[i] = argName;
                            args[i] = prefix + args[i];
                        } else if (argName.equals("keys")) {
                            if (args[i] instanceof List) {
                                for (Object argTemp : (List) args[i]) {
                                    argTemp = prefix + (String) argTemp;
                                }
                            }
                            /*else if(args[i] instanceof Set){
                                for (Object argTemp : (Set)args[i]) {
                                    argTemp=prefix+(String)argTemp;
                                }
                            }else if(args[i] instanceof Map){
                                for (Object argTemp : (Map)args[i]) {
                                    argTemp=prefix+(String)argTemp;
                                }
                            }*/
                        }
                    }
                }
            }
        }
        //用改变后的参数执行目标方法
        Object returnValue = point.proceed(args);
        return returnValue;
    }

    /**
     * @param point point
     * @return Object
     * @throws Throwable Throwable
     */
    public Object process(ProceedingJoinPoint point) throws Throwable {
        //System.out.println("@Around：执行目标方法之前1...");
        logger.debug("@Around：执行目标方法之前1...");
        //访问目标方法的参数：
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            String classType = point.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String methodName = point.getSignature().getName(); //获取方法名称
            //获取参数名称和值
            //Map<String,Object > nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName,args);
            //System.out.println(nameAndArgs.toString());
            //args[0] = "改变后的参数1";

            Signature signature = point.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] strings = methodSignature.getParameterNames();
            //System.out.println(Arrays.toString(strings));
            logger.debug(Arrays.toString(strings));

            String prefix = PropertyUtil.getPropValue("redis.cache.prefix");
            addPrefixToArg(this.getClass(), clazzName, methodName, args, prefix);
            //getFieldsName( this.getClass(), clazzName, methodName,args);
        }
        //用改变后的参数执行目标方法
        Object returnValue = point.proceed(args);
        //System.out.println("@Around：执行目标方法之后...");
        //System.out.println("@Around：被织入的目标对象为：" + point.getTarget());
        //return "原返回值：" + returnValue + "，这是返回结果的后缀";
        return returnValue;
    }


    /**
     * 获取字段名称
     *
     * @param cls        cls
     * @param clazzName  clazzName
     * @param methodName methodName
     * @param args       args
     * @return Map
     * @throws NotFoundException NotFoundException
     */
    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        // String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            //paramNames即参数名
            map.put(attr.variableName(i + pos), args[i]);
        }

        //Map<>
        return map;
    }

    /**
     * @param cls        cls
     * @param clazzName  clazzName
     * @param methodName methodName
     * @param args       args
     * @param prefix     prefix
     * @throws NotFoundException NotFoundException
     */
    private void addPrefixToArg(Class cls, String clazzName, String methodName, Object[] args, String prefix) throws NotFoundException {

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            String n = attr.variableName(i + pos);
            System.out.println(attr.variableName(i + pos));
            System.out.println(attr.variableName(i));
            Object[] n1 = cm.getParameterTypes();
            /*String n1=cm.getParameterTypes()[i];
            if(attr.variableName(i + pos)!=null&&attr.variableName(i + pos).equals("key")){*/
            if (attr.variableName(i) != null && attr.variableName(i).equals("key")) {
                args[i] = prefix + (String) args[i];
                System.out.println("参数名" + prefix);
            /*}else if(attr.variableName(i + pos)!=null&&attr.variableName(i + pos).equals("keys")){*/
            } else if (attr.variableName(i) != null && attr.variableName(i).equals("keys")) {
                if (args[i] instanceof List) {
                    for (Object argTemp : (List) args[i]) {
                        argTemp = prefix + (String) argTemp;
                    }
                }
                /*else if(args[i] instanceof Set){
                    for (Object argTemp : (Set)args[i]) {
                        argTemp=prefix+(String)argTemp;
                    }
                }else if(args[i] instanceof Map){
                    for (Object argTemp : (Map)args[i]) {
                        argTemp=prefix+(String)argTemp;
                    }
                }*/
            }
        }

    }
}
