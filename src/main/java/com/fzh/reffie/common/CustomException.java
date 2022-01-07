package com.fzh.reffie.common;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/8 0:50
 * @description 自定义业务异常类
 */
public class CustomException extends RuntimeException{
    public CustomException (String message){
        super(message);
    }
}
