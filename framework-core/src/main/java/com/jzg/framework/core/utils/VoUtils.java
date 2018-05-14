package com.jzg.framework.core.utils;

import com.jzg.framework.core.dto.BaseDto;
import com.jzg.framework.core.vo.BaseVo;
import org.springframework.beans.BeanUtils;

/**
 * Vo转化为Dto
 */
public class VoUtils {
    /**
     * Vo转化为Dto
     * @param baseVo
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T extends BaseDto> T toBaseDto(BaseVo baseVo, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T t = clazz.newInstance();
        BeanUtils.copyProperties(baseVo, t);
        return t;
    }
}
