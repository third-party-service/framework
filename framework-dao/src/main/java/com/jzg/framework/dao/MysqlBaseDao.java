package com.jzg.framework.dao;


import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * Created by JZG on 2016/11/11.
 */
/*public interface BaseDao<T> extends Mapper<T>, MySqlMapper<T> {
}*/


/*public interface BaseDao<T> extends Mapper<T>, SqlServerMapper<T> {
}*/


public interface MysqlBaseDao<T> extends BaseDao<T>, Mapper<T>, MySqlMapper<T>, IdsMapper<T>, InsertListMapper<T> {
}
