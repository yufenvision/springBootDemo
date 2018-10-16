package com.yuf.demo.sys.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class Girl {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private String cupSize;
	
	private Integer age;
	
}
