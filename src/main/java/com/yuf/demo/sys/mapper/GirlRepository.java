package com.yuf.demo.sys.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuf.demo.sys.entity.Girl;

/**
 * @author dyf
 * @version 2018年10月15日下午11:02:03
 */
public interface GirlRepository extends JpaRepository<Girl,Integer>{
	
	List<Girl> findByAge(Integer age);
}
