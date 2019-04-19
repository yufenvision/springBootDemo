package com.yuf.demo.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
* @author 作者 dyf:
* @version 创建时间：2019年4月18日 下午3:09:01
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessInstanceTestDemo {
	
	//1、创建ProcessEngine核心对象
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/*1、按照规则，启动流程
	 * 	会在2张表产生数据：
	 * 		act_ru_execution: 如何流程执行到任何一个节点时，都会产生一个
	 * 		act_ru_task
	 */
	@Test
	public void startProcess(){
		//获取服务
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//调用方法
		String processDefinitionKey = "MyProcess";
		//根据流程规则的key启动流程，每次都自动启动版本最新的流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		
		System.out.println("processInstance: " + processInstance);
	}
}
