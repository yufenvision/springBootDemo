package com.yuf.demo.business.excel;

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

    @PostMapping("/file")
    public Response importExcel(@RequestParam("file") MultipartFile file){

        return excelImportService.importApplyInfo(file);
    }


}
