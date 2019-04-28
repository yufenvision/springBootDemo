package com.yuf.demo.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public ResultForm() {
	}

	/**
	 * boolean类型结果返回，新增/修改失败
	 * @return:  ResultForm
	 * @param: flagFromDB db新增修改结果
	 * @param: msg 失败提示
	 * @Author: dyf
	 * @Date: 2019/4/28 14:57
	*/
	public ResultForm(boolean flagFromDB, String msg) {
		if(flagFromDB == false){
			this.msg = msg;
			this.setStatus(Status.FAILURE);
		}
	}
	
	/**
	 * 对象或列表结果返回
	 * @return: ResultForm
	 * @param: data db返回的结果对象或列表
	 * @param: msg 失败提示
	 * @Author: dyf 
	 * @Date: 2019/4/28 15:00
	*/
	public ResultForm(T data, String msg) {
		if(data == null){
			this.msg = msg;
			this.setStatus(Status.FAILURE);
		}
		this.data = data;
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
