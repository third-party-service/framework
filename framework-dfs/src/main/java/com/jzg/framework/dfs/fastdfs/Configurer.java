package com.jzg.framework.dfs.fastdfs;

import org.apache.log4j.LogManager;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by JZG on 2016/11/29.
 */
public abstract class Configurer {
    public static final String CLASSPATH_URL_PREFIX = "classpath:";
    public static final String XML_FILE_EXTENSION = ".xml";

    public Configurer() {

    }

    public static void init(String location) throws FileNotFoundException {
        String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
        URL url = ResourceUtils.getURL(resolvedLocation);

        DefaultGlobal.clientConfigFilePath = url.getPath();
    }

    public static void init(String location, long refreshInterval) throws FileNotFoundException {
        String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
        File file = ResourceUtils.getFile(resolvedLocation);
        if (!file.exists()) {
            throw new FileNotFoundException("Fdfs config file [" + resolvedLocation + "] not found");
        } else {
            DefaultGlobal.clientConfigFilePath = file.getAbsolutePath();
        }
    }

    public static void shutdownLogging() {
        LogManager.shutdown();
    }

    public static void setWorkingDirSystemProperty(String key) {
        System.setProperty(key, (new File("")).getAbsolutePath());
    }
}