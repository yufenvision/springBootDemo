package com.yuf.demo.myTest.cglib_proxy;

//import org.junit.Test;

public class MainTest {
	// 真实主题角色
	UserService targetObject = new UserService();
	// 事务对象
	TxManager txManager = new TxManager();
	
	
//	// 没有代理的时候
//	@Test
//	public void test1() throws Exception {
//		User user = new User();
//		user.setName("xxxx");
//		targetObject.save(user);
//	}
//
//
//	@Test
//	public void test2() throws Exception {
//		// 代理对象
//		CglibProxy jdkProxy = new CglibProxy(targetObject,txManager);
//		UserService userService = (UserService)jdkProxy.createObject();
//		// JDK代理后的类名：$Proxy5 implements IUserService
//		System.out.println("test代理对象:"+userService+getClass());
//		System.out.println("test代理对象是否是真是主题角色子类:" + (userService instanceof UserService));
//
//		User user = new User();
//		user.setName("xxxx");
//		userService.save(user);
//
////		System.out.println(userService.get(1000L));
//	}
}
