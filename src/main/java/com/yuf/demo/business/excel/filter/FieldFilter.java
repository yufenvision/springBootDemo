package com.yuf.demo.business.excel.filter;

import com.alibaba.fastjson.JSONObject;
import com.yuf.demo.business.excel.entity.ApplyExcelImport;

import java.util.Date;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/16 17:25
 */
public class FieldFilter implements Filter<ApplyExcelImport, JSONObject>{

    @Override
    public void doFilter(ApplyExcelImport excelImport, JSONObject jo, FilterChain filterChain) {
        StringBuffer sb = new StringBuffer();
        excelImport.setYwlsh(jo.getString("ywlsh"));
        excelImport.setName(jo.getString("name"));
        excelImport.setIdCard(jo.getString("idCard"));
        excelImport.setSource(jo.getString("source"));
        excelImport.setPhoto(jo.getString("photo"));
        excelImport.setPhone(jo.getString("phone"));
        excelImport.setType(jo.getString("type"));
        excelImport.setAddress(jo.getString("address"));
        excelImport.setPlaceCode(jo.getString("xqid"));
        excelImport.setImportId(jo.getString("personId"));
        excelImport.setCreateTime(new Date());

        filterChain.doFilter(excelImport, jo);
    }
}
