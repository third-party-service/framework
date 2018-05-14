package com.jzg.framework.web.auth;


import com.jzg.framework.core.vo.RetStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 权限拦截器
 * @author: JZG
 * @date: 2016/12/1 17:39
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isAjax = getIsAjaxRequest(request);
        //判断是否为API请求
        if(authService.isApi()){
            //Sign签名是否正确
            if(!authService.isSign()){
                response.setStatus(RetStatus.NoAuth.getValue());
                return false;
            }
            else{
                return true;
            }
        }

        //第一步：登录验证
        if (!authService.isLogin()) {
            if (isAjax || authService.isAjax()) {
                response.setStatus(RetStatus.NoLogin.getValue());
                return false;
            } else {
                response.sendRedirect(authService.getLoginUrl());
                return false;
            }
        }

        //第二步：权限验证
        if (!authService.isAuth()) {
            if (isAjax || authService.isAjax()) {
                response.setStatus(RetStatus.NoAuth.getValue());
                return false;
            } else {
                response.getWriter().write("No permission");
                response.getWriter().flush();
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    }


    /**
     * 是否为ajax请求
     *
     * @param request
     * @return
     */
    private boolean getIsAjaxRequest(HttpServletRequest request) {
        boolean bRet = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        return bRet;
    }
}
