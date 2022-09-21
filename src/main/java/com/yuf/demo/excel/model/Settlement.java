package com.yuf.demo.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2022/9/21 17:39
 */
@Data
public class Settlement {

    @ExcelProperty(value = "Book No.")
    private String bookNo;
    @ExcelProperty(value = "Serial Number")
    private String serialNumber;

    @ExcelProperty(value = "Processing Fee")
    private BigDecimal processingFee;

    @ExcelProperty(value = "Value Back per asset")
    private BigDecimal valueBackPerAsset;

}
