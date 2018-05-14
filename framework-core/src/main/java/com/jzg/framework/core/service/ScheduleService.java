package com.jzg.framework.core.service;

import com.jzg.framework.core.vo.ResultListVo;
import com.jzg.framework.core.vo.ResultVo;

/**
 * @description: DUBBO计划任务 接口
 * @author: JZG
 * @date: 2017/11/16 10:42
 */
public interface ScheduleService extends Service {
    /**
     * 获取待执行列表，返回主键List K代表获取主键ID
     * @return
     */
    ResultListVo<String> findAllList();

    /**
     * 根据id更新信息
     * @param id 主键
     * @return 执行成功或失败
     */
    ResultVo<Boolean> updateById(String id);
}
