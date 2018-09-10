/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.1.62-community : Database - crm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`crm` /*!40100 DEFAULT CHARACTER SET gb2312 */;

USE `crm`;

/*Table structure for table `authorities` */

DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `authorities` (
  `id` float DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  `parent_authority_id` float DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `authorities` */

insert  into `authorities`(`id`,`name`,`display_name`,`permissions`,`parent_authority_id`,`url`) values (15,'','营销管理','',NULL,'/'),(16,'chance','营销机会管理','',15,'/chance/list'),(17,'plan','客户开发计划','',15,'/plan/list'),(18,'','客户管理','',NULL,'/'),(19,'customer','客户信息管理','',18,'/customer/list'),(20,'drain','客户流失管理','',18,'/drain/list'),(21,'','服务管理','',NULL,'/'),(22,'service-create','服务创建','',21,'/service/create'),(23,'service-allot','服务分配','',21,'/service/allot/list'),(24,'service-deal','服务处理','',21,'/service/deal/list'),(25,'service-feedback','服务反馈','',21,'/service/feedback/list'),(26,'service-archive','服务归档','',21,'/service/archive/list'),(27,'','统计报表','',NULL,'/'),(28,'report-pay','客户贡献分析','',27,'/report/pay'),(29,'report-consist','客户构成分析','',27,'/report/consist?type=search_level'),(30,'report-service','客户服务分析','',27,'/report/service'),(31,'report-drain','客户流失分析','',27,'/report/drain'),(32,'','基础数据','',NULL,'/'),(33,'dict','数据字典管理','',32,'/dict/list'),(34,'product','查询产品信息','',32,'/product/list'),(35,'storage','查询库存信息','',32,'/storage/list'),(36,'','系统权限管理','',NULL,'/'),(37,'user','系统用户管理','',36,'/user/list'),(39,'role','角色管理','',36,'/role/list');

/*Table structure for table `contacts` */

DROP TABLE IF EXISTS `contacts`;

CREATE TABLE `contacts` (
  `id` float DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `customer_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `contacts` */

insert  into `contacts`(`id`,`memo`,`mobile`,`name`,`position`,`sex`,`tel`,`customer_id`) values (142,'英雄人物的名字','13456789912','杨倩','技术经理','女','86789967',141),(155,'','','黄章2','','','13456789900',154),(1,'','','丽丽','开发部经理','女','13456789900',2),(2,'','','王自如','','','13456778800',3),(3,'岳飞的好朋友','13455667788','杨再兴','开发部经理','男','86789988',4),(4,'','','付老师','','','13456778900',5),(5,'','','陈雷','','','51571522',6),(6,'','','宋红康','','','13456789900',7),(8,'最牛逼的将领','13999999998','岳飞','大帅','男','010-99999999',4);

/*Table structure for table `customer_activities` */

DROP TABLE IF EXISTS `customer_activities`;

CREATE TABLE `customer_activities` (
  `id` float DEFAULT NULL,
  `activity_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `customer_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer_activities` */

insert  into `customer_activities`(`id`,`activity_date`,`description`,`place`,`title`,`customer_id`) values (160,'1990-12-12 00:00:00','AASSSDDDD','北京','技术培训',154),(2,'2013-12-15 00:00:00','认识了魅族的老总-郭靖','北京北体体育场','手机发展论坛2',4),(3,'2014-01-01 00:00:00','意向 10000W 的大单','大连富丽华酒店','PARTY',2);

/*Table structure for table `customer_drains` */

DROP TABLE IF EXISTS `customer_drains`;

CREATE TABLE `customer_drains` (
  `id` float DEFAULT NULL,
  `delay` varchar(255) DEFAULT NULL,
  `drain_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_order_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `reason` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `customer_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer_drains` */

insert  into `customer_drains`(`id`,`delay`,`drain_date`,`last_order_date`,`reason`,`status`,`customer_id`) values (204,'AA`BB`CC','2016-08-27 09:01:19','2014-01-28 00:00:00','客户对我们不满意. 就是不满意! ','流失',4);

/*Table structure for table `customer_services` */

DROP TABLE IF EXISTS `customer_services`;

CREATE TABLE `customer_services` (
  `id` float DEFAULT NULL,
  `allot_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `deal_date` datetime DEFAULT NULL,
  `deal_result` varchar(255) DEFAULT NULL,
  `satisfy` varchar(255) DEFAULT NULL,
  `service_deal` varchar(255) DEFAULT NULL,
  `service_request` varchar(255) DEFAULT NULL,
  `service_state` varchar(255) DEFAULT NULL,
  `service_title` varchar(255) DEFAULT NULL,
  `service_type` varchar(255) DEFAULT NULL,
  `allot_id` float DEFAULT NULL,
  `created_id` float DEFAULT NULL,
  `customer_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer_services` */

insert  into `customer_services`(`id`,`allot_date`,`create_date`,`deal_date`,`deal_result`,`satisfy`,`service_deal`,`service_request`,`service_state`,`service_title`,`service_type`,`allot_id`,`created_id`,`customer_id`) values (240,'2014-02-07 00:00:00','2014-02-07 00:00:00','2014-02-07 00:00:00','收到','☆☆☆☆☆','OK。 以安排','每次送货后, 请电话通知下. 联系人: 宋小宝：13456789988','已归档','送货后, 电话通知下','建议',24,24,2),(220,'2014-02-07 00:00:00','2014-02-07 00:00:00','2014-02-07 00:00:00','AAA','☆☆☆☆☆','一台以上即可打折','团购多少才能打折优惠啊 ?','已归档','团购打折','咨询',24,24,154),(221,'2014-02-07 00:00:00','2014-02-07 00:00:00','2014-02-07 00:00:00','已经就此道歉','☆☆☆☆','开除了。','你们的 52 号客服的很不耐烦感觉','已归档','服务态度','投诉',24,24,4);

/*Table structure for table `customers` */

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `id` float DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `bank_account` varchar(255) DEFAULT NULL,
  `bankroll` float DEFAULT NULL,
  `chief` varchar(255) DEFAULT NULL,
  `credit` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `licence_no` varchar(255) DEFAULT NULL,
  `local_tax_no` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `national_tax_no` varchar(255) DEFAULT NULL,
  `no` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `satify` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `turnover` float DEFAULT NULL,
  `websit` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `manager_id` float DEFAULT NULL,
  `customer_level` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customers` */

insert  into `customers`(`id`,`address`,`bank`,`bank_account`,`bankroll`,`chief`,`credit`,`fax`,`licence_no`,`local_tax_no`,`name`,`national_tax_no`,`no`,`region`,`satify`,`state`,`tel`,`turnover`,`websit`,`zip`,`manager_id`,`customer_level`) values (141,'e世界','建设银行2','YX900987677855400',10000,'尚硅谷','☆☆☆☆☆','010-22345678','YYZZ-ZX908765545','DS-67Y655','联想移动','GS-78X00ZZ','e9852130-84fd-495c-a1d7-cb357f1ee399','北京','☆☆☆☆☆','流失预警','86789966',300000,'www.atguigu.com','100001',142,'战略合作伙伴'),(154,'大连沙河口区','建设银行','YX900987677855400',10000,'尚硅谷','☆☆☆☆☆','010-22345678','YYZZ-ZX908765545','DS-67Y655','魅族科技','GS-78X00ZZ','95d687fd-382d-4bad-87d2-6be7a7c57d04','北京','☆☆☆☆☆','流失','86789967',300000,'www.atguigu.com','100001',155,'战略合作伙伴'),(2,'大连沙河口区','农业银行','YX900987677855466',10000,'陈雷','☆☆☆☆','0411-89789901','YYZZ-ZX908765544','DS-67Y655','大连重工','GS-78X00ZZ','dcbc0726-4632-409c-ae27-80e2e8f8be2c','辽宁','☆☆☆','正常','0411-89789900',900,'www.atguigu.com','160000',1,'大客户'),(3,'北京海龙电子城','农业银行','YX900987677855488',20000,'尚硅谷','☆☆☆☆☆','010-22345678','YYZZ-ZX908765599','DS-67Y677','恒大电脑','GS-78X00YY','1f2e93e7-9514-4a88-b7db-b35ace6343b1','北京','☆☆☆☆☆','删除','010-12345678',800,'www.atguigu.com','100001',2,'战略合作伙伴'),(4,'e世界','建设银行','YX900987677855400',10,'宋红康','☆','010-98765432','YYZZ-ZX908765545','DS-67Y699','阿拉灯','GS-78X00OO','edf8f801-7fd5-4ebb-9660-6c115de36c87','北京','☆','正常','010-98765433',300000,'www.atguigu.com','100001',3,'合作伙伴'),(5,'','','',NULL,'','','','','','北京培黎师范学院','','9a94dfcb-38b0-4e79-ae96-08f19da9f0f1','','','删除','',NULL,'','',NULL,''),(6,'','','',NULL,'','','','','','新浪','','ff704114-40cc-41c3-b30a-853f322eae9e','','','删除','',NULL,'','',NULL,''),(7,'e世界','建设银行','YX900987677855400',10,'尚硅谷','☆','010-22345678','YYZZ-ZX908765544','DS-67Y655','腾讯','GS-78X00OO','5baaf7e3-556c-42cf-bb2a-b00964dad13a','上海','☆','正常','86789966',300000,'www.atguigu.com','100001',6,'普通客户');

/*Table structure for table `dicts` */

DROP TABLE IF EXISTS `dicts`;

CREATE TABLE `dicts` (
  `id` float DEFAULT NULL,
  `editable` float DEFAULT NULL,
  `item` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dicts` */

insert  into `dicts`(`id`,`editable`,`item`,`type`,`value`) values (8,0,'☆','满意度',''),(9,0,'☆☆','满意度',''),(10,0,'☆☆☆','满意度',''),(11,0,'☆☆☆☆','满意度',''),(12,0,'☆☆☆☆☆','满意度',''),(13,0,'☆','信用度',''),(14,0,'☆☆','信用度',''),(15,0,'☆☆☆','信用度',''),(16,0,'☆☆☆☆','信用度',''),(17,0,'☆☆☆☆☆','信用度',''),(18,0,'北京','地区',''),(19,0,'上海','地区',''),(20,0,'广州','地区',''),(21,0,'深圳','地区',''),(22,0,'香港','地区',''),(28,0,'辽宁','地区',''),(23,0,'普通客户','客户等级',''),(24,0,'大客户','客户等级',''),(25,0,'重点开发客户','客户等级',''),(26,0,'合作伙伴','客户等级',''),(27,0,'战略合作伙伴','客户等级',''),(31,0,'咨询','服务类型',''),(29,0,'投诉','服务类型',''),(30,0,'建议','服务类型','');

/*Table structure for table `order_items` */

DROP TABLE IF EXISTS `order_items`;

CREATE TABLE `order_items` (
  `id` float DEFAULT NULL,
  `item_count` float DEFAULT NULL,
  `money` float DEFAULT NULL,
  `order_id` float DEFAULT NULL,
  `product_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_items` */

insert  into `order_items`(`id`,`item_count`,`money`,`order_id`,`product_id`) values (1,10,1000,1,1),(2,20,2000,1,3);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` float DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `no` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `customer_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`id`,`address`,`order_date`,`no`,`status`,`customer_id`) values (7,'长春','2014-02-01 00:00:00','DD98000','已付款',141),(8,'深圳','2014-02-01 00:00:00','DD98012','已付款',154),(1,'北京','2014-01-26 00:00:00','DD98065','未付款',4),(2,'上海','2014-01-28 00:00:00','DD98066','已付款',4),(3,'深圳','2013-09-28 00:00:00','DD98088','已付款',4),(4,'广州','2013-05-28 00:00:00','DD98099','已付款',4),(5,'大连','2013-10-28 00:00:00','DD98055','已付款',4),(6,'石家庄','2013-11-28 00:00:00','DD98011','未付款',4);

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` float DEFAULT NULL,
  `batch` varchar(255) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `products` */

insert  into `products`(`id`,`batch`,`memo`,`name`,`price`,`type`,`unit`) values (1,'国行','保证行货','ThinkPad T430 笔记本',8000,'T430','台'),(3,'水货','不保修','Nexus 手机',2000,'Nexus5','台'),(4,'二手','','ipad',1000,'2','台'),(5,'32G','','小米手机',1500,'2s','台'),(6,'金色','','iphone',5000,'5s','台'),(7,'32G','','三星NOTE',3000,'3','台');

/*Table structure for table `role_authority` */

DROP TABLE IF EXISTS `role_authority`;

CREATE TABLE `role_authority` (
  `role_id` float DEFAULT NULL,
  `authority_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_authority` */

insert  into `role_authority`(`role_id`,`authority_id`) values (1,16),(1,17),(1,19),(1,20),(1,22),(1,23),(1,24),(1,25),(1,26),(1,28),(1,29),(1,30),(1,31),(1,33),(1,34),(1,35),(1,37),(1,39),(2,17),(3,16),(3,17),(3,19),(3,20),(3,22),(3,23),(3,24),(3,25),(3,26),(3,28),(3,29),(3,30),(3,31),(3,33),(3,34),(3,35),(3,37),(3,39);

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` float DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `enabled` float DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `roles` */

insert  into `roles`(`id`,`description`,`enabled`,`name`) values (3,'测试时使用, 上线需删除',1,'测试管理员'),(1,'',1,'管理员'),(2,'',1,'测试');

/*Table structure for table `sales_chances` */

DROP TABLE IF EXISTS `sales_chances`;

CREATE TABLE `sales_chances` (
  `id` float DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `contact_tel` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `cust_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `designee_date` datetime DEFAULT NULL,
  `rate` float DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `status` float DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `created_user_id` float DEFAULT NULL,
  `designee_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sales_chances` */

insert  into `sales_chances`(`id`,`contact`,`contact_tel`,`create_date`,`cust_name`,`description`,`designee_date`,`rate`,`source`,`status`,`title`,`created_user_id`,`designee_id`) values (150,'黄章2','13456789900','2014-02-06 00:00:00','魅族科技','好机会, 不可多得！','2014-02-06 00:00:00',90,'手机论坛',3,'原配件',24,24),(156,'付老师','51571522','2014-02-06 00:00:00','IPHONE 直销','好像是个传说','2014-02-06 00:00:00',10,'IOS 论坛',4,'IPHONE 直销',24,24),(1,'宋红康','13456789900','2014-02-02 00:00:00','腾讯','JavaEE','2014-02-02 00:00:00',90,'微信',3,'技术培训',21,24),(2,'陈雷','51571522','2014-02-02 00:00:00','新浪','团队凝聚力','2014-02-02 00:00:00',90,'微博',3,'团队培训',21,24),(4,'付老师','13456778900','2014-02-02 00:00:00','北京培黎师范学院','50 太高端服务器','2014-02-02 00:00:00',80,'客户介绍',3,'50 台服务器',21,24),(5,'杨再兴','13455667788','2014-02-04 00:00:00','阿拉灯','靠谱','2014-02-04 00:00:00',100,'朋友介绍',3,'手机经销商',21,24),(7,'王自如','13456778800','2014-02-04 00:00:00','恒大电脑','高端服务器 10 台','2014-02-04 00:00:00',90,'QQ 群',3,'服务器 10 台',24,24),(8,'丽丽','13456789900','2014-02-04 00:00:00','大连重工','办公使用','2014-02-04 00:00:00',80,'微信',3,'台式机 50 台',24,24),(120,'杨倩','13456789912','2014-02-05 00:00:00','联想移动','北京乐 PHONE 总代','2014-02-05 00:00:00',80,'手机博览会',3,'乐Phone代理',24,24);

/*Table structure for table `sales_plan` */

DROP TABLE IF EXISTS `sales_plan`;

CREATE TABLE `sales_plan` (
  `id` float DEFAULT NULL,
  `plan_date` datetime DEFAULT NULL,
  `plan_result` varchar(255) DEFAULT NULL,
  `todo` varchar(255) DEFAULT NULL,
  `chance_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sales_plan` */

insert  into `sales_plan`(`id`,`plan_date`,`plan_result`,`todo`,`chance_id`) values (140,'1990-12-12 00:00:00','AAA-RESULT','AAA',120),(152,'1991-12-12 00:00:00','AA','ASDDD',150),(153,'1992-12-12 00:00:00','BB','ASBBB',150),(1,'1990-12-12 00:00:00','初步意向','吃个饭?',1),(2,'1991-12-12 00:00:00','培训费没达成','泡个脚?',1),(3,'1990-12-12 00:00:00','RESULT....','XYZZZ',4),(4,'1990-12-12 00:00:00','AAA-RESULT','AAA',5),(5,'1991-12-12 00:00:00','BBB-RESULT','BBB',5),(7,'1990-12-12 00:00:00','AAA-RESULT','AAA',7),(8,'1990-12-12 00:00:00','AAA-RESULT','AAA',8);

/*Table structure for table `storages` */

DROP TABLE IF EXISTS `storages`;

CREATE TABLE `storages` (
  `id` float DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `stock_count` float DEFAULT NULL,
  `stock_ware` varchar(255) DEFAULT NULL,
  `ware_house` varchar(255) DEFAULT NULL,
  `product_id` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `storages` */

insert  into `storages`(`id`,`memo`,`stock_count`,`stock_ware`,`ware_house`,`product_id`) values (1,'Nexus 手机比较抢手',100,'1','北京五棵松',3),(2,'',2000,'2','中关村海龙',1),(3,'',200,'3','京东一号',3),(4,'',100,'4','e世界',4),(5,'',200,'10','北京五棵松',1),(6,'',100,'11','北京五棵松',3),(7,'',100,'13','北京五棵松',4),(8,'',100,'14','北京五棵松',5),(9,'',100,'15','北京五棵松',6),(10,'',100,'16','北京五棵松',7);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` float DEFAULT NULL,
  `enabled` float DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` float DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`id`,`enabled`,`name`,`password`,`role_id`,`salt`) values (21,1,'bcde','4f6ed9e4ab25a6dac05933a8a0c5822ada8177e5',1,'e2b87e6eced06509'),(22,1,'abcd','f7e480709b119c14621301576eb572ee009a47ce',2,'db314a8d91bd6f83'),(24,1,'a','9bba13aaeb55b59ce72f9f6aad672e2c32544630',3,'ceadfd47cdaa814c');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
