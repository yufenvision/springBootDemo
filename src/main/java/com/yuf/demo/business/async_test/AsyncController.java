package com.yuf.demo.business.async_test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: dyf
 * @Date: 2020/12/6 23:00
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncController {
    @Autowired
    AsyncMethodService asyncMethodService;

    @GetMapping("/noReturn")
    public String test3Async() throws InterruptedException {
        log.info("调用类servcie为：{}", asyncMethodService.getClass().getName());
        log.info("主线程开始=====>"+ Thread.currentThread().getName());
        asyncMethodService.asyncNoReturn();
        log.info("主线程结束=====>"+ Thread.currentThread().getName());
        return "test3Async-success";
    }

    @GetMapping("/hasReturn")
    public List<User> get() throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();
        List<User> users = new ArrayList<>();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = asyncMethodService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = asyncMethodService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = asyncMethodService.findUser("Spring-Projects");

        // 同步阻塞，Wait until they are all done
        CompletableFuture.allOf(page1,page2,page3).join();

        // Print results, including elapsed time
        log.info("耗时：{} 毫秒 ", (System.currentTimeMillis() - start));
        log.info("--> {}", page1.get());
        log.info("--> {}", page2.get());
        log.info("--> {}", page3.get());
        users.add(page1.get());
        users.add(page2.get());
        users.add(page3.get());
        return users;
    }



//    @GetMapping("/testAsync")
//    public Callable<String> testAsync() throws InterruptedException {
//        log.info("主线程开始=====>"+ Thread.currentThread().getName());
//        Callable<String> callable = () -> {
//            log.info("异步线程开始=====>" + Thread.currentThread().getName());
//            Thread.sleep(10000);
//            log.info("异步线程结束=====>" + Thread.currentThread().getName());
//            return "异步callable返回:success";
//        };
//        log.info("主线程结束=====>"+ Thread.currentThread().getName());
//        return callable;
//    }

    @GetMapping("/sameClass")//因为异步方法在同一个类中失效
    public String sameClass() throws InterruptedException {
        log.info("主线程开始=====>"+ Thread.currentThread().getName());
        backStr();
        log.info("主线程结束=====>"+ Thread.currentThread().getName());
        return "test2Async-success";
    }

    @GetMapping("/wrapperMethod")//包装方法
    public String wrapperMethod() throws InterruptedException {
        log.info("调用类servcie为：{}", asyncMethodService.getClass().getName());
        log.info("主线程开始=====>"+ Thread.currentThread().getName());
        asyncMethodService.wrapperMethod();
        log.info("主线程结束=====>"+ Thread.currentThread().getName());
        return "test2Async-success";
    }


    /*
    什么情况下会导致@Async异步方法会失效？
    调用同一个类下注有@Async异步方法：
    在spring中像@Async和@Transactional、cache等注解本质使用的是动态代理，其实Spring容器在初始化的时候Spring容器会将含有AOP注解的类对象“替换”为代理对象（简单这么理解），
    那么注解失效的原因就很明显了，就是因为调用方法的是对象本身而不是代理对象，因为没有经过Spring容器，那么解决方法也会沿着这个思路来解决。
     */
    @Async
    public String backStr() throws InterruptedException {
        log.info("异步线程开始=====>" + Thread.currentThread().getName());
        Thread.sleep(10000);
        log.info("异步线程结束=====>" + Thread.currentThread().getName());
        return "异步线程结束，返回结果";
    }

}
