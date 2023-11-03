SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `rythm`;

use rythm;

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

create index `FOREING ARTIST`
    on album (id_artist);

create index `FOREING USER`
    on artist (id_user);

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

create index `FOREING USER`
    on commentlistusers (id_user);

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

# -------------------------------------------------------- USER

INSERT INTO rythm.user (id_user, name, email, picture, password, nickname, lastname)
VALUES (1, 'Luis', 'luishidalgoa@outlook.es',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\Luis.png', '1234',
        'luishidalgoa', 'Hidalgo');
INSERT INTO rythm.user (id_user, name, email, picture, password, nickname, lastname)
VALUES (2, 'Jose', 'jose@gmail.es',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\default.jepg', '1234',
        'jose', 'Benitez');


