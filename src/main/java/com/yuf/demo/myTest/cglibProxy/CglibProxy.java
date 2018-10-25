package com.yuf.demo.myTest.cglibProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor{
	// 最终必须要传入真实主题角色，传入接口(抽象主题角色，这样才能实现多次代理)
	private Object targetObject;// 需要代理的对象，本质是UserServiceImpl
	
	//传入事务对象
	private TxManager txManager;
	
	//构造器传入参数
	public CglibProxy(Object targetObject, TxManager txManager) {
		super();
		this.targetObject = targetObject;
		this.txManager = txManager;
	}
	
	// 返回经过代理后的对象====Object proxy $Proxy5
	public Object createObject() {
		//实例化一个增强对象
		Enhancer enhancer = new Enhancer();
		//对那个目标对象进行代理
		enhancer.setSuperclass(targetObject.getClass());
		//添加回调方法
		enhancer.setCallback(this);
		
		//返回经过cglib代理后的对象
		return enhancer.create();
	}
	/**
	 * @param Object
	 *            proxy 代理任何一个接口后类的实例化对象，一般都不用此参数
	 * @param Method
	 *            method 反射的方法对象
	 * @param Object
	 *            [] args args参数，真实主题角色调用的参数
	 * @param MethodProxy
	 *            methodProxy 反射的方法代理对象，一般都不用此参数            
	 * @return Object 通过method对象调用真实主题角色之后的返回值
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,MethodProxy methodProxy) throws Throwable {
		// getClass()方法:public final native Class<?> getClass();
		// 如果一个方法添加native，表示这个方法是本地(底层)方法，在java代码不用写方法体，代码在是用c/c++实现的
		//getClass()返回此 Object 的运行时类。
		//对象方法 toString()方法:public String toString()
		System.out.println("proxy：" + proxy.getClass());
		System.out.println("method：" + method.getName());
		System.out.println("args：" + Arrays.toString(args));
		Object returnObject = null;
		try {
			if("get".equals(method.getName())){//不需要事物
				// 第一个参数：目标对象，调用那个对象里面的方法UserServiceImpl.get save
				// 第二个参数：目标对象调用的时候需要的参数get(20L),save(user)
				returnObject = method.invoke(targetObject, args);
			}else{
				txManager.begin();
				returnObject = method.invoke(targetObject, args);
				txManager.commit();
			}
			
		} catch (Exception e) {
			txManager.rollback(e);
		}finally{
			txManager.close();
		}
		
		
		return returnObject;
	}

}
