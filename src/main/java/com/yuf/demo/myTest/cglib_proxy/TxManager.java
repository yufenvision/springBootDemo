package com.yuf.demo.myTest.cglib_proxy;

//事务对象
public class TxManager {
	// 开启事务
	public void begin() {
		System.out.println("开启事务");
	}

	// 提交事务
	public void commit() {
		System.out.println("提交事务");
	}

	// 回滚事务
	public void rollback(Exception e) {
		System.out.println("回滚事务:"+e.getMessage());
	}

	// 关闭资源
	public void close() {
		System.out.println("关闭资源");
	
	}	
}
