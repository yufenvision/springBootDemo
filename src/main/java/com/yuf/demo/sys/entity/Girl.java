package com.yuf.demo.sys.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@TableName("girl")
public class Girl {
//	@Id
//	@GeneratedValue
	private String id;
	
	private String name;
	
	private String cupSize;
	
	@Min(value=18,message="未成年少女禁止入内")
	private Integer age;
	
}
