package com.jzg.framework.core.search;

import com.jzg.framework.core.vo.ResultVo;

import java.io.Serializable;
import java.util.List;

/**
 * 更新索引
 */
public interface DeleteService {
    /**
     * 更新主键Id索引
     * @param id
     * @return
     */
    <K extends Serializable> ResultVo delete(K id);

    /**
     * 更新列表索引
     * @param ids
     * @return
     */
    <K extends Serializable> ResultVo delete(List<K> ids);

    /**
     * 更新全部索引
     * @return
     */
    ResultVo clear();
}
