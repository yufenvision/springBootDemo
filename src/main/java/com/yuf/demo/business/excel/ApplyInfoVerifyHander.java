package com.yuf.demo.business.excel;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.yuf.demo.business.excel.dto.ApplyExcelDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: dyf
 * @Date: 2020/12/9 20:41
 * @Description:
 */
@Slf4j
@Component
public class ApplyInfoVerifyHander implements IExcelVerifyHandler<ApplyExcelDTO> {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static BASE64Encoder base64Encoder = new BASE64Encoder();

    @Override
    public ExcelVerifyHandlerResult verifyHandler(ApplyExcelDTO applyExcel) {
        Long start = System.currentTimeMillis();
        ExcelVerifyHandlerResult result = new ExcelVerifyHandlerResult();

        if(StringUtils.isBlank(applyExcel.getPlaceCode())){
            result.setMsg("该省厅小区还未在运营平台创建关联");
            result.setSuccess(false);
            return result;
        }

        //调用外网，看是否有数据，且转换为base64格式
        try {
            URL url = new URL(applyExcel.getFaceUrl());
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream in = conn.getInputStream();
            if (applyExcel.getFaceUrl().equalsIgnoreCase(conn.getURL().toString())){
                log.info("图片存在：{}", applyExcel.getFaceUrl());
                ByteArrayOutputStream data = new ByteArrayOutputStream();
                // 将内容读取内存中
                int len;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    data.write(buffer, 0, len);
                }
                applyExcel.setPhoto(base64Encoder.encode(data.toByteArray()));
            }
            in.close();
//            boolean exist = new UrlResource(applyExcel.getFaceUrl()).exists();
//            if(exist){
//                log.info("图片存在：{}", applyExcel.getFaceUrl());
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setMsg("该人像图片地址无法访问");
            result.setSuccess(false);
            return result;
        }
        log.info("数据{}校验花费：【{}】 毫秒", applyExcel.getYwlsh(), System.currentTimeMillis() - start);
        result.setSuccess(true);
        return result;
    }
}
