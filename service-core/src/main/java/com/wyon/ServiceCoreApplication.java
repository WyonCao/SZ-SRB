package com.wyon;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(value = "com.wyon.srb.core.mapper")
@ComponentScan({"com.wyon.srb", "com.wyon.common"})
public class ServiceCoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceCoreApplication.class,args);
    }
}
