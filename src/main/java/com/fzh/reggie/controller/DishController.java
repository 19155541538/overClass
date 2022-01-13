package com.fzh.reggie.controller;
/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/9 21:08
 * @description 添加菜品
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzh.reggie.common.R;
import com.fzh.reggie.dto.DishDto;
import com.fzh.reggie.entity.Category;
import com.fzh.reggie.entity.Dish;
import com.fzh.reggie.service.CategoryService;
import com.fzh.reggie.service.DishFlavorService;
import com.fzh.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return R.success("新增菜品成功");
    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<Dish>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //构造过滤条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        //排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo, queryWrapper);

        //对象拷贝  从pageInfo拷贝到dishDtoPage 排除records,
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        //获取 records  在把records集合处理一下
        List<Dish> records = pageInfo.getRecords();
        //遍历records
/*        List<DishDto> list = records.stream().map((item) -> {

            DishDto dishDto = new DishDto();

            //因为下面只给dishDto赋值了categoryName(分类名称)其他的元素还是空的 ,, 所以还要拷贝其他的元素 给dishDto
            BeanUtils.copyProperties(item, dishDto);

            //拿到每一个菜品的分类ID 最终目的是用ID去表中查相对应的分类名称
            Long categoryId = item.getCategoryId();

            //根据ID查询分类对象
            Category category = categoryService.getById(categoryId);
            if (categoryId != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());*/

        List<DishDto> list = new ArrayList<>();
        for (Dish dish : records) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);

            Long categoryId = dish.getCategoryId();

            Category category = categoryService.getById(categoryId);
            if (categoryId != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            list.add(dishDto);

        }
        //给dishDtoPage赋值
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息  -->回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    /**
     * 根据条件查询对应的菜品数据
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        //构建查询条件
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId())
                .eq(Dish::getStatus, 1)
                .orderByAsc(Dish::getSort).orderByDesc(Dish::getCreateTime);
        List<Dish> list = dishService.list(dishLambdaQueryWrapper);
        return R.success(list);
    }
}