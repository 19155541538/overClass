package com.fzh.reggie.controller;

import com.fzh.reggie.common.R;
import com.fzh.reggie.entity.Orders;
import com.fzh.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 20:54
 * @description 下单
 */

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据{}",orders);
        orderService.submit(orders);

        return R.success("下单成功");
    }

}
