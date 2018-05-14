package com.jzg.framework.web.auth;


import com.jzg.framework.utils.CookieUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private String cookieLoginToken = "JZGPASSPORT_LOGIN_TOKEN";

    public void TokenInterceptor(){}

    public void TokenInterceptor(String cookieLoginToken){
           this.cookieLoginToken = cookieLoginToken;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Cookie tokenCookie = CookieUtils.getCookieByName(request, cookieLoginToken);
        if (tokenCookie != null) {
            request.setAttribute("token", tokenCookie.getValue());
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}  