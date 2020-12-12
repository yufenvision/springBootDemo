package com.yuf.demo.business.excel.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuf.demo.business.excel.entity.ApplyExcelImport;
import com.yuf.demo.business.filecenter.entity.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: dyf
 * @Date: 2020/12/12 13:32
 * @Description:
 */
@Mapper
public interface ApplyExcelImportDao extends BaseMapper<ApplyExcelImport> {

    @Insert("<script>"  +
            "INSERT INTO sichuan_center_person_info_excel_import(id,error_msg,ywlsh,name,id_card,source,photo,type,address,phone,face_url,place_code,import_id) "
            + "VALUES <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">"
            + "(#{item.id},#{item.errorMsg},#{item.ywlsh},#{item.name},#{item.idCard},#{item.source},#{item.photo},#{item.type},#{item.address},#{item.phone},#{item.faceUrl},#{item.placeCode},#{item.importId})"
            + " </foreach>"
            + "</script>")
    Integer insertBatch(@Param("list") List<ApplyExcelImport> list);
    //+ "on duplicate key update id_card=#{item.idCard} and place_code=#{item.placeCode}"
}
