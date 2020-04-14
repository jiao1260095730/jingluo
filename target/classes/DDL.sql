/*
Navicat MySQL Data Transfer

Source Server         : 阿里云
Source Server Version : 50646
Source Host           : 47.98.174.241:3306
Source Database       : db_jingluo

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2020-04-12 14:45:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `class_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `class_name` varchar(255) DEFAULT '' COMMENT '班级名称',
  `class_grade` varchar(255) DEFAULT '' COMMENT '年级',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段3',
  PRIMARY KEY (`id`,`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class
-- ----------------------------

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `course_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `teacher_id` int(10) unsigned DEFAULT NULL COMMENT '课程对应老师id，一个老师对应多个课程',
  `course_name` varchar(255) DEFAULT '' COMMENT '课程(节点名)',
  `course_status` char(255) DEFAULT '' COMMENT '当前课程状态(0未启动；1进行中；2已完成)',
  `course_info` varchar(255) DEFAULT '' COMMENT '课程详情',
  `notice` varchar(255) DEFAULT '' COMMENT '课程通知',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段3',
  PRIMARY KEY (`id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------

-- ----------------------------
-- Table structure for course_class
-- ----------------------------
DROP TABLE IF EXISTS `course_class`;
CREATE TABLE `course_class` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `cour_cls_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `course_id` int(10) unsigned DEFAULT NULL COMMENT '课程id',
  `class_id` int(10) unsigned DEFAULT NULL COMMENT '班级id',
  PRIMARY KEY (`id`,`cour_cls_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_class
-- ----------------------------

-- ----------------------------
-- Table structure for directory
-- ----------------------------
DROP TABLE IF EXISTS `directory`;
CREATE TABLE `directory` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `dir_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `base_id` int(10) unsigned DEFAULT NULL COMMENT '知识库id',
  `parent_id` int(10) unsigned DEFAULT NULL COMMENT '父级id(分为1/2级，1级为0，2级为对应的逻辑主键)',
  `dir_title` varchar(255) DEFAULT '' COMMENT '目录标题',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段3',
  PRIMARY KEY (`id`,`dir_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of directory
-- ----------------------------

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `course_id` int(10) unsigned DEFAULT NULL COMMENT '团队对应课程id',
  `class_id` int(10) unsigned DEFAULT NULL COMMENT '团队所在班级id',
  `group_name` varchar(255) DEFAULT '' COMMENT '团队名称',
  `group_info` varchar(255) DEFAULT '' COMMENT '团队简介',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `stu_num` int(10) unsigned DEFAULT NULL COMMENT '成员数量',
  `create_from` char(1) DEFAULT '' COMMENT '创建来源(0从课程创建；1自主创建)',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段3',
  PRIMARY KEY (`id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group
-- ----------------------------

-- ----------------------------
-- Table structure for know_base
-- ----------------------------
DROP TABLE IF EXISTS `know_base`;
CREATE TABLE `know_base` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `base_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `course_id` int(10) unsigned DEFAULT NULL COMMENT '知识库对应课程id',
  `group_id` int(10) unsigned DEFAULT NULL COMMENT '知识库对应团队id',
  `base_name` varchar(255) DEFAULT '' COMMENT '知识库名称',
  `parent_name` varchar(255) DEFAULT '' COMMENT '父级名称',
  `base_info` varchar(255) DEFAULT '' COMMENT '知识库简介',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  `doc_num` int(10) unsigned DEFAULT NULL COMMENT '知识库包含文档总量',
  `read_num` int(10) unsigned DEFAULT NULL COMMENT '知识库阅读总量',
  `att_num` int(10) unsigned DEFAULT NULL COMMENT '关注总量',
  `base_kind` char(1) DEFAULT '' COMMENT '知识库种类（1文档知识库；2画板知识库；3模板知识库）',
  `is_public` char(1) DEFAULT '' COMMENT '是否发布(0私有；1发布)',
  `is_delete` char(1) DEFAULT '' COMMENT '是否已删除(0否；1是)',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段3',
  PRIMARY KEY (`id`,`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of know_base
-- ----------------------------

-- ----------------------------
-- Table structure for know_doc
-- ----------------------------
DROP TABLE IF EXISTS `know_doc`;
CREATE TABLE `know_doc` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `doc_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `base_id` int(10) unsigned DEFAULT NULL COMMENT '文档对应知识库id',
  `dir_id` int(10) unsigned DEFAULT NULL COMMENT '目录节点id',
  `doc_title` varchar(100) DEFAULT '' COMMENT '文档标题',
  `doc_article` text COMMENT '文档内容',
  `read_num` int(10) unsigned DEFAULT NULL COMMENT '文档阅读总量',
  `yest_num` int(10) unsigned DEFAULT NULL COMMENT '昨日阅读总量',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  `collect_num` int(10) unsigned DEFAULT NULL COMMENT '收藏量',
  `att_num` int(10) unsigned DEFAULT NULL COMMENT '关注量',
  `is_delete` char(1) DEFAULT '' COMMENT '是否已删除(0否；1是)',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段3',
  PRIMARY KEY (`id`,`doc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of know_doc
-- ----------------------------

-- ----------------------------
-- Table structure for mission
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `mission_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `course_id` int(10) unsigned DEFAULT NULL COMMENT '课程id，一个课程对应多个任务',
  `mis_title` varchar(255) DEFAULT '' COMMENT '任务标题',
  `description` varchar(255) DEFAULT '' COMMENT '描述',
  `mis_leader` varchar(255) DEFAULT '' COMMENT '任务负责人',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `plan_deal_time` date DEFAULT NULL COMMENT '预期完成时间',
  `mis_key` varchar(255) DEFAULT '' COMMENT '标签，关键字',
  `mis_status` char(1) DEFAULT '' COMMENT '任务状态(0未启动；1进行中；2已完成)',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段3',
  PRIMARY KEY (`id`,`mission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mission
-- ----------------------------

-- ----------------------------
-- Table structure for stage
-- ----------------------------
DROP TABLE IF EXISTS `stage`;
CREATE TABLE `stage` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `stage_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `course_id` int(10) unsigned DEFAULT NULL COMMENT '课程id，一个课程对应多个任务',
  `sta_title` varchar(255) DEFAULT '' COMMENT '任务标题',
  `sta_leader` varchar(255) DEFAULT '' COMMENT '任务负责人',
  `sta_time` int(10) unsigned DEFAULT NULL COMMENT '课时',
  `description` varchar(255) DEFAULT '' COMMENT '描述',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `plan_deal_time` date DEFAULT NULL COMMENT '预期完成时间',
  `sta_key` varchar(255) DEFAULT '' COMMENT '标签，关键字',
  `sta_status` char(1) DEFAULT '' COMMENT '任务状态(0未启动；1进行中；2已完成)',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段3',
  PRIMARY KEY (`id`,`stage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stage
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `student_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `class_id` int(10) unsigned DEFAULT NULL COMMENT '学生所在班级id',
  `group_id` int(10) unsigned DEFAULT NULL COMMENT '学生所在团队id',
  `is_admin` char(1) DEFAULT '' COMMENT '是否是管理员(1管理员；2普通成员)',
  `student_code` varchar(255) DEFAULT '' COMMENT '学生学号，登陆凭证',
  `password` varchar(255) DEFAULT '' COMMENT '学生登录密码',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号',
  `head_img` varchar(255) DEFAULT '' COMMENT '头像',
  `nick_name` varchar(255) DEFAULT '' COMMENT '学生昵称',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段1',
  PRIMARY KEY (`id`,`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `stu_cour_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `student_id` int(10) unsigned DEFAULT NULL COMMENT '学生id',
  `course_id` int(10) unsigned DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`id`,`stu_cour_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_course
-- ----------------------------

-- ----------------------------
-- Table structure for student_group
-- ----------------------------
DROP TABLE IF EXISTS `student_group`;
CREATE TABLE `student_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `stu_gro_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `student_id` int(10) unsigned DEFAULT NULL COMMENT '学生id',
  `group_id` int(10) unsigned DEFAULT NULL COMMENT '团队id',
  PRIMARY KEY (`id`,`stu_gro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_group
-- ----------------------------

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `teacher_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `teacher_code` varchar(255) DEFAULT '' COMMENT '教师工号，登录凭证',
  `password` varchar(255) DEFAULT '' COMMENT '教师工号对应密码',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号',
  `nick_name` varchar(255) DEFAULT '' COMMENT '教师昵称',
  `head_img` varchar(255) DEFAULT '' COMMENT '头像',
  `reserved_1` varchar(255) DEFAULT '' COMMENT '预留字段1',
  `reserved_2` varchar(255) DEFAULT '' COMMENT '预留字段2',
  `reserved_3` varchar(255) DEFAULT '' COMMENT '预留字段3',
  PRIMARY KEY (`id`,`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------

-- ----------------------------
-- Table structure for teacher_class
-- ----------------------------
DROP TABLE IF EXISTS `teacher_class`;
CREATE TABLE `teacher_class` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `tch_cls_id` int(10) unsigned NOT NULL COMMENT '业务主键id，唯一索引',
  `teacher_id` int(10) unsigned DEFAULT NULL COMMENT '教师id',
  `class_id` int(10) unsigned DEFAULT NULL COMMENT '班级id',
  PRIMARY KEY (`id`,`tch_cls_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_class
-- ----------------------------

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rec_phone` varchar(255) DEFAULT NULL COMMENT '接收短息的手机号',
  `code` varchar(255) DEFAULT NULL COMMENT '接收到的验证码',
  `info` varchar(255) DEFAULT NULL COMMENT '记录信息',
  `type` int(10) unsigned DEFAULT NULL COMMENT '记录类型（1绑定手机号  2找回密码）',
  `flag` int(10) unsigned DEFAULT NULL COMMENT '标记（1成功  2失败）',
  `send_time` date DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sms_log
-- ----------------------------
