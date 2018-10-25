package com.yuf.demo.myTest.configTest;

/**
 * 需要交给springboot管理的类
 * @author dyf
 * @version 2018年10月25日上午10:48:20
 */
public class MyBean {
	
	private String value = "我是一个bean";
	
	public MyBean() {
		super();
	}

	public MyBean(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
