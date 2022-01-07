package com.fzh.reffie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reffie.entity.Dish;
import com.fzh.reffie.mapper.DishMapper;
import com.fzh.reffie.service.DishService;
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
