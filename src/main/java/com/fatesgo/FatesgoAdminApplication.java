package com.fatesgo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.fatesgo.mapper")
public class FatesgoAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FatesgoAdminApplication.class, args);
    }

}
