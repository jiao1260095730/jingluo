package com.jingluo.jingluo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
* swagger配置
* @author 焦斌
* @date 2020/3/28
*/
@Configuration //配置文件
public class SwaggerConfig {
    //创建文档说明
    public ApiInfo createAI(){
        ApiInfo apiInfo=new ApiInfoBuilder().title("鲸落-学习平台")
                .description("实现一款学习平台")
                .contact(new Contact("焦斌","http://www.17feri.top","jiaobin5229@153.com"))
                .build();
        return apiInfo;
    }
    //创建Swagger扫描信息
    @Bean
    public Docket createD(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(createAI()).select()
                .apis(RequestHandlerSelectors.basePackage("com.jingluo.jingluo.controller")).build();
    }
}
