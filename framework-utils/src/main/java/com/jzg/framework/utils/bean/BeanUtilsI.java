package com.jzg.framework.utils.bean;


import org.apache.http.client.utils.DateUtils;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class BeanUtilsI {
    /**
     * 返回由对象的属性为key,值为map的value的Map集合
     *
     * 注意：此方法对日期格式进行了处理，默认转化为yyyy-MM-dd HH:mm:ss的格式！！！！
     *
     * @param obj Object
     * @return mapValue Map<String,String>
     * @throws Exception
     */
    public static Map<String, String> getFields(Object obj) throws Exception {
        Map<String, String> map = org.apache.commons.beanutils.BeanUtils.describe(obj);
        if (map.containsKey("class")) {
            map.remove("class");
        }

        //日期格式进行处理
        for (Map.Entry entry : map.entrySet()) {
            Object objVal = entry.getValue();
            String val = null;
            if (entry.getValue() instanceof String) {
                val = String.valueOf(objVal);
                if (val.matches("^[A-Z][a-z]{2}\\s+[A-Z][a-z]{2}\\s+\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}\\s+CST\\s+\\d{4}$")) {
                    entry.setValue(DateUtils.formatDate(DateUtils.parseDate(val)));
                }
            } else if(objVal instanceof Date){
                entry.setValue(DateUtils.formatDate((Date)objVal));
            }
        }
        return map;
    }

    /*public static Map<String, String> getFields(Object obj) throws Exception {
        Map<String, String> map = org.apache.commons.beanutils.BeanUtils.describe(obj);
        if (map.containsKey("class")) {
            map.remove("class");
        }
        return map;
    }*/



    /**
     * 通过已有对象创建新的对象
     *
     * @param obj
     * @param clazz
     * @return
     * @throws Exception
     */
    public static Object getObject(Object obj, Class<?> clazz) throws Exception {
        Assert.notNull(obj);
        Object newOjb = clazz.newInstance();
        org.apache.commons.beanutils.BeanUtils.copyProperties(newOjb, obj);
        return newOjb;
    }

    /**
     * 返回由Map的key对属性，value对应值组成的对应
     *
     * @param fieldValues Map<String,String>
     * @param clazz       Class
     * @return obj Object
     * @throws Exception
     */
    public static Object getObject(Map<String, String> fieldValues, Class<?> clazz) throws Exception {
        /*Object obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Class<?> clsType = field.getType();
            String name = field.getName();
            if (fieldValues.containsKey(name)) {
                Object objValue = ConvertUtils.convert(fieldValues.get(name), clsType);
                field.setAccessible(true);
                field.set(obj, objValue);
            }
        }

        return obj;*/

        Assert.notNull(fieldValues);
        Object obj = clazz.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, fieldValues);
        return obj;
    }


    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {
        Assert.notNull(map);
        Assert.notNull(obj);

        try {
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
        }
    }

    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }

        return;

    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {
        Assert.notNull(obj);

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }
}