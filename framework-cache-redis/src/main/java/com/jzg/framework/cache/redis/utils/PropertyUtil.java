package com.jzg.framework.cache.redis.utils;

import com.jzg.framework.cache.redis.RedisCacheConstant;
import com.jzg.framework.cache.redis.RedisCacheProvider;
import com.jzg.framework.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by JZG on 2017/7/15.
 */
public final class PropertyUtil {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(RedisCacheProvider.class);

    /**
     * PropertiesUtil实例
     */
    private static PropertiesUtil propertiesUtil;


    private PropertyUtil() {

    }

    static {
        //try {
        propertiesUtil = new PropertiesUtil(RedisCacheConstant.REDIS_CONFIG_LOCATION_ROOT);
        //} catch (Exception e) {
        //    logger.error("从" + REDIS_CONFIG_LOCATION_ROOT + "获取redis配置文件失败", e);
        //}

        if (propertiesUtil == null || propertiesUtil.getAllProperty().size() <= 0) {
            //try {
            propertiesUtil = new PropertiesUtil(RedisCacheConstant.REDIS_CONFIG_LOCATION);
            //} catch (Exception e) {
            //    logger.error("从" + REDIS_CONFIG_LOCATION + "获取redis配置文件失败", e);
            //}
        }

        if (propertiesUtil == null || propertiesUtil.getAllProperty().size() <= 0) {
            logger.error("没有获取到Redis配置文件，请查看classPath下redis.properites或conf/redis.properties是否存在");
        }
    }

    /**
     * 获取属性值
     *
     * @param key 键
     * @return 键值
     */
    public static String getPropValue(String key) {
        return propertiesUtil.getProperty(key);
    }

    /**
     * 获取某文件的，某个key对应的值
     *
     * @param key  键
     * @param path 文件路径
     * @return 键值
     */
    public static String getPropValue(String key, String path) {
        PropertiesUtil util = new PropertiesUtil(path);
        return util.getProperty(key);
    }
}
