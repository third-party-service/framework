package com.jzg.framework.core.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -7400020856061148004L;

    public Page() {
    }

    public Page(int total, int pageNo, int pageSize, List<T> list) {
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.list = list;
    }

    public Page(int pageNo, int pageSize, List<T> list) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.list = list;
    }

    //当前页
    private int pageNo = 1;

    //每页的数量
    private int pageSize = 10;

    //总记录数
    private int total;

    //总页数
    private int pageCount;

    //结果集
    private List<T> list;


    /**
     * 当前页码
     * @return
     */
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 页大小（每页记录数量）
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 总条数（总记录数）
     * @return
     */
    public int getTotal() {
        return total;
    }

    /**
     * 总条数（总记录数）
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }


    /**
     * 总页数
     * @return
     */
    public int getPageCount() {
        if (this.pageSize == 0) {
            this.pageSize = 1;
        }

        if (this.total % this.pageSize == 0) {
            return this.total / this.pageSize;
        }
        return this.total / this.pageSize + 1;
    }

    /**
     * 总页数
     * @param pageCount
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


    /**
     * 当前页结果集
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 当前页结果集
     * @param list
     */
    public void setList(List<T> list) {
        this.list = list;
    }

}
