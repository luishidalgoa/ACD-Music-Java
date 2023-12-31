package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Artist;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ArtistDAO extends Artist implements iArtistDAO {
    private static final String INSERT ="INSERT INTO artist (nacionality, id_user) VALUES (?, ?)";
    private static final String DELETE ="DELETE FROM artist WHERE id_artist=?";
    private static final String SELECTBYID ="SELECT a.id_artist, a.nacionality, a.id_user, u.name, u.email, u.picture, u.nickname, u.lastname FROM artist AS a JOIN user AS u ON a.id_user=u.id_user WHERE a.id_artist = ?";
    private static final String SEARCHBYNAME ="SELECT a.id_artist, a.nacionality, a.id_user, u.name, u.email, u.picture, u.nickname, u.lastname FROM artist AS a JOIN user AS u ON a.id_user=u.id_user WHERE name LIKE CONCAT('%',?,'%') LIMIT 3";
    private static final String SEARCHBYIDSONG= "select a.id_artist, a.nacionality, a.id_user, u.name, u.email, u.picture, u.nickname, u.lastname from artist a JOIN user u ON a.id_user = u.id_user JOIN album al on a.id_artist = al.id_artist JOIN song s on al.id_album = s.id_album where s.id_song = ?;";
    private static final String SEARCHARTISTBYIDALBUM="select a.id_artist, a.nacionality, a.id_user, u.name, u.email, u.picture, u.password, u.nickname, u.lastname From artist a JOIN user u ON a.id_user = u.id_user\n" +
            "JOIN album a2 on a.id_artist = a2.id_artist where a2.id_album = ?;";
    private static final String SELECTBYIDUser ="SELECT a.id_artist, a.nacionality, a.id_user, u.name, u.email, u.picture, u.nickname, u.lastname FROM artist AS a JOIN user AS u ON a.id_user=u.id_user WHERE a.id_user = ?";

    private UserDAO udao= new UserDAO();

    private static ArtistDAO instance;
    public ArtistDAO() {}

    public static ArtistDAO getInstance() {
        if (instance == null) {
            instance = new ArtistDAO();
        }
        return instance;
    }

    /**
     * Metodo para insertar un artist en la base de datos
     * @param artist objeto artista con los datos propios del artista y el puntero al usuario
     * @return ArtistDTO si la constulta SQL ha tenido exito, false si no ha tenido exito
     */
    @Override
    public ArtistDTO addArtist(Artist artist) {
        Connection conn= ConnectionData.getConnection();
        if(conn==null || artist==null) return null;
        ArtistDTO result = new ArtistDTO(artist);

        try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, artist.getNacionality());
            ps.setInt(2, artist.getId_user());

            if (ps.executeUpdate()==1){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()){
                        setId_artist(rs.getInt(1));
                        return result;
                    } else {
                        return null;
                    }
                }
            }
           setId_artist(-1);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo que recibe un artista y lo elimina de la base de datos
     * @param artist astista a eliminar
     * @return True en caso de que se haya borrado con exito o falso
     * en caso de que la conexion falle, artist sea null o la consulta sql falle
     */
    @Override
    public boolean removeArtist(Artist artist) {
        Connection conn= ConnectionData.getConnection();
        if(conn==null || artist == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETE)){
            ps.setInt(1,artist.getId());
            if(ps.executeUpdate()==1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Este metodo busca un objeto artist dentro de la base de datos
     * @param idArtist id del artista a buscar
     * @return Deveuvle un objeto ArtistDTO si ha sido satisfactorio y null en caso de que IdUser sea null,
     * la conexion falle o la base de datos devuelva cualquier tipo de error(Como no encontrado).
     */
    @Override
    public ArtistDTO searchArtistByIdArtist(int idArtist) {
        Connection conn= ConnectionData.getConnection();
        if(conn==null || idArtist==-1) {
            return null;
        } else {
            ArtistDTO searchedArtist = new ArtistDTO();
            try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
                ps.setInt(1,idArtist);
                if(ps.execute()){
                    try(ResultSet rs = ps.getResultSet()){
                        if (rs.next()){
                            searchedArtist.setId_artist((rs.getInt("id_artist")));
                            searchedArtist.setNacionality((rs.getString("nacionality")));
                            searchedArtist.setId_user((rs.getInt("id_user")));
                            searchedArtist.setId((rs.getInt("id_user")));
                            searchedArtist.setName((rs.getString("name")));
                            searchedArtist.setLastName((rs.getString("lastname")));
                            searchedArtist.setNickName(rs.getString("nickname"));
                            searchedArtist.setPicture((rs.getString("picture")));
                            searchedArtist.setEmail((rs.getString("email")));
                            return searchedArtist;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public ArtistDTO searchArtistByIdUser(int idUser) {
        Connection conn= ConnectionData.getConnection();
        if(conn==null || idUser==-1) {
            return null;
        } else {
            ArtistDTO searchedArtist = new ArtistDTO();
            try(PreparedStatement ps = conn.prepareStatement(SELECTBYIDUser)){
                ps.setInt(1,idUser);
                if(ps.execute()){
                    try(ResultSet rs = ps.getResultSet()){
                        if (rs.next()){
                            searchedArtist.setId_artist((rs.getInt("id_artist")));
                            searchedArtist.setNacionality((rs.getString("nacionality")));
                            searchedArtist.setId_user((rs.getInt("id_user")));
                            searchedArtist.setId((rs.getInt("id_user")));
                            searchedArtist.setName((rs.getString("name")));
                            searchedArtist.setLastName((rs.getString("lastname")));
                            searchedArtist.setNickName(rs.getString("nickname"));
                            searchedArtist.setPicture((rs.getString("picture")));
                            searchedArtist.setEmail((rs.getString("email")));
                            return searchedArtist;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Set<ArtistDTO> searchArtistByName(String filterWord) {
        Connection conn= ConnectionData.getConnection();
        if (conn==null || filterWord==null){
            return null;
        } else {
            Set<ArtistDTO> searchedArtists = new HashSet<ArtistDTO>();
            try (PreparedStatement ps = conn.prepareStatement(SEARCHBYNAME)) {
                ps.setString(1, filterWord);
                if (ps.execute()) {
                    try(ResultSet rs = ps.executeQuery()){
                        while (rs.next()) {
                            ArtistDTO sa= new ArtistDTO();
                            sa.setId((rs.getInt("id_user")));
                            sa.setName((rs.getString("name")));
                            sa.setLastName((rs.getString("lastName")));
                            sa.setNickName((rs.getString("nickName")));
                            sa.setPicture((rs.getString("picture")));
                            sa.setEmail((rs.getString("email")));
                            sa.setNacionality((rs.getString("nacionality")));
                            sa.setId_artist((rs.getInt("id_artist")));
                            searchedArtists.add(sa);
                        }
                    }
                    return searchedArtists;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    public ArtistDTO searchArtistByIdSong(int idSong){
        Connection conn= ConnectionData.getConnection();
        try {
            ArtistDTO searchedArtist = new ArtistDTO();
            PreparedStatement ps= conn.prepareStatement(SEARCHBYIDSONG);
            ps.setInt(1,idSong);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if (rs.next()){
                        return this.searchArtistByIdArtist(rs.getInt("id_artist"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public ArtistDTO searchArtistByIdAlbum(int idAlbum){
        Connection conn = ConnectionData.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(SEARCHARTISTBYIDALBUM);
            ps.setInt(1,idAlbum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return this.searchArtistByIdArtist(rs.getInt("id_artist"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
