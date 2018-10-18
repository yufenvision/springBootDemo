package com.yuf.demo.sys.exception;
/**
* @author 作者 dyf:
* @version 创建时间：2018年10月18日 下午10:57:51
* 类说明
*/
public class GirlException extends RuntimeException{
	
	private Integer code;
	
	public GirlException(Integer code,String msg){
		super(msg);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
