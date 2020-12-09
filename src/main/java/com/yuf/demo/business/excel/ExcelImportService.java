package com.yuf.demo.business.excel;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSON;
import com.yuf.demo.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: dyf
 * @Date: 2020/12/9 19:26
 * @Description:
 */
@Slf4j
@Service
public class ExcelImportService {
    @Autowired
    ApplyInfoVerifyHander applyInfoVerifyHander;

    @Transactional
    public Response importApplyInfo(MultipartFile file){
        ImportParams importParams = new ImportParams();
        importParams.setNeedVerfiy(true);//打开验证
        importParams.setVerifyHandler(applyInfoVerifyHander);

        ExcelImportResult<ApplyExcelDTO> excelResult;
        try{
            excelResult = ExcelImportUtil.importExcelMore(file.getInputStream(), ApplyExcelDTO.class, importParams);
        } catch (Exception e) {
            return new Response().fail(e.getMessage());
        }

        log.info("导入失败记录：{}", JSON.toJSONString(excelResult.getFailList()));

        return new Response().success(excelResult.getList());
    }

}
