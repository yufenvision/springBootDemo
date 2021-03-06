package com.yuf.demo.business.async_test;

import com.yuf.demo.config.theadpool.AsyncThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: dyf
 * @Date: 2020/12/6 23:39
 * @Description:
 */
@Slf4j
@Service
public class AsyncMethodServiceImpl implements AsyncMethodService {
    private final RestTemplate restTemplate;

    public AsyncMethodServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async(AsyncThreadPoolConfig.EXECUTOR_NAME)
    @Override
    public void asyncNoReturn() throws InterruptedException {
        log.info("异步线程开始=====>" + Thread.currentThread().getName());
        Thread.sleep(10000);
        log.info("异步线程结束=====>" + Thread.currentThread().getName());
    }

    @Async(AsyncThreadPoolConfig.EXECUTOR_NAME)
    @Override
    public CompletableFuture<User> findUser(String userName) throws InterruptedException {
        log.info("{}:Looking up {}", Thread.currentThread().getName(), userName);
        String url = String.format("https://api.github.com/users/%s", userName);
        User results = restTemplate.getForObject(url, User.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(3000L);
        return CompletableFuture.completedFuture(results);

    }

    @Override
    public Long wrapperMethod() throws InterruptedException {
        long start = System.currentTimeMillis();
        log.info("异步线程开始=====>" + Thread.currentThread().getName());
        Thread.sleep(10000);
        log.info("异步线程结束=====>" + Thread.currentThread().getName());
        return System.currentTimeMillis() - start;
    }

}
