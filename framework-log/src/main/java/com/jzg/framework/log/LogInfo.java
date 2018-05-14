package com.jzg.framework.log;

import java.io.Serializable;

/**
 * 日志信息
 */
public class LogInfo implements Serializable{
    /**
     * 日志ID
     */
    private long logId;

    /**
     * 日志等级
     */
    private int level;

    /**
     * 业务类型
     */
    private int bussinessType = 0;

    /**
     * 系统ID
     */
    private int systemId = 0;

    /**
     * 参数 json
     */
    private String params;

    /**
     * 执行结果 json
     */
    private String result;

    /**
     * 说明
     */
    private String content;

    /**
     * 请求IP地址
     */
    private String ip;

    /**
     * 请求MAC地址
     */
    private String mac;
}
