package com.yuf.demo.activiti.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

/**
* @author 作者 dyf:
* @version 创建时间：2019年4月14日 下午3:01:33
*/
@Service
public class TestService {
	
	
	public void activiti(){
		System.out.println("任务已运行");
	}
	
	
	public List<Object> user(){
		return Arrays.asList("xiaoming,xiaohong");
	}
}
