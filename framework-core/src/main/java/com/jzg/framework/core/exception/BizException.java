package com.jzg.framework.core.exception;

public class BizException extends RuntimeException {
    private static final long serialVersionUID = 3879724773383389410L;

    private int code = 500;
    private String msg;

    public BizException(String message){
        super(message);
    }

    public BizException(){
        super();
    }

    public BizException(int code, String message){
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }


    /**
     * 数据库操作,insert返回0
     */
    public static final BizException DB_INSERT_RESULT_0 = new BizException(80050001, "数据库操作,insert返回0");

    /**
     * 数据库操作,update返回0
     */
    public static final BizException DB_UPDATE_RESULT_0 = new BizException(80050002, "数据库操作,update返回0");

    /**
     * 数据库操作,selectOne返回null
     */
    public static final BizException DB_SELECTONE_IS_NULL = new BizException(80050003, "数据库操作,selectOne返回null");

    /**
     * 数据库操作,list返回null
     */
    public static final BizException DB_LIST_IS_NULL = new BizException(80050004, "数据库操作,list返回null");

    /**
     * Token 验证不通过
     */
    public static final BizException TOKEN_IS_ILLICIT = new BizException(80050005, "Token 验证非法");
    /**
     * 会话超时　获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
     */
    public static final BizException SESSION_IS_OUT_TIME = new BizException(80050006, "会话超时");

    /**
     * 获取序列出错
     */
    public static final BizException DB_GET_SEQ_NEXT_VALUE_ERROR = new BizException(80050007, "获取序列出错");


}
