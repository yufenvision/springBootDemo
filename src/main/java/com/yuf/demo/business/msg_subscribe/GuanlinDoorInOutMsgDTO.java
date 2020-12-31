package com.yuf.demo.business.msg_subscribe;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/31 10:25
 */
@Data
public class GuanlinDoorInOutMsgDTO {

    /**
     * 1：指令应答反馈消息
     * 2：事件通知消息
     * 3：设备状态变更消息
     */
    @ApiModelProperty(name = "消息类型", required = true)
    int msgtype;

    /**
     * msgtype 为 2 时该值生效
     * 1001：门禁通行事件
     * 1002：门禁异常事件
     * 1003：门禁告警事件
     */
    @ApiModelProperty(name = "事件类型", required = false)
    int eventtype;

    @ApiModelProperty(name = "第三方小区id", required = false)
    String cid;

    @ApiModelProperty(name = "json 对象消息数据对象", required = true)
    GuanlinMsgData msgdata;

}
