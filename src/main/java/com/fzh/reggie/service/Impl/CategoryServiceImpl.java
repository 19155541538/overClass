package com.fzh.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.common.CustomException;
import com.fzh.reggie.entity.Category;
import com.fzh.reggie.entity.Dish;
import com.fzh.reggie.entity.Setmeal;
import com.fzh.reggie.mapper.CategoryMapper;
import com.fzh.reggie.service.CategoryService;
import com.fzh.reggie.service.DishService;
import com.fzh.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/7 23:02
 * @description 业务层接口 实现类
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;


    /**
     * 根据ID删除分类,,删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        //添加查询条件,根据分类ID进行查询菜品数据
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        //count查出当前菜品下关联几个菜
        int count1 = dishService.count(dishLambdaQueryWrapper);

        //如果已经关联,抛出一个业务异常
        if(count1>0){
            throw  new CustomException("当前分类下关联了菜品,不能删除");//已经关联菜品,抛出一个业务异常
        }

        //查询当前分类是否关联了套餐,如果已经关联,抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0 ){
            throw  new CustomException("当前分类下关联了套餐,不能删除");//已经关联套餐,抛出异常
        }
        //没有抛出异常怎么删除
        super.removeById(id);
    }
}
