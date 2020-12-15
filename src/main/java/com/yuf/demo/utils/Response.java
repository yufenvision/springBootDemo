package com.yuf.demo.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response<T> {
	
	/**
	 * 返回状态码
	 */
	private int code = 200;
	
	/**
	 * 返回消息
	 */
	private String msg = "请求成功";
	
	/**
	 * 返回数据
	 */
	private T data;

	public Response() {
	}

	public Response(boolean flag, String msg) {
		if(flag == false){
			this.code = 500;
			this.msg = msg;
		}else {
			setStatus(Status.SUCCESS);
		}
	}

	public Response<T> success(){
		return makeResponse(Status.SUCCESS, null);
	}

	public Response<T> success(T data){
		return makeResponse(Status.SUCCESS, data);
	}

	public Response<T> fail(){
		return makeResponse(Status.FAILURE, null);
	}

	public Response<T> fail(T data){
		return makeResponse(Status.FAILURE, data);
	}

	public Response<T> makeResponse(int code, String msg, T data){
		this.code = code;
		this.msg = msg;
		this.data = data;
		return this;
	}
	public Response<T> makeResponse(Status eStatus, T data){
		setStatus(eStatus);
		this.data = data;
		return this;
	}

	/**
	 * 作为的是状态码和消息的常量
	 * @author dyf
	 * @version 2018年10月25日上午10:04:06
	 */
	public enum Status{
		SUCCESS(200,"成功"),
        ACCOUNT_WRONG(1001, "账户不存在或未激活"),
        PASSWORD_WRONG(1002, "密码错误"),
        NEED_LOGIN(1003,"需要登录"),
        UNAUTHORIZED(1004, "未授权"),
        FAILURE(1005, "操作失败"),
        TIMEOUT(1006, "请求超时"),
        DUPLICATEDATA(1007,"重复数据"),
		ERROR(9999,"未知错误");
		
		private int code;
		private String msg;

		Status(int code,String msg){
			this.code = code;
			this.msg = msg;
		}
	}

	public void setStatus(Status code){
		this.code = code.code;
		this.msg = code.msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
