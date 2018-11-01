package com.yuf.demo.myTest.configTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UseBeanTest {
	
	public static void main(String[] args) {
//		ApplicationContext context = new AnnotationConfigApplicationContext(MyConfigurationByCode.class);
//		ApplicationContext context = new AnnotationConfigApplicationContext(MyConfigurationByXML.class);
//		MyBean myBean = (MyBean) context.getBean("myBean");//默认取bean方法名称
//		System.out.println(myBean.getValue());
		
//		ApplicationContext context1 = new ClassPathXmlApplicationContext("com/yuf/demo/myTest/configTest/spring-myCofig.xml");
		ApplicationContext context1 = new ClassPathXmlApplicationContext("classpath:spring-myCofig.xml");
		MyBean myBean = (MyBean) context1.getBean("myBean");//默认取bean方法名称
		MyBean1 myBean1 = (MyBean1) context1.getBean("myBean1");//默认取bean方法名称
		System.out.println(myBean.getValue());
		System.out.println(myBean1.getValue());
	}
}
