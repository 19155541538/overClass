package com.fzh.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzh.reggie.dto.SetmealDto;
import com.fzh.reggie.entity.Setmeal;
import com.fzh.reggie.entity.SetmealDish;

import java.util.List;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/8 0:45
 * @description 套餐表业务层接口
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 自定义添加方法
     * @param setmealDto
     */
    public void saveWithDish (SetmealDto setmealDto);

    /**
     * 自定义删除方法
     * @param ids
     */
    public  void  removeWithDish(List<Long> ids);
}
