package com.yuf.demo.business.filecenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuf.demo.business.filecenter.entity.FileInfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
/**
 * <p>
 * 文件表 Mapper 接口
 * </p>
 *
 * @author yuf
 * @since 2019-01-27
 */
@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {
	/**
	 * 根据查询条件获取分页文件列表
	 * @param params
	 * @param page
	 * @return
	 */
//	List<FileInfo> getFileByParams(Map params, BasePage page);


	/**
	 * 获取没有和中间表关联的无效文件id列表
	 * @return
	 */
	List<String> getUselessIds();

	/**
	 * 根据原始文件名获取fullPath
	 * @param id
	 * @return
	 */
	List<String> getFullPath(String originalName);

	/**
	 * 根据文件全路径名删除文件
	 * @param pathList
	 * @return
	 */
	Integer delFileByFullPath(List<String> pathList);
}