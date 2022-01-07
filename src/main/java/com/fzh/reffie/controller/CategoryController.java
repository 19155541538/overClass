package com.fzh.reffie.controller;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/7 23:07
 * @description 控制层CategoryController
 */


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzh.reffie.common.R;
import com.fzh.reffie.entity.Category;
import com.fzh.reffie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
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


    @DeleteMapping
    public R<String> deleteById(Long id) {
        log.info("删除分类,id为{}", id);

        categoryService.removeById(id);
        return R.success("删除成功了咯!");
    }

}
