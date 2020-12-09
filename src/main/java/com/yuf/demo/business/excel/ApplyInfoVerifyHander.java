package com.yuf.demo.business.excel;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: dyf
 * @Date: 2020/12/9 20:41
 * @Description:
 */
@Component
public class ApplyInfoVerifyHander implements IExcelVerifyHandler<ApplyExcelDTO> {
    @Override
    public ExcelVerifyHandlerResult verifyHandler(ApplyExcelDTO applyExcel) {
        ExcelVerifyHandlerResult result = new ExcelVerifyHandlerResult();

        String placeName = applyExcel.getAddress();
        String placeCode = "";
        if(StringUtils.isBlank(placeCode)){
            result.setMsg("该省厅小区还未在运营平台创建关联");
            result.setSuccess(false);
            return result;
        }

        String faceUrl = applyExcel.getFaceUrl();
        //调用外网，看是否是数据


        result.setSuccess(true);
        return result;
    }
}
