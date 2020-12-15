package com.yuf.demo.business.excel.controller;

import com.yuf.demo.business.excel.ExcelImportService;
import com.yuf.demo.business.excel.service.ApplyExcellmportService;
import com.yuf.demo.utils.Response;
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

    @PostMapping("/fileCheck")
    public Response importExcelCheck(@RequestParam("file") MultipartFile file){

        return applyExcellmportService.importExcelData(file);
    }

    @PostMapping("/pushData")
    public Response pushDataList(String url, String placeCode){
        String sendUrl = url + "/" + placeCode;
        return applyExcellmportService.pushDataAndSavePushResult(sendUrl, placeCode);
    }

}
