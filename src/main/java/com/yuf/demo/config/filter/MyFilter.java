package com.yuf.demo.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/11/13 16:57
 */
@Slf4j
@Component
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("自定义过滤器开始---------------------------");
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("自定义过滤器结束***************************");
    }

    @Override
    public void destroy() {

    }
}
