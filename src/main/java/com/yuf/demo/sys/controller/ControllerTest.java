package com.yuf.demo.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuf.demo.sys.entity.GirlProperties;

@RestController
public class ControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);
	
	
	@Autowired
	private GirlProperties girlProperties;
	
	@Value("${value}")
	private String value;
	
	@RequestMapping("/hello")
	public String hello(){
		logger.info("调用了hello接口");
		return girlProperties.getCupSize()+"---"+girlProperties.getAge();
	}
	
	@RequestMapping("/value")
	public String value(){
		return value;
	}
	
}
