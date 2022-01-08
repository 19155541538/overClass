package com.fzh.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzh.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/8 0:40
 * @description 菜品表的接口
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
