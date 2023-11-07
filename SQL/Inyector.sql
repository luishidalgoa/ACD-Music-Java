SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `rythm`;

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


create table user
(
    id_user  int auto_increment
        primary key,
    name     varchar(256) not null,
    email    varchar(256) not null,
    picture  varchar(256) null,
    password varchar(256) not null,
    nickname varchar(256) not null,
    lastname varchar(256) not null
)
    charset = utf8mb4;

create table artist
(
    id_artist   int auto_increment
        primary key,
    nacionality varchar(256) not null,
    picture     varchar(256) null,
    id_user     int          null,
    constraint `FK ARTIST_USER`
        foreign key (id_user) references user (id_user)
            on update cascade on delete cascade
)
    charset = utf8mb4;

<<<<<<< HEAD
create table album
(
    id_album      int auto_increment
        primary key,
    id_artist     int          not null,
    name          varchar(256) not null,
    date          date         not null,
    picture       varchar(256) null,
    reproductions int          null,
    constraint `FK ARTIST`
        foreign key (id_artist) references artist (id_artist)
            on update cascade on delete cascade
)
    charset = utf8mb4;
=======
CREATE TABLE `artist`
(
    `id_artist`   int(11)      NOT NULL,
    `nacionality` varchar(256) NOT NULL,
    `picture`     varchar(256) DEFAULT NULL,
    `id_user`     int(11)      DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
>>>>>>> 1ade952b069e205f0fe47492f2b1d89e3a8683f0

create index `FOREING ARTIST`
    on album (id_artist);

create index `FOREING USER`
    on artist (id_user);

<<<<<<< HEAD
create table reproductionlist
(
    id_reproductionList int auto_increment
        primary key,
    id_user             int           not null,
    name                varchar(256)  not null,
    description         varchar(1024) null,
    constraint `FK USER_RPLIST`
        foreign key (id_user) references user (id_user)
            on update cascade on delete cascade
)
    charset = utf8mb4;
=======
CREATE TABLE `commentlistusers`
(
    `id_comment`          int(11)  NOT NULL,
    `id_user`             int(11)  NOT NULL,
    `id_reproductionList` int(11)  NOT NULL,
    `date`                datetime NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
>>>>>>> 1ade952b069e205f0fe47492f2b1d89e3a8683f0

create table commentlistusers
(
    id_comment          int auto_increment
        primary key,
    id_user             int          not null,
    id_reproductionList int          not null,
    date                datetime     not null,
    description         varchar(256) null,
    constraint `FK LIST_COMMENTLIST`
        foreign key (id_reproductionList) references reproductionlist (id_reproductionList)
            on update cascade on delete cascade,
    constraint `FK USER_COMMENTLIST`
        foreign key (id_user) references user (id_user)
            on update cascade on delete cascade
)
    charset = utf8mb4;

create index `FOREING REPRODUCIONTLIST`
    on commentlistusers (id_reproductionList);

<<<<<<< HEAD
create index `FOREING USER`
    on commentlistusers (id_user);
=======
CREATE TABLE `reproductionlist`
(
    `id_reproductionList` int(11)      NOT NULL,
    `id_user`             int(11)      NOT NULL,
    `name`                varchar(256) NOT NULL,
    `description`         varchar(1024) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
>>>>>>> 1ade952b069e205f0fe47492f2b1d89e3a8683f0

create index `FOREING USER`
    on reproductionlist (id_user);

create table song
(
    id_song       int auto_increment
        primary key,
    id_album      int          not null,
    name          varchar(256) not null,
    url           varchar(256) not null,
    lenght        time         not null,
    genre         varchar(256) not null,
    reproductions int          null,
    constraint `FK ALBUM`
        foreign key (id_album) references album (id_album)
            on update cascade
)
    charset = utf8mb4;

<<<<<<< HEAD
create table reproductionsonglist
(
    id_reproductionList int not null,
    id_song             int not null,
    primary key (id_reproductionList, id_song),
    constraint `FK RPLIST`
        foreign key (id_reproductionList) references reproductionlist (id_reproductionList)
            on update cascade on delete cascade,
    constraint `FK SONG`
        foreign key (id_song) references song (id_song)
            on update cascade on delete cascade
)
    charset = utf8mb4;
=======
CREATE TABLE `reproductionsonglist`
(
    `id_reproductionList` int(11) NOT NULL,
    `id_song`             int(11) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
>>>>>>> 1ade952b069e205f0fe47492f2b1d89e3a8683f0

create index `FOREING ALBUM`
    on song (id_album);

create table usersubscriptionlist
(
    id_user             int not null,
    id_reproductionList int not null,
    primary key (id_user, id_reproductionList),
    constraint `FK LIST`
        foreign key (id_reproductionList) references reproductionlist (id_reproductionList)
            on update cascade on delete cascade,
    constraint `FK USER`
        foreign key (id_user) references user (id_user)
            on update cascade on delete cascade
)
    charset = utf8mb4;

<<<<<<< HEAD
# -------------------------------------------------------- USER
=======
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
>>>>>>> 1ade952b069e205f0fe47492f2b1d89e3a8683f0

INSERT INTO rythm.user (id_user, name, email, picture, password, nickname, lastname)
VALUES (1, 'Luis', 'luishidalgoa@outlook.es',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\Luis.png', '1234',
        'luishidalgoa', 'Hidalgo');
INSERT INTO rythm.user (id_user, name, email, picture, password, nickname, lastname)
VALUES (2, 'Jose', 'jose@gmail.es',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\default.jepg', '1234',
        'jose', 'Benitez');

<<<<<<< HEAD
=======
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


-- SELECTS
-- [UserSubcriptionList]
-- buscaremos el usuario dueño de la lista de reproduccion 1
select u.nickname
from usersubscriptionlist r
         JOIN reproductionlist l ON r.id_reproductionList = l.id_reproductionList
         JOIN user u ON r.id_user = u.id_user
WHERE l.id_reproductionList = ?;

-- vamos a poner el autoincrement de reproductionlist a 1
ALTER TABLE reproductionlist AUTO_INCREMENT = 1;

    -- [UserSubcriptionList]
        -- crearemos un trigger en el que cuando un usuario cree una lista de reproduccion se le relacione en la tabla usersubscriptionlist
        CREATE TRIGGER `ownerSubcriptionList`
            AFTER INSERT
            ON `reproductionlist`
            FOR EACH ROW INSERT INTO usersubscriptionlist (id_user, id_reproductionList)
                         VALUES (NEW.id_user, NEW.id_reproductionList);

>>>>>>> 1ade952b069e205f0fe47492f2b1d89e3a8683f0

