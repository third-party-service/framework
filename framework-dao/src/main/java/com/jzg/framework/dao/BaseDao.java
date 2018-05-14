package com.jzg.framework.dao;


import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;

/**
 * Created by JZG on 2016/11/11.
 */
/*public interface BaseDao<T> extends Mapper<T>, MySqlMapper<T> {
}*/


/*public interface BaseDao<T> extends Mapper<T>, SqlServerMapper<T> {
}*/

/**
 * SQLServer的Insert与普通不一致，需要专用的SqlServerMapper
 * @param <T>
 */
public interface BaseDao<T> extends BaseSelectMapper<T>, BaseUpdateMapper<T>, BaseDeleteMapper<T>, ExampleMapper<T>, RowBoundsMapper<T>, Marker {
    int insert(T t);
}
