package com.yuf.demo.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yuf.demo.business.filecenter.entity.FileInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuf
 * @since 2018-11-02
 */
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@TableName("sys_user" )
public class SysUser extends Model<SysUser> {

private static final long serialVersionUID=1L;
    /**
    * 创建基本信息
    **/
    public void createDefaultInfo(){
    	this.id = UUID.randomUUID().toString().replaceAll("-" ,"" );
    	this.createDate = new Date();
    	this.isDel = "0";
    	this.status = "0";
    }
    
	@ApiModelProperty(value = "ID" , position = 1)
	private String id;
	
	
	@ApiModelProperty(value = "用户名" , position = 2)
	private String username;
	
	@ApiModelProperty(value = "密码" , position = 3)
	private String password;
	
	@ApiModelProperty(value = "邮箱" , position = 4)
	private String email;
	
    @TableField("mobile_phone" )
	@ApiModelProperty(value = "电话号码" , position = 5)
	private String mobilePhone;
    
    @TableField("role_id" )
    @ApiModelProperty(value = "角色id" , position = 6)
    private String roleId;
    @TableField("depart_id" )
    @ApiModelProperty(value = "部门id" , position = 7)
    private String departId;
    @TableField("org_id" )
    @ApiModelProperty(value = "行政区划id" , position = 8)
    private String orgId;
    /**有效状态（0-激活，1-未激活）*/
	@ApiModelProperty(value = "有效状态（0-激活，1-未激活）" , position = 9)
	private String status;
    /**是否删除（0-未删除，1-删除）*/
    @TableField("is_del" )
	@ApiModelProperty(value = "是否删除（0-未删除，1-删除）" , position = 10)
	private String isDel;
    @TableField("create_by" )
	@ApiModelProperty(value = "创建人" , position = 11)
	private String createBy;
    @TableField("create_date" )
	@ApiModelProperty(value = "创建时间" , position = 12)
	private Date createDate;
    @TableField("update_by" )
    @ApiModelProperty(value = "更新人" , position = 13)
    private String updateBy;
    @TableField("update_date" )
	@ApiModelProperty(value = "更新时间" , position = 14)
	private Date updateDate;
	
    @ApiModelProperty(value = "头像" , position = 15)
    @TableField(exist = false)
    private List<FileInfo> fileInfos;
    
    
    
	@Override
	protected Serializable pkVal() {
		return this.id;
    }
	
}
