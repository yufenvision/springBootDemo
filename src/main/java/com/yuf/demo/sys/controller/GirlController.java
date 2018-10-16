package com.yuf.demo.sys.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuf.demo.sys.entity.Girl;
import com.yuf.demo.sys.mapper.GirlRepository;

/**
 * @author dyf
 * @version 2018年10月15日下午11:01:28
 */
@RestController
public class GirlController {
	@Autowired
	private GirlRepository girlRepository;
	
	@GetMapping("/girls")
	public List<Girl> girls(){
		return girlRepository.findAll();
	}
	
	@GetMapping("/girlOne/{id}")
	public Optional<Girl> girlOne(@PathVariable("id") Integer id){
		return girlRepository.findById(id);
	}
	
	@PostMapping("/girlAdd")
	public Girl girlAdd(@RequestParam("name") String name
			,@RequestParam("cupSize") String cupSize
			,@RequestParam("age") Integer age){
		Girl girl = new Girl();
		girl.setName(name);
		girl.setAge(age);
		girl.setCupSize(cupSize);
		
		return girlRepository.save(girl);
	}
	
	/**
	 * 更新
	 * @param id
	 * @param name
	 * @param cupSize
	 * @param age
	 * @return
	 */
	@PutMapping("/girlUpdate/{id}")
	public Girl girlUpdate(@PathVariable("id") Integer id
			,@RequestParam("name") String name
			,@RequestParam("cupSize") String cupSize
			,@RequestParam("age") Integer age){
		Girl girl = new Girl();
		girl.setId(id);
		girl.setName(name);
		girl.setAge(age);
		girl.setCupSize(cupSize);
		
		return girlRepository.save(girl);
	}
	
	
	@DeleteMapping("/girlDel/{id}")
	public void girlDel(@PathVariable("id") Integer id){
		girlRepository.deleteById(id);
	}
	
	@GetMapping("/girlGetByAge")
	public List<Girl> girlGetByAge(@RequestParam("age") Integer age){
		return girlRepository.findByAge(age);
	}
}
