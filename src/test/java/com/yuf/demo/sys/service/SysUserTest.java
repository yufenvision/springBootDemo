package com.yuf.demo.sys.service;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yuf.demo.sys.entity.SysUser;

/**
* @author 作者 dyf:
* @version 创建时间：2019年1月26日 下午5:25:54
* 类说明
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserTest {
	
	@Autowired
	private ISysUserService userService;
	
	@Test
	public void userAdd(){
		int i = 20;
		while(i > 0){
			SysUser user = new SysUser();
			user.createDefaultInfo(user);
			user.setUsername("用户" + i);
			user.setPassword("密码：" + (int)(Math.random()*i));
			user.setMobilePhone("电话号码");
			user.setEmail("邮箱");
			user.setCreateBy("admin");
			userService.insert(user);
			
			i--;
		}
		
		
		
	}
}
