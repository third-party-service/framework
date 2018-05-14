package com.jzg.framework.core.dto;

import java.io.Serializable;

/**
 * 普通列表查询  参数
 */
public class BaseQueryDto implements Serializable, BaseDto {

    private static final long serialVersionUID = -7907831589841138921L;

    public BaseQueryDto(){

    }

    public BaseQueryDto(Sort sort) {
        this.sort = sort;
    }

    //排序
    private Sort sort;


    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
