/*
Navicat MySQL Data Transfer

Source Server         : MMA
Source Server Version : 50558
Source Host           : 80.211.245.93:3306
Source Database       : MMA

Target Server Type    : MYSQL
Target Server Version : 50558
File Encoding         : 65001

Date: 2018-01-02 21:29:35
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `Category`
-- ----------------------------
DROP TABLE IF EXISTS `Category`;
CREATE TABLE `Category` (
  `Id_category` int(10) NOT NULL AUTO_INCREMENT,
  `Category_name` varchar(200) NOT NULL,
  PRIMARY KEY (`Id_category`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Category
-- ----------------------------

-- ----------------------------
-- Table structure for `DefaultSchedule`
-- ----------------------------
DROP TABLE IF EXISTS `DefaultSchedule`;
CREATE TABLE `DefaultSchedule` (
  `Id_default_sch` int(11) NOT NULL AUTO_INCREMENT,
  `Id_employee` int(255) DEFAULT NULL,
  `Day_of_the_week` varchar(255) DEFAULT NULL,
  `Start_time` varchar(10) DEFAULT NULL,
  `Closing_time` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Id_default_sch`) USING BTREE,
  KEY `empl_sched` (`Id_employee`),
  CONSTRAINT `empl_sched` FOREIGN KEY (`Id_employee`) REFERENCES `Employee` (`Id_employee`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of DefaultSchedule
-- ----------------------------

-- ----------------------------
-- Table structure for `Employee`
-- ----------------------------
DROP TABLE IF EXISTS `Employee`;
CREATE TABLE `Employee` (
  `Id_employee` int(255) NOT NULL AUTO_INCREMENT,
  `Login` varchar(20) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `First_name` varchar(255) NOT NULL,
  `Last_name` varchar(255) NOT NULL,
  `Pesel` varchar(255) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Phone_number` int(11) DEFAULT NULL,
  `Id_workstation` int(255) DEFAULT NULL,
  PRIMARY KEY (`Id_employee`),
  KEY `empl_works` (`Id_workstation`),
  CONSTRAINT `empl_works` FOREIGN KEY (`Id_workstation`) REFERENCES `Workstation` (`Id_workstation`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Employee
-- ----------------------------
INSERT INTO `Employee` VALUES ('1', 'kwanat', '398d6f53f5b562a7a9769d96ac5dba90f1908a35', 'Kamil', 'Wanat', '94102401313', 'adres', '889743383', '1');
INSERT INTO `Employee` VALUES ('5', 'aszaton', '6a30e37314964c5abf2bd6469b3db17cc70a0ade', 'Anna', 'Szaton', '', '', '0', '1');

-- ----------------------------
-- Table structure for `Message`
-- ----------------------------
DROP TABLE IF EXISTS `Message`;
CREATE TABLE `Message` (
  `Id_message` int(100) NOT NULL AUTO_INCREMENT,
  `Id_employee` int(100) NOT NULL,
  `Title` varchar(100) NOT NULL,
  `Date` date NOT NULL,
  `Message` varchar(1000) NOT NULL,
  PRIMARY KEY (`Id_message`),
  KEY `employe_message` (`Id_employee`),
  CONSTRAINT `employe_message` FOREIGN KEY (`Id_employee`) REFERENCES `Employee` (`Id_employee`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Message
-- ----------------------------
INSERT INTO `Message` VALUES ('1', '1', 'jfhf gg ufud', '2017-12-29', 'hd hd uF uF uF,yxyxhxhdy.');

-- ----------------------------
-- Table structure for `Schedule`
-- ----------------------------
DROP TABLE IF EXISTS `Schedule`;
CREATE TABLE `Schedule` (
  `Id_schedule` int(100) NOT NULL AUTO_INCREMENT,
  `Id_employee` int(100) NOT NULL,
  `Date` date NOT NULL,
  `Start_time` varchar(20) NOT NULL,
  `End_time` varchar(20) NOT NULL,
  `Approved` varchar(2) NOT NULL,
  PRIMARY KEY (`Id_schedule`),
  KEY `employe_schedule` (`Id_employee`),
  CONSTRAINT `employe_schedule` FOREIGN KEY (`Id_employee`) REFERENCES `Employee` (`Id_employee`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Schedule
-- ----------------------------

-- ----------------------------
-- Table structure for `Vacation`
-- ----------------------------
DROP TABLE IF EXISTS `Vacation`;
CREATE TABLE `Vacation` (
  `Id_vacation` int(100) NOT NULL AUTO_INCREMENT,
  `Id_employee` int(100) NOT NULL,
  `Start_date` date NOT NULL,
  `End_date` date NOT NULL,
  `Status` varchar(1) NOT NULL,
  PRIMARY KEY (`Id_vacation`),
  KEY `employe_vacation` (`Id_employee`),
  CONSTRAINT `employe_vacation` FOREIGN KEY (`Id_employee`) REFERENCES `Employee` (`Id_employee`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Vacation
-- ----------------------------
INSERT INTO `Vacation` VALUES ('1', '1', '2018-01-02', '2018-01-16', 'r');

-- ----------------------------
-- Table structure for `Warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `Warehouse`;
CREATE TABLE `Warehouse` (
  `Id_product` int(100) NOT NULL AUTO_INCREMENT,
  `Id_category` int(10) NOT NULL,
  `Product_name` varchar(100) NOT NULL,
  `Actual_quantity` int(10) NOT NULL,
  `Critical_quantity` int(10) NOT NULL,
  PRIMARY KEY (`Id_product`),
  KEY `category_warehouse` (`Id_category`),
  CONSTRAINT `category_warehouse` FOREIGN KEY (`Id_category`) REFERENCES `Category` (`Id_category`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for `Workstation`
-- ----------------------------
DROP TABLE IF EXISTS `Workstation`;
CREATE TABLE `Workstation` (
  `Id_workstation` int(255) NOT NULL AUTO_INCREMENT,
  `Workstation_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id_workstation`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Workstation
-- ----------------------------
INSERT INTO `Workstation` VALUES ('1', 'Wlasciciel');
INSERT INTO `Workstation` VALUES ('2', 'Kierownik');
INSERT INTO `Workstation` VALUES ('3', 'Techniczny');
INSERT INTO `Workstation` VALUES ('4', 'Pracownik sali');
