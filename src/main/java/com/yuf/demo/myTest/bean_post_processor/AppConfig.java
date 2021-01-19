package com.yuf.demo.myTest.bean_post_processor;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: dyf
 * @Date: 2021/1/19 21:49
 * @Description:
 */
@Configuration //JavaConfig方式，即当前配置类相当于一个applicationContext.xml文件
@ComponentScan //不写路径，则默认扫描当前配置类（AppConfig）所在包及其子包
public class AppConfig {
}
