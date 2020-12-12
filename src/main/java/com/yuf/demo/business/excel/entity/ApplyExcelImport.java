package com.yuf.demo.business.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
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

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/9 17:38
 */
@Data
@TableName("sichuan_center_person_info_excel_import")
public class ApplyExcelImport extends Model<ApplyExcelImport> implements Serializable, IExcelModel {

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(name = "导入id")
    private String importId;

    @ApiModelProperty(name = "姓名")
    private String name;

    @ApiModelProperty(name = "电话号码")
    private String phone;

    @ApiModelProperty(name = "照片base64地址")
    private String photo;

    @ApiModelProperty(name = "人脸图片地址")
    private String faceUrl;

    @ApiModelProperty(name = "身份证号")
    private String idCard;

    @ApiModelProperty(name = "省厅流水号")
    private String ywlsh;

//    @ApiModelProperty(name = "标准5端地址")
//    private String fiveAdress;

    @ApiModelProperty(name = "地址")
    private String address;

    @ApiModelProperty(name = "用户类型（省厅用户类型）")
    private String type;

    @ApiModelProperty(name = "数据来源 01：手机扫码填报，02：AI 能力层小区管理 后台填报")
    private String source;

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


    @ApiModelProperty(name = "导入失败消息提示")
    private String errorMsg;

    @ApiModelProperty(name = "小区编码")
    private String placeCode;

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
