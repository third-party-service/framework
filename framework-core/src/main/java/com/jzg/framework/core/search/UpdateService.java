package com.jzg.framework.core.search;

import com.jzg.framework.core.vo.ResultVo;

import java.io.Serializable;
import java.util.List;

/**
 * 更新索引
 *
 * UpdateService
 */
public interface UpdateService {
    /**
     * 更新主键Id索引
     * @param id
     * @return
     */
    <K extends Serializable> ResultVo update(K id);

    /**
     * 更新列表索引
     * @param ids
     * @return
     */
    <K extends Serializable> ResultVo update(List<K> ids);

    /**
     * 更新全部索引
     * @return
     */
    ResultVo update();
}
