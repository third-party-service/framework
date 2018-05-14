package com.jzg.framework.web.auth;

/**
 * @description:
 * @author: JZG
 * @date: 2016/12/1 17:47
 */
public interface AuthService {

    /**
     * 用户是否登录
     * @return
     */
    public boolean isLogin();
    /**
     * 用户是否登陆
     * @param uid  用户ID
     * @return
     */
    //public boolean isLogin(long uid);

    /**
     * 通过SessionID判断用户是登陆
     * @param sessionId
     * @return
     */
    //public boolean isLogin(String sessionId);

    /**
     * 通过SessionID登录
     * @return
     */
    public boolean login();

    /**
     * 通过SessionID登录
     * @param sessionId
     * @return
     */
    //public boolean login(String sessionId);

    /**
     * 手机号验证码登录
     * @param key     手机号、用户登录名等等
     * @param value   验证码、密码等等
     * @param type    类型：1-手机号验证码登录；2-用户名密码登录
     * @return
     */
    //public boolean login(String key, String value, LoginType type);

    /**
     * 用户名密码登录
     * @param loginName
     * @param password
     * @return
     */
    //public boolean login(String loginName, String password);


    /**
     * 退出登录
     * @param key       手机号、用户登录名等等
     * @param type      类型：1-手机号验证码登录；2-用户名密码登录
     * @return
     */
    //public boolean logout(String key, LoginType type);

    /**
     * 通过用户ID退出
     * @param uid
     * @return
     */
    //public boolean logout(long uid);

    /**
     * 通过用户登录名退出
     * @param loginName
     * @return
     */
    //public boolean logout(String loginName);

    /**
     * 用户退出
     * @return
     */
    public boolean logout();


    /**
     * 获取权限列表
     * @return
     */
    public boolean isAuth();


    /**
     * 获取登录URL
     */
    public String getLoginUrl();

    /**
     * 获取首页URL
     */
    public String getHomeUrl();

    /**
     * 获取没有权限页面URL
     */
    public String getNoAuthUrl();

    /**
     * 获取回跳URL
     * @return
     */
    public String getRedirectUrl(String backUrl);


    /**
     * 判断是否为Ajax请求
     * @return
     */
    public boolean isAjax();

    /**
     * 是否为API请求
     * true:是API请求
     * false:不是API请求
     * @return
     */
    public boolean isApi();

    /**
     * API请求签名是否通过
     * true: 签名通过
     * false: 签名未通过
     * @return
     */
    public boolean isSign();
}
