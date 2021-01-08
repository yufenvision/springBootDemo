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
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo = new ApiInfoBuilder()
            .title("快速开发")//api标题
            .description("yuf快速开发项目")//api描述
            .version("1.0.0")//版本号
            .build();


//	@Bean(value = "系统管理")
//    public Docket sys_api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.yuf.demo.sys"))
//                .paths(PathSelectors.any())
//                .build()
//                .groupName("系统管理模块")
//                .pathMapping("/")
////                .securitySchemes(securitySchemes())
////                .securityContexts(securityContexts())
//                .useDefaultResponseMessages(false)
//                ;
//    }
//
//	@Bean(value = "文件中心")
//    public Docket filecenter_api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.yuf.demo.business.filecenter"))
//                .paths(PathSelectors.any())
//                .build()
//                .groupName("文件中心模块")
//                ;
//    }

    @Bean(value = "数据导入")
    public Docket dataInput_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yuf.demo.business.excel"))
                .paths(PathSelectors.any())
                .build()
                .groupName("数据导入模块")
                ;
    }

    @Bean(value = "测试")
    public Docket test_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yuf.demo.business.async_test"))
                .paths(PathSelectors.any())
                .build()
                .groupName("异步测试模块")
                ;
    }
}
