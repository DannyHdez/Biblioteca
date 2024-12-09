-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-12-2024 a las 17:56:45
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
-- Base de datos: `biblioteca`
--

DROP DATABASE IF EXISTS biblioteca;
CREATE DATABASE biblioteca;
USE biblioteca;


CREATE USER 'admin_biblioteca'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL ON biblioteca.* TO 'admin_biblioteca'@'localhost';
FLUSH PRIVILEGES;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `authors`
--

CREATE TABLE `authors` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surnames` varchar(255) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `biography` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `authors`
--

INSERT INTO `authors` (`id`, `name`, `surnames`, `date_of_birth`, `nationality`, `biography`) VALUES
(1, 'Juan', 'Pérez', '1975-06-15', 'Estadounidense', 'Un reconocido autor de novelas de misterio.'),
(2, 'María', 'García', '1980-03-22', 'Británica', 'Una prolífica escritora de ciencia ficción.'),
(3, 'Carlos', 'González', '1990-01-10', 'Mexicano', 'Conocido por sus cautivadoras novelas históricas.'),
(4, 'Sofía', 'López', '1985-09-17', 'Francesa', 'Escribe entretenidas comedias románticas.'),
(5, 'Hiroshi', 'Tanaka', '1978-11-30', 'Japonés', 'Experto en literatura sobre tecnología e inteligencia artificial.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `isbn` varchar(255) NOT NULL,
  `title_es` varchar(255) NOT NULL,
  `title_en` varchar(255) DEFAULT NULL,
  `title_ca` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `page_count` int(11) DEFAULT NULL,
  `cover_url` varchar(255) DEFAULT NULL,
  `publication_date` date DEFAULT NULL,
  `editorial_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `books`
--

