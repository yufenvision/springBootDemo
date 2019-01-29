package com.yuf.demo.business.filecentre.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yuf.demo.sys.entity.SysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author yuf
 * @since 2019-01-27
 */
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@TableName("b_file_info" )
public class FileInfo extends Model<FileInfo> {

private static final long serialVersionUID=1L;
    /**
    * 创建基本信息
    **/
    public void createDefaultInfo(){
        this.id=UUID.randomUUID().toString().replaceAll("-" ,"" );
    }
    /**文件ID*/
	@ApiModelProperty(value = "文件ID" , position = 1)
	private String id;
    /**文件名（自动生成，唯一）*/
    @TableField("file_name" )
	@ApiModelProperty(value = "文件名（自动生成，唯一）" , position = 2)
	private String fileName;
    /**原始文件名称*/
    @TableField("original_name" )
	@ApiModelProperty(value = "原始文件名称" , position = 3)
	private String originalName;
    /**业务类型（0-保留，1-用户）*/
    @TableField("busi_type" )
	@ApiModelProperty(value = "业务类型（0-保留，1-用户）" , position = 4)
	private String busiType;
    /**文件大小*/
    @TableField("file_size" )
	@ApiModelProperty(value = "文件大小" , position = 5)
	private BigDecimal fileSize;
    /**文件全路径*/
    @TableField("full_path" )
	@ApiModelProperty(value = "文件全路径" , position = 6)
	private String fullPath;
    /**文件上传时间*/
    @TableField("upload_time" )
	@ApiModelProperty(value = "文件上传时间" , position = 7)
	private Date uploadTime;
    /**关联业务id*/
    @TableField("business_id" )
	@ApiModelProperty(value = "关联业务id" , position = 8)
	private String businessId;

    @Override
	protected Serializable pkVal() {
        return this.id;
    }

}
