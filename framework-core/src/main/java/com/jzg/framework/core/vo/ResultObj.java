package com.jzg.framework.core.vo;

/**
 *
 * ajax调用统一返回该结果
 *
 * 此为老版返回值，【请使用新版 ResultVo】
 */
public class ResultObj {

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private int status = 500;

    private Object data = null;

    private String msg = "";

    public ResultObj(int status, String msg, Object data) {
        this.data = data;
        this.msg = msg;
        this.status = status;
    }

    public ResultObj(int status, String msg) {
        this.msg = msg;
        this.status = status;
    }

    public ResultObj(int status) {
        this.status = status;
    }

    public ResultObj() {
    }
}
