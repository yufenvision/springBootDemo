package com.yuf.demo.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2021/8/31 16:53
 */
@FeignClient(name = "core-feign-test", url = "${outside.net.host}")
public interface FeignClientTestCall {

    @GetMapping("/hello")
    public String getWeatherByCity();
}
