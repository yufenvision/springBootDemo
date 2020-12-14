package com.yuf.demo.business.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/9 17:38
 */
@Data
@TableName("sichuan_center_person_info_excel_import")
public class ApplyExcelImport extends Model<ApplyExcelImport> implements Serializable{

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(name = "省厅流水号")
    private String ywlsh;

    @ApiModelProperty(name = "姓名")
    private String name;

    @ApiModelProperty(name = "身份证号")
    private String idCard;

    @ApiModelProperty(name = "数据来源 01：手机扫码填报，02：AI 能力层小区管理 后台填报")
    private String source;

    @ApiModelProperty(name = "照片base64地址")
    private String photo;

    @ApiModelProperty(name = "电话号码")
    private String phone;

    @ApiModelProperty(name = "人员类型，01 住户，02 租户，03 物业人员，04 服务人员， 05 来访人员")
    private String type;

    @ApiModelProperty(name = "地址")
    private String address;

    @ApiModelProperty(name = "省级小区编码")
    private String placeCode;

    @ApiModelProperty(name = "人脸图片地址")
    private String faceUrl;

    @ApiModelProperty(name = "导入失败消息提示")
    private String errorMsg;

//    @ApiModelProperty(name = "标准5端地址")
//    private String fiveAdress;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("导入类型")
    private String importType;

    @ApiModelProperty("导入id")
    private String importId;

    @ApiModelProperty("推送返回http请求码")
    private String pushCode;

    @ApiModelProperty("推送返回http请求消息")
    private String pushMsg;

    @ApiModelProperty(name = "楼栋号")
    @TableField(exist = false)
    private String buildNum;

    @ApiModelProperty(name = "单元号")
    @TableField(exist = false)
    private String unitNum;

    @ApiModelProperty(name = "楼层")
    @TableField(exist = false)
    private String floorNum;

    @ApiModelProperty(name = "门户号")
    @TableField(exist = false)
    private String roomNum;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
