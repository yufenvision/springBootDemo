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
@RequestMapping("/layui")
public class ControllerTest {
	
	
	@Autowired
	private GirlProperties girlProperties;
	
	@Value("${value}")
	private String value;
	@Autowired
	private ISysUserService userService;

	@RequestMapping("/noPage")
	public String noPage(){
		return "/noPage";
	}

	@RequestMapping("/index")
	public String index(){
		return "/index";
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
