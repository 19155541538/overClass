package com.fzh.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzh.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 16:53
 * @description 购物车 持久层
 */

@Mapper
public interface ShoppingCartMapper  extends BaseMapper<ShoppingCart> {
}
