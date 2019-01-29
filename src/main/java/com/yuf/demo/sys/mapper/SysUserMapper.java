package com.yuf.demo.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuf.demo.sys.entity.SysUser;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuf
 * @since 2018-11-02
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
	
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
	
	@Select("select * from sys_user where username like concat(#{username},'%') order by create_date")
	List<SysUser> getUsers(@Param("username") String username);
}