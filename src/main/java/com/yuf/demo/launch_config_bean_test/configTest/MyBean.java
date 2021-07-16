package com.yuf.demo.launch_config_bean_test.configTest;

/**
 * 需要交给springboot管理的类
 * @author dyf
 * @version 2018年10月25日上午10:48:20
 */
public class MyBean {
	
	private String value = "我是一个MyBean";

	public MyBean() {
	}

	public MyBean(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
