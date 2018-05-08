-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 08-05-2018 a las 08:31:51
-- Versión del servidor: 5.7.22-0ubuntu18.04.1
-- Versión de PHP: 7.2.3-1ubuntu1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `aepad-tp`
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
  `ID` bigint(20) NOT NULL,
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

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `CATEGORIA`
--
ALTER TABLE `CATEGORIA`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `FACTURA`
--
ALTER TABLE `FACTURA`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_FACTURA_ID_CLIENTE` (`ID_CLIENTE`);

--
-- Indices de la tabla `FACTURADETALLE`
--
ALTER TABLE `FACTURADETALLE`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_FACTURADETALLE_ID_PRODUCTO` (`ID_PRODUCTO`),
  ADD KEY `FK_FACTURADETALLE_ID_FACTURA` (`ID_FACTURA`);

--
-- Indices de la tabla `LOG_ACCESO`
--
ALTER TABLE `LOG_ACCESO`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `ORDENCOMPRA`
--
ALTER TABLE `ORDENCOMPRA`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `ORDENCOMPRADETALLE`
--
ALTER TABLE `ORDENCOMPRADETALLE`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_ORDENCOMPRADETALLE_ID_PRODUCTO` (`ID_PRODUCTO`),
  ADD KEY `FK_ORDENCOMPRADETALLE_ID_ORDEN` (`ID_ORDEN`);

--
-- Indices de la tabla `PAGO`
--
ALTER TABLE `PAGO`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_PAGO_ID_CLIENTE` (`ID_CLIENTE`),
  ADD KEY `FK_PAGO_ID_FACTURA` (`ID_FACTURA`);

--
-- Indices de la tabla `PRODUCTO`
--
ALTER TABLE `PRODUCTO`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `PRODUCTO_CATEGORIA`
--
ALTER TABLE `PRODUCTO_CATEGORIA`
  ADD PRIMARY KEY (`ID_CATEGORIA`,`ID_PRODUCTO`),
  ADD KEY `FK_PRODUCTO_CATEGORIA_ID_PRODUCTO` (`ID_PRODUCTO`);

--
-- Indices de la tabla `USUARIO`
--
ALTER TABLE `USUARIO`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `CATEGORIA`
--
ALTER TABLE `CATEGORIA`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `FACTURA`
--
ALTER TABLE `FACTURA`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `FACTURADETALLE`
--
ALTER TABLE `FACTURADETALLE`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `LOG_ACCESO`
--
ALTER TABLE `LOG_ACCESO`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `ORDENCOMPRA`
--
ALTER TABLE `ORDENCOMPRA`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `ORDENCOMPRADETALLE`
--
ALTER TABLE `ORDENCOMPRADETALLE`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `PAGO`
--
ALTER TABLE `PAGO`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `PRODUCTO`
--
ALTER TABLE `PRODUCTO`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `USUARIO`
--
ALTER TABLE `USUARIO`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `FACTURA`
--
ALTER TABLE `FACTURA`
  ADD CONSTRAINT `FK_FACTURA_ID_CLIENTE` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `USUARIO` (`ID`);

--
-- Filtros para la tabla `FACTURADETALLE`
--
ALTER TABLE `FACTURADETALLE`
  ADD CONSTRAINT `FK_FACTURADETALLE_ID_FACTURA` FOREIGN KEY (`ID_FACTURA`) REFERENCES `FACTURA` (`ID`),
  ADD CONSTRAINT `FK_FACTURADETALLE_ID_PRODUCTO` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `PRODUCTO` (`ID`);

--
-- Filtros para la tabla `ORDENCOMPRADETALLE`
--
ALTER TABLE `ORDENCOMPRADETALLE`
  ADD CONSTRAINT `FK_ORDENCOMPRADETALLE_ID_ORDEN` FOREIGN KEY (`ID_ORDEN`) REFERENCES `ORDENCOMPRA` (`ID`),
  ADD CONSTRAINT `FK_ORDENCOMPRADETALLE_ID_PRODUCTO` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `PRODUCTO` (`ID`);

--
-- Filtros para la tabla `PAGO`
--
ALTER TABLE `PAGO`
  ADD CONSTRAINT `FK_PAGO_ID_CLIENTE` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `USUARIO` (`ID`),
  ADD CONSTRAINT `FK_PAGO_ID_FACTURA` FOREIGN KEY (`ID_FACTURA`) REFERENCES `FACTURA` (`ID`);

--
-- Filtros para la tabla `PRODUCTO_CATEGORIA`
--
ALTER TABLE `PRODUCTO_CATEGORIA`
  ADD CONSTRAINT `FK_PRODUCTO_CATEGORIA_ID_CATEGORIA` FOREIGN KEY (`ID_CATEGORIA`) REFERENCES `CATEGORIA` (`ID`),
  ADD CONSTRAINT `FK_PRODUCTO_CATEGORIA_ID_PRODUCTO` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `PRODUCTO` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
