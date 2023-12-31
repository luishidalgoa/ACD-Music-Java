package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iAlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class AlbumDAO implements iAlbumDAO {
    private static final String INSERT_ALBUM = "INSERT INTO album (id_artist, name, date, picture, reproductions) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALBUM_BY_NAME = "SELECT id_album, id_artist, name, date, picture, reproductions FROM album WHERE name LIKE CONCAT('%',?,'%') LIMIT 3";
    private static final String SELECT_ALL_ALBUMS_BY_ARTIST = "SELECT id_album, id_artist, name, date, picture, reproductions FROM album WHERE id_artist = ?";
    private static final String SELECT_MORE_RECENT_ALBUMS = "SELECT id_album, id_artist, name, date, picture, reproductions FROM album ORDER BY date DESC LIMIT 3";
    private static final String UPDATE_ALBUM_NAME = "UPDATE album SET name = ? WHERE id_album = ?";
    private static final String SELECT_ALBUM_BY_ID = "SELECT id_album, id_artist, name, date, picture, reproductions FROM album WHERE id_album = ?";
    private static final String SELECT_ALBUM_BY_ID_SONG = "SELECT a.id_album, a.id_artist, a.name, a.date, a.picture, a.reproductions FROM album a JOIN song ON a.id_album = song.id_album WHERE song.id_song = ?;";


    private static AlbumDAO instance;

    private AlbumDAO() {
    }

    public static AlbumDAO getInstance() {
        if (instance == null) {
            instance = new AlbumDAO();
        }
        return instance;
    }

    @Override
    public boolean addAlbum(Album album) {
        Connection conn = ConnectionData.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_ALBUM);
            preparedStatement.setInt(1, album.getIdArtist());
            preparedStatement.setString(2, album.getName());
            preparedStatement.setString(3, LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
            preparedStatement.setString(4, album.getPicture());
            preparedStatement.setInt(5, 0);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionData.close();
        }
    }


    @Override
    public Set<Album> searchAlbumByName(String filterWord) {
        Connection conn = ConnectionData.getConnection();
        Set<Album> albums = new HashSet<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALBUM_BY_NAME);
            preparedStatement.setString(1, "%" + filterWord + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_album = resultSet.getInt("id_album");
                int id_artist = resultSet.getInt("id_artist");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                String picture = resultSet.getString("picture");
                int reproductions = resultSet.getInt("reproductions");

                Album album = new Album(id_album, id_artist, name, date, picture, reproductions);
                albums.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionData.close();
        }

        return albums;
    }


    @Override
    public Set<Album> searchAllAlbumsByArtist(ArtistDTO artist) {
        Connection conn=ConnectionData.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(SELECT_ALL_ALBUMS_BY_ARTIST);
            ps.setInt(1,artist.getId_artist());
            ResultSet rs = ps.executeQuery();
            Set<Album> albums = new HashSet<>();
            while (rs.next()){
                albums.add(AlbumDAO.getInstance().getAlbumById(rs.getInt("id_album")));
            }
            return albums;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Album> searchMoreRecent() {
        Connection conn = ConnectionData.getConnection();
        Set<Album> albums = new HashSet<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_MORE_RECENT_ALBUMS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_album = resultSet.getInt("id_album");
                int id_artist = resultSet.getInt("id_artist");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                String picture = resultSet.getString("picture");
                int reproductions = resultSet.getInt("reproductions");

                Album album = new Album(id_album, id_artist, name, date, picture, reproductions);
                albums.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionData.close();
        }

        return albums;
    }

    @Override
    public Album updateAlbum(int id, String newName) {
        Connection conn = ConnectionData.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_ALBUM_NAME);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Si la actualización fue exitosa, devuelve el álbum actualizado
                return getAlbumById(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionData.close();
        }

        return null;
    }


    public Album getAlbumById(int id) {
        Connection conn = ConnectionData.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALBUM_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_album = resultSet.getInt("id_album");
                int id_artist = resultSet.getInt("id_artist");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                String picture = resultSet.getString("picture");
                int reproductions = resultSet.getInt("reproductions");

                return new Album(id_album, id_artist, name, date, picture, reproductions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionData.close();
        }

        return null;
    }

    /**
     * Buscaremos un album a partir del id de una de sus canciones
     * @param idSong id de la cancion
     * @return Album que contiene la cancion
     */
    public Album searchAlbumByIdSong(int idSong){
        Connection conn= ConnectionData.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(SELECT_ALBUM_BY_ID_SONG);
            ps.setInt(1,idSong);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){

                return getAlbumById(rs.getInt("id_album"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
