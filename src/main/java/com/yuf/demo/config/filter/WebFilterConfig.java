package com.yuf.demo.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;

/**
 * @Author: dyf
 * @Date: 2020/11/13 22:16
 * @Description:
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter> registerFilter(){
        FilterRegistrationBean<MyFilter> filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilter());
        filterRegistrationBean.addUrlPatterns("/test/*");

        return filterRegistrationBean;
    }

}
