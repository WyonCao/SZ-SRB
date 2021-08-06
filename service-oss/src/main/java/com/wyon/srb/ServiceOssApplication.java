package com.wyon.srb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.wyon.srb","com.wyon.common"})
public class ServiceOssApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceOssApplication.class,args);
    }

}
