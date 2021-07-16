package com.yuf.demo.launch_config_bean_test.configTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UseBeanTest {
	
	public static void main(String[] args) {
		//通过注解获取
		//getByAnnotationConfig();

		//通过xml获取
		getByXmlConfig();

	}

	public static void getByXmlConfig(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-myCofig.xml");
//		ApplicationContext context = new AnnotationConfigApplicationContext(MyConfigurationByXML.class);
		MyBean myBean = context.getBean(MyBean.class);//通过bean方法名称,也就是bean对象的名称获取该对象
		MyBean1 myBean1 = (MyBean1) context.getBean("xx");//默认取bean方法名称
		System.out.println(myBean.getValue());
		System.out.println(myBean1.getValue());
	}


	public static void getByAnnotationConfig(){
		ApplicationContext context = new AnnotationConfigApplicationContext(MyConfigurationByCode.class);

		MyBean myBean = (MyBean) context.getBean("myBean");//通过bean方法名称,也就是bean对象的名称获取该对象
		MyBean1 myBean1 = context.getBean(MyBean1.class);
		System.out.println(myBean.getValue());
		System.out.println(myBean1.getValue());
	}



}
