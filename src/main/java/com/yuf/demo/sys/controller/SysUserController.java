package com.yuf.demo.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	public ResultForm<List<SysUser>> getUserPage(@RequestParam Map params, Page page){
		return ResultUtil.getResult(userService.getSysUserPage(params));
	}

	@ApiOperation(value="根据用户id查询")
	@ApiImplicitParam(name = "userId", value= "用户id")
	@GetMapping("/getUserById")
	public ResultForm<SysUser> getUserById(@RequestParam String userId){
		return ResultUtil.getResult(userService.getUserById(userId));
	}
	
	@ApiOperation(value="导入用户信息")
	@ApiImplicitParam(name = "importFile" , value = "导入excel文件" )
	@GetMapping("/userImport")
	public ResultForm userImport(@RequestParam("importFile") MultipartFile file){
		ResultForm result = new ResultForm();
		
		
		return result;
	}
	
	@ApiOperation(value="新增用户")
	@PostMapping("/userAdd")
	public ResultForm<SysUser> userAdd(SysUser user){
		return ResultUtil.getResult(userService.insert(user), ResultForm.Status.FAILURE, "新增失败");
	}
	
	@ApiOperation(value="修改用户")
	@PostMapping("/userUpdate")
	public ResultForm<SysUser> userUpdate(@RequestBody SysUser user){
		return ResultUtil.getResult(userService.update(user, new EntityWrapper<SysUser>().eq("id", user.getId())), ResultForm.Status.FAILURE, "修改失败");
	}
	
	//逻辑删除
	@ApiOperation(value="删除用户")
	@ApiImplicitParam(name = "ids", value ="[\"id1\",\"id2\",....]")
	@DeleteMapping("/userDel")
	public ResultForm<Map<String, Integer>> userDel(@RequestBody List<String> ids){
		int success = 0;
		for (String id : ids) {
			success = userService.deleteById(id) ? ++success : success;
		}
		return ResultUtil.getResult(ids.size(), success);
	}
}
