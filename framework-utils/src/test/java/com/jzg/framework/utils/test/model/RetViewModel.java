package com.jzg.framework.utils.test.model;


public class RetViewModel {

    private int status = 500;
    private Object data = new Object();
    private String msg = "";

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RetViewModel(int status, String msg, Object data) {
        this.data = data;
        this.msg = msg;
        this.status = status;
    }

    public RetViewModel(int status, String msg) {
        this.msg = msg;
        this.status = status;
    }

    public RetViewModel(int status) {
        this.status = status;
    }

    public RetViewModel() {
    }
}
