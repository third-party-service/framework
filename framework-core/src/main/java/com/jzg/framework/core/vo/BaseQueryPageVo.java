package com.jzg.framework.core.vo;

/**
 * 基本分页查询参数Vo
 */
public class BaseQueryPageVo extends BaseQueryVo {
    //当前页
    private int pageNo = 1;

    //每页的数量
    private int pageSize = 10;

    public int getPageNo() {
        if (pageNo <= 0) {
            pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if (pageNo <= 0) {
            pageNo = 1;
        }
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        this.pageSize = pageSize;
    }

}
