package com.fzh.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzh.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/12 23:09
 * @description 1
 */
@Mapper
public interface UserMapper  extends BaseMapper<User> {
}
