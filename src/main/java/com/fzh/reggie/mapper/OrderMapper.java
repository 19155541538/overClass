package com.fzh.reggie.mapper;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 20:50
 * @description 下单 持久层
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzh.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}