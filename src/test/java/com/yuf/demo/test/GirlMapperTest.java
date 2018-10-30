package com.yuf.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yuf.demo.sys.entity.Girl;
import com.yuf.demo.sys.mapper.GirlMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GirlMapperTest {
	@Autowired 
	private GirlMapper girlMapper;
	
	@Test
	public void test1(){
		System.out.println(girlMapper.selectById(1));
	}
	
	@Test
	public void test2(){
		Girl girl = new Girl();
		girl.setName("小红");
		girl.setAge(29);
		girl.setCupSize("D");
		System.out.println(girlMapper.insert(girl));
	}
}
