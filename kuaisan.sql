/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.1.62-community : Database - kuaisan
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kuaisan` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `kuaisan`;

/*Table structure for table `isautostar` */

DROP TABLE IF EXISTS `isautostar`;

CREATE TABLE `isautostar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '0' COMMENT '是否自动随机开奖 0、不自动  1、自动',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `isautostar` */

insert  into `isautostar`(`id`,`status`) values (1,1);

/*Table structure for table `lottery` */

DROP TABLE IF EXISTS `lottery`;

CREATE TABLE `lottery` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `Num` int(11) DEFAULT NULL COMMENT '开奖数',
  `creantime` varchar(50) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `lottery` */

/*Table structure for table `lottery_log` */

DROP TABLE IF EXISTS `lottery_log`;

CREATE TABLE `lottery_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '期数',
  `qiNum` varchar(50) DEFAULT NULL COMMENT '期号',
  `Num` int(11) DEFAULT NULL COMMENT '开奖号码',
  `creantime` varchar(50) DEFAULT NULL COMMENT '开奖时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=798 DEFAULT CHARSET=utf8;

/*Data for the table `lottery_log` */

/*Table structure for table `opennum` */

DROP TABLE IF EXISTS `opennum`;

CREATE TABLE `opennum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openNum` int(11) DEFAULT NULL COMMENT '设置一天开几期',
  `spareNum` int(11) DEFAULT NULL COMMENT '还剩下几期',
  `nowNum` int(11) DEFAULT NULL COMMENT '现在到哪一期了',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `opennum` */

insert  into `opennum`(`id`,`openNum`,`spareNum`,`nowNum`) values (1,99,45,55);

/*Table structure for table `tasktimer` */

DROP TABLE IF EXISTS `tasktimer`;

CREATE TABLE `tasktimer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL COMMENT '定时器状态 1、运行中 0、已停止',
  `person` int(11) DEFAULT NULL COMMENT '结束原因 1、手动停止 2、开奖号码为空',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tasktimer` */

insert  into `tasktimer`(`id`,`status`,`person`) values (1,0,1);

/*Table structure for table `timelong` */

DROP TABLE IF EXISTS `timelong`;

CREATE TABLE `timelong` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `timelong` int(11) DEFAULT NULL COMMENT '时长',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `timelong` */

insert  into `timelong`(`id`,`timelong`) values (4,10);

/*Table structure for table `timenumover` */

DROP TABLE IF EXISTS `timenumover`;

CREATE TABLE `timenumover` (
  `id` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL COMMENT '倒计时 用于同步前后端的时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `timenumover` */

insert  into `timenumover`(`id`,`number`) values (1,10);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
