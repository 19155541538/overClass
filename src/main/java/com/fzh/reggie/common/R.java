package com.fzh.reggie.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//common 这个包就用来存放 通用的类
//R是通用返回结果类 服务端响应的数据都会封装成此对象
@Data
@ApiModel("返回结果")
public class R<T> {
    @ApiModelProperty("编码")
    private Integer code; //编码：1成功，0和其它数字为失败

    @ApiModelProperty("错误信息")
    private String msg; //错误信息

    @ApiModelProperty("数据")
    private T data; //数据

    @ApiModelProperty("动态数据")
    private Map map = new HashMap(); //动态数据  用的比较少

    //响应成功的方法
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;//接收成功信息
        r.code = 1;//1表示成功
        return r;
    }

    //响应失败的方法
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;//接收失败的信息
        r.code = 0;//0表示失败
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
