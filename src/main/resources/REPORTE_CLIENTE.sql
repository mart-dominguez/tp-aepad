-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 24-04-2018 a las 12:30:54
-- Versión del servidor: 5.7.21-0ubuntu0.17.10.1
-- Versión de PHP: 7.1.15-0ubuntu0.17.10.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `aepad`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `REPORTE_CLIENTE`
--

CREATE TABLE `REPORTE_CLIENTE` (
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;