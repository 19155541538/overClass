package com.fzh.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzh.reggie.dto.DishDto;
import com.fzh.reggie.entity.Dish;

import java.util.List;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/8 0:44
 * @description 菜品表业务层接口
 */
public interface DishService extends IService<Dish> {
    //扩展方法  新增菜品,同时插入菜品对应的口味数据,需要2个表 dish,dish_flavor
    public void  saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    //删除
    public void reomveWithFlavor(List<Long> ids);
}
