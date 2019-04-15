/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-03-27 14:34:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for b_file_info
-- ----------------------------
DROP TABLE IF EXISTS `b_file_info`;
CREATE TABLE `b_file_info` (
  `id` varchar(32) NOT NULL COMMENT '文件ID',
  `file_name` varchar(100) NOT NULL COMMENT '文件名（自动生成，唯一）',
  `original_name` varchar(100) NOT NULL COMMENT '原始文件名称',
  `busi_type` varchar(6) NOT NULL COMMENT '业务类型(0-一河一策，1-通知公告，2-工作汇报，3-公示牌,4-问题图片,5-问题视频,6-问题记录图片,7-问题记录视频,8-排污口,9-企业图片,10-投诉建议图片,11-投诉建议视频,12-投诉建议处理图片,13-摄像头,14-无人机,15-水质监测,16-一河一档,17-人员头像)',
  `file_size` decimal(10,0) NOT NULL COMMENT '文件大小',
  `full_path` varchar(400) NOT NULL COMMENT '文件全路径',
  `upload_time` datetime(3) NOT NULL COMMENT '文件上传时间',
  `business_id` varchar(32) DEFAULT NULL COMMENT '关联业务id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件表';

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` varchar(32) NOT NULL,
  `departname` varchar(50) NOT NULL COMMENT '部门名称',
  `description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父部门ID',
  `depart_code` varchar(64) DEFAULT NULL COMMENT '部门编码',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `fax` varchar(32) DEFAULT NULL COMMENT '传真',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `depart_order` varchar(5) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `sys_department_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `sys_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL,
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `role_rank` int(11) DEFAULT NULL COMMENT '角色级别(1~19)',
  `org_rank` varchar(2) DEFAULT NULL COMMENT '行政区划级别(1~5)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code_unique` (`role_code`) USING BTREE COMMENT '角色编码唯一性'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(30) DEFAULT '666666' COMMENT '密码',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `mobile_phone` varchar(30) DEFAULT NULL COMMENT '电话号码',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `depart_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `org_id` varchar(32) DEFAULT NULL COMMENT '行政区划id',
  `status` varchar(1) DEFAULT NULL COMMENT '有效状态（0-激活，1-未激活）',
  `is_del` varchar(1) DEFAULT NULL COMMENT '是否删除（0-未删除，1-删除）',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(30) DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
