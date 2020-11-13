package com.yuf.demo.config.aop;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
	
//	@Pointcut("execution(public * com.yuf.demo.sys.controller.*.*(..))")
	@Pointcut("@annotation(com.yuf.demo.config.aop.LogInfo)")
	public void log(){
	}
	
	@Before("log()")
	public void doBefore(JoinPoint joinpoint){
		log.info("AOP拦截开始-----------------------------");
		//开始记录http请求
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		//url
		log.info("url={}",request.getRequestURL());
		//method
		log.info("method={}",request.getMethod());
		
		//id
		log.info("id={}",request.getRemoteAddr());
		
		//类方法
		log.info("class_method={}",joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName());
		
		//参数
		log.info("args={}",joinpoint.getArgs());
		
		
	}
	
	@After("log()")
	public void doAfter(){
		log.info("AOP拦截结束----------------------");
	}
	
	//获取到返回的对象
	@AfterReturning(returning="object", pointcut="log()")
	public void doAfterReturning(Object object){
		log.info("response={}",object);
	}
}
