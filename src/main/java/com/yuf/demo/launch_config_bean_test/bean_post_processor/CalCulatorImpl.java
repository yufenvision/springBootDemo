package com.yuf.demo.launch_config_bean_test.bean_post_processor;

import org.springframework.stereotype.Component;

/**
 * @Author: dyf
 * @Date: 2021/1/19 21:47
 * @Description:
 */
@Component
public class CalCulatorImpl implements Calculator {
    @Override
    public void add(int a, int b) {
        System.out.println(a+b);
    }
}
