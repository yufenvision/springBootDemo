//package com.yuf.demo.sys.service;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.yuf.demo.sys.entity.Girl;
//import com.yuf.demo.sys.service.GirlService;
//
//import junit.framework.Assert;
//
///**
// * @author dyf
// * @version 2018年10月22日下午9:45:03
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class GirlServiceTest {
//	
//	@Autowired
//	private GirlService girlService;
//	
//	@Test
//	public void findOneTest(){
//		Girl girl = girlService.findOne(73).get();
//		Assert.assertEquals(new Integer(13), girl.getAge());
//	}
//	
//	
//}
