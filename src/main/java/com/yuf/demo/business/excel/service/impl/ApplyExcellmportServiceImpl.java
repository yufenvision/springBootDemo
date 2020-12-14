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
import org.apache.commons.lang3.StringUtils;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<ApplyExcelDTO> successList = new ArrayList<>();
        try{
            ExcelImportResult<ApplyExcelDTO> excelResult = ExcelImportUtil.importExcelMore(file.getInputStream(), ApplyExcelDTO.class, importParams);
            log.info("总校验时间为：【{}】毫秒", System.currentTimeMillis() - start);
            //字段基础校验，有一条不通过则重传
            if(excelResult.isVerfiyFail()){
                List<String> failStr = excelResult.getFailList().stream().map(a -> "第"+ (a.getRowNum() + 1)+"行，" + a.getName() + "-" + a.getErrorMsg()).collect(Collectors.toList());
                return new Response().fail(failStr);
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
        List<ApplyExcelImport> applyExcelImports = new ArrayList<>();
        successList.forEach(v -> {
            ApplyExcelImport applyExcelImport = new ApplyExcelImport();
            BeanUtils.copyProperties(v, applyExcelImport);
            applyExcelImport.setAddress(v.getAddress() + String.format("%s栋%s单元%s层%s号",v.getBuildNum(), v.getUnitNum(),v.getFloorNum(), v.getRoomNum()));
            applyExcelImport.setSource("2");
            if(StringUtils.isBlank(applyExcelImport.getPlaceCode()))applyExcelImport.setPlaceCode("100000");
            applyExcelImports.add(applyExcelImport);
//            CompletableFuture.runAsync(() -> {
//                log.info("异步入库：{}----------", applyExcelImport.getIdCard());
//                applyExcelImportDao.insert(applyExcelImport);
//            }).exceptionally(e -> {
//                e.printStackTrace();
//                return null;
//            });
        });
        //mysql默认语句长度4M，分批插入
        List<List<ApplyExcelImport>> listBatch = splitList(applyExcelImports, 30);
        CompletableFuture.runAsync(() -> listBatch.forEach(d -> applyExcelImportDao.insertBatch(d)))
        .exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
        log.info("入库总耗时【{}】毫秒", System.currentTimeMillis() - dbStart);

        return new Response().success(successList.stream().map(v -> v.getName() + (v.getPhoto() != null ? "：头像存在":"：头像不存在")).collect(Collectors.toList()));
    }

    public static <T> List<List<T>> splitList(List<T> list, int splitSize) {
        //判断集合是否为空
        if (list.size() == 0)
            return Collections.emptyList();
        //计算分割后的大小
        int maxSize = (list.size() + splitSize - 1) / splitSize;
        //开始分割
        return Stream.iterate(0, n -> n + 1)
                .limit(maxSize)
                .parallel()
                .map(a -> list.parallelStream().skip(a * splitSize).limit(splitSize).collect(Collectors.toList()))
                .filter(b -> !b.isEmpty())
                .collect(Collectors.toList());
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
                ByteArrayOutputStream data = new ByteArrayOutputStream();
                // 将内容读取内存中
                int len;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    data.write(buffer, 0, len);
                }
                applyExcel.setPhoto(base64Encoder.encode(data.toByteArray()));
                log.info("{}读取耗时：{}毫秒-由【{}】线程完成", applyExcel.getFaceUrl(), System.currentTimeMillis()-start, Thread.currentThread().getName());
            }
            in.close();

        } catch (Exception e) {
            log.error(e.getMessage());
            applyExcel.setErrorMsg(Optional.ofNullable(applyExcel.getErrorMsg()).orElse(",") + "该人像图片地址无法访问");
        }

    }


}
