-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-11-2023 a las 12:43:14
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `rythm`
--
CREATE DATABASE IF NOT EXISTS `rythm`;
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `album`
--
use rythm;
CREATE TABLE `album`
(
    `id_album`      int(11)      NOT NULL,
    `id_artist`     int(11)      NOT NULL,
    `name`          varchar(256) NOT NULL,
    `date`          date         NOT NULL,
    `picture`       varchar(256) DEFAULT NULL,
    `reproductions` int(11)      DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `artist`
--

CREATE TABLE `artist`
(
    `id_artist`   int(11)      NOT NULL,
    `nacionality` varchar(256) NOT NULL,
    `picture`     varchar(256) DEFAULT NULL,
    `id_user`     int(11)      DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `commentlistusers`
--

CREATE TABLE `commentlistusers`
(
    `id_comment`          int(11)  NOT NULL,
    `id_user`             int(11)  NOT NULL,
    `id_reproductionList` int(11)  NOT NULL,
    `date`                datetime NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reproductionlist`
--

CREATE TABLE `reproductionlist`
(
    `id_reproductionList` int(11)      NOT NULL,
    `id_user`             int(11)      NOT NULL,
    `name`                varchar(256) NOT NULL,
    `description`         varchar(1024) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reproductionsonglist`
--

CREATE TABLE `reproductionsonglist`
(
    `id_reproductionList` int(11) NOT NULL,
    `id_song`             int(11) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `song`
--

CREATE TABLE `song`
(
    `id_song`       int(11)      NOT NULL,
    `id_album`      int(11)      NOT NULL,
    `name`          varchar(256) NOT NULL,
    `url`           varchar(256) NOT NULL,
    `lenght`        time         NOT NULL,
    `genre`         varchar(256) NOT NULL,
    `reproductions` int(11) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user`
(
    `id_user`  int(11)      NOT NULL,
    `name`     varchar(256) NOT NULL,
    `email`    varchar(256) NOT NULL,
    `picture`  varchar(256) DEFAULT NULL,
    `password` varchar(256) NOT NULL,
    `nickname` varchar(256) NOT NULL,
    `lastname` varchar(256) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usersubscriptionlist`
--

CREATE TABLE `usersubscriptionlist`
(
    `id_user`             int(11) NOT NULL,
    `id_reproductionList` int(11) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `album`
--
ALTER TABLE `album`
    ADD PRIMARY KEY (`id_album`),
    ADD KEY `FOREING ARTIST` (`id_artist`);

--
-- Indices de la tabla `artist`
--
ALTER TABLE `artist`
    ADD PRIMARY KEY (`id_artist`),
    ADD KEY `FOREING USER` (`id_user`);

--
-- Indices de la tabla `commentlistusers`
--
ALTER TABLE `commentlistusers`
    ADD PRIMARY KEY (`id_comment`),
    ADD KEY `FOREING USER` (`id_user`),
    ADD KEY `FOREING REPRODUCIONTLIST` (`id_reproductionList`);

--
-- Indices de la tabla `reproductionlist`
--
ALTER TABLE `reproductionlist`
    ADD PRIMARY KEY (`id_reproductionList`),
    ADD KEY `FOREING USER` (`id_user`);

--
-- Indices de la tabla `reproductionsonglist`
--
ALTER TABLE `reproductionsonglist`
    ADD PRIMARY KEY (`id_reproductionList`, `id_song`),
    ADD KEY `FK SONG` (`id_song`);

--
-- Indices de la tabla `song`
--
ALTER TABLE `song`
    ADD PRIMARY KEY (`id_song`),
    ADD KEY `FOREING ALBUM` (`id_album`) USING BTREE;

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
    ADD PRIMARY KEY (`id_user`);

--
-- Indices de la tabla `usersubscriptionlist`
--
ALTER TABLE `usersubscriptionlist`
    ADD PRIMARY KEY (`id_user`, `id_reproductionList`),
    ADD KEY `FK LIST` (`id_reproductionList`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `album`
--
ALTER TABLE `album`
    MODIFY `id_album` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `artist`
--
ALTER TABLE `artist`
    MODIFY `id_artist` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `commentlistusers`
--
ALTER TABLE `commentlistusers`
    MODIFY `id_comment` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reproductionlist`
--
ALTER TABLE `reproductionlist`
    MODIFY `id_reproductionList` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `song`
--
ALTER TABLE `song`
    MODIFY `id_song` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
    MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `album`
--
ALTER TABLE `album`
    ADD CONSTRAINT `FK ARTIST` FOREIGN KEY (`id_artist`) REFERENCES `artist` (`id_artist`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `artist`
--
ALTER TABLE `artist`
    ADD CONSTRAINT `FK ARTIST_USER` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Filtros para la tabla `commentlistusers`
--
ALTER TABLE `commentlistusers`
    ADD CONSTRAINT `FK LIST_COMMENTLIST` FOREIGN KEY (`id_reproductionList`) REFERENCES `reproductionlist` (`id_reproductionList`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK USER_COMMENTLIST` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `reproductionlist`
--
ALTER TABLE `reproductionlist`
    ADD CONSTRAINT `FK USER_RPLIST` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `reproductionsonglist`
--
ALTER TABLE `reproductionsonglist`
    ADD CONSTRAINT `FK RPLIST` FOREIGN KEY (`id_reproductionList`) REFERENCES `reproductionlist` (`id_reproductionList`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK SONG` FOREIGN KEY (`id_song`) REFERENCES `song` (`id_song`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `song`
--
ALTER TABLE `song`
    ADD CONSTRAINT `FK ALBUM` FOREIGN KEY (`id_album`) REFERENCES `album` (`id_album`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `usersubscriptionlist`
--
ALTER TABLE `usersubscriptionlist`
    ADD CONSTRAINT `FK LIST` FOREIGN KEY (`id_reproductionList`) REFERENCES `reproductionlist` (`id_reproductionList`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `FK USER` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
ALTER TABLE `artist`
    DROP FOREIGN KEY `FK ARTIST_USER`;
ALTER TABLE `artist`
    ADD CONSTRAINT `FK ARTIST_USER` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `commentlistusers`
    ADD Column `description` varchar(256) DEFAULT NULL;


-- TRIGGERS

-- [UserSubcriptionList]
-- crearemos un trigger en el que cuando un usuario cree una lista de reproduccion se le relacione en la tabla usersubscriptionlist
CREATE TRIGGER `ownerSubcriptionList`
    AFTER INSERT
    ON `reproductionlist`
    FOR EACH ROW INSERT INTO usersubscriptionlist (id_user, id_reproductionList)
                 VALUES (NEW.id_user, NEW.id_reproductionList);



-- vamos a poner el autoincrement de reproductionlist a 1
ALTER TABLE reproductionlist AUTO_INCREMENT = 1;

    -- [UserSubcriptionList]
        -- crearemos un trigger en el que cuando un usuario cree una lista de reproduccion se le relacione en la tabla usersubscriptionlist
        CREATE TRIGGER `ownerSubcriptionList`
            AFTER INSERT
            ON `reproductionlist`
            FOR EACH ROW INSERT INTO usersubscriptionlist (id_user, id_reproductionList)
                         VALUES (NEW.id_user, NEW.id_reproductionList);
