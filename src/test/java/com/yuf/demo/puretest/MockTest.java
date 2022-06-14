package com.yuf.demo.puretest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@Slf4j
@RunWith(SpringRunner.class)
public class MockTest {

	@Test
	public void test1(){
		// 创建Mock对象，参数可以是类或者接口
		List<String> list = mock(List.class);

		//设置方法的预期返回值
		when(list.get(0)).thenReturn("list1");
		when(list.get(1)).thenThrow(new ArrayIndexOutOfBoundsException("超出范围"));

		String result = list.get(0);

		//验证方法调用
		//verify(list).get(0);

		//断言，list的第一个元素是否是"list1"
		Assert.assertEquals(result, "list1");
	}

}
