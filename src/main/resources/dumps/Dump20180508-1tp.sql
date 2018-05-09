-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: aepad-tp
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factura` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA` date DEFAULT NULL,
  `ID_CLIENTE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FACTURA_ID_CLIENTE` (`ID_CLIENTE`),
  CONSTRAINT `FK_FACTURA_ID_CLIENTE` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `usuario` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `facturadetalle`
--

DROP TABLE IF EXISTS `facturadetalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facturadetalle` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CANTIDAD` int(11) DEFAULT NULL,
  `PRECIOUNITARIOFACTURADO` double DEFAULT NULL,
  `ID_FACTURA` int(11) DEFAULT NULL,
  `ID_PRODUCTO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FACTURADETALLE_ID_PRODUCTO` (`ID_PRODUCTO`),
  KEY `FK_FACTURADETALLE_ID_FACTURA` (`ID_FACTURA`),
  CONSTRAINT `FK_FACTURADETALLE_ID_FACTURA` FOREIGN KEY (`ID_FACTURA`) REFERENCES `factura` (`ID`),
  CONSTRAINT `FK_FACTURADETALLE_ID_PRODUCTO` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `producto` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `log_acceso`
--

DROP TABLE IF EXISTS `log_acceso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_acceso` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CLASE` varchar(255) DEFAULT NULL,
  `DURACION` bigint(20) DEFAULT NULL,
  `METODO` varchar(255) DEFAULT NULL,
  `MILLISFIN` bigint(20) DEFAULT NULL,
  `MILLISINICIO` bigint(20) DEFAULT NULL,
  `PARAMETROS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordencompra`
--

DROP TABLE IF EXISTS `ordencompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordencompra` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=140001 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordencompradetalle`
--

DROP TABLE IF EXISTS `ordencompradetalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordencompradetalle` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CANTIDAD` int(11) DEFAULT NULL,
  `PRECIOUNITARIOCOMPRA` double DEFAULT NULL,
  `ID_ORDEN` int(11) DEFAULT NULL,
  `ID_PRODUCTO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ORDENCOMPRADETALLE_ID_PRODUCTO` (`ID_PRODUCTO`),
  KEY `FK_ORDENCOMPRADETALLE_ID_ORDEN` (`ID_ORDEN`),
  CONSTRAINT `FK_ORDENCOMPRADETALLE_ID_ORDEN` FOREIGN KEY (`ID_ORDEN`) REFERENCES `ordencompra` (`ID`),
  CONSTRAINT `FK_ORDENCOMPRADETALLE_ID_PRODUCTO` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `producto` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=454952 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA` datetime DEFAULT NULL,
  `MONTO` double DEFAULT NULL,
  `ID_CLIENTE` int(11) DEFAULT NULL,
  `ID_FACTURA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PAGO_ID_CLIENTE` (`ID_CLIENTE`),
  KEY `FK_PAGO_ID_FACTURA` (`ID_FACTURA`),
  CONSTRAINT `FK_PAGO_ID_CLIENTE` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `usuario` (`ID`),
  CONSTRAINT `FK_PAGO_ID_FACTURA` FOREIGN KEY (`ID_FACTURA`) REFERENCES `factura` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `PRECIO` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40001 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `producto_categoria`
--

DROP TABLE IF EXISTS `producto_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto_categoria` (
  `ID_CATEGORIA` int(11) NOT NULL,
  `ID_PRODUCTO` int(11) NOT NULL,
  PRIMARY KEY (`ID_CATEGORIA`,`ID_PRODUCTO`),
  KEY `FK_PRODUCTO_CATEGORIA_ID_PRODUCTO` (`ID_PRODUCTO`),
  CONSTRAINT `FK_PRODUCTO_CATEGORIA_ID_CATEGORIA` FOREIGN KEY (`ID_CATEGORIA`) REFERENCES `categoria` (`ID`),
  CONSTRAINT `FK_PRODUCTO_CATEGORIA_ID_PRODUCTO` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `producto` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reporte_cliente`
--

DROP TABLE IF EXISTS `reporte_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reporte_cliente` (
  `ID_CLIENTE` int(11) NOT NULL,
  `COMPRAS` int(11) NOT NULL DEFAULT '0',
  `COMPRA_PROMEDIO` decimal(10,0) NOT NULL DEFAULT '0',
  `COMPRAS_TOTAL` decimal(10,0) NOT NULL DEFAULT '0',
  `PRODCUTOS_COMPRADOS` int(11) NOT NULL DEFAULT '0',
  `PAGOS` int(11) NOT NULL DEFAULT '0',
  `PAGO_PROMEDIO` decimal(10,0) NOT NULL DEFAULT '0',
  `PAGOS_TOTALES` decimal(10,0) NOT NULL DEFAULT '0',
  `SALDO` decimal(10,0) NOT NULL DEFAULT '0',
  `FECHA_CALCULO` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MAIL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20001 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-08 23:23:48
