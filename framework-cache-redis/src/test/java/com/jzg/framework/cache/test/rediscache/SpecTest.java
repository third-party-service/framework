package com.jzg.framework.cache.test.rediscache;

import com.jzg.framework.cache.test.rediscache.model.Specification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext-cacheaop.xml")
public class SpecTest {

    @Resource
    private SpecService specService;

    @Test
    public void cache(){
        List<Specification> specifications = specService.findSpecAllList();

        System.out.println(specifications.toString());
        System.out.println("****************************");
    }


}
