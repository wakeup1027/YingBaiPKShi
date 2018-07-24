/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.1.62-community : Database - bjpks
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bjpks` /*!40100 DEFAULT CHARACTER SET gbk */;

USE `bjpks`;

/*Table structure for table `applymoney` */

DROP TABLE IF EXISTS `applymoney`;

CREATE TABLE `applymoney` (
  `id` varchar(36) NOT NULL,
  `fd_money` int(11) DEFAULT NULL COMMENT '申请提现金额',
  `fd_userid` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `fd_username` varchar(36) DEFAULT NULL COMMENT '会员名',
  `fd_status` varchar(5) DEFAULT NULL COMMENT '申请状态 0.审核中  1.已受理  2.提现完成',
  `fd_creatime` datetime DEFAULT NULL COMMENT '申请时间',
  `fd_arraytime` datetime DEFAULT NULL COMMENT '完成时间',
  `fd_commit` varchar(255) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `applymoney` */

insert  into `applymoney`(`id`,`fd_money`,`fd_userid`,`fd_username`,`fd_status`,`fd_creatime`,`fd_arraytime`,`fd_commit`) values ('7484fa3713354c24932c3a11d9086384',10000,'a1c71b91db23414e8d31899e8665145e','wakeup1027','0','2018-07-22 01:12:51','2018-07-26 01:30:37','提现10000元');

/*Table structure for table `applymoneylog` */

DROP TABLE IF EXISTS `applymoneylog`;

CREATE TABLE `applymoneylog` (
  `fd_id` varchar(36) NOT NULL,
  `fd_money` int(11) DEFAULT NULL COMMENT '申请提现金额',
  `fd_userid` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `fd_username` varchar(36) DEFAULT NULL COMMENT '会员名',
  `fd_status` varchar(5) DEFAULT NULL COMMENT '申请状态',
  `fd_creatime` datetime DEFAULT NULL COMMENT '申请时间',
  `fd_commit` varchar(255) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`fd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `applymoneylog` */

/*Table structure for table `betsdata` */

DROP TABLE IF EXISTS `betsdata`;

