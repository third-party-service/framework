package com.jzg.framework.core.service;

import com.jzg.framework.core.dto.Page;
import com.jzg.framework.core.dto.Sort;

import java.io.Serializable;
import java.util.List;


public interface GenericService<T extends Serializable, Id extends Serializable> extends Service {
    public abstract void save(T paramT);

    public abstract void update(T paramT);

    public abstract void delete(T paramT);

    public abstract T findById(Id paramId);

    public abstract T findByUniqueProperty(String paramString, Object paramObject);

    public abstract List<T> findAll();

    public abstract List<T> findRange(int paramInt1, int paramInt2);

    public abstract List<T> findByExample(T paramT, String[] paramArrayOfString);

    public abstract List<T> findRangeByExample(T paramT, String[] paramArrayOfString, int paramInt1, int paramInt2);

    public abstract List<T> findByExample(T paramT, String[] paramArrayOfString, Sort paramSort);

    public abstract List<T> findRangeByExample(T paramT, String[] paramArrayOfString, Sort paramSort, int paramInt1, int paramInt2);

    public abstract int count();

    public abstract int countByExample(T paramT, String[] paramArrayOfString);

    public abstract Page<T> findPage(String paramString, Object[] paramArrayOfObject, int paramInt1, int paramInt2);
}
