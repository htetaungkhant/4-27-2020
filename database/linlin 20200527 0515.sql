-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.6.26


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema 4_27_2020
--

CREATE DATABASE IF NOT EXISTS 4_27_2020;
USE 4_27_2020;

--
-- Definition of table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `idadmin` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `password` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  PRIMARY KEY (`idadmin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `admin`
--

/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` (`idadmin`,`username`,`password`) VALUES 
 (1,'htetaungkhant','htetaungkhant');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;


--
-- Definition of table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `idcustomer` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `address` varchar(225) COLLATE utf8_general_mysql500_ci NOT NULL,
  PRIMARY KEY (`idcustomer`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `customer`
--

/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`idcustomer`,`customer_name`,`phone`,`address`) VALUES 
 (1,'Default Customer','',''),
 (2,'Zay Min Thant','09777703980','No.4 ,Kanbalu'),
 (3,'အောင်လင်းထိန်','09798922327','အမှတ် ၄, ကန့်ဘလူ'),
 (4,'Zaw Zaw','09792568456','နတ်ပေး'),
 (5,'Nga Khant','09796511254','Yangon'),
 (6,'Htoo Khant','09625511448','Late htu'),
 (7,'Khant Khant','09776512354','No .1, Kanbalu'),
 (8,'Aung Min','09561532556','Kanbalu'),
 (9,'စိန်လင်း','09798655486','ကိုင်းတော');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


--
-- Definition of table `money_transfer`
--

DROP TABLE IF EXISTS `money_transfer`;
CREATE TABLE `money_transfer` (
  `idmoney_transfer` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `invoice_number` int(10) unsigned NOT NULL,
  `supplier` int(10) unsigned NOT NULL,
  `remark` varchar(225) COLLATE utf8_general_mysql500_ci NOT NULL,
  PRIMARY KEY (`idmoney_transfer`) USING BTREE,
  KEY `FK_supplier_pay_record_2` (`invoice_number`),
  KEY `FK_supplier_pay_record_3` (`supplier`),
  CONSTRAINT `FK_supplier_pay_record_2` FOREIGN KEY (`invoice_number`) REFERENCES `purchase` (`idpurchase`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_supplier_pay_record_3` FOREIGN KEY (`supplier`) REFERENCES `supplier` (`idsupplier`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `money_transfer`
--

/*!40000 ALTER TABLE `money_transfer` DISABLE KEYS */;
INSERT INTO `money_transfer` (`idmoney_transfer`,`date`,`amount`,`invoice_number`,`supplier`,`remark`) VALUES 
 (4,'2020-05-26 03:42:32',200000,3,3,'');
/*!40000 ALTER TABLE `money_transfer` ENABLE KEYS */;


--
-- Definition of table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase` (
  `idpurchase` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `supplier` int(10) unsigned NOT NULL,
  `original_invoice_number` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL DEFAULT '0',
  `amount` int(10) unsigned NOT NULL,
  `paid_amount` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`idpurchase`),
  KEY `FK_purchase_1` (`supplier`),
  CONSTRAINT `FK_purchase_1` FOREIGN KEY (`supplier`) REFERENCES `supplier` (`idsupplier`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `purchase`
--

/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` (`idpurchase`,`date`,`supplier`,`original_invoice_number`,`amount`,`paid_amount`) VALUES 
 (1,'2020-05-06 00:00:00',3,'00001',650000,650000),
 (3,'2020-05-07 00:00:00',3,'00002',370000,200000),
 (5,'2020-05-08 00:00:00',1,'00001',71500,71500),
 (6,'2020-05-08 00:00:00',4,'00003',71500,71500),
 (8,'2020-05-10 02:24:02',2,'-----',145000,145000),
 (9,'2020-05-16 23:22:22',8,'-----',25000,25000);
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;


--
-- Definition of table `purchase_detail`
--

DROP TABLE IF EXISTS `purchase_detail`;
CREATE TABLE `purchase_detail` (
  `idpurchase_detail` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `invoice_number` int(10) unsigned NOT NULL,
  `item` int(10) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `unit_price` int(10) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idpurchase_detail`),
  KEY `FK_purchase_detail_2` (`item`),
  KEY `FK_purchase_detail_1` (`invoice_number`),
  CONSTRAINT `FK_purchase_detail_1` FOREIGN KEY (`invoice_number`) REFERENCES `purchase` (`idpurchase`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_purchase_detail_2` FOREIGN KEY (`item`) REFERENCES `stock` (`idstock`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `purchase_detail`
--

/*!40000 ALTER TABLE `purchase_detail` DISABLE KEYS */;
INSERT INTO `purchase_detail` (`idpurchase_detail`,`invoice_number`,`item`,`quantity`,`unit_price`,`amount`) VALUES 
 (1,1,11,20,2500,50000),
 (2,1,1,50,3000,150000),
 (3,1,16,50,5000,250000),
 (4,1,4,1,200000,200000),
 (7,3,2,4,70000,280000),
 (8,3,15,10,9000,90000),
 (10,5,21,13,5500,71500),
 (11,6,21,13,5500,71500),
 (14,8,11,20,2500,50000),
 (15,8,15,10,9500,95000),
 (16,9,11,10,2500,25000);
/*!40000 ALTER TABLE `purchase_detail` ENABLE KEYS */;


--
-- Definition of table `sale`
--

DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale` (
  `idsale` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `customer` int(10) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `net_amount` int(10) unsigned NOT NULL,
  `discount` int(10) unsigned NOT NULL,
  `remark` varchar(225) COLLATE utf8_general_mysql500_ci NOT NULL,
  PRIMARY KEY (`idsale`),
  KEY `FK_sale_1` (`customer`),
  CONSTRAINT `FK_sale_1` FOREIGN KEY (`customer`) REFERENCES `customer` (`idcustomer`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `sale`
--

/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` (`idsale`,`date`,`customer`,`amount`,`net_amount`,`discount`,`remark`) VALUES 
 (6,'2020-05-18 03:35:37',3,78000,76000,2000,''),
 (7,'2020-05-18 23:22:21',1,170000,170000,0,''),
 (8,'2020-05-23 19:55:17',1,40000,30000,10000,''),
 (9,'2020-05-26 23:25:49',9,210000,210000,0,'အကြွေး'),
 (10,'2020-05-27 00:24:04',1,336000,336000,0,'အကြွေး');
/*!40000 ALTER TABLE `sale` ENABLE KEYS */;


--
-- Definition of table `sale_detail`
--

DROP TABLE IF EXISTS `sale_detail`;
CREATE TABLE `sale_detail` (
  `idsale_detail` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `invoice_number` int(10) unsigned NOT NULL,
  `item` int(10) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `sale_price` int(10) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `cogs` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idsale_detail`),
  KEY `FK_sale_detail_1` (`invoice_number`),
  KEY `FK_sale_detail_2` (`item`),
  CONSTRAINT `FK_sale_detail_1` FOREIGN KEY (`invoice_number`) REFERENCES `sale` (`idsale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_sale_detail_2` FOREIGN KEY (`item`) REFERENCES `stock` (`idstock`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `sale_detail`
--

/*!40000 ALTER TABLE `sale_detail` DISABLE KEYS */;
INSERT INTO `sale_detail` (`idsale_detail`,`invoice_number`,`item`,`quantity`,`sale_price`,`amount`,`cogs`) VALUES 
 (11,6,1,9,4000,36000,3000),
 (12,6,9,2,21000,42000,15000),
 (13,7,11,10,4000,40000,2500),
 (14,7,15,10,13000,130000,9125),
 (15,8,1,10,4000,40000,3000),
 (16,9,9,10,21000,210000,15000),
 (17,10,8,10,30000,300000,20000),
 (18,10,12,3,12000,36000,8000);
/*!40000 ALTER TABLE `sale_detail` ENABLE KEYS */;


--
-- Definition of table `saving`
--

DROP TABLE IF EXISTS `saving`;
CREATE TABLE `saving` (
  `idsaving` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `person` int(10) unsigned NOT NULL,
  `is_achieved` tinyint(1) NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `receive_date` datetime NOT NULL,
  PRIMARY KEY (`idsaving`),
  KEY `FK_saving_2` (`person`),
  CONSTRAINT `FK_saving_2` FOREIGN KEY (`person`) REFERENCES `saving_people` (`idsaving_person`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `saving`
--

/*!40000 ALTER TABLE `saving` DISABLE KEYS */;
/*!40000 ALTER TABLE `saving` ENABLE KEYS */;


--
-- Definition of table `saving_people`
--

DROP TABLE IF EXISTS `saving_people`;
CREATE TABLE `saving_people` (
  `idsaving_person` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `phone` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `address` varchar(225) COLLATE utf8_general_mysql500_ci NOT NULL,
  `saving_type` int(10) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `saving_time` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  PRIMARY KEY (`idsaving_person`),
  KEY `FK_saving_person_1` (`saving_type`),
  CONSTRAINT `FK_saving_person_1` FOREIGN KEY (`saving_type`) REFERENCES `saving_type` (`idsaving_type`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `saving_people`
--

/*!40000 ALTER TABLE `saving_people` DISABLE KEYS */;
/*!40000 ALTER TABLE `saving_people` ENABLE KEYS */;


--
-- Definition of table `saving_type`
--

DROP TABLE IF EXISTS `saving_type`;
CREATE TABLE `saving_type` (
  `idsaving_type` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) COLLATE utf8_general_mysql500_ci NOT NULL,
  PRIMARY KEY (`idsaving_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `saving_type`
--

/*!40000 ALTER TABLE `saving_type` DISABLE KEYS */;
INSERT INTO `saving_type` (`idsaving_type`,`type_name`) VALUES 
 (1,'၁ရက် ၁ခါ'),
 (2,'၂ရက် ၁ခါ'),
 (3,'၅ရက် ၁ခါ'),
 (4,'၁၀ရက် ၁ခါ');
/*!40000 ALTER TABLE `saving_type` ENABLE KEYS */;


--
-- Definition of table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `idstock` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `item_name` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `barcode` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `cost` int(10) unsigned NOT NULL,
  `sale_price` int(10) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `limit_quantity` int(10) unsigned NOT NULL,
  `remark` varchar(225) COLLATE utf8_general_mysql500_ci DEFAULT NULL,
  PRIMARY KEY (`idstock`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `stock`
--

/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` (`idstock`,`item_name`,`barcode`,`cost`,`sale_price`,`quantity`,`limit_quantity`,`remark`) VALUES 
 (1,'Test1','0000000001',3000,4000,11,3,''),
 (2,'Test2','0000000002',70000,90000,5,2,''),
 (3,'Test3','0000000003',10000,15000,20,5,''),
 (4,'Test4','0000000004',200000,260000,2,1,''),
 (5,'Test5','0000000005',1000,1500,50,20,''),
 (6,'Test6','0000000006',500,700,100,20,''),
 (7,'Test7','0000000007',5000,6500,100,10,' This is remark for Test7.'),
 (8,'Test8','0000000008',20000,30000,40,10,''),
 (9,'Test9','0000000009',15000,21000,8,5,''),
 (10,'Test10','0000000010',1000,1500,30,10,''),
 (11,'linlin1','0000000011',2500,4000,70,20,''),
 (12,'linlin2','0000000012',8000,12000,2,10,''),
 (13,'linlin3','0000000013',4000,6500,30,5,''),
 (14,'linlin4','0000000014',7000,10000,100,10,''),
 (15,'linlin5','0000000015',9125,13000,30,3,''),
 (16,'linlin6','0000000016',5000,8000,20,3,''),
 (17,'linlin7','0000000017',6500,9000,4,5,''),
 (18,'linlin8','0000000018',9500,14000,50,10,''),
 (19,'linlin9','0000000019',16000,20000,30,5,''),
 (20,'linlin10','0000000020',8500,12000,50,10,''),
 (21,'Testing2','0000000021',4331,6500,77,15,''),
 (22,'Tesing1','0000000022',7777,10000,12,2,''),
 (23,'linlin11','0000000023',6500,9000,0,1,''),
 (24,'Test99','0000000099',42000,55000,0,3,''),
 (25,'ပန်းကုတင်','0000000L99',28000,35000,0,2,''),
 (26,'linlin12','0000000024',85000,120000,0,1,''),
 (27,'Testing3','0000000025',50000,65000,0,3,''),
 (28,'Testing4','0000000026',8900,14000,10,5,'');
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;


--
-- Definition of table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `idsupplier` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `phone` varchar(45) COLLATE utf8_general_mysql500_ci NOT NULL,
  `address` varchar(225) COLLATE utf8_general_mysql500_ci NOT NULL,
  PRIMARY KEY (`idsupplier`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

--
-- Dumping data for table `supplier`
--

/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` (`idsupplier`,`supplier_name`,`phone`,`address`) VALUES 
 (1,'စည်သူ(လျှပ်စစ်)','09798922327','မန္တလေး'),
 (2,'ကြည်မြန်မာ','09777703980','မန္တလေး'),
 (3,'အောင်တံခွန်(သင်္ကန်းဆိုင်)','09789238100','Mandalay'),
 (4,'ရွှေကြေးစည်','09421144558','Mandalay'),
 (5,'အောင်သမာဓိ (ဖိနပ်ဆိုင်)','09695321658','Mandalay'),
 (6,'Forever (Fashion Shop)','09425566117','Mandalay'),
 (7,'Ko Htoo (Mobile)','09795621976','Yangon'),
 (8,'မျိုးသိန်း(လျှပ်စစ်)','09695186495','Mandalay');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
