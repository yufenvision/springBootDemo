package com.yuf.demo.business.async_test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    //利用CompletableFuture异步
    @GetMapping("/future")//包装方法
    public String future() throws InterruptedException {
        log.info("主线程开始=====>"+ Thread.currentThread().getName());
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //后面如果加上.join()会阻塞主线程，保证当前线程执行完以后再执行后面代码
        task1().thenCombine(task1(),(r1,r2) -> r1 + "+" + r2).thenAccept(res -> System.out.println(res));

        task1().thenAccept(e -> log.info("总耗时：{} 毫秒", e));
        task1().thenAccept(e -> log.info("总耗时：{} 毫秒", e));

        task2(executorService).thenAccept(e -> log.info("总耗时：{} 毫秒", e));
        task2(executorService).thenAccept(e -> log.info("总耗时：{} 毫秒", e));

        log.info("主线程结束=====>"+ Thread.currentThread().getName());
        return "test2Async-success";
    }

    //利用CompletableFuture异步
    @GetMapping("/future2")//包装方法
    public String future2() throws InterruptedException {
        log.info("主线程开始=====>"+ Thread.currentThread().getName());

        List<CompletableFuture<Long>> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(task1());
        }
//        list.forEach(v -> v.thenAccept(t -> log.info("总耗时：{} 毫秒", t)));


        log.info("主线程结束=====>"+ Thread.currentThread().getName());
        return "test2Async-success";
    }

    //不指定默认用ForkJoinPool线程池
    private CompletableFuture<Long> task1(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                return asyncMethodService.wrapperMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0L;
        });
    }
    //指定线程池
    private CompletableFuture<Long> task2(ExecutorService pool){
        return CompletableFuture.supplyAsync(() -> {
            try {
                return asyncMethodService.wrapperMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0L;
        },pool);
    }

    @GetMapping("/servlet")
    public void servlet(HttpServletRequest request, HttpServletResponse response){
        log.info("主线程开始=====>"+ Thread.currentThread().getName());
        AsyncContext asyncContext = request.startAsync();
        //设置监听器:可设置其开始、完成、异常、超时等事件的回调处理
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("超时了...");
                //做一些超时后的相关操作...
            }
            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("线程开始");
            }
            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("发生错误："+event.getThrowable());
            }
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("执行完成");
                //这里可以做一些清理资源的操作...
            }
        });
        //设置超时时间
        asyncContext.setTimeout(20000);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    System.out.println("内部线程：" + Thread.currentThread().getName());
                    asyncContext.getResponse().setCharacterEncoding("utf-8");
                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                    asyncContext.getResponse().getWriter().println("这是异步的请求返回");
                } catch (Exception e) {
                    System.out.println("异常："+e);
                }
                //异步请求完成通知
                //此时整个请求才完成
                asyncContext.complete();
            }
        });
        //此时之类 request的线程连接已经释放了
        System.out.println("主线程：" + Thread.currentThread().getName());
        log.info("主线程结束=====>"+ Thread.currentThread().getName());
    }


    @GetMapping("/callable")
    public Callable<String> callable() throws InterruptedException {
        log.info("主线程开始=====>"+ Thread.currentThread().getName());
        Callable<String> callable = () -> {
            log.info("异步线程开始=====>" + Thread.currentThread().getName());
            Thread.sleep(10000);
            log.info("异步线程结束=====>" + Thread.currentThread().getName());
            return "异步callable返回:success";
        };
        log.info("主线程结束=====>"+ Thread.currentThread().getName());
        return callable;
    }

    @GetMapping("/sameClass")//因为异步方法在同一个类中失效
    public String sameClass() throws InterruptedException {
        log.info("主线程开始=====>"+ Thread.currentThread().getName());
        backStr();
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
