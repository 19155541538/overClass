package com.fzh.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.common.CustomException;
import com.fzh.reggie.dto.DishDto;
import com.fzh.reggie.entity.Category;
import com.fzh.reggie.entity.Dish;
import com.fzh.reggie.entity.DishFlavor;
import com.fzh.reggie.mapper.DishMapper;
import com.fzh.reggie.service.CategoryService;
import com.fzh.reggie.service.DishFlavorService;
import com.fzh.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/8 0:47
 * @description 1
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品，同时保存对应的口味数据
     *
     * @param dishDto
     */

    //加入数据控制 保证2张表数据的一致性
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        //获取菜品id
        Long dishId = dishDto.getId();

        //获取菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();

        //把集合中的元素进行一个处理,
        //遍历flavors集合
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查  用于回显数据
     *
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息,从dish表查询
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        //查询当前菜品对应的口味信息,从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        //查看菜品ID和当前ID是否一样
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }


    /**
     * 修改
     *
     * @param dishDto
     */
    @Override
    @Transactional//事务注解保证数据一致性
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据 -- dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect((Collectors.toList()));

        dishFlavorService.saveBatch(flavors);
    }


    /**
     * 删除
     *
     * @param ids
     */
    @Override
    @Transactional
    public void reomveWithFlavor(List<Long> ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.in(Dish::getId, ids)
                .eq(Dish::getStatus, 1);

        int count = this.count(dishLambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("当前菜品在售中,,,不能删除");
        }

        //使用本地删除方法, 删除菜品
        this.removeByIds(ids);

        LambdaQueryWrapper<DishFlavor> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //根据 菜名表Dish 的 id 删除 口味表DishFlavor
        categoryLambdaQueryWrapper.in(DishFlavor::getDishId, ids);

        dishFlavorService.remove(categoryLambdaQueryWrapper);
    }
}
