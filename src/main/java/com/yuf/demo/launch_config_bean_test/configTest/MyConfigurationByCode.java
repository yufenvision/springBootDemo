package com.yuf.demo.launch_config_bean_test.configTest;

import org.springframework.context.annotation.Bean;

/**
 * 配置类
 * @author dyf
 * @version 2018年10月25日上午10:50:29
 */

//@Configuration
public class MyConfigurationByCode {
	
	public MyConfigurationByCode() {
		System.out.println("我的Code配置初始化。。。。。。");
	}

	@Bean
	public MyBean myBean(){
		MyBean mybean = new MyBean();
		return mybean;
	}


	@Bean
	public MyBean1 xx(){
		MyBean1 mybean1 = new MyBean1();
		return mybean1;
	}
}
