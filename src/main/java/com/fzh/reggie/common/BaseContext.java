package com.fzh.reggie.common;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/7 22:38
 * @description 基于ThreadLocal封装工具类, 用于保存和获取当前用户ID
 */
//当前类的作用范围在某个线程之内
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    public static  void  setCurrentId(long  id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}

