package com.yuf.demo.business.excel.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yuf.demo.business.excel.entity.ApplyExcelImport;
import com.yuf.demo.business.filecenter.entity.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: dyf
 * @Date: 2020/12/12 13:32
 * @Description:
 */
@Mapper
public interface ApplyExcelImportDao extends BaseMapper<ApplyExcelImport> {

    @Insert("<script>"  +
            "INSERT INTO sichuan_center_person_info_excel_import(id,error_msg,ywlsh,name,id_card,source,photo,type,address,phone,face_url,place_code,import_id,push_code,push_msg) "
            + "VALUES <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">"
            + "(#{item.id},#{item.errorMsg},#{item.ywlsh},#{item.name},#{item.idCard},#{item.source},#{item.photo},#{item.type},#{item.address},#{item.phone},#{item.faceUrl},#{item.placeCode},#{item.importId},#{item.pushCode},#{item.pushMsg})"
            + " </foreach>"
            + "ON DUPLICATE KEY UPDATE id_card=values(id_card),place_code=values(place_code)"
            + "</script>")
    Integer insertBatch(@Param("list") List<ApplyExcelImport> list);

    @Insert("<script>"  +
            "INSERT INTO sichuan_center_person_info_excel_import(id,error_msg,ywlsh,name,id_card,source,type,address,phone,face_url,place_code,import_id,push_code,push_msg) "
            + "VALUES <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">"
            + "(#{item.id},#{item.errorMsg},#{item.ywlsh},#{item.name},#{item.idCard},#{item.source},#{item.type},#{item.address},#{item.phone},#{item.faceUrl},#{item.placeCode},#{item.importId},#{item.pushCode},#{item.pushMsg})"
            + " </foreach>"
            + "ON DUPLICATE KEY UPDATE id_card=values(id_card),place_code=values(place_code),push_code=values(push_code),push_msg=values(push_msg)"
            + "</script>")
    Integer updateBatch(@Param("list") List<ApplyExcelImport> list);




    @Select("SELECT id,place_code placeCode, ywlsh,NAME,id_card idCard,source,photo,type,address,phone FROM"
            +" sichuan_center_person_info_excel_import"
            +" WHERE"
            +" place_code = #{placeCode}"
            +" AND error_msg IS NULL"
            +" and push_code is NULL")
    List<ApplyExcelImport> getPushList(String placeCode);
}
