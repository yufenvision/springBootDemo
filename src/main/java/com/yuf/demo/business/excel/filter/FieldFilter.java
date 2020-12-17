package com.yuf.demo.business.excel.filter;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.yuf.demo.business.excel.entity.ApplyExcelImport;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.Set;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/16 17:25
 */
@Slf4j
public class FieldFilter implements Filter<ApplyExcelImport, JSONObject>{

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public void doFilter(ApplyExcelImport excelImport, JSONObject jo, FilterChain filterChain) {
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

        Set<ConstraintViolation<ApplyExcelImport>> validateSet = validator.validate(excelImport, Default.class);
        if (CollectionUtils.isNotEmpty(validateSet)) {
            validateSet.forEach(v -> excelImport.setErrorMsg((excelImport.getErrorMsg() == null ? "" : excelImport.getErrorMsg() + ", ") + v.getMessage()));
        }
        filterChain.doFilter(excelImport, jo);
    }
}
