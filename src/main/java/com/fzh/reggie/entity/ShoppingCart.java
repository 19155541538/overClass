package com.fzh.reggie.entity;


import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 16:51
 * @description 购物车实体类
 */
@Data
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;

    //购物车ID
    private Long id;

    //名称
    private String name;

    //用户id
    private Long userId;

    //菜品id
    private Long dishId;

    //套餐id
    private Long setmealId;

    //口味
    private String dishFlavor;

    //数量
    private Integer number;

    //金额
    private BigDecimal amount;

    //图片
    private String image;

    private LocalDateTime createTime;
}