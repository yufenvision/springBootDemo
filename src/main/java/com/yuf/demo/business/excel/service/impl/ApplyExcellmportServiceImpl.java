package com.yuf.demo.business.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSON;
import com.yuf.demo.business.excel.ApplyExcelDTO;
import com.yuf.demo.business.excel.entity.ApplyExcelImport;
import com.yuf.demo.business.excel.mapper.ApplyExcelImportDao;
import com.yuf.demo.business.excel.service.ApplyExcellmportService;
import com.yuf.demo.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @Author: dyf
 * @Date: 2020/12/12 14:47
 * @Description:
 */
@Slf4j
@Service
public class ApplyExcellmportServiceImpl implements ApplyExcellmportService {

    private static BASE64Encoder base64Encoder = new BASE64Encoder();

    @Autowired
    Executor asyncPool;
    @Autowired
    ApplyExcelImportDao applyExcelImportDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response importExcelData(MultipartFile file) {
        Long start = System.currentTimeMillis();
        ImportParams importParams = new ImportParams();
        importParams.setNeedVerfiy(true);//打开验证

        ExcelImportResult<ApplyExcelDTO> excelResult;
        List<ApplyExcelDTO> successList = new ArrayList<>();
        try{
            excelResult = ExcelImportUtil.importExcelMore(file.getInputStream(), ApplyExcelDTO.class, importParams);
            log.info("总校验时间为：【{}】毫秒", System.currentTimeMillis() - start);
            //字段基础校验，有一条不通过则重传
            if(excelResult.isVerfiyFail()){
                ApplyExcelDTO a = excelResult.getFailList().get(0);
                return new Response().fail("第"+a.getRowNum()+"行，"+a.getErrorMsg());
            }

            //将图片转为base64
            successList = excelResult.getList();
            Long printStart = System.currentTimeMillis();
            CountDownLatch countDownLatch = new CountDownLatch(successList.size());
            base64Transfer(successList, countDownLatch);//并行发送http请求
            countDownLatch.await();
            log.info("图片转换总耗时【{}】毫秒", System.currentTimeMillis() - printStart);
        } catch (Exception e) {
            return new Response().fail(e.getMessage());
        }
        //异步入库
        long dbStart = System.currentTimeMillis();
        successList.forEach(v -> {
            ApplyExcelImport applyExcelImport = new ApplyExcelImport();
            BeanUtils.copyProperties(v, applyExcelImport);
            CompletableFuture.supplyAsync(() -> {
                log.info("入库：{}----------", applyExcelImport.getIdCard());
                return applyExcelImportDao.insert(applyExcelImport);
            });
//            log.info("入库：{}----------", applyExcelImport.getIdCard());
//            applyExcelImportDao.insert(applyExcelImport);
        });
        log.info("入库总耗时【{}】毫秒", System.currentTimeMillis() - dbStart);


        return new Response().success(successList.stream().map(v -> v.getPhoto() != null));
    }



    private void base64Transfer(List<ApplyExcelDTO> applyExcelDTOS, CountDownLatch countDownLatch){
        applyExcelDTOS.forEach(v -> {
            asyncPool.execute(() -> {
                transfer2Base64(v);
                countDownLatch.countDown();
            });
        });
    }

    private void transfer2Base64(ApplyExcelDTO applyExcel){
        long start = System.currentTimeMillis();
        //调用外网，看是否有数据，且转换为base64格式
        try {
            URL url = new URL(applyExcel.getFaceUrl());
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream in = conn.getInputStream();
            if (applyExcel.getFaceUrl().equalsIgnoreCase(conn.getURL().toString())){
                log.info("{}读取耗时：{}毫秒-由【{}】线程完成", applyExcel.getFaceUrl(), System.currentTimeMillis()-start, Thread.currentThread().getName());
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

        } catch (Exception e) {
            log.error(e.getMessage());
            applyExcel.setErrorMsg(Optional.ofNullable(applyExcel.getErrorMsg()).orElse(",") + "该人像图片地址无法访问");
        }

    }


}
