package com.fzh.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.common.CustomException;
import com.fzh.reggie.dto.SetmealDto;
import com.fzh.reggie.entity.Setmeal;
import com.fzh.reggie.entity.SetmealDish;
import com.fzh.reggie.mapper.SetmealMapper;
import com.fzh.reggie.service.SetmealDishService;
import com.fzh.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/8 0:49
 * @description 1
 */

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Transactional //因为要操作2张表所以要加 , 保证事务的一致性 要么全成功要么全失败
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息,操作setmeal 执行insert操作
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

       /* List<SetmealDish> setmealDishes1 = new ArrayList<>();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
            return setmealDish;
        }
        setmealDishes1.add()
        */

        //保存套餐和菜品的关联信息,操作 setmeal_dish 执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }


    @Override
    @Transactional //因为操作2张表 为了确保数据的一致性 加入事务注解 同时成功,同时失败
    public void removeWithDish(List<Long> ids) {
        //查询一下确定一个是否可以删
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapper);
        //大于0表示在售卖中 1是售卖中
        if (count > 0) {
            //如果不能删除,,抛出一个业务异常
            throw new CustomException("套餐正在售卖中,不能删除");
        }

        //如果可以删除,先删除套餐表中的数据 ----setmeal
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);

        //删除关系表中的数据 ===setmeal_dish
        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }

}
