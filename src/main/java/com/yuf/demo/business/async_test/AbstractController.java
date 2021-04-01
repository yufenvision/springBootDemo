package com.yuf.demo.business.async_test;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description: 这里用接口或者抽象类都可以
 * @Author: dyf
 * @Date: 2021/4/1 17:14
 */
public interface AbstractController{

    @GetMapping("/testS")
    abstract String testS();


}
