package com.yuf.demo.config.theadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/7 10:04
 */
@Configuration
public class AsyncThreadPoolConfig {
    public static final String EXECUTOR_NAME = "asyncPool";

    @Bean
    public Executor asyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("asyncPool-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//饱和策略
        executor.initialize();
        return executor;
    }
}
