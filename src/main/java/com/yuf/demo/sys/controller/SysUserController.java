package com.yuf.demo.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yuf.demo.sys.entity.SysUser;
import com.yuf.demo.sys.service.ISysUserService;
import com.yuf.demo.utils.ResultForm;

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
@RestController
@RequestMapping("/sys/sysUser" )
public class SysUserController {
	
	@Value("{storage.path}")
	private String storagePath;
	@Autowired
	private ISysUserService userService;
	
	@ApiOperation(value="导入摄像头信息")
	@ApiImplicitParam(name = "importFile" , value = "导入excel文件" )
	@GetMapping("/userImport")
	public ResultForm userImport(@RequestParam("importFile") MultipartFile file){
		ResultForm result = new ResultForm();
		
		
		return result;
	}
	
	@ApiOperation(value="添加用户")
	@PostMapping("/userAdd")
	public ResultForm<SysUser> userAdd(@RequestBody SysUser user){
		ResultForm<SysUser> result = new ResultForm<>();
		String msg = userService.insert(user) ? "插入成功" : "插入失败";
		result.setMsg(msg);
		return result;
	}
	
	@ApiOperation(value="查询用户")
	@ApiImplicitParam(name = "username", value= "用户名")
	@GetMapping("/getUserPage")
	public ResultForm<List<SysUser>> getUserPage(@RequestParam Map map){
		ResultForm<List<SysUser>> result = new ResultForm<>();
		List<SysUser> list = null;
		String username = (String) map.get("userName");
		if(username != null){
			list = userService.selectList(new EntityWrapper<SysUser>().like("username", username, SqlLike.RIGHT).orderBy(true, "create_date", false));
		}else{
			list = userService.selectList((new EntityWrapper<SysUser>().orderBy(true, "create_date", false)));
		}
		result.setData(list);
		return result;
	}
}
