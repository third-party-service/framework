package com.jzg.framework.cache.test.rediscache.impl;

import com.jzg.framework.cache.Cacheable;
import com.jzg.framework.cache.test.rediscache.SpecService;
import com.jzg.framework.cache.test.rediscache.model.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("specService")
public class SpecServiceImpl implements SpecService {
    @Cacheable(cacheName = "redis", expire = 60)
    @Override
    public List<Specification> findSpecAllList() {
        List<Specification> specifications = new ArrayList<>();
        Specification specification = new Specification();
        specification.setSysno(1);
        specification.setCreateTime(new Date());
        specification.setCreateUserName("sss");

        return specifications;
    }
}
