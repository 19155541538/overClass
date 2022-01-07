package com.fzh.reffie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzh.reffie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.stream.IntStream;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/8 0:40
 * @description 菜品表的接口
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
