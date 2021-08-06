package com.wyon.srb.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.wyon.srb", "com.wyon.common"})
public class ServiceSmsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class, args);
    }
}