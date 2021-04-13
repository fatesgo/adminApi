package com.fatesgo.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "mapper文件所在的包路径, ex:com.fatesfo.admin.api.mapper")
public class FatesgoAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FatesgoAdminApplication.class, args);
    }

}