CREATE TABLE `betsdata` (
  `fd_id` varchar(36) NOT NULL,
  `fd_userid` varchar(50) DEFAULT NULL COMMENT '会员ID',
  `fd_username` varchar(15) DEFAULT NULL COMMENT '会员名',
  `fd_type` varchar(5) DEFAULT NULL COMMENT '类型',
  `fd_num` varchar(5) DEFAULT NULL COMMENT '下注号码',
  `fd_qishu` varchar(50) DEFAULT NULL COMMENT '期数',
  `fd_creatime` datetime DEFAULT NULL COMMENT '下注时间',
  `fd_tatol` int(11) DEFAULT NULL COMMENT '总价',
  PRIMARY KEY (`fd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `betsdata` */

insert  into `betsdata`(`fd_id`,`fd_userid`,`fd_username`,`fd_type`,`fd_num`,`fd_qishu`,`fd_creatime`,`fd_tatol`) values ('17fabab0849c450c966c7368485373db','a1c71b91db23414e8d31899e8665145e','wakeup1027','2','7','688747','2018-07-02 23:30:17',100),('1df5b0cf37214fd1aeebfe38242f6394','a1c71b91db23414e8d31899e8665145e','wakeup1027','4','3','688747','2018-07-02 23:34:24',200),('3b3d6557365d46ee8ac369fd1e67540c','c6addc6c85d8401fabbe78add88ff2c3','wwww','1','4','688747','2018-07-08 21:20:25',50),('47c2549080b849899c7b905e5fd264a4','a1c71b91db23414e8d31899e8665145e','wakeup1027','1','1','688747','2018-07-02 23:34:24',5000),('48b96258b8174a17a01468e9115bb661','a1c71b91db23414e8d31899e8665145e','wakeup1027','5','7','688747','2018-07-02 23:30:17',100),('495a3173ad29472fb314454f6126061c','a1c71b91db23414e8d31899e8665145e','wakeup1027','5','1','688747','2018-07-02 23:34:24',1000),('6507185ea3af4e4692c819fbd162f861','a1c71b91db23414e8d31899e8665145e','wakeup1027','3','2','688747','2018-07-02 23:34:24',800),('7513d2f928194dce9828c2d08abaa320','c6addc6c85d8401fabbe78add88ff2c3','wwww','2','6','688747','2018-07-08 21:20:25',30),('7722a3b9ec0f4fad8fbf2ca49c7a73a7','a1c71b91db23414e8d31899e8665145e','wakeup1027','3','2','688747','2018-07-02 23:30:17',500),('7bf3040c5f1b41d180c343c26f969d4c','c6addc6c85d8401fabbe78add88ff2c3','wwww','4','3','688747','2018-07-08 21:20:25',50),('88a9b72b5e5b4444a120467c773a16a5','a1c71b91db23414e8d31899e8665145e','wakeup1027','3','5','688747','2018-07-07 20:07:02',100),('9fda18f13da6405f82367d126e2f0325','a1c71b91db23414e8d31899e8665145e','wakeup1027','4','7','688747','2018-07-02 23:38:20',500),('a3374ca2651346ffa62fd44e6b8d2835','a1c71b91db23414e8d31899e8665145e','wakeup1027','2','3','688747','2018-07-02 23:34:24',3000),('c24b6484cff64d1588e8ca81645c9bb2','a1c71b91db23414e8d31899e8665145e','wakeup1027','1','1','688747','2018-07-02 23:30:17',100),('dc80c3e21595400990688d8586df678e','a1c71b91db23414e8d31899e8665145e','wakeup1027','2','1','688747','2018-07-12 23:59:48',60),('e147c587a2024beeb9945dbba4d1c4ac','a1c71b91db23414e8d31899e8665145e','wakeup1027','3','4','688747','2018-07-12 23:59:48',50),('e5e518928f4c47c98db4b54e977ec516','a1c71b91db23414e8d31899e8665145e','wakeup1027','1','1','688747','2018-07-02 23:38:09',100),('e691e58f9f924e9284386a1ecff736c7','c6addc6c85d8401fabbe78add88ff2c3','wwww','1','7','688747','2018-07-08 21:20:25',30),('f7b7b818c64d4d5387759a300e27f174','c6addc6c85d8401fabbe78add88ff2c3','wwww','2','1','688747','2018-07-08 21:20:25',40),('ff302263d50d4f81a32c549d72e8e049','a1c71b91db23414e8d31899e8665145e','wakeup1027','5','2','688747','2018-07-12 23:59:48',40);

/*Table structure for table `betsdatalog` */

DROP TABLE IF EXISTS `betsdatalog`;

CREATE TABLE `betsdatalog` (
  `fd_id` varchar(36) NOT NULL,
  `fd_userid` varchar(50) DEFAULT NULL COMMENT '会员ID',
  `fd_username` varchar(15) DEFAULT NULL COMMENT '会员名',
  `fd_type` varchar(5) DEFAULT NULL COMMENT '类型',
  `fd_num` varchar(5) DEFAULT NULL COMMENT '下注号码',
  `fd_qishu` varchar(50) DEFAULT NULL COMMENT '期数',
  `fd_creatime` datetime DEFAULT NULL COMMENT '下注时间',
  `fd_tatol` int(11) DEFAULT NULL COMMENT '总价',
  `fd_iswin` varchar(10) DEFAULT NULL COMMENT '是否赢 0.输  1赢  2.未开奖',
  PRIMARY KEY (`fd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `betsdatalog` */

insert  into `betsdatalog`(`fd_id`,`fd_userid`,`fd_username`,`fd_type`,`fd_num`,`fd_qishu`,`fd_creatime`,`fd_tatol`,`fd_iswin`) values ('04188c6763fb4b689bdeef7348260092','a1c71b91db23414e8d31899e8665145e','wakeup1027','1','1','691981','2018-07-13 00:06:28',80,'2'),('07be4bd8968545b891c59a47347da272','a1c71b91db23414e8d31899e8665145e','wakeup1027','5','1','691981','2018-07-13 00:06:28',60,'2'),('41484a1256cf44859647438895461a93','a1c71b91db23414e8d31899e8665145e','wakeup1027','2','3','693385','2018-07-17 21:42:27',40,'2'),('48dbe4ddcb5a4344ac379376d26f71cf','a1c71b91db23414e8d31899e8665145e','wakeup1027','4','4','693385','2018-07-17 21:39:32',50,'2'),('4f8a376200f14807bfdbe1021efbb0d8','a1c71b91db23414e8d31899e8665145e','wakeup1027','2','5','693386','2018-07-17 21:42:50',40,'2'),('55b581b177a8476984193bd991e7e744','a1c71b91db23414e8d31899e8665145e','wakeup1027','3','2','693385','2018-07-17 21:41:48',40,'2'),('571817816dca4a40a724ac838416dd22','a1c71b91db23414e8d31899e8665145e','wakeup1027','2','3','693386','2018-07-17 21:43:19',40,'2'),('58afd88030784a569eac5311f7b1feb2','a1c71b91db23414e8d31899e8665145e','wakeup1027','1','1','693386','2018-07-17 21:42:50',70,'2'),('59a6bfee7ace45138c9b802724e05e03','a1c71b91db23414e8d31899e8665145e','wakeup1027','3','5','691981','2018-07-13 00:06:28',70,'2'),('8090edcfe56540a09d34387c6bd46fbf','a1c71b91db23414e8d31899e8665145e','wakeup1027','1','1','693385','2018-07-17 21:39:32',60,'2'),('95557fd07d364cb7b780215ea243b7b9','a1c71b91db23414e8d31899e8665145e','wakeup1027','2','2','693385','2018-07-17 21:39:32',50,'2'),('b6038c970170442891fb3395deba8a58','a1c71b91db23414e8d31899e8665145e','wakeup1027','3','4','693386','2018-07-17 21:42:50',30,'2'),('b909e37d19544d7fb805e41e805f9b55','a1c71b91db23414e8d31899e8665145e','wakeup1027','3','1','693385','2018-07-17 21:42:27',40,'2'),('d261a9912edb4f3ab29a40ac628eafa1','a1c71b91db23414e8d31899e8665145e','wakeup1027','2','1','693385','2018-07-17 21:41:48',40,'2'),('d70d75d24c7f420e97c43dd72596e8d8','a1c71b91db23414e8d31899e8665145e','wakeup1027','2','5','693385','2018-07-17 21:39:32',130,'2'),('e82ce0d17646441990e49f12589695bb','a1c71b91db23414e8d31899e8665145e','wakeup1027','3','1','693386','2018-07-17 21:43:19',40,'2'),('f407c76b10dc4fd5972caedc37df40c9','a1c71b91db23414e8d31899e8665145e','wakeup1027','4','1','693386','2018-07-17 21:43:06',40,'2');

/*Table structure for table `opennumber` */

DROP TABLE IF EXISTS `opennumber`;

CREATE TABLE `opennumber` (
  `fd_id` varchar(36) NOT NULL,
  `fd_number` varchar(50) DEFAULT NULL COMMENT '开奖号码',
  `fd_qishu` int(11) DEFAULT NULL COMMENT '期数',
  `fd_creatime` datetime DEFAULT NULL COMMENT '开奖时间',
  `fd_timestamp` int(11) DEFAULT NULL COMMENT '时间戳',
  PRIMARY KEY (`fd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `opennumber` */

insert  into `opennumber`(`fd_id`,`fd_number`,`fd_qishu`,`fd_creatime`,`fd_timestamp`) values ('007c0a9206084626b045a02fd9f730c5','04,02,01,08,03,09,10,07,05,06',693752,'2018-07-19 22:27:42',1532010462),('0a111031a9f74b879eae1bda0907ec49','04,06,08,01,09,02,03,05,10,07',693753,'2018-07-19 22:32:50',1532010770),('0ef845612699487cbd140e14c0c0406a','03,06,02,01,08,04,10,09,07,05',693386,'2018-07-17 21:47:41',1531835261),('0ff5fa19d0e14820ba97ef8daff86704','05,07,06,02,09,10,04,03,08,01',693027,'2018-07-15 21:42:43',1531662163),('17e26807777443ddb0d180566e308946','04,05,02,09,01,03,08,06,07,10',693028,'2018-07-15 21:47:40',1531662460),('1b9b369b8afd40ff8f116d4328f8544e','04,10,01,09,08,05,03,02,06,07',693036,'2018-07-15 22:27:41',1531664861),('21c1a0c2c0af45a18b8c0f91be8da1f6','07,10,09,04,02,01,06,03,05,08',693385,'2018-07-17 21:42:43',1531834963),('23a4e08656e54cb08e4a13743d07e958','10,08,04,03,07,01,05,09,02,06',693020,'2018-07-15 21:07:39',1531660059),('2496e48b900b42b2b544e86aaf55a478','02,03,01,07,04,05,08,06,10,09',693029,'2018-07-15 21:52:41',1531662761),('399e01e5394b49628d277e4bc5e2cc96','05,10,02,01,09,07,08,03,04,06',693381,'2018-07-17 21:22:43',1531833763),('3e4bffabfe5249adac320c37d8cda451','07,09,08,01,03,05,10,02,06,04',693393,'2018-07-17 22:22:39',1531837359),('45c26fa029804af58d554f90d00bd2fb','06,10,02,04,03,07,05,09,01,08',693026,'2018-07-15 21:37:40',1531661860),('4999623318b2490b9285fb56e9bbd32e','07,06,05,02,10,01,08,04,03,09',693754,'2018-07-19 22:37:39',1532011059),('4a6a7187afb247ed90c837d94a7466a6','05,06,10,07,03,09,01,02,08,04',693388,'2018-07-17 21:57:41',1531835861),('4b2cfbb69f5a47c392b3241d54bc78d7','04,05,08,06,07,01,03,10,02,09',693390,'2018-07-17 22:07:38',1531836458),('4cf7fa912ee04e00afd9db644f033148','06,03,01,04,09,02,08,05,10,07',693034,'2018-07-15 22:17:41',1531664261),('52ae9a00be264ae4b32dad9b80764467','10,07,08,09,02,04,05,01,03,06',693380,'2018-07-17 21:17:39',1531833459),('582634157d3443c3948323146bda1f4c','07,03,10,06,05,01,02,08,09,04',693387,'2018-07-17 21:52:40',1531835560),('5dcb423de2484d8b8696d9ab7e0ff888','09,02,03,04,06,05,10,01,08,07',693030,'2018-07-15 21:57:40',1531663060),('676694c97fa144d1a908737765471f57','03,02,01,09,04,05,10,08,07,06',693391,'2018-07-17 22:12:35',1531836755),('67e24fa8277a4fac91c6580b1b23e5e6','05,02,01,10,07,06,03,04,08,09',691980,'2018-07-09 23:57:39',1531151859),('689ab98eb2754195902b4ed1e50e59c2','05,06,08,07,01,09,02,04,03,10',693392,'2018-07-17 22:17:36',1531837056),('856f4cc75c18474bb3ebd9c50fd88658','01,07,02,09,08,06,05,04,03,10',693025,'2018-07-15 21:32:43',1531661563),('8bea940272394ed5abc725268803fe02','10,04,02,01,03,07,08,09,05,06',693755,'2018-07-19 22:42:39',1532011359),('8cdd97c83a294eab853bf74d193eca7b','04,01,06,10,09,08,05,03,02,07',693383,'2018-07-17 21:32:41',1531834361),('913cc936b7fd4cc688c3215ebdd1d1f8','07,08,01,04,05,03,02,09,06,10',693024,'2018-07-15 21:27:40',1531661260),('921a93b6cb4f4ff6b4e9dad64216bce9','04,08,02,09,05,06,10,03,07,01',693394,'2018-07-17 22:27:39',1531837659),('956b4c5ad22446338db342868ff43a09','08,01,05,07,03,04,02,06,10,09',693382,'2018-07-17 21:27:43',1531834063),('963d04ae259b432287d8d3818db330a0','04,08,07,02,10,01,09,05,06,03',694128,'2018-07-21 23:57:41',1532188661),('9c4dd9e532d342f3a771a1bbb97250e2','01,09,06,04,05,07,10,03,02,08',693035,'2018-07-15 22:22:41',1531664561),('a74c5cd0b35943ec81b5a117b92bc4e4','01,03,02,08,06,04,07,05,10,09',693021,'2018-07-15 21:12:40',1531660360),('b59c67bf196a4758191e42f76670ceba','03,01,04,09,08,07,06,10,02,05',688747,'2018-06-23 23:56:34',NULL),('b77024b3f86845f1aad97982b5893880','05,07,01,03,08,04,02,09,10,06',693376,'2018-07-17 20:57:39',1531832259),('b892f2694f6549f3bae797e8c1c8f785','06,10,05,07,02,08,04,03,01,09',693031,'2018-07-15 22:02:41',1531663361),('bd6aa55519be4ed19053b0871506c1e1','09,07,06,08,03,04,10,02,05,01',693384,'2018-07-17 21:37:44',1531834664),('bdf9ff7519b340e08a18d322cd1ae57a','06,05,01,09,08,02,03,10,04,07',691979,'2018-07-09 23:52:42',1531151562),('c1dd478da1d247d3b07efe69496eb47a','09,06,02,04,05,01,08,03,07,10',693033,'2018-07-15 22:12:44',1531663964),('c74499a08f5347bba23d26c0bb431fb5','09,03,02,10,06,07,08,04,01,05',693023,'2018-07-15 21:22:42',1531660962),('c9abeb05e5084403858de47868a6a9d6','06,10,04,09,01,07,02,03,08,05',693760,'2018-07-19 23:07:39',1532012859),('ca6abca840774643a39c631b8d405d05','05,10,02,04,07,08,06,09,03,01',693379,'2018-07-17 21:12:40',1531833160),('cc5c2190177b4f81a6e3a192d16581f9','09,08,06,03,07,01,04,10,02,05',693019,'2018-07-15 21:02:39',1531659759),('cd56b2c444b24cdc8de01c6a74e4a61a','07,08,05,09,01,06,03,02,10,04',693389,'2018-07-17 22:02:39',1531836159),('ce0c34177cb04860abba1ce84dc7fc40','01,02,03,09,04,06,07,05,08,10',691978,'2018-07-09 23:47:38',1531151258),('cf61674293f44fa99089db071c487ce6','02,03,09,08,10,05,04,01,06,07',693756,'2018-07-19 22:47:40',1532011660),('cff202b31089475ea1e9f52a33a6d3cb','07,04,10,01,05,03,09,02,08,06',693022,'2018-07-15 21:17:42',1531660662),('d3c8bab015be41ffb3e4821c2dc2bd59','01,05,10,07,03,08,02,09,04,06',693759,'2018-07-19 23:02:43',1532012563),('d4c5e369ba3e4262b74be46c5237339a','06,07,05,10,01,03,04,02,08,09',693032,'2018-07-15 22:07:44',1531663664),('d6def4905120400084fa075c9642d88e','08,01,04,10,05,07,02,03,09,06',693758,'2018-07-19 22:57:43',1532012263),('d99f042d6c8d45769e5863976e39cd1f','08,02,06,07,10,03,05,01,04,09',693018,'2018-07-15 20:57:39',1531659459),('eda5f53037204f91be6cd631babfa946','06,03,05,01,09,10,07,08,02,04',693378,'2018-07-17 21:07:40',1531832860),('f386bac3c9e5499f8588c7fd9bd193e8','01,05,02,09,04,08,06,03,07,10',693377,'2018-07-17 21:02:40',1531832560);

/*Table structure for table `recharge` */

DROP TABLE IF EXISTS `recharge`;

CREATE TABLE `recharge` (
  `id` varchar(36) NOT NULL,
  `fd_money` int(11) DEFAULT NULL COMMENT '充值金额',
  `fd_userid` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `fd_username` varchar(36) DEFAULT NULL COMMENT '会员名',
  `fd_status` varchar(5) DEFAULT NULL COMMENT '充值状态 0.审核中  1.已到账',
  `fd_creatime` datetime DEFAULT NULL COMMENT '充值时间',
  `fd_arraytime` datetime DEFAULT NULL COMMENT '到账时间',
  `fd_ordernum` varchar(36) DEFAULT NULL COMMENT '支付订单号',
  `fd_ordertype` varchar(36) DEFAULT NULL COMMENT '支付平台',
  `fd_commit` varchar(250) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `recharge` */

insert  into `recharge`(`id`,`fd_money`,`fd_userid`,`fd_username`,`fd_status`,`fd_creatime`,`fd_arraytime`,`fd_ordernum`,`fd_ordertype`,`fd_commit`) values ('8cc3fee8d83f4ca7ab8f7ecea4a28d9d',14500,'a1c71b91db23414e8d31899e8665145e','wakeup1027','1','2018-07-22 00:58:50','2018-07-22 00:59:12','ewrwer3455412123','2',NULL);

/*Table structure for table `rechargenow` */

DROP TABLE IF EXISTS `rechargenow`;

CREATE TABLE `rechargenow` (
  `fd_id` varchar(36) NOT NULL,
  `fd_money` int(11) DEFAULT NULL COMMENT '充值金额',
  `fd_userid` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `fd_username` varchar(36) DEFAULT NULL COMMENT '会员名',
  `fd_status` varchar(5) DEFAULT NULL COMMENT '充值状态 0、审核中 1、审核成功 2、审核失败',
  `fd_creatime` datetime DEFAULT NULL COMMENT '充值时间',
  `fd_arraytime` datetime DEFAULT NULL COMMENT '到账时间',
  `fd_ordernum` varchar(36) DEFAULT NULL COMMENT '支付订单号',
  `fd_ordertype` varchar(36) DEFAULT NULL COMMENT '支付平台',
  `fd_commit` varchar(250) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`fd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `rechargenow` */

/*Table structure for table `secondtable` */

DROP TABLE IF EXISTS `secondtable`;

CREATE TABLE `secondtable` (
  `id` varchar(50) NOT NULL,
  `second` int(11) DEFAULT NULL COMMENT '倒数秒数',
  `close` varchar(5) DEFAULT NULL COMMENT '封盘 0.封盘  1.正常',
  `closetime` int(11) DEFAULT NULL COMMENT '封盘时间（秒）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `secondtable` */

insert  into `secondtable`(`id`,`second`,`close`,`closetime`) values ('857bef8a26ba4e97aa5550c4072fdebe',0,'0',40);

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` varchar(36) NOT NULL,
  `fd_money` int(11) DEFAULT '0' COMMENT '账户余额',
  `fd_icemoney` int(11) DEFAULT '0' COMMENT '冻结金额',
  `fd_applymoney` int(11) DEFAULT NULL COMMENT '提现金额',
  `fd_username` varchar(36) DEFAULT NULL COMMENT '会员名',
  `fd_truename` varchar(36) DEFAULT NULL COMMENT '真实姓名',
  `fd_phone` varchar(36) DEFAULT NULL COMMENT '手机号码',
  `fd_bank` varchar(36) DEFAULT NULL COMMENT '银行卡',
  `fd_creatime` datetime DEFAULT NULL COMMENT '注册时间',
  `fd_status` varchar(5) DEFAULT NULL COMMENT '账号状态 0.未激活 1.正常 2.冻结',
  `fd_password` varchar(100) DEFAULT NULL COMMENT '密码',
  `fd_IDcase` varchar(100) DEFAULT NULL COMMENT '身份证',
  `fd_paypassword` varchar(100) DEFAULT NULL COMMENT '支付密码',
  `fd_tjUser` varchar(50) DEFAULT NULL COMMENT '推荐人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `userinfo` */

insert  into `userinfo`(`id`,`fd_money`,`fd_icemoney`,`fd_applymoney`,`fd_username`,`fd_truename`,`fd_phone`,`fd_bank`,`fd_creatime`,`fd_status`,`fd_password`,`fd_IDcase`,`fd_paypassword`,`fd_tjUser`) values ('a1c71b91db23414e8d31899e8665145e',10000,0,10000,'wakeup1027','李向前','15799024022','4560359982233','2018-07-03 00:11:04','1','123456','4600016393165556','456789','wakeup1027');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
