package com.yuf.demo.business.excel.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuf.demo.business.excel.entity.ApplyExcelImport;
import com.yuf.demo.business.filecenter.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: dyf
 * @Date: 2020/12/12 13:32
 * @Description:
 */
@Mapper
public interface ApplyExcelImportDao extends BaseMapper<ApplyExcelImport> {


    void insertBatch(List<ApplyExcelImport> list);

}
