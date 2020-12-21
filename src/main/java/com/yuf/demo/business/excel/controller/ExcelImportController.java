package com.yuf.demo.business.excel.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yuf.demo.business.excel.ExcelImportService;
import com.yuf.demo.business.excel.service.ApplyExcellmportService;
import com.yuf.demo.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: dyf
 * @Date: 2020/12/9 20:47
 * @Description:
 */
@Api(tags = "excel与json数据导入接口集")
@RestController
@RequestMapping("/import")
public class ExcelImportController {
    @Autowired
    ExcelImportService excelImportService;
    @Autowired
    ApplyExcellmportService applyExcellmportService;

    @PostMapping("/file")
    public Response importExcel(@RequestParam("file") MultipartFile file){

        return excelImportService.importApplyInfo(file);
    }

    @ApiOperation("excel省厅数据导入")
    @PostMapping("/fileCheck")
    public Response importExcelCheck(@RequestParam("file") MultipartFile file){

        return applyExcellmportService.importExcelData(file);
    }

    @ApiOperation("根据身体小区编码，推送到指定url地址")
    @PostMapping("/pushData")
    public Response pushDataList(String url, String placeCode){
        String sendUrl = url + "/" + placeCode;
        return applyExcellmportService.pushDataAndSavePushResult(sendUrl, placeCode);
    }

    @ApiOperation("json省厅数据导入")
    @PostMapping("/uploadJsonFile")
    public Response uploadJsonFile(@RequestParam("file") MultipartFile file){

        return applyExcellmportService.uploadJsonFile(file);

    }


}
