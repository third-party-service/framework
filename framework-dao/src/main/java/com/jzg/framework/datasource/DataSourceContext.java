package com.jzg.framework.datasource;

/**
 * @description: 保存当前线程数据源的key
 * @author: JZG
 * @date: 2016/11/24 9:44
 */
public class DataSourceContext {
    public static final ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 绑定当前线程数据源路由的key
     * @param datasource
     */
    public static void putDataSource(String datasource) {
        holder.set(datasource);
    }

    /**
     * 获取当前线程的数据源路由的key
     * @return
     */
    public static String getDataSource() {
        return holder.get();
    }
}