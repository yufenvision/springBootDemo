package com.yuf.demo.config.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
* @author 作者 dyf:
* @version 创建时间：2018年10月17日 下午9:52:14
* 类说明
*/
@Aspect
@Component
public class HttpAspect {
	
//	@Before("execution(public * com.yuf.demo.sys.controller.*.*(..))")
//	public void doBefore(){
//		System.out.println("==== before ======");
//	}
//	
//	@After("execution(public * com.yuf.demo.sys.controller.*.*(..))")
//	public void doAfter(){
//		System.out.println("==== after ======");
//	}
	
	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
	
	@Pointcut("execution(public * com.yuf.demo.sys.controller.*.*(..))")
	public void log(){
	}
	
	@Before("log()")
	public void doBefore(JoinPoint joinpoint){
//		System.out.println("==== before ======");
		logger.info("==== before ======");
		//开始记录http请求
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		//url
		logger.info("url={}",request.getRequestURL());
		//method
		logger.info("method={}",request.getMethod());
		
		//id
		logger.info("id={}",request.getRemoteAddr());
		
		//类方法
		logger.info("class_method={}",joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName());
		
		//参数
		logger.info("args={}",joinpoint.getArgs());
		
		
	}
	
	@After("log()")
	public void doAfter(){
//		System.out.println("==== after ======");
		logger.info("==== after ======");
	}
	
	//获取到返回的对象
	@AfterReturning(returning="object", pointcut="log()")
	public void doAfterReturning(Object object){
		logger.info("response={}",object);
	}
}
