package com.fzh.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieAppcation {
    public static void main(String[] args) {
        SpringApplication.run(ReggieAppcation.class);
        log.info("我跑起来啦...哈哈哈...");
    }
}
