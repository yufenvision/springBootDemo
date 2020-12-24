package com.yuf.demo.business.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuf.demo.business.excel.dto.ApplyExcelDTO;
import com.yuf.demo.business.excel.entity.ApplyExcelImport;
import com.yuf.demo.business.excel.filter.FieldFilter;
import com.yuf.demo.business.excel.filter.FilterChain;
import com.yuf.demo.business.excel.mapper.ApplyExcelImportDao;
import com.yuf.demo.business.excel.service.ApplyExcellmportService;
import com.yuf.demo.utils.HttpRequestUtil;
import com.yuf.demo.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
    @Autowired
    FilterChain filterChain;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response importExcelData(MultipartFile file) {
        Long start = System.currentTimeMillis();
        ImportParams importParams = new ImportParams();
        importParams.setNeedVerfiy(true);//打开验证

        List<ApplyExcelDTO> successList = new ArrayList<>();
        List<ApplyExcelDTO> failList = new ArrayList<>();
        try{
            ExcelImportResult<ApplyExcelDTO> excelResult = ExcelImportUtil.importExcelMore(file.getInputStream(), ApplyExcelDTO.class, importParams);
            log.info("总校验时间为：【{}】毫秒", System.currentTimeMillis() - start);
            //字段基础校验，有一条不通过则重传
//            if(excelResult.isVerfiyFail()){
//                List<String> failStr = excelResult.getFailList().stream().map(a -> "第"+ (a.getRowNum() + 1)+"行，" + a.getName() + "-" + a.getErrorMsg()).collect(Collectors.toList());
//                return new Response().fail(failStr);
//            }
            failList = excelResult.getFailList();
            //将图片转为base64
            successList = excelResult.getList();
            Long printStart = System.currentTimeMillis();
            multiInvokeBase64Transfer(successList);//并行发送http请求
            log.info("图片转换总耗时【{}】毫秒", System.currentTimeMillis() - printStart);
        } catch (Exception e) {
            return new Response().fail(e.getMessage());
        }
        //异步入库
        long dbStart = System.currentTimeMillis();
        List<ApplyExcelImport> applyExcelImports = new ArrayList<>();
        successList.addAll(failList);
        successList.forEach(v -> {
            ApplyExcelImport applyExcelImport = new ApplyExcelImport();
            BeanUtils.copyProperties(v, applyExcelImport);
//            applyExcelImport.setAddress(v.getAddress() + String.format("%s栋%s单元%s层%s号",v.getBuildNum(), v.getUnitNum(),v.getFloorNum(), v.getRoomNum()));
            if("1".equals(applyExcelImport.getType()) || "2".equals(applyExcelImport.getType())){
                if(!applyExcelImport.getAddress().matches("^.*\\d+栋\\d+单元\\d+层\\d+号")){
                    applyExcelImport.setErrorMsg("业主和租户的地址应包含楼栋单元楼层房间号，" + applyExcelImport.getErrorMsg());
                }
            }
            applyExcelImport.setSource("02");
            applyExcelImport.setCreateTime(new Date());
            if(StringUtils.isBlank(applyExcelImport.getPlaceCode()))applyExcelImport.setPlaceCode("100000");
            applyExcelImports.add(applyExcelImport);
        });
        //mysql默认语句长度4M，分批插入
        List<List<ApplyExcelImport>> listBatch = splitList(applyExcelImports, 30);
        ayncInvoke(listBatch, (d)-> applyExcelImportDao.insertBatch(d));
        log.info("入库总耗时【{}】毫秒", System.currentTimeMillis() - dbStart);

        return new Response().success(successList.stream().map(v -> v.getName() + (v.getPhoto() != null ? "：头像存在":"：头像不存在")).collect(Collectors.toList()));
    }

    //异步调用入库
    public <T> CompletableFuture<Void> ayncInvoke(List<T> listBatch, Consumer<T> consumer){
        return CompletableFuture.runAsync(() -> listBatch.forEach(consumer))
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    @Override
    @Transactional
    public Response pushDataAndSavePushResult(String url, String placeCode) {
        List<ApplyExcelImport> pushList = getPushList(placeCode);
        try {
            multiThreadInvoke(pushList, url);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //mysql默认语句长度4M，分批插入
        List<List<ApplyExcelImport>> listBatch = splitList(pushList, 1000);
        ayncInvoke(listBatch, (d) -> applyExcelImportDao.updateBatch(d));
        return new Response().success(pushList.stream().map(v -> v.getPushCode() + v.getPushMsg()).collect(Collectors.toList()));
    }

    @Override
    public List<ApplyExcelImport> getPushList(String placeCode) {
        List<ApplyExcelImport> list = applyExcelImportDao.getPushList(placeCode);

        return list;
    }

    @Override
    public Response uploadJsonFile(MultipartFile file) {
        StringBuffer sb = null;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            sb = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response result = JSONObject.parseObject(sb.toString(), Response.class);
        JSONArray jsonArray = (JSONArray) JSONObject.parseObject(result.getData().toString(), Map.class).get("records");
        List<ApplyExcelImport> list = new ArrayList<>();
        filterChain.add(new FieldFilter());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            ApplyExcelImport excelImport = new ApplyExcelImport();
            filterChain.setIndex(0);
            filterChain.doFilter(excelImport, jo);
            list.add(excelImport);
        }
        List<List<ApplyExcelImport>> listBatch = splitList(list, 30);
        ayncInvoke(listBatch, (d)-> applyExcelImportDao.insertBatch(d));

        return new Response().success(list.stream().map(v -> v.getName().concat(":").concat(v.getIdCard())).collect(Collectors.toList()));
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

    private void multiThreadInvoke(List<ApplyExcelImport> list, String url) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        list.forEach(v -> asyncPool.execute(()->{
            List<ApplyExcelImport> sub = new ArrayList<>();
            sub.add(v);
            Response result = HttpRequestUtil.postBackResponse(url, JSONObject.toJSONString(sub));
            v.setPushCode(String.valueOf(result.getCode()));//回调入库
            String pushMsg = Optional.ofNullable(v.getPushMsg()).orElse("") + "," + (DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + result.getData());
            v.setPushMsg(pushMsg);
            countDownLatch.countDown();
        }));
        countDownLatch.await();
    }

    private void multiInvokeBase64Transfer(List<ApplyExcelDTO> applyExcelDTOS) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(applyExcelDTOS.size());
        applyExcelDTOS.forEach(v -> {
            asyncPool.execute(() -> {
                transfer2Base64(v);
                countDownLatch.countDown();
            });
        });
        countDownLatch.await();
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
            applyExcel.setErrorMsg((StringUtils.isBlank(applyExcel.getErrorMsg()) ? "" : applyExcel.getErrorMsg() + ",") + "该人像图片地址无法访问");
        }

    }


}
