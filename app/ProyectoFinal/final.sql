-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 27-05-2024 a las 05:05:00
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `final`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Administrador`
--

CREATE TABLE `Administrador` (
  `id_administrador` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `contrasena` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `Administrador`
--

INSERT INTO `Administrador` (`id_administrador`, `nombre`, `email`, `telefono`, `contrasena`) VALUES
(1000621138, 'Cristhian Andrey Castiblanco Sanchez', 'criscast2602@gmail.com', '3003415650', '1234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Asiento`
--

CREATE TABLE `Asiento` (
  `codigo` int(11) NOT NULL,
  `estado` enum('ocupado','libre') DEFAULT NULL,
  `idSala` int(11) DEFAULT NULL,
  `nombreCine` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Cine`
--

CREATE TABLE `Cine` (
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `numero` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `id_administrador` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Cine_Pelicula`
--

CREATE TABLE `Cine_Pelicula` (
  `nombreCine` varchar(100) NOT NULL,
  `titulo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Cliente`
--

CREATE TABLE `Cliente` (
  `idCliente` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `contrasena` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Entrada`
--

CREATE TABLE `Entrada` (
  `idEntrada` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `idAsiento` int(11) DEFAULT NULL,
  `idSala` int(11) DEFAULT NULL,
  `nombreCine` varchar(100) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `idCliente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Factura`
--

CREATE TABLE `Factura` (
  `idFactura` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `precioWeb` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `idEntrada` int(11) DEFAULT NULL,
  `idTarifa` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Pelicula`
--

CREATE TABLE `Pelicula` (
  `titulo` varchar(255) NOT NULL,
  `director` varchar(255) DEFAULT NULL,
  `clasificacion` varchar(50) DEFAULT NULL,
  `protagonista` varchar(255) DEFAULT NULL,
  `genero` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Sala`
--

CREATE TABLE `Sala` (
  `idSala` int(11) NOT NULL,
  `nombreCine` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Tarifa`
--

CREATE TABLE `Tarifa` (
  `idTarifa` int(11) NOT NULL,
  `dia` varchar(20) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `nombreCine` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Administrador`
--
ALTER TABLE `Administrador`
  ADD PRIMARY KEY (`id_administrador`);

--
-- Indices de la tabla `Asiento`
--
ALTER TABLE `Asiento`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `idSala` (`idSala`),
  ADD KEY `nombreCine` (`nombreCine`);

--
-- Indices de la tabla `Cine`
--
ALTER TABLE `Cine`
  ADD PRIMARY KEY (`nombre`),
  ADD KEY `id_administrador` (`id_administrador`);

--
-- Indices de la tabla `Cine_Pelicula`
--
ALTER TABLE `Cine_Pelicula`
  ADD PRIMARY KEY (`nombreCine`,`titulo`),
  ADD KEY `titulo` (`titulo`);

--
-- Indices de la tabla `Cliente`
--
ALTER TABLE `Cliente`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indices de la tabla `Entrada`
--
ALTER TABLE `Entrada`
  ADD PRIMARY KEY (`idEntrada`),
  ADD KEY `idAsiento` (`idAsiento`),
  ADD KEY `idSala` (`idSala`),
  ADD KEY `nombreCine` (`nombreCine`),
  ADD KEY `titulo` (`titulo`),
  ADD KEY `idCliente` (`idCliente`);

--
-- Indices de la tabla `Factura`
--
ALTER TABLE `Factura`
  ADD PRIMARY KEY (`idFactura`),
  ADD KEY `idEntrada` (`idEntrada`),
  ADD KEY `idTarifa` (`idTarifa`);

--
-- Indices de la tabla `Pelicula`
--
ALTER TABLE `Pelicula`
  ADD PRIMARY KEY (`titulo`);

--
-- Indices de la tabla `Sala`
--
ALTER TABLE `Sala`
  ADD PRIMARY KEY (`idSala`),
  ADD KEY `nombreCine` (`nombreCine`);

--
-- Indices de la tabla `Tarifa`
--
ALTER TABLE `Tarifa`
  ADD PRIMARY KEY (`idTarifa`),
  ADD KEY `nombreCine` (`nombreCine`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Administrador`
--
ALTER TABLE `Administrador`
  MODIFY `id_administrador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1000621139;

--
-- AUTO_INCREMENT de la tabla `Cliente`
--
ALTER TABLE `Cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Entrada`
--
ALTER TABLE `Entrada`
  MODIFY `idEntrada` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Factura`
--
ALTER TABLE `Factura`
  MODIFY `idFactura` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Sala`
--
ALTER TABLE `Sala`
  MODIFY `idSala` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Tarifa`
--
ALTER TABLE `Tarifa`
  MODIFY `idTarifa` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Asiento`
--
ALTER TABLE `Asiento`
  ADD CONSTRAINT `Asiento_ibfk_1` FOREIGN KEY (`idSala`) REFERENCES `Sala` (`idSala`),
  ADD CONSTRAINT `Asiento_ibfk_2` FOREIGN KEY (`nombreCine`) REFERENCES `Cine` (`nombre`);

--
-- Filtros para la tabla `Cine`
--
ALTER TABLE `Cine`
  ADD CONSTRAINT `Cine_ibfk_1` FOREIGN KEY (`id_administrador`) REFERENCES `Administrador` (`id_administrador`);

--
-- Filtros para la tabla `Cine_Pelicula`
--
ALTER TABLE `Cine_Pelicula`
  ADD CONSTRAINT `Cine_Pelicula_ibfk_1` FOREIGN KEY (`nombreCine`) REFERENCES `Cine` (`nombre`),
  ADD CONSTRAINT `Cine_Pelicula_ibfk_2` FOREIGN KEY (`titulo`) REFERENCES `Pelicula` (`titulo`);

--
-- Filtros para la tabla `Entrada`
--
ALTER TABLE `Entrada`
  ADD CONSTRAINT `Entrada_ibfk_1` FOREIGN KEY (`idAsiento`) REFERENCES `Asiento` (`codigo`),
  ADD CONSTRAINT `Entrada_ibfk_2` FOREIGN KEY (`idSala`) REFERENCES `Sala` (`idSala`),
  ADD CONSTRAINT `Entrada_ibfk_3` FOREIGN KEY (`nombreCine`) REFERENCES `Cine` (`nombre`),
  ADD CONSTRAINT `Entrada_ibfk_4` FOREIGN KEY (`titulo`) REFERENCES `Pelicula` (`titulo`),
  ADD CONSTRAINT `Entrada_ibfk_5` FOREIGN KEY (`idCliente`) REFERENCES `Cliente` (`idCliente`);

--
-- Filtros para la tabla `Factura`
--
ALTER TABLE `Factura`
  ADD CONSTRAINT `Factura_ibfk_1` FOREIGN KEY (`idEntrada`) REFERENCES `Entrada` (`idEntrada`),
  ADD CONSTRAINT `Factura_ibfk_2` FOREIGN KEY (`idTarifa`) REFERENCES `Tarifa` (`idTarifa`);

--
-- Filtros para la tabla `Sala`
--
ALTER TABLE `Sala`
  ADD CONSTRAINT `Sala_ibfk_1` FOREIGN KEY (`nombreCine`) REFERENCES `Cine` (`nombre`);

--
-- Filtros para la tabla `Tarifa`
--
ALTER TABLE `Tarifa`
  ADD CONSTRAINT `Tarifa_ibfk_1` FOREIGN KEY (`nombreCine`) REFERENCES `Cine` (`nombre`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
