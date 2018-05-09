-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 08-05-2018 a las 08:59:20
-- Versión del servidor: 5.7.22-0ubuntu18.04.1
-- Versión de PHP: 7.2.3-1ubuntu1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `aepad-bk`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CATEGORIA`
--

CREATE TABLE `CATEGORIA` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `FACTURA`
--

CREATE TABLE `FACTURA` (
  `ID` int(11) NOT NULL,
  `FECHA` date DEFAULT NULL,
  `ID_CLIENTE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `FACTURADETALLE`
--

CREATE TABLE `FACTURADETALLE` (
  `ID` int(11) NOT NULL,
  `CANTIDAD` int(11) DEFAULT NULL,
  `PRECIOUNITARIOFACTURADO` double DEFAULT NULL,
  `ID_FACTURA` int(11) DEFAULT NULL,
  `ID_PRODUCTO` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `LOG_ACCESO`
--

CREATE TABLE `LOG_ACCESO` (
  `ID` int(11) NOT NULL,
  `CLASE` varchar(255) DEFAULT NULL,
  `DURACION` bigint(20) DEFAULT NULL,
  `METODO` varchar(255) DEFAULT NULL,
  `MILLISFIN` bigint(20) DEFAULT NULL,
  `MILLISINICIO` bigint(20) DEFAULT NULL,
  `PARAMETROS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ORDENCOMPRA`
--

CREATE TABLE `ORDENCOMPRA` (
  `ID` int(11) NOT NULL,
  `FECHA` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ORDENCOMPRADETALLE`
--

CREATE TABLE `ORDENCOMPRADETALLE` (
  `ID` int(11) NOT NULL,
  `CANTIDAD` int(11) DEFAULT NULL,
  `PRECIOUNITARIOCOMPRA` double DEFAULT NULL,
  `ID_ORDEN` int(11) DEFAULT NULL,
  `ID_PRODUCTO` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PAGO`
--

CREATE TABLE `PAGO` (
  `ID` int(11) NOT NULL,
  `FECHA` datetime DEFAULT NULL,
  `MONTO` double DEFAULT NULL,
  `ID_CLIENTE` int(11) DEFAULT NULL,
  `ID_FACTURA` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PRODUCTO`
--

CREATE TABLE `PRODUCTO` (
  `ID` int(11) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `PRECIO` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PRODUCTO_CATEGORIA`
--

CREATE TABLE `PRODUCTO_CATEGORIA` (
  `ID_CATEGORIA` int(11) NOT NULL,
  `ID_PRODUCTO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `USUARIO`
--

CREATE TABLE `USUARIO` (
  `ID` int(11) NOT NULL,
  `MAIL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
