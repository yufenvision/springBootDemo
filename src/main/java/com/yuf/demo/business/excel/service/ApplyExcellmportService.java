package com.yuf.demo.business.excel.service;

import com.yuf.demo.utils.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: dyf
 * @Date: 2020/12/12 14:43
 * @Description:
 */
public interface ApplyExcellmportService {

    Response importExcelData(MultipartFile file);

}
