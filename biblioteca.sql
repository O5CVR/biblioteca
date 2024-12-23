-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-12-2024 a las 19:26:41
-- Versión del servidor: 10.6.17-MariaDB
-- Versión de PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `biblioteca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ejemplar`
--

CREATE TABLE `ejemplar` (
  `id` int(11) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `estado` enum('Disponible','Prestado','Dañado') DEFAULT 'Disponible'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ejemplar`
--

INSERT INTO `ejemplar` (`id`, `isbn`, `estado`) VALUES
(1, '9781234567890', 'Prestado'),
(2, '9781234567890', 'Disponible'),
(3, '9781234567890', 'Prestado'),
(4, '9789876543210', 'Disponible'),
(5, '9789876543210', 'Dañado'),
(6, '9781111111111', 'Disponible'),
(7, '9781111111111', 'Disponible'),
(8, '4564374535', 'Disponible'),
(9, '4564374535', 'Disponible');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libro`
--

CREATE TABLE `libro` (
  `isbn` varchar(20) NOT NULL,
  `titulo` varchar(200) NOT NULL,
  `autor` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `libro`
--

INSERT INTO `libro` (`isbn`, `titulo`, `autor`) VALUES
('4564374535', 'El monje que vendió su ferrari', 'Robin S. Sharma'),
('9781111111111', '1984', 'George Orwell'),
('9781234567890', 'El Quijote', 'Miguel de Cervantes'),
('9789876543210', 'Cien Años de Soledad', 'Gabriel García Márquez');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamo`
--

CREATE TABLE `prestamo` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `ejemplar_id` int(11) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaDevolucion` date DEFAULT NULL,
  `entregado` enum('OK','KO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `prestamo`
--

INSERT INTO `prestamo` (`id`, `usuario_id`, `ejemplar_id`, `fechaInicio`, `fechaDevolucion`, `entregado`) VALUES
(16, 10, 3, '2024-12-21', '2025-01-05', 'OK'),
(17, 10, 1, '2024-12-21', '2025-01-05', 'KO'),
(19, 10, 3, '2024-12-22', '2025-01-06', 'KO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `tipo` enum('normal','administrador') NOT NULL,
  `penalizacionHasta` date DEFAULT '1999-01-01'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `dni`, `nombre`, `email`, `password`, `tipo`, `penalizacionHasta`) VALUES
(5, '49101142Y', 'Oscar', 'oscar@gmail.com', 'Oscar12345', 'administrador', '1999-01-01'),
(10, '49101142N', 'Casillas', 'casillas@gmail.com', 'Casillas12345', 'normal', '2024-12-24');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ejemplar`
--
ALTER TABLE `ejemplar`
  ADD PRIMARY KEY (`id`),
  ADD KEY `isbn` (`isbn`);

--
-- Indices de la tabla `libro`
--
ALTER TABLE `libro`
  ADD PRIMARY KEY (`isbn`);

--
-- Indices de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_id` (`usuario_id`),
  ADD KEY `ejemplar_id` (`ejemplar_id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ejemplar`
--
ALTER TABLE `ejemplar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ejemplar`
--
ALTER TABLE `ejemplar`
  ADD CONSTRAINT `ejemplar_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE;

--
-- Filtros para la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD CONSTRAINT `prestamo_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `prestamo_ibfk_2` FOREIGN KEY (`ejemplar_id`) REFERENCES `ejemplar` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
