package com.fzh.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.entity.ShoppingCart;
import com.fzh.reggie.mapper.ShoppingCartMapper;
import com.fzh.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 16:55
 * @description 购物车 业务层 实体类
 */

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>  implements ShoppingCartService {
}
