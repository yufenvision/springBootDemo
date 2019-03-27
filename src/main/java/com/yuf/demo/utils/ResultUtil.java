package com.yuf.demo.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 作者 dyf:
* @version 创建时间：2018年10月18日 下午10:16:19
* 类说明
*/
public class ResultUtil {
	
	/**
	 * boolean类型结果返回，新增/修改失败
	 * @param flag DB操作成功失败返回
	 * @param status 失败状态
	 * @param msg 失败消息
	 * @return
	 */
	public static <T> ResultForm<T> getResult(Boolean flag, ResultForm.Status status, String msg){
		ResultForm<T> result = new ResultForm<>();
		if(!flag){
			result.setStatus(status);
			result.setMsg(msg);
		}
		return result;
	}
	
	/**
	 * 对象类型结果返回，根据id查询结果
	 * @param data DB返回对象
	 * @return
	 */
	public static <F> ResultForm<F> getResult(F data){
		ResultForm<F> result = new ResultForm<>();
		result.setData(data);
		return result;
	}
	
	/**
	 * 列表类型结果返回，分页查询结果
	 * @param <F>
	 * @param data DB的list分页数据
	 * @return
	 */
	public static <F> ResultForm<List<F>> getResult(List<F> data){
		ResultForm<List<F>> result = new ResultForm<>();
		result.setData(data);
		return result;
	}
	
	/**
	 * 删除结果返回
	 * @param total 删除总条数
	 * @param success 成功删除数
	 * @return
	 */
	public static ResultForm<Map<String, Integer>> getResult(int total, int success){
		ResultForm<Map<String, Integer>> result = new ResultForm<>();
		Map<String, Integer> map = new HashMap<>();
		map.put("delSucess", success);
		map.put("delFailure", total - success);
		result.setData(map);
		return result;
	}
	
}
