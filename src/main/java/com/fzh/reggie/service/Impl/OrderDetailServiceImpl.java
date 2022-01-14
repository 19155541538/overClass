package com.fzh.reggie.service.Impl;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 20:53
 * @description 2
 */
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.entity.OrderDetail;
import com.fzh.reggie.mapper.OrderDetailMapper;
import com.fzh.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}