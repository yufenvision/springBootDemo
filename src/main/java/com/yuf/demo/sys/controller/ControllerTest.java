package com.yuf.demo.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuf.demo.sys.entity.GirlProperties;
import com.yuf.demo.sys.entity.SysUser;
import com.yuf.demo.sys.service.ISysUserService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags="测试接口")
@Slf4j
@Controller
@RequestMapping("/page")
public class ControllerTest {
	
	
	@Autowired
	private GirlProperties girlProperties;
	
	@Value("${value}")
	private String value;
	@Autowired
	private ISysUserService userService;
	
	@RequestMapping("/hello")
	public String hello(ModelMap map){
		map.put("message", "我用了新的方式");
		List<SysUser> list = userService.selectList(null);
		map.put("users", list);
//		return girlProperties.getCupSize()+"---"+girlProperties.getAge();
		SysUser sysUser = new SysUser();
		sysUser.createDefaultInfo();
		map.put("sysUser", sysUser);
		return "userAdd";
	}
	
	@RequestMapping("/value")
	public String value(){
		return value;
	}
	
	@RequestMapping("/")
	public ModelAndView getHello(ModelMap map){
		ModelAndView modelAndView = new ModelAndView("hello");
		map.addAttribute("message", "回家哈哈");
		return modelAndView;
	}
}
