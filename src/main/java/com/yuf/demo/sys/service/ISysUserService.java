package com.yuf.demo.sys.service;

import com.yuf.demo.sys.entity.SysUser;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuf
 * @since 2018-11-02
 */
public interface ISysUserService extends IService<SysUser> {
	
	
	/**
	 * 获取用户分页信息
	 * @param params
	 * @return
	 */
	List<SysUser> getSysUserPage(Map params);
	
	/**
	 * 根据用户id获取用户信息
	 * @param id
	 * @return
	 */
	SysUser getUserById(String id);
}
