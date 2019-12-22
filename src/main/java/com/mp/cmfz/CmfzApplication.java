package com.mp.cmfz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

//@EnableRedisHttpSession
@MapperScan(basePackages = "com.mp.cmfz.dao")
@SpringBootApplication
public class CmfzApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmfzApplication.class, args);
    }

}
