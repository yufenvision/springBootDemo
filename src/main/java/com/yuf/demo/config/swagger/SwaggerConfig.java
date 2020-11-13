package com.yuf.demo.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* @author 作者 dyf:
* @version 创建时间：2019年1月26日 下午4:42:24
* 类说明
*/
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
	
	@Bean(value = "系统管理")
    public Docket sys_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/sys/**"))
                .build()
                .groupName("系统管理模块")
                .pathMapping("/")
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts())
                .useDefaultResponseMessages(false)
                ;
    }
	
	@Bean(value = "文件中心")
    public Docket filecenter_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yuf.demo.business.filecenter"))
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
                .groupName("文件中心模块")
                .pathMapping("/")
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts())
                .useDefaultResponseMessages(false)
                ;
    }
	
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("demo项目")
                .description("接口文档")
//                .termsOfServiceUrl("http://blog.didispace.com/")
                .version("1.0")
                .build();
    }
}
