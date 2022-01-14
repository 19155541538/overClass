package com.fzh.reggie.mapper;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 20:51
 * @description 2
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzh.reggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}