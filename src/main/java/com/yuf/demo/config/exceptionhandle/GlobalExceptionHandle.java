package com.yuf.demo.config.exceptionhandle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuf.demo.sys.exception.GirlException;
import com.yuf.demo.utils.Response;

import lombok.extern.slf4j.Slf4j;

/**
* @author 作者 dyf:
* @version 创建时间：2018年10月18日 下午10:42:03
* 统一异常处理
*/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandle {
	
//	@ExceptionHandler(value = Exception.class)
//	@ResponseBody
//	public Result handle(Exception e){
//		if(e instanceof GirlException){
//			GirlException girlException = (GirlException) e;
//			return ResultUtil.error(girlException.getCode(), girlException.getMessage());
//		}
//		return ResultUtil.error(99, e.getMessage());
//	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Response handle(Exception e){
		Response result = new Response();
		if(e instanceof GirlException){
			GirlException girlException = (GirlException) e;
			result.setCode(girlException.getCode());
			result.setMsg(girlException.getMessage());
			return result;
		}else{
			result.setStatus(Response.Status.ERROR);
			log.error(result.getMsg(),e);
		}
		log.info(result.getMsg());
		return result;
	}
}
