package com.yuf.demo.utils;

import com.yuf.demo.sys.entity.Result;

/**
* @author 作者 dyf:
* @version 创建时间：2018年10月18日 下午10:16:19
* 类说明
*/
public class ResultUtil {
	
	public static Result success(Object object){
		Result result = new Result();
		result.setCode(200);
		result.setMsg("成功");
		result.setData(object);
		return result;
	}
	
	public static Result success(){
		return success(null);
	}
	
	public static Result error(Integer code,String msg){
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
	
}
