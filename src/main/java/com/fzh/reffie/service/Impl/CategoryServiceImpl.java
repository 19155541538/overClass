package com.fzh.reffie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reffie.entity.Category;
import com.fzh.reffie.mapper.CategoryMapper;
import com.fzh.reffie.service.CategoryService;
import com.fzh.reffie.service.DishService;
import com.fzh.reffie.service.SetmealService;
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

    }
}
