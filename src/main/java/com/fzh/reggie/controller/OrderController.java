package com.fzh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzh.reggie.common.R;
import com.fzh.reggie.entity.Orders;
import com.fzh.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 20:54
 * @description 订单
 */

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单数据{}", orders);
        orderService.submit(orders);

        return R.success("下单成功");
    }

    /**
     *订单明细 -->管理端
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number , String beginTime , String endTime) {
      Page<Orders> pageInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Orders> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.eq(number != null, Orders::getNumber,number)
                        .ge(beginTime != null ,Orders::getOrderTime,beginTime)
                                .lt(endTime != null , Orders::getOrderTime , endTime)
                                        .orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo,Wrapper);

        log.info("订单号是{}",number);
        log.info("开始时间{}",beginTime);
        log.info("结束时间{}",endTime);

        return  R.success(pageInfo);
    }

    /**
     *订单派送
     * @param orders
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Orders orders){
        orderService.updateById(orders);
        return R.success("修改成功");
    }
}
