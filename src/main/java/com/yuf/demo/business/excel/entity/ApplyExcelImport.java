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

import javax.validation.constraints.NotBlank;
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

    @Pattern(regexp = "[a-z0-9A-Z]{32}", message = "省厅流水号必须为32位,且均为字母数字")
    @NotNull(message = "省厅流水号不能为空")
    @ApiModelProperty(name = "省厅流水号")
    private String ywlsh;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(name = "姓名")
    private String name;

    @Pattern(regexp = ".{15}|.{18}", message = "身份证号码必须为15位或18位")
    @NotNull(message = "身份证号不能为空")
    @ApiModelProperty(name = "身份证号")
    private String idCard;

    @ApiModelProperty(name = "数据来源 01：手机扫码填报，02：AI 能力层小区管理 后台填报")
    private String source;

    @NotBlank(message = "base64照片不能为空")
    @ApiModelProperty(name = "照片base64地址")
    private String photo;

    @Pattern(regexp = "[0-9]{11}", message = "电话号码必须为11位(且均为数字)")
    @NotNull(message = "电话号码不能为空")
    @ApiModelProperty(name = "电话号码")
    private String phone;

    @NotBlank(message = "用户类型不能为空")
    @ApiModelProperty(name = "人员类型，01 住户，02 租户，03 物业人员，04 服务人员， 05 来访人员")
    private String type;

    @NotBlank(message = "地址不能为空")
    @ApiModelProperty(name = "地址")
    private String address;

    @ApiModelProperty(name = "省级小区编码")
    private String placeCode;

    @ApiModelProperty(name = "人脸图片地址")
    private String faceUrl;

    @ApiModelProperty(name = "字段错误提示")
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
