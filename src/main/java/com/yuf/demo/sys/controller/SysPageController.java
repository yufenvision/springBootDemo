package com.yuf.demo.sys.controller;

import com.yuf.demo.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: dyf
 * @Date: 2019/6/10 22:12
 * @Description:
 */
@Api(tags="页面入口")
@Slf4j
@Controller
//@RequestMapping("/layui")
public class SysPageController {

    @Value("${value}")
    private String value;
    @Autowired
    private ISysUserService userService;

    @RequestMapping("/sys/sysUser/userIndex")
    public String index(ModelMap map){
        return "userList";
    }

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
        ModelAndView modelAndView = new ModelAndView("index");
        map.addAttribute("message", "回家哈哈");
        return modelAndView;
    }
}
