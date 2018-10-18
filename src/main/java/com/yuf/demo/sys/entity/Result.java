package com.yuf.demo.sys.entity;
/**
* @author 作者 dyf:
* @version 创建时间：2018年10月18日 下午10:11:21
* 类说明 http请求返回的最外层对象
 * @param <T>
*/
public class Result<T> {
	
	/**
	 * 错误码
	 */
	private Integer code;
	
	/**
	 * 提示信息
	 */
	private String msg;
	
	/**
	 * 具体的内容
	 */
	private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
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
