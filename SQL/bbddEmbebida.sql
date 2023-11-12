Create database IF NOT EXISTS rythm;
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
) charset = utf8mb4;

create table artist
(
    id_artist   int auto_increment
        primary key,
    nacionality varchar(256) not null,
    picture     varchar(256) null,
    id_user     int null,
    constraint `FK ARTIST_USER`
        foreign key (id_user) references user (id_user)
            on update cascade on delete cascade
) charset = utf8mb4;

create table album
(
    id_album      int auto_increment
        primary key,
    id_artist     int          not null,
    name          varchar(256) not null,
    date          date         not null,
    picture       varchar(256) null,
    reproductions int null,
    constraint `FK ARTIST`
        foreign key (id_artist) references artist (id_artist)
            on update cascade on delete cascade
) charset = utf8mb4;

create index `FOREING ARTIST`
    on album (id_artist);

create index `FOREING USER`
    on artist (id_user);

create table reproductionlist
(
    id_reproductionList int auto_increment
        primary key,
    id_user             int          not null,
    name                varchar(256) not null,
    description         varchar(1024) null,
    constraint `FK USER_RPLIST`
        foreign key (id_user) references user (id_user)
            on update cascade on delete cascade
) charset = utf8mb4;

create table commentlistusers
(
    id_comment          int auto_increment
        primary key,
    id_user             int      not null,
    id_reproductionList int      not null,
    date                datetime not null,
    description         varchar(256) null,
    constraint `FK LIST_COMMENTLIST`
        foreign key (id_reproductionList) references reproductionlist (id_reproductionList)
            on update cascade on delete cascade,
    constraint `FK USER_COMMENTLIST`
        foreign key (id_user) references user (id_user)
            on update cascade on delete cascade
) charset = utf8mb4;

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
    reproductions int null,
    constraint `FK ALBUM`
        foreign key (id_album) references album (id_album)
            on update cascade
) charset = utf8mb4;

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
) charset = utf8mb4;

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
) charset = utf8mb4;






-- [UserSubcriptionList]
-- crearemos un trigger en el que cuando un usuario cree una lista de reproduccion se le relacione en la tabla usersubscriptionlist
CREATE TRIGGER `ownerSubcriptionList`
    AFTER INSERT
    ON `reproductionlist`
    FOR EACH ROW INSERT INTO usersubscriptionlist (id_user, id_reproductionList)
                 VALUES (NEW.id_user, NEW.id_reproductionList);

-- Crearemos un trigger para que cuando se haya ejecutado un Update en la tabla song en la columna reproductions. entonces las reproducciones del album sera = a la suma de las reproducciones de las canciones que pertenecen a ese album
CREATE TRIGGER `updateReproductionsAlbum`
    AFTER UPDATE
    ON `song`
    FOR EACH ROW UPDATE album
                 SET reproductions = (SELECT SUM(reproductions) FROM song WHERE id_album = NEW.id_album)
                 WHERE id_album = NEW.id_album;


-- Users Inserts

insert into rythm.user (id_user, name, email, picture, password, nickname, lastname)
values (1, 'Luis', 'luishidalgoa@outlook.es',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\Luis.png', '1234',
        'luishidalgoa', 'Hidalgo');
insert into rythm.user (id_user, name, email, picture, password, nickname, lastname)
values (2, 'Jose', 'jose@gmail.es',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\default.jepg', '1234',
        'jose', 'Benitez');
insert into rythm.user (id_user, name, email, picture, password, nickname, lastname)
values (4, 'Paopoa', 'paopoa@gmail.com',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\paopao.jpeg', '1234',
        'Paopoa', 'test');
insert into rythm.user (id_user, name, email, picture, password, nickname, lastname)
values (5, 'Quevedo', 'Quevedo@outlook.com',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\Quevedo.jpg', '1234',
        'Quevedo', 'El pedro');
insert into rythm.user (id_user, name, email, picture, password, nickname, lastname)
values (6, 'Emilia', 'Emilia@gmail.es',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\Emilia.jpg', '1234',
        'Emilia', 'Mermes');
insert into rythm.user (id_user, name, email, picture, password, nickname, lastname)
values (7, 'Michel', 'michael@gmail.com',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\MichelBubble.jpg',
        '1234', 'Michael Bublé', 'Bublé');


-- Artist Inserts

insert into rythm.artist (id_artist, nacionality, picture, id_user)
values (1, 'Canarias,Spain', 'test', 5);
insert into rythm.artist (id_artist, nacionality, picture, id_user)
values (2, 'Spain', 'test', 4);
insert into rythm.artist (id_artist, nacionality, picture, id_user)
values (3, 'Argentina', 'test', 6);
insert into rythm.artist (id_artist, nacionality, picture, id_user)
values (4, 'Canadian', 'test', 7);


-- Album Inserts

