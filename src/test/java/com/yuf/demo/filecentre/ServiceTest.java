package com.yuf.demo.filecentre;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.yuf.demo.sys.entity.SysUser;
import com.yuf.demo.sys.mapper.SysUserMapper;

/**
* @author 作者 dyf:
* @version 创建时间：2019年1月27日 下午3:50:09
* 类说明
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ServiceTest {
	
	@Value("${storage.path}")
    private String storageBasePath;
	
	@Value("${value}")
    private String myValue;
	
	@Autowired
	private SysUserMapper userMapper;
	
	@Test
	public void getPath(){
		System.out.println(storageBasePath);
		System.out.println(myValue);
		
		List<SysUser> list = userMapper.getUsers("用户");
		System.out.println(list);
	}
}
