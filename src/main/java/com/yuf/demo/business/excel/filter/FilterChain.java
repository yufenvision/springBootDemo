package com.yuf.demo.business.excel.filter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/16 17:30
 */
@Component
public class FilterChain {
    List<Filter> filterList = new ArrayList<>();
    private int index = 0;

    public FilterChain add(Filter filter){
        filterList.add(filter);
        return this;
    }

    public void removeAllFilter(){
        filterList.clear();
    }

    public <T,S>void doFilter(T t, S s){
        if(index < filterList.size()){
            Filter filter = filterList.get(index);
            index++;
            filter.doFilter(t,s,this);
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