insert into rythm.album (id_album, id_artist, name, date, picture, reproductions)
values (1, 1, 'Donde quiero estar', '2023-11-10',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\album\\quevedo.png', 21);
insert into rythm.album (id_album, id_artist, name, date, picture, reproductions)
values (2, 2, 'Paopoa', '2023-11-09',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\album\\algo_asi.jpg', 65);
insert into rythm.album (id_album, id_artist, name, date, picture, reproductions)
values (3, 3, '.mp3', '2023-11-10',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\album\\mp3_Emilia.jpg', 47);
insert into rythm.album (id_album, id_artist, name, date, picture, reproductions)
values (4, 4, 'Christmas (Deluxe 10th Anniversary Edition)', '2023-11-11',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\album\\BubléChritmats.jpg',
        20);
insert into rythm.album (id_album, id_artist, name, date, picture, reproductions)
values (11, 4, 'Higher', '2023-11-12',
        './src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/pictures/album/-2042670142.jpg', 4);


-- Songs Inserts

insert into rythm.song (id_song, id_album, name, url, lenght, genre, reproductions)
values (1, 1, 'Quédate', '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\music\\quedate.mp3',
        '00:03:23', 'POP', 21);
insert into rythm.song (id_song, id_album, name, url, lenght, genre, reproductions)
values (2, 2, 'Algo Así', '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\music\\algo_asi.mp3',
        '00:03:43', 'REGGAE', 65);
insert into rythm.song (id_song, id_album, name, url, lenght, genre, reproductions)
values (3, 3, 'Facts.mp3',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\music\\Facts.mp3.mp3', '00:02:10',
        'POP', 16);
insert into rythm.song (id_song, id_album, name, url, lenght, genre, reproductions)
values (4, 3, 'IConic.mp3',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\music\\IConic.mp3.mp3', '00:03:01',
        'POP', 21);
insert into rythm.song (id_song, id_album, name, url, lenght, genre, reproductions)
values (5, 3, 'Jagger.mp3',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\music\\Jagger.mp3.mp3', '00:02:35',
        'POP', 11);
insert into rythm.song (id_song, id_album, name, url, lenght, genre, reproductions)
values (6, 4, 'Michael''s Christmas Greeting',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\music\\Michael''s Christmas Greeting.mp3',
        '00:00:08', 'OTHER', 8);
insert into rythm.song (id_song, id_album, name, url, lenght, genre, reproductions)
values (7, 4, 'The More You Give (The More You''ll Have)',
        '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\music\\The More You Give (The More You''ll Have).mp3',
        '00:01:13', 'POP', 12);
insert into rythm.song (id_song, id_album, name, url, lenght, genre, reproductions)
values (8, 11, 'I´ll Never Not Love You',
        './src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/music/-235178185.mp3', '00:02:30', 'POP', 3);
insert into rythm.song (id_song, id_album, name, url, lenght, genre, reproductions)
values (9, 11, 'My Valentine (Lyric Video)',
        './src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/music/2015415309.mp3', '00:02:30', 'POP', 1);


-- Reproduction Lists Inserts


insert into rythm.reproductionlist (id_reproductionList, id_user, name, description)
values (1, 1, 'Musicote', 'La mejor musica del mundo 🌍');
insert into rythm.reproductionlist (id_reproductionList, id_user, name, description)
values (2, 1, 'Lo nuevo de Emilia', 'Todo lo nuevo de Emilia 💖');


-- Reproduction lists Songs inserts

insert into rythm.reproductionsonglist (id_reproductionList, id_song)
values (1, 1);
insert into rythm.reproductionsonglist (id_reproductionList, id_song)
values (1, 2);
insert into rythm.reproductionsonglist (id_reproductionList, id_song)
values (2, 3);
insert into rythm.reproductionsonglist (id_reproductionList, id_song)
values (2, 4);
insert into rythm.reproductionsonglist (id_reproductionList, id_song)
values (2, 5);


-- User Subcription for Reproduction Lists:
insert into rythm.usersubscriptionlist (id_user, id_reproductionList)
values (2, 1);
insert into rythm.usersubscriptionlist (id_user, id_reproductionList)
values (5, 1);


-- Comments for Reproduction lists Inserts

insert into rythm.commentlistusers (id_comment, id_user, id_reproductionList, date, description)
values (1, 4, 1, '2023-11-11 10:58:38', 'Esta lista de reproduccion es lo mas');
insert into rythm.commentlistusers (id_comment, id_user, id_reproductionList, date, description)
values (6, 1, 1, '2023-11-11 00:00:00', 'hola');
insert into rythm.commentlistusers (id_comment, id_user, id_reproductionList, date, description)
values (7, 1, 1, '2023-11-11 00:00:00', 'adios');
insert into rythm.commentlistusers (id_comment, id_user, id_reproductionList, date, description)
values (8, 1, 1, '2023-11-11 00:00:00', 'dfgh');
