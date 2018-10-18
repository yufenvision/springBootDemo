package com.yuf.demo.handle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuf.demo.sys.entity.Result;
import com.yuf.demo.sys.exception.GirlException;
import com.yuf.demo.utils.ResultUtil;

/**
* @author 作者 dyf:
* @version 创建时间：2018年10月18日 下午10:42:03
* 类说明
*/

@ControllerAdvice
public class ExceptionHandle {
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result handle(Exception e){
		if(e instanceof GirlException){
			GirlException girlException = (GirlException) e;
			return ResultUtil.error(girlException.getCode(), girlException.getMessage());
		}
		return ResultUtil.error(99, e.getMessage());
	}
}
