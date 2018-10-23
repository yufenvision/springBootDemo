package com.yuf.demo.utils;

public enum ExceptionEnum {
	SUCCESS(200,"请求成功"),
	ERROR(999,"未知错误"),
	PRIMARY_SCHOOL(100,"你还在上小学吧"),
	MIDDLE_SCHOOL(101,"你可能在上初中");
	
	private int code = 200;
	private String msg = "请求成功";
	
	private ExceptionEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
