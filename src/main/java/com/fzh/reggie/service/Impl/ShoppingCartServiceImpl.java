package com.fzh.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.common.BaseContext;
import com.fzh.reggie.common.R;
import com.fzh.reggie.entity.ShoppingCart;
import com.fzh.reggie.mapper.ShoppingCartMapper;
import com.fzh.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 16:55
 * @description 购物车 业务层 实体类
 */



@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>  implements ShoppingCartService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public R<ShoppingCart> cutWithShoppingCart(ShoppingCart shoppingCart) {
        //获取用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        //获取前端给的套餐id
        Long setmealId = shoppingCart.getSetmealId();

        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);//判断登录用户 id

        if (setmealId == null) {
            //删除菜品
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            //删除taoc
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //查询当前菜品或者套餐是否在购物车中
        ShoppingCart cartServiceOne = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);
        Integer number = cartServiceOne.getNumber(); //获取数量


        if (number > 1) {
            cartServiceOne.setNumber(number - 1);
            shoppingCartService.updateById(cartServiceOne);
        } else {
            cartServiceOne.setNumber(0);
            shoppingCartService.remove(shoppingCartLambdaQueryWrapper);
        }
        return R.success(shoppingCart);
    }
}
