package com.jzg.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * PropertiesUtil.java
 *
 * @author Guoxp
 * @desc properties 资源文件解析工具
 * @datatime Apr 7, 2013 3:58:45 PM
 */
public class PropertiesUtil {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private Properties props;

    public PropertiesUtil(String fileName) {
        readProperties(fileName);
    }

    private void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStream fis = getClass().getResourceAsStream(fileName);
            if (fis != null) {
                props.load(fis);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 获取某个属性
     */
    public String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * 获取所有属性，返回一个map,不常用
     * 可以试试props.putAll(t)
     */
    public Map getAllProperty() {
        Map<String, String> map = new HashMap();
        Enumeration enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 在控制台上打印出所有属性，调试时用。
     */
    public void printProperties() {
        props.list(System.out);
    }


}