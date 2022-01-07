package com.fzh.reffie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzh.reffie.entity.Category;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/7 23:00
 * @description 业务层接口
 */
public interface CategoryService extends IService<Category> {
    //删除ID分类
    public void remove (Long id);
}
