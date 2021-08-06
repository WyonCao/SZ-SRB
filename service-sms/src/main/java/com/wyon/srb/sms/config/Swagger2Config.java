package com.wyon.srb.sms.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket adminApiConfig(){

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                //只显示api路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();

        return docket;
    }

    private ApiInfo adminApiInfo() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("尚融宝短信-API文档")
                .description("本文档描述了尚融宝阿里短信接口")
                .version("1.0")
                .contact(new Contact("Wyon", "http://wyon.com", "939996900@qq.com"))
                .build();

        return apiInfo;

    }
}
