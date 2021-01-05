package com.yuf.demo.business.msg_subscribe;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/31 10:21
 */
@Slf4j
@RestController
public class MsgController {


    @PostMapping("/msg")
    public void receiveMsg(@RequestBody GuanlinDoorInOutMsgDTO msgDTO){

        log.info(JSONObject.toJSONString(msgDTO));
    }

    @PostMapping("/msgMap")
    public void receiveMsg(@RequestBody Map msgDTO){

        log.info(JSONObject.toJSONString(msgDTO));
    }

}
