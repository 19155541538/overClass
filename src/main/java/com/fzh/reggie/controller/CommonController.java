package com.fzh.reggie.controller;

import com.fzh.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;


/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/8 20:21
 * @description 通用 文件上传下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    //声明一个变量 使用value注解
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file是一个临时文件,需要转存到指定的位置,否则本次请求完成后临时文件会删除
        log.info(file.toString());

        //获取原始文件名
        String originalFilename = file.getOriginalFilename();//abc.jpg
        //根据小数点 截取文件名得到后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String[] split = originalFilename.split("\\.");

        //使用UUID重新生成文件名,防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        //创建一个目录对象
        File dir = new File(basePath);

        //判断当前目录是否存在
        if (!dir.exists()) {
            //目录不存在,需要创建
            dir.mkdirs();
        }

        try {
            //将临时文件转存到指定位置   fileName是临时文件名    basePath是转存的位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回文件名称,,最终文件名是要存到数据库中的
        return R.success(fileName);
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     * @return
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
