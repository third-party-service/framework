package com.jzg.framework.dfs.fastdfs;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by JZG on 2016/11/29.
 */
public class ConfigListener implements ServletContextListener {
    /**
     * * Notification that the web application initialization
     * * process is starting.
     * * All ServletContextListeners are notified of context
     * * initialization before any filter or servlet in the web
     * * application is initialized.
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebConfigurer.init(sce.getServletContext());
    }

    /**
     * * Notification that the servlet context is about to be shut down.
     * * All servlets and filters have been destroy()ed before any
     * * ServletContextListeners are notified of context
     * * destruction.
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        WebConfigurer.shutdownLogging(sce.getServletContext());
    }
}
