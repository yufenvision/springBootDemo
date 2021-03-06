package com.yuf.demo.business.excel.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2020/12/9 17:38
 */
@Data
public class ApplyExcelDTO implements Serializable, IExcelModel, IExcelDataModel {

    private Long id;

    @NotBlank(message = "导入id不能为空")
    @Excel(name = "id")
    private String importId;

    @NotBlank(message = "姓名不能为空")
    @Excel(name = "姓名")
    private String name;

    @Pattern(regexp = "[0-9]{11}", message = "电话号码必须为11位，且均为数字")
    @NotNull(message = "电话号码不能为空")
    @Excel(name = "电话号码")
    private String phone;

    @NotBlank(message = "人脸图片地址不能为空")
    @Excel(name = "人脸图片地址")
    private String faceUrl;

    @Pattern(regexp = ".{15}|.{18}", message = "身份证号码必须为15位或18位")
    @NotNull(message = "身份证号不能为空")
    @Excel(name = "身份证号")
    private String idCard;

    @NotNull(message = "省厅流水号不能为空")
    @Excel(name = "省厅流水号")
    private String ywlsh;

//    @NotNull
//    @Excel(name = "标准5端地址")
//    private String fiveAdress;

    //楼栋单元楼层房间号全地址
    @NotBlank(message = "地址不能为空")
    @Excel(name = "地址")
    private String address;

    @NotBlank(message = "用户类型不能为空")
    @Excel(name = "用户类型（省厅用户类型）")
    private String type;


    @Excel(name = "楼栋号")
    private String buildNum;

    @Excel(name = "单元号")
    private String unitNum;

    @Excel(name = "楼层")
    private String floorNum;

    @Excel(name = "门户号")
    private String roomNum;


    @Excel(name = "返回失败备注")
    private String errorMsg;

    private String photo;

    @Excel(name = "省级小区编码")
    @NotBlank(message = "省级小区编码不能为空")
    private String placeCode;

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    //错误行号
    private Integer rowNum;
    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
}
