package com.yuf.demo.business.excel.filter;

/**
 * @Author: dyf
 * @Date: 2020/12/16 17:29
 * @Description:
 */
public interface Filter <T,S>{

    void doFilter(T t, S s, FilterChain filterChain);
}
