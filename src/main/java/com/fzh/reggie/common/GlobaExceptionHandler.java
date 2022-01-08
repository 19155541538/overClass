package com.fzh.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/7 14:39
 * @description 全局异常处理
 */
//指定拦截那些类型的控制器; ControllerAdvice  RestController意思是拦截类上加了RestController的类
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
//将方法的返回值 R 对象转换为json格式的数据, 响应给页面;
@ResponseBody
public class GlobaExceptionHandler {

    /**
     * 异常处理方法
     *
     * @param exception
     * @return
     */
//    处理SQLIntegrityConstraintViolationException的异常
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.error(exception.getMessage());
//        getMessage获取异常信息
        if (exception.getMessage().contains("Duplicate entry")) {
            String[] s = exception.getMessage().split(" ");
            String msg = s[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    /**
     * 异常处理方法
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex) {
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }
}
