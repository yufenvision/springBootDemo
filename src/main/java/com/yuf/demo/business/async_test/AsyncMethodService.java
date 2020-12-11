package com.yuf.demo.business.async_test;

import com.yuf.demo.config.theadpool.AsyncThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: dyf
 * @Date: 2020/12/6 23:39
 * @Description:
 */
public interface AsyncMethodService {


    void asyncNoReturn() throws InterruptedException;

    CompletableFuture<User> findUser(String userName) throws InterruptedException;

    Long wrapperMethod() throws InterruptedException;

}
