package com.yuf.demo.sys.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yuf.demo.utils.ResultForm;

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
@RestController
@RequestMapping("/sys/sysUser" )
public class SysUserController {
	
	@Value("{storage.path}")
	private String storagePath;
	
	@ApiOperation(value="导入摄像头信息")
	@ApiImplicitParam(name = "importFile" , value = "导入excel文件" )
	@GetMapping("userImport")
	public ResultForm userImport(@RequestParam("importFile") MultipartFile file){
		ResultForm result = new ResultForm();
		
		
		return result;
	}
	
}
