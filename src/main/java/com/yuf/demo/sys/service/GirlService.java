package com.yuf.demo.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuf.demo.sys.entity.Girl;
import com.yuf.demo.sys.exception.GirlException;
import com.yuf.demo.sys.mapper.GirlMapper;
import com.yuf.demo.utils.ExceptionEnum;

@Service
public class GirlService {
	
	@Autowired
	private GirlMapper girlMapper;
	
	@Transactional
	public void insertTwo(){
		Girl girlA = new Girl();
		girlA.setCupSize("A");
		girlA.setName("女孩1");
		girlA.setAge(18);
		girlMapper.insert(girlA);
		
		Girl girlB = new Girl();
		girlB.setCupSize("BBBB");
		girlB.setName("女孩2");
		girlB.setAge(19);
		girlMapper.insert(girlB);
	}
	
	public void getAge(Integer id) throws Exception{
		Girl girl = girlMapper.selectById(id);
		Integer age = girl.getAge();
		if(age < 10){
			//返回 code 100
//			throw new Exception("你还在上小学吧");
			throw new GirlException(ExceptionEnum.PRIMARY_SCHOOL);
		}else if(age > 10 && age < 16){
			//返回 code 101
//			throw new Exception("你可能在上初中");
			throw new GirlException(ExceptionEnum.MIDDLE_SCHOOL);
		}else{
			//返回 code 99
			throw new GirlException(ExceptionEnum.ADD_MONEY);
		}
		
		//....如果大于16岁，加钱
	}
	
	
	/**
	 * 通过Id查询一个女生的信息
	 * @param id
	 * @return
	 */
	public Girl findOne(Integer id){
		return girlMapper.selectById(id);
	}
}
