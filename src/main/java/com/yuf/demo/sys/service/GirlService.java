package com.yuf.demo.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuf.demo.sys.entity.Girl;
import com.yuf.demo.sys.mapper.GirlRepository;

@Service
public class GirlService {
	
	@Autowired
	private GirlRepository girlRepository;
	
	@Transactional
	public void insertTwo(){
		Girl girlA = new Girl();
		girlA.setCupSize("A");
		girlA.setName("女孩1");
		girlA.setAge(18);
		girlRepository.save(girlA);
		
		Girl girlB = new Girl();
		girlB.setCupSize("BBBB");
		girlB.setName("女孩2");
		girlB.setAge(19);
		girlRepository.save(girlB);
	}
}
