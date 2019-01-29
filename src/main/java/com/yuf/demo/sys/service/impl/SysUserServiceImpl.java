package com.yuf.demo.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yuf.demo.sys.entity.SysUser;
import com.yuf.demo.sys.mapper.SysUserMapper;
import com.yuf.demo.sys.service.ISysUserService;
import com.yuf.demo.utils.ResultForm;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuf
 * @since 2018-11-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Autowired
	private SysUserMapper userMapper;

	@Override
	public SysUser getUserById(String id) {
		return userMapper.getUserById(id);
	}

	@Override
	public List<SysUser> getSysUserPage(Map params) {
		
		return userMapper.getSysUserPage(params);
	}
	

}
