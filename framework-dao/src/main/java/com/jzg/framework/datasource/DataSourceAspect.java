package com.jzg.framework.datasource;

import com.jzg.framework.datasource.utils.PropertiesUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行dao方法前的切面
 *
 * @description: 获取datasource对象之前往HandleDataSource中指定当前线程数据源路由的key
 * @author: JZG
 * @date: 2016/11/24 9:39
 */
public class DataSourceAspect {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * 在dao层方法之前获取datasource对象之前在切面中指定当前线程数据源路由的key
     *
     * @param point 连接点
     */
    public void before(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Object target = point.getTarget();

        try {
            DataSource dataSource = signature.getMethod().getAnnotation(DataSource.class);
            if (dataSource == null) {
                dataSource = target.getClass().getAnnotation(DataSource.class);
            }
            if (dataSource != null) {
                DataSourceContext.putDataSource(dataSource.value());
            } else {
                //默认的数据库源Key
                String defaultDataSourceKey = PropertiesUtils.getProperty("defaultDataSourceKey"); //DataSourcePropertiesUtil.getValue("defaultDataSourceKey");
                //当前线程的数据源key
                String currentDataSource = DataSourceContext.getDataSource();
                if (defaultDataSourceKey == null) {
                    defaultDataSourceKey = "";
                }
                if (currentDataSource != null && !currentDataSource.equals(defaultDataSourceKey)) {
                    logger.debug("无@DataSource默认数据库类型：" + defaultDataSourceKey);
                    DataSourceContext.putDataSource(defaultDataSourceKey);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}