package com.yuf.demo.sys.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuf.demo.sys.entity.Girl;
import com.yuf.demo.sys.entity.Result;
import com.yuf.demo.sys.mapper.GirlRepository;
import com.yuf.demo.sys.service.GirlService;
import com.yuf.demo.utils.ResultForm;

/**
 * @author dyf
 * @version 2018年10月15日下午11:01:28
 */
@RestController
public class GirlController {
	
	private final static Logger logger = LoggerFactory.getLogger(GirlController.class);
	
	@Autowired
	private GirlRepository girlRepository;
	
	@Autowired
	private GirlService girlService;
	
	@GetMapping("/girls")
	public List<Girl> girls(){
		return girlRepository.findAll();
	}
	
	@GetMapping("/girlOne/{id}")
	public Optional<Girl> girlOne(@PathVariable("id") Integer id){
		return girlRepository.findById(id);
	}
	
	@PostMapping("/girlAdd")
	public ResultForm<Girl> girlAdd(@Valid Girl girl,BindingResult bindingResult){
//		Result<Girl> result = new Result<>();
//		if(bindingResult.hasErrors()){
//			return ResultUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
//		}
//		girl.setName(girl.getName());
//		girl.setAge(girl.getAge());
//		girl.setCupSize(girl.getCupSize());
//		return ResultUtil.success(girlRepository.save(girl));
		ResultForm<Girl> result = new ResultForm<>();
		if(bindingResult.hasErrors()){
			result.setStatus(1);
			result.setMsg(bindingResult.getFieldError().getDefaultMessage());
//			result.setStatus(ResultForm.Status.ERROR);
		}
		girl.setName(girl.getName());
		girl.setAge(girl.getAge());
		girl.setCupSize(girl.getCupSize());
		girlRepository.save(girl);
		return result;
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
	
	@PostMapping(value="/girls/two")
	public void girlTwo(){
		girlService.insertTwo();
	}
	
	@GetMapping("/girlAge/{id}")
	public void girlAge(@PathVariable("id") Integer id) throws Exception{
		girlService.getAge(id);
	}
}
