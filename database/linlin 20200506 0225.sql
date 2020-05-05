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
-- Definition of table `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `idaccount` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account_name` varchar(45) NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idaccount`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account`
--

/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`idaccount`,`account_name`,`amount`) VALUES 
 (1,'Saving Account',0),
 (2,'Selling Account',0);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;


--
-- Definition of table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `idadmin` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`idadmin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

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
  `customer_name` varchar(45) NOT NULL,
  `phone` int(10) unsigned NOT NULL,
  `address` varchar(225) NOT NULL,
  PRIMARY KEY (`idcustomer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


--
-- Definition of table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase` (
  `idpurchase` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `supplier` int(10) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `original_invoice_number` varchar(45) NOT NULL DEFAULT '00000',
  `paid_amount` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`idpurchase`),
  KEY `FK_purchase_1` (`supplier`),
  CONSTRAINT `FK_purchase_1` FOREIGN KEY (`supplier`) REFERENCES `supplier` (`idsupplier`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchase`
--

/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
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
  CONSTRAINT `FK_purchase_detail_1` FOREIGN KEY (`invoice_number`) REFERENCES `purchase` (`idpurchase`),
  CONSTRAINT `FK_purchase_detail_2` FOREIGN KEY (`item`) REFERENCES `stock` (`idstock`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchase_detail`
--

/*!40000 ALTER TABLE `purchase_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_detail` ENABLE KEYS */;


--
-- Definition of table `sale`
--

DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale` (
  `idsale` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `customer` int(10) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `net_amount` int(10) unsigned NOT NULL,
  `discount` int(10) unsigned NOT NULL,
  `remark` varchar(225) NOT NULL,
  PRIMARY KEY (`idsale`),
  KEY `FK_sale_1` (`customer`),
  CONSTRAINT `FK_sale_1` FOREIGN KEY (`customer`) REFERENCES `customer` (`idcustomer`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sale`
--

/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
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
  `amount` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idsale_detail`),
  KEY `FK_sale_detail_1` (`invoice_number`),
  KEY `FK_sale_detail_2` (`item`),
  CONSTRAINT `FK_sale_detail_1` FOREIGN KEY (`invoice_number`) REFERENCES `sale` (`idsale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_sale_detail_2` FOREIGN KEY (`item`) REFERENCES `stock` (`idstock`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sale_detail`
--

/*!40000 ALTER TABLE `sale_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `sale_detail` ENABLE KEYS */;


--
-- Definition of table `saving`
--

DROP TABLE IF EXISTS `saving`;
CREATE TABLE `saving` (
  `idsaving` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `person` int(10) unsigned NOT NULL,
  `is_achieved` tinyint(1) NOT NULL,
  `account` int(10) unsigned NOT NULL DEFAULT '1',
  `amount` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idsaving`),
  KEY `FK_saving_1` (`account`),
  KEY `FK_saving_2` (`person`),
  CONSTRAINT `FK_saving_1` FOREIGN KEY (`account`) REFERENCES `account` (`idaccount`),
  CONSTRAINT `FK_saving_2` FOREIGN KEY (`person`) REFERENCES `saving_person` (`idsaving_person`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `saving`
--

/*!40000 ALTER TABLE `saving` DISABLE KEYS */;
/*!40000 ALTER TABLE `saving` ENABLE KEYS */;


--
-- Definition of table `saving_person`
--

DROP TABLE IF EXISTS `saving_person`;
CREATE TABLE `saving_person` (
  `idsaving_person` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `phone` int(10) unsigned NOT NULL,
  `address` varchar(225) NOT NULL,
  PRIMARY KEY (`idsaving_person`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `saving_person`
--

/*!40000 ALTER TABLE `saving_person` DISABLE KEYS */;
/*!40000 ALTER TABLE `saving_person` ENABLE KEYS */;


--
-- Definition of table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `idstock` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `item_name` varchar(45) NOT NULL,
  `barcode` varchar(45) NOT NULL,
  `purchase_price` int(10) unsigned NOT NULL,
  `sale_price` int(10) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `limit_quantity` int(10) unsigned NOT NULL,
  `remark` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`idstock`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stock`
--

/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` (`idstock`,`item_name`,`barcode`,`purchase_price`,`sale_price`,`quantity`,`limit_quantity`,`remark`) VALUES 
 (1,'Test1','0000000001',3000,4000,10,3,''),
 (2,'Test2','0000000002',70000,90000,5,2,''),
 (3,'Test3','0000000003',10000,15000,20,5,''),
 (4,'Test4','0000000004',200000,260000,2,1,''),
 (5,'Test5','0000000005',1000,1500,50,20,''),
 (6,'Test6','0000000006',500,700,100,20,''),
 (7,'Test7','0000000007',5000,6500,100,10,' This is remark for Test7.'),
 (8,'Test8','0000000008',20000,30000,50,10,''),
 (9,'Test9','0000000009',15000,21000,20,5,''),
 (10,'Test10','0000000010',1000,1500,30,10,''),
 (11,'linlin1','0000000011',2500,4000,50,20,''),
 (12,'linlin2','0000000012',8000,12000,30,10,''),
 (13,'linlin3','0000000013',4500,6500,30,5,''),
 (14,'linlin4','0000000014',7000,10000,100,10,''),
 (15,'linlin5','0000000015',9000,13000,20,3,''),
 (16,'linlin6','0000000016',5000,8000,20,3,''),
 (17,'linlin7','0000000017',6500,9000,30,5,''),
 (18,'linlin8','0000000018',9500,14000,50,10,''),
 (19,'linlin9','0000000019',16000,20000,30,5,'');
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;


--
-- Definition of table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `idsupplier` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `address` varchar(225) NOT NULL,
  PRIMARY KEY (`idsupplier`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` (`idsupplier`,`supplier_name`,`phone`,`address`) VALUES 
 (1,'Htet Aung Khant','09798922327','Kanbalu'),
 (2,'Zay Min Thant','09777703980','Kanbalu'),
 (3,'Daw Hla Hla Maw','09789238100','Kanbalu'),
 (4,'U Myo Win Tun','09421144558','Kanbalu');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;


--
-- Definition of table `supplier_pay_record`
--

DROP TABLE IF EXISTS `supplier_pay_record`;
CREATE TABLE `supplier_pay_record` (
  `idsupplier_pay_record` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `from_account` int(10) unsigned NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `invoice_number` int(10) unsigned NOT NULL,
  `supplier` int(10) unsigned NOT NULL,
  `remark` varchar(225) NOT NULL,
  PRIMARY KEY (`idsupplier_pay_record`),
  KEY `FK_supplier_pay_record_1` (`from_account`),
  KEY `FK_supplier_pay_record_2` (`invoice_number`),
  KEY `FK_supplier_pay_record_3` (`supplier`),
  CONSTRAINT `FK_supplier_pay_record_1` FOREIGN KEY (`from_account`) REFERENCES `account` (`idaccount`),
  CONSTRAINT `FK_supplier_pay_record_2` FOREIGN KEY (`invoice_number`) REFERENCES `purchase` (`idpurchase`),
  CONSTRAINT `FK_supplier_pay_record_3` FOREIGN KEY (`supplier`) REFERENCES `supplier` (`idsupplier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier_pay_record`
--

/*!40000 ALTER TABLE `supplier_pay_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier_pay_record` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
