package com.yuf.demo.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@TableName("sys_user")
public class SysUser {
	
	private String id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private Integer phoneNumber;
	
}