INSERT INTO `books` (`id`, `isbn`, `title_es`, `title_en`, `title_ca`, `description`, `language`, `page_count`, `cover_url`, `publication_date`, `editorial_id`, `user_id`) VALUES
(1, '978-3-16-148410-0', 'El Misterio', NULL, NULL, 'Una intrigante novela de misterio.', 'ES', 320, NULL, '2020-05-15', 1, 1),
(2, '978-1-4028-9462-6', 'La Ciencia Ficción', NULL, NULL, 'Explora el futuro de la humanidad.', 'ES', 250, NULL, '2019-03-22', 2, 2),
(3, '978-0-19-852663-6', 'El Pasado', NULL, NULL, 'Aventuras históricas en tiempos antiguos.', 'ES', 400, NULL, '2018-07-10', 3, 3),
(4, '978-4-06-521410-6', 'La Fantasía', NULL, NULL, 'Un mundo lleno de magia y maravillas.', 'ES', 370, NULL, '2021-11-17', 4, 4),
(5, '978-0-330-40034-8', 'La Biografía', NULL, NULL, 'La inspiradora historia de una vida.', 'ES', 280, NULL, '2020-09-30', 5, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `book_author`
--

CREATE TABLE `book_author` (
  `book_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `book_author`
--

INSERT INTO `book_author` (`book_id`, `author_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `book_genre`
--

CREATE TABLE `book_genre` (
  `book_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `book_genre`
--

INSERT INTO `book_genre` (`book_id`, `genre_id`) VALUES
(1, 1),
(2, 3),
(3, 2),
(4, 4),
(5, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `editorials`
--

CREATE TABLE `editorials` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `founded_year` int(11) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `editorials`
--

INSERT INTO `editorials` (`id`, `name`, `country`, `founded_year`, `logo`) VALUES
(1, 'Editorial Planeta', 'España', 1949, NULL),
(2, 'Alfaguara', 'España', 1964, NULL),
(3, 'Seix Barral', 'España', 1911, NULL),
(4, 'Anagrama', 'España', 1969, NULL),
(5, 'Santillana', 'España', 1960, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genres`
--

CREATE TABLE `genres` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `genres`
--

INSERT INTO `genres` (`id`, `name`) VALUES
(18, 'Arte'),
(10, 'Autoayuda'),
(20, 'Aventuras'),
(7, 'BiografÃ­a'),
(9, 'Ciencia'),
(1, 'Ciencia FicciÃ³n'),
(15, 'Ensayo'),
(2, 'FantasÃ­a'),
(16, 'FilosofÃ­a'),
(8, 'Historia'),
(12, 'Infantil'),
(13, 'Juvenil'),
(11, 'Literatura ClÃ¡sica'),
(5, 'Misterio'),
(14, 'PoesÃ­a'),
(17, 'ReligiÃ³n'),
(3, 'Romance'),
(19, 'Teatro'),
(4, 'Terror'),
(6, 'Thriller');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surnames` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profile_picture_url` varchar(255) DEFAULT NULL,
  `registration_date` date DEFAULT curdate(),
  `deactivation_date` date DEFAULT NULL,
  `user_type` varchar(255) NOT NULL,
  `account_disabled` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `name`, `surnames`, `email`, `username`, `password`, `profile_picture_url`, `registration_date`, `deactivation_date`, `user_type`, `account_disabled`) VALUES
(1, 'Alicia', 'Martínez', 'alicia.martinez@ejemplo.com', 'alicia123', '$2a$10$Q2qgh6AWr3zaBBPeyJq.puevmvIC17oZ3YwmY568MeaLnPMPVc8RC', NULL, '2024-11-18', NULL, 'ADMIN', 0),
(2, 'Roberto', 'Fernández', 'roberto.fernandez@ejemplo.com', 'robertoF', '$2a$10$Q2qgh6AWr3zaBBPeyJq.puevmvIC17oZ3YwmY568MeaLnPMPVc8RC', NULL, '2024-11-18', NULL, 'USER', 0),
(3, 'Carlos', 'Ruiz', 'carlos.ruiz@ejemplo.com', 'carlosR', '$2a$10$Q2qgh6AWr3zaBBPeyJq.puevmvIC17oZ3YwmY568MeaLnPMPVc8RC', NULL, '2024-11-18', NULL, 'USER', 0),
(4, 'Diana', 'Blanco', 'diana.blanco@ejemplo.com', 'dianab', '$2a$10$Q2qgh6AWr3zaBBPeyJq.puevmvIC17oZ3YwmY568MeaLnPMPVc8RC', NULL, '2024-11-18', NULL, 'USER', 0),
(5, 'Eva', 'Negro', 'eva.negro@ejemplo.com', 'evaN', '$2a$10$has2skxFB/i/6up4mW93Lu/W1UzcUhZKzBEJKH9w2dClSje9jh4py', NULL, '2024-11-18', '2024-12-02', 'USER', 1),
(6, 'John', 'Doe', 'john.doe@example.com', 'john_doe', '$2a$10$has2skxFB/i/6up4mW93Lu/W1UzcUhZKzBEJKH9w2dClSje9jh4py', NULL, '2024-11-20', NULL, 'user', 0),
(10, 'Dani', 'Hernandez', 'danihernandez@ejemplo.com', 'Danny', '$2a$10$sx/UFgfgtbCKP03s9UJIoujC2McL2L0MKkpsS9oCZ/Xa0zVqhAfVe', NULL, '2024-12-02', '2024-12-04', 'ADMIN', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `authors`
--
ALTER TABLE `authors`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `isbn` (`isbn`),
  ADD KEY `editorial_id` (`editorial_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indices de la tabla `book_author`
--
ALTER TABLE `book_author`
  ADD PRIMARY KEY (`book_id`,`author_id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `author_id` (`author_id`);

--
-- Indices de la tabla `book_genre`
--
ALTER TABLE `book_genre`
  ADD PRIMARY KEY (`book_id`,`genre_id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `genre_id` (`genre_id`);

--
-- Indices de la tabla `editorials`
--
ALTER TABLE `editorials`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indices de la tabla `genres`
--
ALTER TABLE `genres`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `authors`
--
ALTER TABLE `authors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `editorials`
--
ALTER TABLE `editorials`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `genres`
--
ALTER TABLE `genres`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `books_ibfk_1` FOREIGN KEY (`editorial_id`) REFERENCES `editorials` (`id`),
  ADD CONSTRAINT `books_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Filtros para la tabla `book_author`
--
ALTER TABLE `book_author`
  ADD CONSTRAINT `book_author_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  ADD CONSTRAINT `book_author_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`);

--
-- Filtros para la tabla `book_genre`
--
ALTER TABLE `book_genre`
  ADD CONSTRAINT `book_genre_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  ADD CONSTRAINT `book_genre_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
