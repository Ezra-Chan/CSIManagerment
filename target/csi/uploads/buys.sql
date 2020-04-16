/*
Navicat MySQL Data Transfer

Source Server         : Teacher
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : buys

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-12-29 11:12:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `eid` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(255) COLLATE utf8_bin NOT NULL,
  `epassword` varchar(255) COLLATE utf8_bin NOT NULL,
  `esex` varchar(255) COLLATE utf8_bin NOT NULL,
  `epost` varchar(255) COLLATE utf8_bin NOT NULL,
  `ephone` varchar(255) COLLATE utf8_bin NOT NULL,
  `achievement` varchar(255) COLLATE utf8_bin NOT NULL,
  `ework` int(11) NOT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('2', '何进表哥', '123345', '男', '何进组长', '17771418852', '2', '500');

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gkind` varchar(255) COLLATE utf8_bin NOT NULL,
  `gname` varchar(255) COLLATE utf8_bin NOT NULL,
  `price` int(11) NOT NULL,
  `gcount` int(11) NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('8', '肉类', '南京盐水鸭', '60', '500');
INSERT INTO `goods` VALUES ('10', '肉类', '南京酱板鸭0', '60', '500');
INSERT INTO `goods` VALUES ('11', '肉类', '烟台苹果', '60', '500');
INSERT INTO `goods` VALUES ('12', '肉类', '海南香蕉', '60', '500');

-- ----------------------------
-- Table structure for `manager`
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `mname` varchar(255) COLLATE utf8_bin NOT NULL,
  `mpassword` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('1', '何进亲媳妇', '123456');

-- ----------------------------
-- Table structure for `members`
-- ----------------------------
DROP TABLE IF EXISTS `members`;
CREATE TABLE `members` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `mname` varchar(255) COLLATE utf8_bin NOT NULL,
  `mpassword` varchar(255) COLLATE utf8_bin NOT NULL,
  `msex` varchar(255) COLLATE utf8_bin NOT NULL,
  `mphone` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of members
-- ----------------------------
INSERT INTO `members` VALUES ('1', '特技VIP', '123456', '男', '17771418850');

-- ----------------------------
-- Table structure for `selgoods`
-- ----------------------------
DROP TABLE IF EXISTS `selgoods`;
CREATE TABLE `selgoods` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `sekind` varchar(255) COLLATE utf8_bin NOT NULL,
  `sename` varchar(255) COLLATE utf8_bin NOT NULL,
  `secount` int(11) NOT NULL,
  `seprice` int(11) NOT NULL,
  `secountprice` int(11) NOT NULL,
  `setime` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of selgoods
-- ----------------------------
INSERT INTO `selgoods` VALUES ('5', '肉类', '南京酱板鸭', '60', '500', '2500', '2018/12/28 15:34:44');
INSERT INTO `selgoods` VALUES ('9', '肉类', '北京烤鸭', '60', '500', '30000', '2018/12/29 11:09:29');
