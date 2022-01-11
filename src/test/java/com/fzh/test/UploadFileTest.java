package com.fzh.test;

import org.junit.jupiter.api.Test;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/9 19:47
 * @description 测试
 */

public class UploadFileTest {

    //分割获取文件后缀
    @Test
    public void  test1(){
        String fileName = "erer.jpg";
        String[] split = fileName.split("\\.");
        System.out.println(split[1]);
    }

    //截取获取文件后缀
    @Test
    public void  test2(){
        String fileName = "3223.jpg";
        String substring = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(substring);
    }
}
