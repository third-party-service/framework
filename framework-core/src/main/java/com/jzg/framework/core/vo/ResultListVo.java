package com.jzg.framework.core.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用列表返回值
 */
public class ResultListVo<T> extends BaseResultVo implements Serializable {
    private static final long serialVersionUID = -8794273233597978869L;
    private List<T> list = new ArrayList<T>();


    public ResultListVo() {
        super();
    }
    public ResultListVo(int status) {
        super(status);
    }

    public ResultListVo(int status, String msg) {
        super(status, msg);
    }

    /**
     * 列表
     * @return
     */
    public List<T> getList() {
        if (list == null) {
            list = new ArrayList<T>();
        }
        return list;
    }

    /**
     * 列表
     * @param list
     */
    public void setList(List<T> list) {
        this.list = list;
    }


}
