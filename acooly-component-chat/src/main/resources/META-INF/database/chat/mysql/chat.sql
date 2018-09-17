/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : acooly

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-08-31 18:30:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `im_chat_user`
-- ----------------------------
DROP TABLE IF EXISTS `im_chat_user`;
CREATE TABLE `im_chat_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `nick_name` varchar(64) NOT NULL COMMENT '昵称',
  `signature` varchar(128) DEFAULT NULL COMMENT '个性签名',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `gender` int(11) DEFAULT NULL COMMENT '性别',
  `type` varchar(40) DEFAULT 'normal' COMMENT '类型{normal:普通用户,admin:管理员}',
  `status` varchar(40) DEFAULT NULL COMMENT '状态',
  `info_temp_id` bigint(20) DEFAULT NULL COMMENT '客服置顶消息模板id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='IM聊天-用户名';

-- ----------------------------
-- Records of im_chat_user
-- ----------------------------
-- INSERT INTO `im_chat_user` VALUES ('4', 'cuifuq72', '123123', '你好12', '你好', '2018-08-14', '1', 'admin', 'enable', null, '2018-08-31 17:46:58', '2018-08-31 17:54:30', '');

-- ----------------------------
-- Table structure for `im_chat_info_template`
-- ----------------------------
DROP TABLE IF EXISTS `im_chat_info_template`;
CREATE TABLE `im_chat_info_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `text` varchar(5000) NOT NULL COMMENT '内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IM聊天-客服信息模板';

-- ----------------------------
-- Records of im_chat_info_template
-- ----------------------------
