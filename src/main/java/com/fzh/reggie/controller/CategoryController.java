package com.fzh.reggie.controller;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/7 23:07
 * @description 控制层CategoryController
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzh.reggie.common.R;
import com.fzh.reggie.entity.Category;
import com.fzh.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类 管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        //创建分页构造器
        Page<Category> categoryPage = new Page<>();
        //创建条件构造器
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加条件排序,根据sort进行排序
        categoryLambdaQueryWrapper.orderByDesc(Category::getSort);

        //进行分页
        categoryService.page(categoryPage, categoryLambdaQueryWrapper);
        return R.success(categoryPage);
    }

    /**
     * 分类信息删除
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> deleteById(Long id) {
        log.info("删除分类,id为{}", id);

//      categoryService.removeById(id);
        //因为需要判断当前菜品是否关联了套餐  所以要使用自定义的删除方法(在自定义删除的方法中判断了当前菜品是否可以删除)
        categoryService.remove(id);
        return R.success("分类信息删除成功!");
    }

    /**
     * 修改
     *
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类信息:{}", category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件 并对查询的结果按照sort排序字段进行升序排序，如果sort相同，再按照修改时间倒序排序。
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }

}
