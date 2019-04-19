package com.yuf.demo.myTest.configTest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath*:spring-myCofig.xml")
//@ImportResource("com/yuf/demo/myTest/configTest/spring-myCofig.xml")
public class MyConfigurationByXML {

	public MyConfigurationByXML() {
		System.out.println("我的XML配置初始化。。。。");
	}
	
}
