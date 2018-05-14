package com.jzg.framework.utils.test.model;

import java.math.BigDecimal;
import java.util.Date;


public class Product {
    private Long sysno;
    private String productName;
    private Integer serviceType;
    private BigDecimal originalPrice;
    private BigDecimal price;
    private Integer stock;
    private Integer inventory;
    private Integer status;
    private String cancelReason;
    private Date expireTime;
    private Date createTime;
    private Date editTime;
    private Long createUserSysno;
    private String createUserName;
    private String briefName;
    private Long skuSysno;
    private Integer provinceSysno;
    private Integer citySysno;
    private Integer regionSysno;
    private String description;
    private Integer isDelete;

    public Product() {
    }

    public Integer getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Long getSysno() {
        return this.sysno;
    }

    public void setSysno(Long sysno) {
        this.sysno = sysno;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null?null:productName.trim();
    }

    public Integer getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getOriginalPrice() {
        return this.originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getInventory() {
        return this.inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCancelReason() {
        return this.cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null?null:cancelReason.trim();
    }

    public Date getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return this.editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Long getCreateUserSysno() {
        return this.createUserSysno;
    }

    public void setCreateUserSysno(Long createUserSysno) {
        this.createUserSysno = createUserSysno;
    }

    public String getCreateUserName() {
        return this.createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null?null:createUserName.trim();
    }

    public String getBriefName() {
        return this.briefName;
    }

    public void setBriefName(String briefName) {
        this.briefName = briefName == null?null:briefName.trim();
    }

    public Long getSkuSysno() {
        return this.skuSysno;
    }

    public void setSkuSysno(Long skuSysno) {
        this.skuSysno = skuSysno;
    }

    public Integer getProvinceSysno() {
        return this.provinceSysno;
    }

    public void setProvinceSysno(Integer provinceSysno) {
        this.provinceSysno = provinceSysno;
    }

    public Integer getCitySysno() {
        return this.citySysno;
    }

    public void setCitySysno(Integer citySysno) {
        this.citySysno = citySysno;
    }

    public Integer getRegionSysno() {
        return this.regionSysno;
    }

    public void setRegionSysno(Integer regionSysno) {
        this.regionSysno = regionSysno;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description == null?null:description.trim();
    }
}
