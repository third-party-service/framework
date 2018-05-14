package com.jzg.framework.core.dto;

/**
 * 分页查询  参数
 */
public class BaseQueryPageDto extends BaseQueryDto {

    private static final long serialVersionUID = 2389813155800115582L;

    public BaseQueryPageDto(){

    }

    public BaseQueryPageDto(Sort sort, int pageNo, int pageSize) {
        super(sort);
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }


    //当前页
    private int pageNo = 1;

    //每页的数量
    private int pageSize = 10;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
