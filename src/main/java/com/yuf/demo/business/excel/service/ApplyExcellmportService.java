package com.yuf.demo.business.excel.service;

import com.yuf.demo.business.excel.entity.ApplyExcelImport;
import com.yuf.demo.utils.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: dyf
 * @Date: 2020/12/12 14:43
 * @Description:
 */
public interface ApplyExcellmportService {

    Response importExcelData(MultipartFile file);


    Response pushDataAndSavePushResult(String url, String placeCode);


    List<ApplyExcelImport> getPushList(String placeCode);

    Response uploadJsonFile(MultipartFile file);
}
