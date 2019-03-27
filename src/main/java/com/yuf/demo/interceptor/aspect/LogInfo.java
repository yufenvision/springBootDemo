package com.yuf.demo.interceptor.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author 作者 dyf:
* @version 创建时间：2019年3月27日 上午10:10:58
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogInfo {
	String logContent() default "";
	int logLevel() default 1;
}
