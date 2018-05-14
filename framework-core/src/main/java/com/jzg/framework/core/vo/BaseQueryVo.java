package com.jzg.framework.core.vo;

import com.jzg.framework.core.dto.Sort;

import java.io.Serializable;

/**
 * 基本查询参数Vo
 */
public class BaseQueryVo implements Serializable, BaseVo {

    private static final long serialVersionUID = -7907831589841138921L;

    //排序
    private Sort sort;

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

}
