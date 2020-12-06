package com.yuf.demo.business.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: dyf
 * @Date: 2020/12/6 23:39
 * @Description:
 */
@Slf4j
@Component
public class AsyncMethodClass {

    @Async
    public String asyncReturn() throws InterruptedException {
        log.info("异步线程开始=====>" + Thread.currentThread().getName());
        Thread.sleep(10000);
        log.info("异步线程结束=====>" + Thread.currentThread().getName());
        return "异步线程结束，返回结果";
    }

}
