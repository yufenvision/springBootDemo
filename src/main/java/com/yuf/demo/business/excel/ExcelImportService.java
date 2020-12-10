package com.yuf.demo.business.excel;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSON;
import com.yuf.demo.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author: dyf
 * @Date: 2020/12/9 19:26
 * @Description:
 */
@Slf4j
@Service
public class ExcelImportService {
    @Autowired
    ApplyInfoVerifyHander applyInfoVerifyHander;

    @Autowired
    RedisTemplate redisTemplate;

    @Transactional
    public Response importApplyInfo(MultipartFile file){
        Long start = System.currentTimeMillis();
        ImportParams importParams = new ImportParams();
        importParams.setNeedVerfiy(true);//打开验证
        importParams.setVerifyHandler(applyInfoVerifyHander);

        ExcelImportResult<ApplyExcelDTO> excelResult;
        try{
            excelResult = ExcelImportUtil.importExcelMore(file.getInputStream(), ApplyExcelDTO.class, importParams);
            log.info("总校验时间为：【{}】毫秒", System.currentTimeMillis() - start);
            Long printStart = System.currentTimeMillis();
            List<ApplyExcelDTO> successList = excelResult.getList();
            successList.forEach(v -> redisTemplate.opsForList().leftPush("successList", JSON.toJSONString(v)));

            Object result = redisTemplate.execute(new SessionCallback() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.multi();
                    List<String> list = redisOperations.opsForList().range("successList",0,300);
                    list.forEach(v -> {
                        log.info(JSON.parseObject(v, ApplyExcelDTO.class).getYwlsh());
                    });
                    return redisOperations.exec();
                }
            });
            System.out.println(result);

            log.info("打印总耗时【{}】毫秒", System.currentTimeMillis() - printStart);
        } catch (Exception e) {
            return new Response().fail(e.getMessage());
        }


        return new Response().fail(excelResult.getFailList());
    }

}
