package com.fzh.reggie.service;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 20:52
 * @description 1
 */
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzh.reggie.entity.Orders;
import org.springframework.core.annotation.Order;

public interface OrderService extends IService<Orders> {
    /**
     * 自定义用户下单
     * @param orders
     */
    public void submit( Orders orders);
}