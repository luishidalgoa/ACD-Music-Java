package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iSongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SongDAO implements iSongDAO{

    private static final String INSERT_SONG = "INSERT INTO song (id_album, name, url, length, genre, reproductions) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_SONG = "DELETE FROM song WHERE id_song = ?";
    private static final String SELECT_BY_GENRE = "SELECT id_song, id_album, name, url, length, reproductions FROM song WHERE genre = ?";
    private static final String SELECT_BY_ID = "SELECT id_song, id_album, name, url, length, genre, reproductions FROM song WHERE id_song = ?";
    private static final String SELECT_BY_ALBUM_ID = "SELECT id_song, name, url, length, genre, reproductions FROM song WHERE id_album = ?";
    private static final String SELECT_TOP_SONGS = "SELECT id_song, id_album, name, url, length, genre, reproductions FROM song ORDER BY reproductions DESC LIMIT 4";
    private static final String SELECT_RECENT_SONGS = "SELECT id_song, id_album, name, url, length, genre, reproductions FROM song ORDER BY id_song DESC LIMIT 4";
    private static final String SELECT_BY_NAME = "SELECT id_song, id_album, name, url, length, genre, reproductions FROM song WHERE name LIKE ?";


    private static SongDAO instance;
    private Connection conn;

    private SongDAO() {
        conn = ConnectionData.getConnection();
    }

    public static SongDAO getInstance() {
        if (instance == null) {
            instance = new SongDAO();
        }
        return instance;
    }

    @Override
    public Song addSong(Song song) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SONG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, song.getId_album());
            preparedStatement.setString(2, song.getName());
            preparedStatement.setString(3, song.getUrl());
            preparedStatement.setTime(4, Time.valueOf(song.getTime()));
            preparedStatement.setString(5, song.getGenre().toString());
            preparedStatement.setInt(6, song.getReproductions());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    song.setId_song(generatedId);
                    return song;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean removeSong(int idSong) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SONG);
            preparedStatement.setInt(1, idSong);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Set<Song> searchByGenre(Genre genre) {
        Set<Song> songs = new HashSet<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_BY_GENRE);
            preparedStatement.setString(1, genre.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_song = resultSet.getInt("id_song");
                int id_album = resultSet.getInt("id_album");
                String name = resultSet.getString("name");
                String url = resultSet.getString("url");
                Time length = resultSet.getTime("length");
                int reproductions = resultSet.getInt("reproductions");

                Song song = new Song(id_song, id_album, name, url, length.toLocalTime(), genre, reproductions);
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }


    @Override
    public Song searchById(int idSong) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, idSong);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id_album = resultSet.getInt("id_album");
                String name = resultSet.getString("name");
                String url = resultSet.getString("url");
                Time length = resultSet.getTime("length");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                int reproductions = resultSet.getInt("reproductions");

                return new Song(idSong, id_album, name, url, length.toLocalTime(), genre, reproductions);
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Song> searchByAlbumId(int idAlbum) {
        Set<Song> songs = new HashSet<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_BY_ALBUM_ID);
            preparedStatement.setInt(1, idAlbum);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_song = resultSet.getInt("id_song");
                String name = resultSet.getString("name");
                String url = resultSet.getString("url");
                Time length = resultSet.getTime("length");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                int reproductions = resultSet.getInt("reproductions");

                Song song = new Song(id_song, idAlbum, name, url, length.toLocalTime(), genre, reproductions);
                songs.add(song);
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return songs;
    }

    @Override
    public Set<Song> searchTopSongs() {
        Set<Song> topSongs = new HashSet<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_TOP_SONGS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_song = resultSet.getInt("id_song");
                int id_album = resultSet.getInt("id_album");
                String name = resultSet.getString("name");
                String url = resultSet.getString("url");
                Time length = resultSet.getTime("length");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                int reproductions = resultSet.getInt("reproductions");

                Song song = new Song(id_song, id_album, name, url, length.toLocalTime(), genre, reproductions);
                topSongs.add(song);
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return topSongs;
    }

    @Override
    public Set<Song> searchRecientSongs() {
        Set<Song> recentSongs = new HashSet<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_RECENT_SONGS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_song = resultSet.getInt("id_song");
                int id_album = resultSet.getInt("id_album");
                String name = resultSet.getString("name");
                String url = resultSet.getString("url");
                Time length = resultSet.getTime("length");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                int reproductions = resultSet.getInt("reproductions");

                Song song = new Song(id_song, id_album, name, url, length.toLocalTime(), genre, reproductions);
                recentSongs.add(song);
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return recentSongs;
    }

    @Override
    public Set<Song> searchByNombre(String filterWord) {
        Set<Song> songs = new HashSet<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, "%" + filterWord + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_song = resultSet.getInt("id_song");
                int id_album = resultSet.getInt("id_album");
                String name = resultSet.getString("name");
                String url = resultSet.getString("url");
                Time length = resultSet.getTime("length");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                int reproductions = resultSet.getInt("reproductions");

                Song song = new Song(id_song, id_album, name, url, length.toLocalTime(), genre, reproductions);
                songs.add(song);
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return songs;
    }

}
