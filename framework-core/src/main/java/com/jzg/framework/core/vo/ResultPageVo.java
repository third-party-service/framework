package com.jzg.framework.core.vo;

import com.jzg.framework.core.dto.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 通用分页查询返回值
 */
public class ResultPageVo<T> extends BaseResultVo implements Serializable {
    private static final long serialVersionUID = -1988730000659536010L;
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
    public long getTotal() {
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


    public ResultPageVo() {
        super();
    }
    public ResultPageVo(int status) {
        super(status);
    }
    public ResultPageVo(int status, String msg) {
        super(status, msg);
    }

    public ResultPageVo(Page page) {
        this.pageNo = page.getPageNo();
        this.pageSize = page.getPageSize();
        this.pageCount = page.getPageCount();
        this.total = page.getTotal();

        this.list = page.getList();
    }
}
