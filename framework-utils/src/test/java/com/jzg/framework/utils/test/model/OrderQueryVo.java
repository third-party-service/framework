package com.jzg.framework.utils.test.model;

import java.util.Date;

public class OrderQueryVo {
    private Long orderSysno;

    private Integer orderStatus;

    private Date startTime;

    private Date endTime;

    public Long getOrderSysno() {
        return orderSysno;
    }

    public void setOrderSysno(Long orderSysno) {
        this.orderSysno = orderSysno;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
