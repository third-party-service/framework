package com.jzg.framework.utils.test.model;

import java.util.Date;

public class ProductImage {
    private Long sysno;
    private Long productSysno;
    private String imgName;
    private Byte imgType;
    private Integer priority;
    private Byte status;
    private String createUser;
    private Date createTime;
    private String editUser;
    private Date editTime;

    public ProductImage() {
    }

    public Long getSysno() {
        return this.sysno;
    }

    public void setSysno(Long sysno) {
        this.sysno = sysno;
    }

    public Long getProductSysno() {
        return this.productSysno;
    }

    public void setProductSysno(Long productSysno) {
        this.productSysno = productSysno;
    }

    public String getImgName() {
        return this.imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName == null?null:imgName.trim();
    }

    public Byte getImgType() {
        return this.imgType;
    }

    public void setImgType(Byte imgType) {
        this.imgType = imgType;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null?null:createUser.trim();
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditUser() {
        return this.editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser == null?null:editUser.trim();
    }

    public Date getEditTime() {
        return this.editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }
}
