package com.jzg.framework.web.listener;

import com.jzg.framework.utils.PropertiesUtil;
import com.jzg.framework.web.config.WebResourceConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.ServletContextPropertyUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;

/**
 * @description: 资源配置监听器
 * @author: JZG
 * @date: 2016/12/1 13:03
 */
public class WebResourceListener implements ServletContextListener {
    public static final String CONFIG_LOCATION_PARAM = "webResourceConfigLocation";


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String location = "/web.properties";
        if(location != null) {
            try {
                servletContext.log("Initializing webresource from [" + location + "]");
                PropertiesUtil util = new PropertiesUtil(location);
                Map<String, String> map = util.getAllProperty();
                String[] mustHave = "cssBasePath,jsBasePath,imgBasePath,version,globalCssBasePath,globalJsBasePath,globalImgBasePath".split(",");
                String[] arr$ = mustHave;
                int len$ = mustHave.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String str = arr$[i$];
                    if(!map.containsKey("web.resource." + str)) {
                        throw new Exception("web.resource." + str + "不存在");
                    }
                }

                if(map != null) {
                    Iterator iterator = map.keySet().iterator();

                    while(iterator.hasNext()) {
                        String key = (String)iterator.next();
                        if(StringUtils.isNotBlank(key) && key.indexOf("web.resource.") == 0) {
                            String value = map.get(key);
                            key = key.substring("web.resource.".length());
                            if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                                servletContext.setAttribute(key, value);
                            }
                        }
                    }
                }
            } catch (Exception var12) {
                throw new IllegalArgumentException("Invalid 'webResourceConfigLocation' parameter: " + var12.getMessage());
            }
        }

    }

    /*@Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        String location = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        if (location != null) {
            try {
                location = ServletContextPropertyUtils.resolvePlaceholders(location, servletContext);
                if (!ResourceUtils.isUrl(location)) {
                    location = WebUtils.getRealPath(servletContext, location);
                }
                servletContext.log("Initializing webresource from [" + location + "]");

                WebResourceConfigurer.init(location);
            } catch (FileNotFoundException fe) {
                throw new IllegalArgumentException("Invalid \'webResourceConfigLocation\' parameter: " + fe.getMessage());
            }

            servletContext.setAttribute("cssBasePath", WebResourceConfigurer.getWebResourceEntity().getCssBasePath());
            servletContext.setAttribute("jsBasePath", WebResourceConfigurer.getWebResourceEntity().getJsBasePath());
            servletContext.setAttribute("imgBasePath", WebResourceConfigurer.getWebResourceEntity().getImgBasePath());
            servletContext.setAttribute("version", WebResourceConfigurer.getWebResourceEntity().getVersion());
            servletContext.setAttribute("globalCssBasePath", WebResourceConfigurer.getWebResourceEntity().getGlobalCssBasePath());
            servletContext.setAttribute("globalJsBasePath", WebResourceConfigurer.getWebResourceEntity().getGlobalJsBasePath());
            servletContext.setAttribute("globalImgBasePath", WebResourceConfigurer.getWebResourceEntity().getGlobalImgBasePath());
        }
    }*/

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
