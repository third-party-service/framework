package com.jzg.framework.web.config;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * @description: 静态文件配置管理
 * @author: JZG
 * @date: 2016/12/1 14:23
 */
public class WebResourceConfigurer {
    private static WebResourceEntity webResourceEntity = new WebResourceEntity();
    private static boolean isInit = false;

    static {
        webResourceEntity.setJsBasePath("");
        webResourceEntity.setImgBasePath("");
        webResourceEntity.setCssBasePath("");
        webResourceEntity.setVersion("v1.0");

        webResourceEntity.setGlobalCssBasePath("");
        webResourceEntity.setGlobalJsBasePath("");
        webResourceEntity.setGlobalImgBasePath("");
    }

    /**
     * 获取静态配置信息
     *
     * @return
     */
    public static WebResourceEntity getWebResourceEntity() {
        return webResourceEntity;
    }

    /**
     * 初始化静态配置文件
     *
     * @param location
     * @throws FileNotFoundException
     */
    public static void init(String location) throws FileNotFoundException {
        synchronized (WebResourceConfigurer.class) {
            String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
            URL url = ResourceUtils.getURL(resolvedLocation);
            if ("file".equals(url.getProtocol()) && !ResourceUtils.getFile(url).exists()) {
                throw new FileNotFoundException("WebResource config file [" + resolvedLocation + "] not found");
            } else {
                try {
                    String fileName = ResourceUtils.getFile(url).getName();
                    com.jzg.framework.utils.ResourceUtils resourceUtils = com.jzg.framework.utils.ResourceUtils.getResource(fileName.substring(0, fileName.indexOf(".")));

                    if (!StringUtils.isEmpty(resourceUtils.getValue("web.resource.cssBasePath"))) {
                        webResourceEntity.setCssBasePath(resourceUtils.getValue("web.resource.cssBasePath"));
                    }
                    if (!StringUtils.isEmpty(resourceUtils.getValue("web.resource.jsBasePath"))) {
                        webResourceEntity.setJsBasePath(resourceUtils.getValue("web.resource.jsBasePath"));
                    }
                    if (!StringUtils.isEmpty(resourceUtils.getValue("web.resource.imgBasePath"))) {
                        webResourceEntity.setImgBasePath(resourceUtils.getValue("web.resource.imgBasePath"));
                    }
                    if (!StringUtils.isEmpty(resourceUtils.getValue("web.resource.version"))) {
                        webResourceEntity.setVersion(resourceUtils.getValue("web.resource.version"));
                    }
                    if (!StringUtils.isEmpty(resourceUtils.getValue("web.resource.globalCssBasePath"))) {
                        webResourceEntity.setGlobalCssBasePath(resourceUtils.getValue("web.resource.globalCssBasePath"));
                    }
                    if (!StringUtils.isEmpty(resourceUtils.getValue("web.resource.globalJsBasePath"))) {
                        webResourceEntity.setGlobalJsBasePath(resourceUtils.getValue("web.resource.globalJsBasePath"));
                    }
                    if (!StringUtils.isEmpty(resourceUtils.getValue("web.resource.globalImgBasePath"))) {
                        webResourceEntity.setGlobalImgBasePath(resourceUtils.getValue("web.resource.globalImgBasePath"));
                    }
                } catch (Exception ex) {
                    throw new FileNotFoundException("WebResource config file [" + resolvedLocation + "] is not found");
                }

                isInit = true;
            }
        }
    }

}
