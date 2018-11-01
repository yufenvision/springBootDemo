package com.yuf.demo.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuf.demo.sys.entity.Girl;

@Mapper
public interface GirlMapper extends BaseMapper<Girl>{
	
	
	List<Girl> getGirlList(Map params);
	
}
