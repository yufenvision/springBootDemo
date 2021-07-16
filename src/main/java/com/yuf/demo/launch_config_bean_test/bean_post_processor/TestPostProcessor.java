package com.yuf.demo.launch_config_bean_test.bean_post_processor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: dyf
 * @Date: 2021/1/19 21:48
 * @Description:
 */
public class TestPostProcessor {
    public static void main(String[] args) {

        System.out.println("容器启动成功！");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        //打印当前容器所有BeanDefinition
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

        System.out.println("============");

        //取出Calculator类型的实例，调用add方法
        Calculator calculator = applicationContext.getBean(Calculator.class);
        calculator.add(1, 2);
    }
}
