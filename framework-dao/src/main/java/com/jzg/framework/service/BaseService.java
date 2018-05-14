/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jzg.framework.service;


import com.jzg.framework.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public abstract class BaseService<T> implements IService<T> {
    @Autowired
    protected BaseDao<T> baseDao;

    public BaseDao<T> getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    public T selectByKey(Object key) {
        return baseDao.selectByPrimaryKey(key);
    }

    public int insert(T entity) {
        return baseDao.insert(entity);
    }

    public int delete(Object key) {
        return baseDao.deleteByPrimaryKey(key);
    }

    public int updateAll(T entity) {
        return baseDao.updateByPrimaryKey(entity);
    }

    public int updateNotNull(T entity) {
        return baseDao.updateByPrimaryKeySelective(entity);
    }

    /*public List<T> selectByExample(Object example) {
        return baseDao.selectByExample(example);
    }*/

    /*public PageInfo<T> search(Class<?> t, BaseQueryPageDto baseQueryPageDto) {

        *//*Example example = ExampleBuilder.forClass(t).build(searchModel).getExample();*//*

        Example example = ExampleBuilder.forClass(t).build(searchModel).orderBy(searchModel).getExample();

        PageHelper.startPage(baseQueryPageDto.getPageNo(), baseQueryPageDto.getPageSize());

        List items = selectByExample(example);

        PageInfo pi = new PageInfo<T>(items);

        return pi;
    }*/
}
