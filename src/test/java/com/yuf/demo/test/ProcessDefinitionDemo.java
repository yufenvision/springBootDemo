package com.yuf.demo.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.poi.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
* @author 作者 dyf:
* @version 创建时间：2019年4月16日 下午5:17:51
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessDefinitionDemo {
	
	/**
	 * 1、发布规则
	 * 	会在3张表产生数据:
	 * 		act_ge_bytearray:
	 * 		act_re_deployment:
	 * 		act_re_procdef:
	 * 
	 * @throws Exception
	 */
		
	@Test
	public void deployProcess() throws Exception{
		
		//1、创建ProcessEngine核心对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2、分析需求，获取对应的服务
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//3、调用服务方法做事情
			//创建配置
			DeploymentBuilder builder = repositoryService.createDeployment();
			//做配置
			builder
				.addClasspathResource("processes/MyProcess.bpmn")//流程规则文件 
				.addClasspathResource("processes/MyProcess.png");//流程规则图片
			//应用配置,部署流程
			builder.deploy();
	}
	
	//2、查看规则
		/*
		 * 流程定义的4大属性
		 * id:
		 * name: 规则文件process节点的name属性值
		 * key:	   规则文件process节点的id属性值
		 * version：对比系统中key，如果存在，则在最高版本加1；如果不存在则从1技术
		 */
	@Test
	public void queryProcessDefinition() throws Exception{
		//1、创建ProcessEngine核心对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2、分析需求，获取对应的服务
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//3、调用服务方法做事情
			//1、创建对应的查询对象
			ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
			//2、条件查询条件
			query.processDefinitionKey("leaveFlow");
			//3、执行查询
			List<ProcessDefinition> list = query.list(); 
			for (ProcessDefinition pd : list) {
				System.out.println("id：" + pd.getId() + ",name：" + pd.getName() + "key:" + pd.getKey() +",version：" + pd.getVersion());
			}
			
	}
	
	//3、删除规则
	@Test
	public void deleteProcess() throws Exception{
		//1、创建ProcessEngine核心对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2、分析需求，获取对应的服务
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//3、调用服务方法做事情
		String deploymentId = "1";
		//普通删除，只会删除废弃的规则
		repositoryService.deleteDeployment(deploymentId);
		
		//级联删除，会删除规则下产生的一切信息。运行时信息和历史信息，相对暴力，只推荐给管理员使用。
		repositoryService.deleteDeployment(deploymentId, true);
	}
	
	
	//4、查看规则流程图
	@Test
	public void viewProcessImage() throws Exception{
		//1、创建ProcessEngine核心对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2、分析需求，获取对应的服务
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//3、调用服务方法做事情
		String delploymentId = "10001";
		List<String> names = repositoryService.getDeploymentResourceNames(delploymentId);
		String imageName = null;
		for (String name : names) {
			if(name.indexOf(".png") >= 0){
				imageName = name;
			}
		}
		
		if(imageName != null){
			InputStream in = repositoryService.getResourceAsStream(delploymentId, imageName);
			IOUtils.copy(in, new FileOutputStream(new File("E:/1.png")));
		}
	}
}
