package com.yuf.demo.myTest.cglib_proxy;


//真实主题角色
public class UserService{

	public void save(User user) {
		//int i = 1 / 0;
		user.setId(10L);
		System.out.println("真实主题角色:" + user);
	}

	public User get(Long id) {
		System.out.println("真实主题角色:" + id);
		return new User(id, "模拟name属性");
	}

}
