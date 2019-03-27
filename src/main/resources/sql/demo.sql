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
-- Table structure for boy
-- ----------------------------
DROP TABLE IF EXISTS `boy`;
CREATE TABLE `boy` (
  `id` varchar(32) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `girl_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for girl
-- ----------------------------
DROP TABLE IF EXISTS `girl`;
CREATE TABLE `girl` (
  `id` varchar(32) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `cup_size` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `password` varchar(30) DEFAULT NULL COMMENT '密码',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `mobile_phone` varchar(30) DEFAULT NULL COMMENT '电话号码',
  `depart_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `status` varchar(1) DEFAULT NULL COMMENT '有效状态（0-激活，1-未激活）',
  `is_del` varchar(1) DEFAULT NULL COMMENT '是否删除（0-未删除，1-删除）',
  `create_by` varchar(30) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_unique` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
