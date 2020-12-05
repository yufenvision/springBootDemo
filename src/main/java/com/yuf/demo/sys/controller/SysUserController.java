package com.yuf.demo.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yuf.demo.sys.entity.SysUser;
import com.yuf.demo.sys.service.ISysUserService;
import com.yuf.demo.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuf
 * @since 2018-11-02
 */
@Api(tags="用户管理接口")
@RestController
@RequestMapping("/sys/sysUser" )
public class SysUserController {
	
	@Value("{storage.path}")
	private String storagePath;
	@Autowired
	private ISysUserService userService;

	@ApiOperation(value="用户分页查询")
	@ApiImplicitParam(name = "username", value= "用户名")
	@GetMapping("/getUserPage")
	public Response<List<SysUser>> getUserPage(@RequestParam Map params, Page page){
		return new Response().success(userService.getSysUserPage(params));
	}

	@ApiOperation(value="根据用户id查询")
	@ApiImplicitParam(name = "userId", value= "用户id")
	@GetMapping("/getUserById")
	public Response<SysUser> getUserById(@RequestParam String userId){
		return new Response().success(userService.getUserById(userId));
	}
	
	@ApiOperation(value="导入用户信息")
	@ApiImplicitParam(name = "importFile" , value = "导入excel文件" )
	@GetMapping("/userImport")
	public Response userImport(@RequestParam("importFile") MultipartFile file){
		Response result = new Response();
		
		
		return result;
	}
	
	@ApiOperation(value="新增用户")
	@PostMapping("/userAdd")
	public Response<SysUser> userAdd(@RequestBody SysUser user){
		user.createDefaultInfo();
		return new Response(userService.insert(user), "新增失败");
	}

	@ApiOperation(value="修改用户")
	@PostMapping("/userUpdate")
	public Response<SysUser> userUpdate(@RequestBody SysUser user){
		return new Response(userService.update(user, new EntityWrapper<SysUser>().eq("id", user.getId())),  "修改失败");
	}
	
	//逻辑删除
	@ApiOperation(value="删除用户")
	@ApiImplicitParam(name = "用户id", value ="id")
	@GetMapping("/userDel")
	public Response userDel(String id){
		return new Response(userService.deleteById(id), "删除失败");
	}
}
