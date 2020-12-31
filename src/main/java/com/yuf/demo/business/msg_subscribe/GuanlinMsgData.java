package com.yuf.demo.business.msg_subscribe;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/31 10:29
 */
@Data
public class GuanlinMsgData {

    /**
     * 0：表示机械钥匙、
     * 1：表示密码、
     * 2：表示刷卡、
     * 3：表示指纹识别、
     * 4：表示人脸识别、
     * 6：访客开门、
     * 7：监视开门、
     * 8：手机开门、
     * 9：临时授权开门、
     * 11：远程开门、
     * 12：手机蓝牙开门、
     * 13：分享密码开门、
     * 20：业主二维码开门、
     * 21：授权二维码开门、
     * 22：临时二维码开门
     */
    @ApiModelProperty(name = "开门方式", required = true)
    int open_mode;

    @ApiModelProperty(name = "描述", required = true)
    String desc;

    //yyyy-MM-dd-HH:mm:ss
    @ApiModelProperty(name = "时间", required = true)
    String time;

    @ApiModelProperty(name = "设备id", required = true)
    String device_id;

    @ApiModelProperty(name = "设备名称", required = true)
    String device_name;

    @ApiModelProperty(name = "设备编号", required = true)
    String device_code;

    @ApiModelProperty(name = "设备序列号", required = true)
    String device_sn;

    @ApiModelProperty(name = "房号", required = true)
    String room_no;

    @ApiModelProperty(name = "用户名称", required = false)
    String user_name;

    @ApiModelProperty(name = "用户描述,住户框架或住址", required = false)
    String user_desc;

    @ApiModelProperty(name = "用户类型", required = false)
    String user_type;

    @ApiModelProperty(name = "证件类型,1：身份证", required = false)
    String credential_type;

    @ApiModelProperty(name = "证件号", required = false)
    String credential_id;

    @ApiModelProperty(name = "钥匙id", required = false)
    long key_id;

    @ApiModelProperty(name = "钥匙类型,1、卡；2、密码；3、人脸。", required = false)
    String key_type;

    @ApiModelProperty(name = "人脸地址, 类型为 3 人脸时返回", required = false)
    String face_url;

    @ApiModelProperty(name = "卡号, 类型为 1 卡时返回", required = false)
    String card_no;

}
