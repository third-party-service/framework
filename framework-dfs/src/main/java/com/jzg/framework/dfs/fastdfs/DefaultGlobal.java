package com.jzg.framework.dfs.fastdfs;

/**
 * Created by JZG on 2016/11/29.
 */
public final class DefaultGlobal {

    private DefaultGlobal() {

    }

    /**
     * 默认宽度
     */
    public static final String FILE_DEFAULT_WIDTH = "120";
    /**
     * 默认高度
     */
    public static final String FILE_DEFAULT_HEIGHT = "120";
    /**
     * 默认作者
     */
    public static final String FILE_DEFAULT_AUTHOR = "jzg";
    /**
     * 默认协议
     */
    public static final String PROTOCOL = "http://";
    /**
     * FastDFS客户端配置文件
     */
    public static final String CLIENT_CONFIG_FILE = "fastdfs-client.properties";

    /**
     * 本地配置文件路径
     */
    public static String clientConfigFilePath;
}

