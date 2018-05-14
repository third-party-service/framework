package com.jzg.framework.dfs.fastdfs;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ServletContextPropertyUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;

/**
 * Created by JZG on 2016/11/29.
 */
public class WebConfigurer {
    public static final String FASTDFS_CONFIG_LOCATION_PARAM = "configLocation";
    public static final String FASTDFS_REFRESH_INTERVAL_PARAM = "refreshInterval";

    private WebConfigurer() {
    }

    public static void init(ServletContext servletContext) {
        String location = servletContext.getInitParameter(FASTDFS_CONFIG_LOCATION_PARAM);
        if(location != null) {
            try {
                location = ServletContextPropertyUtils.resolvePlaceholders(location, servletContext);
                if(!ResourceUtils.isUrl(location)) {
                    location = WebUtils.getRealPath(servletContext, location);
                }

                servletContext.log("Initializing fdfs from [" + location + "]");
                String ex = servletContext.getInitParameter(FASTDFS_REFRESH_INTERVAL_PARAM);
                if(StringUtils.hasText(ex)) {
                    try {
                        long ex1 = Long.parseLong(ex);
                        Configurer.init(location, ex1);
                    } catch (NumberFormatException var5) {
                        throw new IllegalArgumentException("Invalid \'RefreshInterval\' parameter: " + var5.getMessage());
                    }
                } else {
                    Configurer.init(location);
                }
            } catch (FileNotFoundException var6) {
                throw new IllegalArgumentException("Invalid \'ConfigLocation\' parameter: " + var6.getMessage());
            }
        }

    }

    public static void shutdownLogging(ServletContext servletContext) {
        servletContext.log("Shutting down FastDFS");

        try {
            Configurer.shutdownLogging();
        } finally {

        }

    }

}
