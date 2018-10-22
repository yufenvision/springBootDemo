package com.yuf.demo.sys.exception;

import com.yuf.demo.utils.ExceptionEnum;

/**
* @author 作者 dyf:
* @version 创建时间：2018年10月18日 下午10:57:51
* 类说明
*/
public class GirlException extends RuntimeException{
	
	private Integer code;
	
	public GirlException(ExceptionEnum exceptionEnum){
		super(exceptionEnum.getMsg());
		this.code = exceptionEnum.getCode();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
