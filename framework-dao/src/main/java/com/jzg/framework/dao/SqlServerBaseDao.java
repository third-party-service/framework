package com.jzg.framework.dao;


import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.SqlServerMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * SqlServer对应的Mapper
 *
 * Created by JZG on 2016/11/11.
 */
public interface SqlServerBaseDao<T> extends BaseDao<T>, SqlServerMapper<T>, IdsMapper<T>, InsertListMapper<T> {
}
