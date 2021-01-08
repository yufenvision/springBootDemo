package com.yuf.demo.business.async_test;

import com.alibaba.fastjson.JSON;
import com.yuf.demo.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: dyf
 * @Date: 2020/12/5 18:04
 * @Description:
 */
@Api(tags = "异步测试")
@RestController
@RequestMapping("/test" )
public class TestController {

//    @Autowired
//    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    StringRedisTemplate redisTemplate;

    @PostMapping("/set")
    public Response addPersonInfo(@RequestBody PersonInfo personInfo){
        redisTemplate.opsForHash().put("@personInfo", personInfo.getId(), JSON.toJSONString(personInfo));
        return new Response<>().success();
    }

    @GetMapping("/{key}")
    public Response getPersonInfo(@PathVariable String key){
        PersonInfo personInfo = JSON.parseObject((String) redisTemplate.opsForHash().get("@personInfo", key), PersonInfo.class);
        return new Response<>().success(personInfo);
    }

}
