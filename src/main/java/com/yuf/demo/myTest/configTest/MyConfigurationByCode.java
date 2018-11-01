package com.yuf.demo.myTest.configTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * @author dyf
 * @version 2018年10月25日上午10:50:29
 */

@Configuration
public class MyConfigurationByCode {
	
	public MyConfigurationByCode() {
		System.out.println("我的Code配置初始化。。。。。。");
	}

	@Bean
	public MyBean myBean(){
		MyBean mybean = new MyBean();
		return mybean;
	}
}
