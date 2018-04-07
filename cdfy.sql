/*
Navicat MySQL Data Transfer

Source Server         : localhost3306
Source Server Version : 50172
Source Host           : localhost:3306
Source Database       : cdfy

Target Server Type    : MYSQL
Target Server Version : 50172
File Encoding         : 65001

Date: 2018-04-07 23:08:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activities_content
-- ----------------------------
DROP TABLE IF EXISTS `activities_content`;
CREATE TABLE `activities_content` (
  `ACTIVITIES_CONTENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVITIES_CONTENT_DESC` varchar(255) DEFAULT NULL,
  `ACTIVITIES_CONTENT_TITLE` varchar(50) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_ID` varchar(50) DEFAULT NULL,
  `ACTIVITIES_PERIOD_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ACTIVITIES_CONTENT_ID`),
  UNIQUE KEY `ACTIVITIES_CONTENT_ID` (`ACTIVITIES_CONTENT_ID`),
  KEY `FKFD51DA273BDCCEB` (`ACTIVITIES_PERIOD_ID`),
  CONSTRAINT `FKFD51DA273BDCCEB` FOREIGN KEY (`ACTIVITIES_PERIOD_ID`) REFERENCES `activities_period` (`ACTIVITIES_PERIOD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activities_content
-- ----------------------------

-- ----------------------------
-- Table structure for activities_period
-- ----------------------------
DROP TABLE IF EXISTS `activities_period`;
CREATE TABLE `activities_period` (
  `ACTIVITIES_PERIOD_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVITIES_PERIOD_NUM` varchar(50) DEFAULT NULL,
  `FILE_ID` varchar(250) DEFAULT NULL,
  `PRO_ACTIVITIES_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ACTIVITIES_PERIOD_ID`),
  UNIQUE KEY `ACTIVITIES_PERIOD_ID` (`ACTIVITIES_PERIOD_ID`),
  KEY `FK78A6625338B6007F` (`PRO_ACTIVITIES_ID`),
  CONSTRAINT `FK78A6625338B6007F` FOREIGN KEY (`PRO_ACTIVITIES_ID`) REFERENCES `pro_activities` (`PRO_ACTIVITIES_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activities_period
-- ----------------------------
INSERT INTO `activities_period` VALUES ('1', '31321', null, '1');

-- ----------------------------
-- Table structure for album
-- ----------------------------
DROP TABLE IF EXISTS `album`;
CREATE TABLE `album` (
  `ALBUM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ALBUM_NAME` varchar(50) DEFAULT NULL,
  `CREATE_ID` varchar(50) DEFAULT NULL,
  `CREAVATE_DATE` datetime DEFAULT NULL,
  `FILE_ID` varchar(250) DEFAULT NULL,
  `ACTIVITIES_PERIOD_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ALBUM_ID`),
  UNIQUE KEY `ALBUM_ID` (`ALBUM_ID`),
  KEY `FK5897E6F3BDCCEB` (`ACTIVITIES_PERIOD_ID`),
  CONSTRAINT `FK5897E6F3BDCCEB` FOREIGN KEY (`ACTIVITIES_PERIOD_ID`) REFERENCES `activities_period` (`ACTIVITIES_PERIOD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of album
-- ----------------------------

-- ----------------------------
-- Table structure for data_dict
-- ----------------------------
DROP TABLE IF EXISTS `data_dict`;
CREATE TABLE `data_dict` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `CREATE_ID` varchar(50) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data_dict
-- ----------------------------
INSERT INTO `data_dict` VALUES ('1', '010', '2016-04-29', 'admin', '代表性项目111', 'ITEM_TYPE');
INSERT INTO `data_dict` VALUES ('2', '010', '2016-04-29', 'admin', '青白江', 'AREA');
INSERT INTO `data_dict` VALUES ('3', '010', '2016-04-29', 'admin', '级别一级', 'GARD');
INSERT INTO `data_dict` VALUES ('4', '010', '2016-04-29', 'admin', '一批次', 'BATCH');
INSERT INTO `data_dict` VALUES ('5', '010', '2016-04-29', 'admin', '一级单位', 'UNIT_TYPE');
INSERT INTO `data_dict` VALUES ('9', '010', '2016-06-15', 'admin', '保存资料项目', 'SAVE_TYPE');

-- ----------------------------
-- Table structure for deputy_item
-- ----------------------------
DROP TABLE IF EXISTS `deputy_item`;
CREATE TABLE `deputy_item` (
  `DEPUTY_ITEM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_ID` varchar(50) DEFAULT NULL,
  `DECLARATION_BATCH` varchar(50) DEFAULT NULL,
  `DEPUTY_ITEM_GRADE` varchar(4) DEFAULT NULL,
  `DEPUTY_ITEM_NAME` varchar(50) DEFAULT NULL,
  `DEPUTY_ITEM_NO` varchar(50) DEFAULT NULL,
  `DEPUTY_ITEM_NUMBER` varchar(50) DEFAULT NULL,
  `DEPUTY_ITEM_TYPE` varchar(4) DEFAULT NULL,
  `ITEM_ADDRESS` varchar(200) DEFAULT NULL,
  `PROTECTION_UNIT` varchar(200) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DEPUTY_ITEM_ID`),
  UNIQUE KEY `DEPUTY_ITEM_ID` (`DEPUTY_ITEM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deputy_item
-- ----------------------------
INSERT INTO `deputy_item` VALUES ('1', '2016-04-29 11:11:33', 'admin', '010', '010', '4324', 'I-', '423423', '010', '010', '423423', '42342343');

-- ----------------------------
-- Table structure for deputy_person
-- ----------------------------
DROP TABLE IF EXISTS `deputy_person`;
CREATE TABLE `deputy_person` (
  `DEPUTY_PERSON_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_ID` varchar(50) DEFAULT NULL,
  `DEATH` varchar(50) DEFAULT NULL,
  `DEPUTY_PERSON_BATCH` varchar(50) DEFAULT NULL,
  `DEPUTY_PERSON_DESC` varchar(255) DEFAULT NULL,
  `DEPUTY_PERSON_GRADE` varchar(50) DEFAULT NULL,
  `DEPUTY_PERSON_NAME` varchar(50) DEFAULT NULL,
  `DEPUTY_IETM_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`DEPUTY_PERSON_ID`),
  UNIQUE KEY `DEPUTY_PERSON_ID` (`DEPUTY_PERSON_ID`),
  KEY `FKACA7CAC910DE0FE5` (`DEPUTY_IETM_ID`),
  CONSTRAINT `FKACA7CAC910DE0FE5` FOREIGN KEY (`DEPUTY_IETM_ID`) REFERENCES `deputy_item` (`DEPUTY_ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deputy_person
-- ----------------------------

-- ----------------------------
-- Table structure for deputy_unit
-- ----------------------------
DROP TABLE IF EXISTS `deputy_unit`;
CREATE TABLE `deputy_unit` (
  `DEPUTY_UNIT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_ID` varchar(50) DEFAULT NULL,
  `DEPUTY_UNIT_BATCH` varchar(50) DEFAULT NULL,
  `DEPUTY_UNIT_DESC` varchar(255) DEFAULT NULL,
  `DEPUTY_UNIT_GRADE` varchar(50) DEFAULT NULL,
  `DEPUTY_UNIT_NAME` varchar(200) DEFAULT NULL,
  `DEPUTY_UNIT_TYPE` varchar(50) DEFAULT NULL,
  `ITEM_ADDRESS` varchar(50) DEFAULT NULL,
  `DEPUTY_ITEM_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`DEPUTY_UNIT_ID`),
  UNIQUE KEY `DEPUTY_UNIT_ID` (`DEPUTY_UNIT_ID`),
  KEY `FKAE6855F829A360A7` (`DEPUTY_ITEM_ID`),
  CONSTRAINT `FKAE6855F829A360A7` FOREIGN KEY (`DEPUTY_ITEM_ID`) REFERENCES `deputy_item` (`DEPUTY_ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deputy_unit
-- ----------------------------

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `FILE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BUSINESS_TYPE` varchar(50) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_ID` varchar(50) DEFAULT NULL,
  `DEPUTY_PERSON_ID` int(11) DEFAULT NULL,
  `FILE_NAME` varchar(100) DEFAULT NULL,
  `FILE_URL` varchar(200) DEFAULT NULL,
  `TYPE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`FILE_ID`),
  UNIQUE KEY `FILE_ID` (`FILE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of files
-- ----------------------------
INSERT INTO `files` VALUES ('1', 'loginImg', '2016-11-04 11:13:25', 'admin', null, '默认1', 'upload//201611/04111325e2m1.jpg', '第1张');
INSERT INTO `files` VALUES ('2', 'loginImg', '2016-04-29 10:40:10', 'admin', null, '默认2', null, '第2张');
INSERT INTO `files` VALUES ('3', 'loginImg', '2016-04-29 10:40:10', 'admin', null, '默认3', null, '第3张');
INSERT INTO `files` VALUES ('4', 'loginImg', '2016-04-29 10:40:10', 'admin', null, '默认4', null, '第4张');
INSERT INTO `files` VALUES ('5', 'loginImg', '2016-04-29 10:40:10', 'admin', null, '默认5', null, '第5张');
INSERT INTO `files` VALUES ('6', 'loginImg', '2016-04-29 10:40:10', 'admin', null, '默认6', null, '第6张');
INSERT INTO `files` VALUES ('7', 'deputy', '2016-04-29 11:11:33', 'admin', null, '4324_测试', 'upload/201604/29111054hjnd.xls', 'doc');
INSERT INTO `files` VALUES ('8', 'deputy', '2016-04-29 11:11:33', 'admin', null, '4324_flvPlayer', 'upload/201604/291111127alr.swf', 'video');
INSERT INTO `files` VALUES ('9', 'deputy', '2016-04-29 11:11:33', 'admin', null, '4324_QQ截图20160130194514', 'upload/201604/29111121e68o.png', 'img');

-- ----------------------------
-- Table structure for item_apply
-- ----------------------------
DROP TABLE IF EXISTS `item_apply`;
CREATE TABLE `item_apply` (
  `APPLY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `APPLY_BATCH` varchar(50) DEFAULT NULL,
  `APPLY_DATE` datetime DEFAULT NULL,
  `APPLY_GARD` varchar(50) DEFAULT NULL,
  `DEPUTY_ITEM_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`APPLY_ID`),
  UNIQUE KEY `APPLY_ID` (`APPLY_ID`),
  KEY `FK8AA7874229A360A7` (`DEPUTY_ITEM_ID`),
  CONSTRAINT `FK8AA7874229A360A7` FOREIGN KEY (`DEPUTY_ITEM_ID`) REFERENCES `deputy_item` (`DEPUTY_ITEM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_apply
-- ----------------------------
INSERT INTO `item_apply` VALUES ('1', '010', '2016-04-29 11:11:33', '010', '1');

-- ----------------------------
-- Table structure for item_apply_files
-- ----------------------------
DROP TABLE IF EXISTS `item_apply_files`;
CREATE TABLE `item_apply_files` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FILE_ID` int(11) DEFAULT NULL,
  `APPALY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK65ECAB3A137849F` (`FILE_ID`),
  KEY `FK65ECAB3AAFA96532` (`APPALY_ID`),
  CONSTRAINT `FK65ECAB3A137849F` FOREIGN KEY (`FILE_ID`) REFERENCES `files` (`FILE_ID`),
  CONSTRAINT `FK65ECAB3AAFA96532` FOREIGN KEY (`APPALY_ID`) REFERENCES `item_apply` (`APPLY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_apply_files
-- ----------------------------
INSERT INTO `item_apply_files` VALUES ('1', '7', '1');
INSERT INTO `item_apply_files` VALUES ('2', '8', '1');
INSERT INTO `item_apply_files` VALUES ('3', '9', '1');

-- ----------------------------
-- Table structure for person_apply
-- ----------------------------
DROP TABLE IF EXISTS `person_apply`;
CREATE TABLE `person_apply` (
  `APPLY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `APPLY_BATCH` varchar(50) DEFAULT NULL,
  `APPLY_DATE` datetime DEFAULT NULL,
  `APPLY_GARD` varchar(50) DEFAULT NULL,
  `DEPUTY_PERSON_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`APPLY_ID`),
  UNIQUE KEY `APPLY_ID` (`APPLY_ID`),
  KEY `FKEC9CEE04BF1A227` (`DEPUTY_PERSON_ID`),
  CONSTRAINT `FKEC9CEE04BF1A227` FOREIGN KEY (`DEPUTY_PERSON_ID`) REFERENCES `deputy_person` (`DEPUTY_PERSON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person_apply
-- ----------------------------

-- ----------------------------
-- Table structure for person_apply_files
-- ----------------------------
DROP TABLE IF EXISTS `person_apply_files`;
CREATE TABLE `person_apply_files` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FILE_ID` int(11) DEFAULT NULL,
  `APPALY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK8E36787C137849F` (`FILE_ID`),
  KEY `FK8E36787C65C22BB0` (`APPALY_ID`),
  CONSTRAINT `FK8E36787C137849F` FOREIGN KEY (`FILE_ID`) REFERENCES `files` (`FILE_ID`),
  CONSTRAINT `FK8E36787C65C22BB0` FOREIGN KEY (`APPALY_ID`) REFERENCES `person_apply` (`APPLY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person_apply_files
-- ----------------------------

-- ----------------------------
-- Table structure for pro_activities
-- ----------------------------
DROP TABLE IF EXISTS `pro_activities`;
CREATE TABLE `pro_activities` (
  `PRO_ACTIVITIES_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_ID` varchar(50) DEFAULT NULL,
  `PRO_ACTIVITIES_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PRO_ACTIVITIES_ID`),
  UNIQUE KEY `PRO_ACTIVITIES_ID` (`PRO_ACTIVITIES_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pro_activities
-- ----------------------------
INSERT INTO `pro_activities` VALUES ('1', '2016-06-15 14:59:59', 'yzy', '123');

-- ----------------------------
-- Table structure for save_item
-- ----------------------------
DROP TABLE IF EXISTS `save_item`;
CREATE TABLE `save_item` (
  `ITEM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AREA` varchar(50) DEFAULT NULL,
  `BATCH` varchar(50) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `ITEM_NAME` varchar(50) DEFAULT NULL,
  `TYPE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ITEM_ID`),
  UNIQUE KEY `ITEM_ID` (`ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of save_item
-- ----------------------------

-- ----------------------------
-- Table structure for save_item_files
-- ----------------------------
DROP TABLE IF EXISTS `save_item_files`;
CREATE TABLE `save_item_files` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FILE_ID` int(11) DEFAULT NULL,
  `ITEM_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FKB7DE724D137849F` (`FILE_ID`),
  KEY `FKB7DE724D4AD7244D` (`ITEM_ID`),
  CONSTRAINT `FKB7DE724D137849F` FOREIGN KEY (`FILE_ID`) REFERENCES `files` (`FILE_ID`),
  CONSTRAINT `FKB7DE724D4AD7244D` FOREIGN KEY (`ITEM_ID`) REFERENCES `save_item` (`ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of save_item_files
-- ----------------------------

-- ----------------------------
-- Table structure for system_info
-- ----------------------------
DROP TABLE IF EXISTS `system_info`;
CREATE TABLE `system_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_info
-- ----------------------------
INSERT INTO `system_info` VALUES ('1', '成都');

-- ----------------------------
-- Table structure for text_fiels
-- ----------------------------
DROP TABLE IF EXISTS `text_fiels`;
CREATE TABLE `text_fiels` (
  `TEXT_FILES_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CATEGORY` varchar(50) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `CREATE_ID` varchar(50) DEFAULT NULL,
  `DEPARTMENT` varchar(50) DEFAULT NULL,
  `GARD` varchar(50) DEFAULT NULL,
  `PATH` varchar(50) DEFAULT NULL,
  `STATE` varchar(10) DEFAULT NULL,
  `TEXT_FILES_NAME` varchar(50) DEFAULT NULL,
  `TYPE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`TEXT_FILES_ID`),
  UNIQUE KEY `TEXT_FILES_ID` (`TEXT_FILES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of text_fiels
-- ----------------------------

-- ----------------------------
-- Table structure for unit_apply
-- ----------------------------
DROP TABLE IF EXISTS `unit_apply`;
CREATE TABLE `unit_apply` (
  `APPLY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `APPLY_BATCH` varchar(50) DEFAULT NULL,
  `APPLY_DATE` datetime DEFAULT NULL,
  `APPLY_GARD` varchar(50) DEFAULT NULL,
  `DEPUTY_UNIT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`APPLY_ID`),
  UNIQUE KEY `APPLY_ID` (`APPLY_ID`),
  KEY `FK964E8CF39A71FF07` (`DEPUTY_UNIT_ID`),
  CONSTRAINT `FK964E8CF39A71FF07` FOREIGN KEY (`DEPUTY_UNIT_ID`) REFERENCES `deputy_unit` (`DEPUTY_UNIT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of unit_apply
-- ----------------------------

-- ----------------------------
-- Table structure for unit_apply_files
-- ----------------------------
DROP TABLE IF EXISTS `unit_apply_files`;
CREATE TABLE `unit_apply_files` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FILE_ID` int(11) DEFAULT NULL,
  `APPALY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FKEAAAE82B137849F` (`FILE_ID`),
  KEY `FKEAAAE82B13226561` (`APPALY_ID`),
  CONSTRAINT `FKEAAAE82B13226561` FOREIGN KEY (`APPALY_ID`) REFERENCES `unit_apply` (`APPLY_ID`),
  CONSTRAINT `FKEAAAE82B137849F` FOREIGN KEY (`FILE_ID`) REFERENCES `files` (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of unit_apply_files
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CREATE_Date` datetime DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `PASS_WORD` varchar(50) DEFAULT NULL,
  `ROLE` varchar(10) DEFAULT NULL,
  `SEX` varchar(2) DEFAULT NULL,
  `STAUTS` varchar(2) DEFAULT NULL,
  `USER_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '2016-04-29 10:07:01', 'yzy', '202cb962ac59075b964b07152d234b70', 'manager', null, 'y', 'admin');
INSERT INTO `users` VALUES ('2', '2016-06-14 11:06:33', '张晓', '202cb962ac59075b964b07152d234b70', 'manager', '1', 'y', 'zx');
