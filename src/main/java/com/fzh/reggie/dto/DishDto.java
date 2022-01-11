package com.fzh.reggie.dto;

import com.fzh.reggie.entity.Dish;
import com.fzh.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义一个实体类，然后继承自 Dish，
 * 并对Dish的属性进行拓展，增加 flavors 集合属性(内部封装DishFlavor)
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    //菜品名称
    private String categoryName;

    //
    private Integer copies;
}
