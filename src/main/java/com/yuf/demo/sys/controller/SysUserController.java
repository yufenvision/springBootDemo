package com.yuf.demo.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yuf.demo.sys.entity.SysUser;
import com.yuf.demo.sys.service.ISysUserService;
import com.yuf.demo.utils.ResultForm;
import com.yuf.demo.utils.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javassist.runtime.Desc;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuf
 * @since 2018-11-02
 */
@Api(tags="用户管理接口")
//@RestController
@Controller
@RequestMapping("/sys/sysUser" )
public class SysUserController {
	
	@Value("{storage.path}")
	private String storagePath;
	@Autowired
	private ISysUserService userService;

	@RequestMapping("/userIndex")
	public String index(ModelMap map){
		return "userList";
	}
	
	
	@ApiOperation(value="用户分页查询")
	@ApiImplicitParam(name = "username", value= "用户名")
	@ResponseBody
	@GetMapping("/getUserPage")
	public ResultForm<List<SysUser>> getUserPage(@RequestParam Map params, Page page){
		return new ResultForm(userService.getSysUserPage(params),"查询失败");
	}

	@ApiOperation(value="根据用户id查询")
	@ApiImplicitParam(name = "userId", value= "用户id")
	@GetMapping("/getUserById")
	public ResultForm<SysUser> getUserById(@RequestParam String userId){
		return new ResultForm(userService.getUserById(userId), "查询失败");
	}
	
	@ApiOperation(value="导入用户信息")
	@ApiImplicitParam(name = "importFile" , value = "导入excel文件" )
	@GetMapping("/userImport")
	public ResultForm userImport(@RequestParam("importFile") MultipartFile file){
		ResultForm result = new ResultForm();
		
		
		return result;
	}
	
	@ApiOperation(value="新增用户")
	@ResponseBody
	@PostMapping("/userAdd")
	public ResultForm<SysUser> userAdd(@RequestBody SysUser user){
		user.createDefaultInfo();
		return new ResultForm(userService.insert(user), "新增失败");
	}

	@ApiOperation(value="修改用户")
	@ResponseBody
	@PostMapping("/userUpdate")
	public ResultForm<SysUser> userUpdate(@RequestBody SysUser user){
		return new ResultForm(userService.update(user, new EntityWrapper<SysUser>().eq("id", user.getId())),  "修改失败");
	}
	
	//逻辑删除
	@ApiOperation(value="删除用户")
	@ApiImplicitParam(name = "用户id", value ="id")
	@ResponseBody
	@GetMapping("/userDel")
	public ResultForm userDel(String id){
		return new ResultForm(userService.deleteById(id), "删除失败");
	}
}
