package com.yuf.demo.business.async_test;

import com.alibaba.fastjson.JSON;
import com.yuf.demo.remote.FeignClientTestCall;
import com.yuf.demo.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: dyf
 * @Date: 2020/12/5 18:04
 * @Description:
 */
@Api(tags = "测试实现2")
@RestController
@RequestMapping("/test2" )
public class TestController2 implements AbstractController{

    @Autowired
    private FeignClientTestCall feignClientTestCall;

    @Override
    public String testS() {
        return "测试实现2的测试方法";
    }

    @GetMapping("/feignTest")
    public String testFeign(){
        return feignClientTestCall.getWeatherByCity();
    }


}
