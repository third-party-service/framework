package com.jzg.framework.core.search;

/**
 * QueryService
 */
public interface QueryService {
    /**
     * 查询
     * @param condition json
     * @return json 分页数据
     */
    String query(String condition);
}
