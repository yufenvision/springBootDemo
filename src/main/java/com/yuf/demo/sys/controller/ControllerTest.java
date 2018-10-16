package com.yuf.demo.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuf.demo.sys.entity.Girl;
import com.yuf.demo.sys.entity.GirlProperties;

@RestController
public class ControllerTest {
	
	@Autowired
	private GirlProperties girlProperties;
	
	@Value("${value}")
	private String value;
	
	@RequestMapping("/hello")
	public String hello(){
		return girlProperties.getCupSize()+"---"+girlProperties.getAge();
	}
	
	@RequestMapping("/value")
	public String value(){
		return value;
	}
	
}
