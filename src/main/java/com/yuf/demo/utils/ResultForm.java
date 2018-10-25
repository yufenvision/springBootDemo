package com.yuf.demo.utils;

public class ResultForm<T> {
	
	/**
	 * 返回状态码
	 */
	private int status = 200;
	
	/**
	 * 返回消息
	 */
	private String msg = "请求成功";
	
	/**
	 * 返回数据
	 */
	private T data;
	
	/**
	 * 作为的是状态码和消息的常量
	 * @author dyf
	 * @version 2018年10月25日上午10:04:06
	 */
	public enum Status{
		SUCCESS(200,"成功"),
		ERROR(9999,"未知错误");
		
		private int status;
		private String msg;

		Status(int status,String msg){
			this.status = status;
			this.msg = msg;
		}
	}
	
	public void setStatus(Status status){
		this.status = status.status;
		this.msg = status.msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
