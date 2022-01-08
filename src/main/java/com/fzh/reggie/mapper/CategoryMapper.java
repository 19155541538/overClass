package com.fzh.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzh.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/7 22:59
 * @description Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}