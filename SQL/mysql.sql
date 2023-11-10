use rythm;

INSERT INTO rythm.user (id_user, name, email, picture, password, nickname, lastname) VALUES (1, 'Luis', 'luishidalgoa@outlook.es', '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\Luis.png', '1234', 'luishidalgoa', 'Hidalgo');
INSERT INTO rythm.user (id_user, name, email, picture, password, nickname, lastname) VALUES (2, 'Jose', 'jose@gmail.es', '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\default.jepg', '1234', 'jose', 'Benitez');
INSERT INTO rythm.user (id_user, name, email, picture, password, nickname, lastname) VALUES (3, 'test', 'raul@gmail.com', 'test', '1234', 'RaulNapias', 'test');
INSERT INTO rythm.user (id_user, name, email, picture, password, nickname, lastname) VALUES (4, 'Paopoa', 'paopoa@gmail.com', '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\user\\paopao.jpeg', '1234', 'Paopoa', 'test');

INSERT INTO rythm.artist (id_artist, nacionality, picture, id_user) VALUES (1, 'Spain', 'test', 3);
INSERT INTO rythm.artist (id_artist, nacionality, picture, id_user) VALUES (2, 'Spain', 'test', 4);


INSERT INTO rythm.album (id_album, id_artist, name, date, picture, reproductions) VALUES (1, 1, 'test', '2023-11-04', '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\album\\quevedo.png', 11);
INSERT INTO rythm.album (id_album, id_artist, name, date, picture, reproductions) VALUES (2, 2, 'Paopoa', '2023-11-09', '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\pictures\\album\\algo_asi.jpg', 46);

INSERT INTO rythm.song (id_song, id_album, name, url, lenght, genre, reproductions) VALUES (1, 1, 'Quedate', '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\music\\quevedo.mp3', '00:02:16', 'POP', 11);
INSERT INTO rythm.song (id_song, id_album, name, url, lenght, genre, reproductions) VALUES (2, 2, 'Algo Asi', '.\\src\\main\\resources\\dev\\iesfranciscodelosrios\\acdmusic\\assets\\music\\algo_asi.mp3', '12:40:15', 'REGGAE', 46);

INSERT INTO rythm.reproductionlist (id_reproductionList, id_user, name, description) VALUES (1, 1, 'ReproductionList1', 'test');
INSERT INTO rythm.reproductionlist (id_reproductionList, id_user, name, description) VALUES (2, 4, 'Musicote', 'Escucha la mejor musica en musicote');

INSERT INTO rythm.reproductionsonglist (id_reproductionList, id_song) VALUES (1, 1);
INSERT INTO rythm.reproductionsonglist (id_reproductionList, id_song) VALUES (1, 2);

INSERT INTO rythm.usersubscriptionlist (id_user, id_reproductionList) VALUES (2, 1);
INSERT INTO rythm.usersubscriptionlist (id_user, id_reproductionList) VALUES (1, 2);

