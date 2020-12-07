package com.yuf.demo.business.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @Author: dyf
 * @Date: 2020/12/6 23:39
 * @Description:
 */
@Slf4j
@Service
public class AsyncMethodService {
    private final RestTemplate restTemplate;

    public AsyncMethodService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public void asyncNoReturn() throws InterruptedException {
        log.info("异步线程开始=====>" + Thread.currentThread().getName());
        Thread.sleep(10000);
        log.info("异步线程结束=====>" + Thread.currentThread().getName());
    }

    @Async
    public CompletableFuture<User> findUser(String userName) throws InterruptedException {
        log.info("{}:Looking up {}", Thread.currentThread().getName(), userName);
        String url = String.format("https://api.github.com/users/%s", userName);
        User results = restTemplate.getForObject(url, User.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(3000L);
        return CompletableFuture.completedFuture(results);

    }


}
