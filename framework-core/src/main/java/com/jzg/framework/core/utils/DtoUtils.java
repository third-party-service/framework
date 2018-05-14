package com.jzg.framework.core.utils;

import com.jzg.framework.core.dto.BaseDto;
import com.jzg.framework.core.vo.BaseVo;
import org.springframework.beans.BeanUtils;

/**
 * Dto转化Vo
 */
public class DtoUtils {
    /**
     * Dto转化为Vo
     * @param baseDto
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T extends BaseVo> T toBaseVo(BaseDto baseDto, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T t = clazz.newInstance();
        BeanUtils.copyProperties(baseDto, t);
        return t;
    }
}
