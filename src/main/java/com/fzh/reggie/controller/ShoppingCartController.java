package com.fzh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzh.reggie.common.BaseContext;
import com.fzh.reggie.common.R;
import com.fzh.reggie.entity.Orders;
import com.fzh.reggie.entity.ShoppingCart;
import com.fzh.reggie.service.OrderService;
import com.fzh.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 16:58
 * @description 购物车 表现层
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderService orderService;
    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据{}", shoppingCart);

        //设置用户id 指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();//获取用户id
        shoppingCart.setUserId(currentId);//把用户id写进购物车中

        //获取菜品id
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //比对一下当前登录用户Id和前端穿进来的用户id是否一致
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
            //添加到购物车的是菜品
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            //添加到购物车的是套餐
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }


        //查询当前菜品或者套餐是否在购物车中
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
        ShoppingCart cartServiceOne = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);

        if (cartServiceOne != null) {
            //如果已经存在,,就在原来数量基础上加一
            Integer number = cartServiceOne.getNumber();//获取 购物车中的数量
            cartServiceOne.setNumber(number + 1); //修改 数量+1
            shoppingCartService.updateById(cartServiceOne);
        } else {
            //如果不存在,则添加到购物车,数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return R.success(cartServiceOne);
    }


    /**
     * 购物车订单 数量减少
     * 删除
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public R<ShoppingCart> cut(@RequestBody ShoppingCart shoppingCart) {
        //获取用户id
        Long userId = BaseContext.getCurrentId();
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

        /*if(number != null){
            //如果已经存在就在原来的基础上  减去一
            Integer number1 = cartServiceOne.getNumber();
            if(number>1){
                cartServiceOne.setNumber(number - 1);
            }else {
                shoppingCartService.remove(shoppingCartLambdaQueryWrapper);
            }
            shoppingCartService.updateById(cartServiceOne);
        }else {
            shoppingCartService.updateById(cartServiceOne);
        }
        */

        if (number >= 2) {
            cartServiceOne.setNumber(number - 1);
            shoppingCartService.updateById(cartServiceOne);
        } else {
            Integer number1 = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number - 1);
            shoppingCartService.remove(shoppingCartLambdaQueryWrapper);
        }
        return R.success(shoppingCart);

    }

    /*
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        log.info("查看购物车.....");
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId())
                .orderByAsc(ShoppingCart::getCreateTime);

        //查询所有满足条件的 放进集合中
        List<ShoppingCart> list = shoppingCartService.list(shoppingCartLambdaQueryWrapper);
        return R.success(list);
    }

    /**
     * 清除购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(shoppingCartLambdaQueryWrapper);

        return R.success("删除成功");
    }


}
