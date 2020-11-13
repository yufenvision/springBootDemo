package com.yuf.demo.myTest.configTest;

import com.yuf.demo.config.aop.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyBeanAutowiredTest {
	@Autowired
	private MyBean myBean;
//	@Autowired
//	private MyBean1 myBean1;

	@LogInfo
	@GetMapping("/testMyBean")
	public String test(){
		System.out.println(myBean.getValue());
		return myBean.getValue();
	}
//	@GetMapping("/testMyBean1")
//	public String test1(){
//		System.out.println(myBean1.getValue());
//		return myBean1.getValue();
//	}
}
