package com.fzh.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.entity.Dish;
import com.fzh.reggie.mapper.DishMapper;
import com.fzh.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/8 0:47
 * @description 1
 */
@Service
@Slf4j
public class DishServiceImpl  extends ServiceImpl<DishMapper, Dish>  implements DishService {
}
