package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ReproductionListDAO implements iReproductionListDAO {
    private static ReproductionListDAO instance;
    String addQuery = "INSERT INTO rythm.reproductionlist (name,description,id_user) VALUES (?,?,?);";
    String removeQuery = "DELETE FROM rythm.reproductionlist WHERE id_reproductionList=?";
    String searchByIdQuery = "SELECT r.id_reproductionList, r.id_user, r.name, r.description FROM rythm.reproductionlist r WHERE r.id_reproductionList=?";
    String SubcribeQuery = "INSERT INTO rythm.usersubscriptionlist (id_user, id_reproductionList) VALUES (?,?);";
    String getUserSubcriptionQuery = "SELECT id_user,id_reproductionList FROM rythm.usersubscriptionlist WHERE id_user=?;";
    String getSubcribeToListByUserQuery = "SELECT id_user,id_reproductionList FROM rythm.usersubscriptionlist WHERE id_user=? AND id_reproductionList=?;";
    String addSongQuery = "INSERT INTO rythm.reproductionsonglist (id_song, id_reproductionList) VALUES (?,?);";
    String searchSonsByIdQuery = "select s.id_song, s.id_album, s.name, s.url, s.lenght, s.genre, s.reproductions from reproductionsonglist rsl JOIN song s on rsl.id_song = s.id_song where rsl.id_reproductionList = ?;";
    String unsubscribeQuery = "DELETE FROM rythm.usersubscriptionlist WHERE id_user=? AND id_reproductionList=?;";
    String searchAllCommentsQuery = "SELECT c.id_comment FROM reproductionlist JOIN commentlistusers c on reproductionlist.id_reproductionList = c.id_reproductionList WHERE c.id_reproductionList LIKE ?";
    String removeSongQuery = "DELETE FROM rythm.reproductionsonglist WHERE id_song=? AND id_reproductionList=?;";
    String searchSongOnList = "SELECT id_song,id_reproductionList FROM rythm.reproductionsonglist WHERE id_song=? AND id_reproductionList=?;";
    //Hazme un Like que devuelva varias Listas con un nombre parecido
    String searchByNameQuery = "SELECT id_reproductionList FROM rythm.reproductionlist WHERE name LIKE CONCAT('%',?,'%') LIMIT 6";
    private ReproductionListDAO() {
    }

    @Override
    public ReproductionList add(ReproductionList reproductionList) {
        Connection conn = ConnectionData.getConnection();
        if (conn == null) return null;
        // statement.RETURN_GENERATED_KEYS es usado para obtener el id del objeto que se acaba de insertar
        try (PreparedStatement ps = conn.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, reproductionList.getName());
            ps.setString(2, reproductionList.getDescription());
            ps.setInt(3, reproductionList.getOwner().getId());
            if (ps.executeUpdate() == 1) {
                // Obtenemos el id autoincremental del objeto que acabamos de insertar
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        //extraemos el id autoincremental
                        int id = rs.getInt(1);
                        // Devolvemos el objeto recien insertado con el id correspondiente
                        return searchReproductionListById(id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionData.close();
        }
        return null;
    }

    @Override
    public boolean removeReproductionList(int id) {
        // Comprobamos que el usuario que quiere borrar la lista es el dueño de la misma
        if (!searchReproductionListById(id).getOwner().equals(Login.getInstance().getCurrentUser())) return false;

        Connection conn = ConnectionData.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(removeQuery)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 1) {
                ConnectionData.close();
                return searchReproductionListById(id) != null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionData.close();
        }
        return false;
    }

    @Override
    public ReproductionList searchReproductionListById(int id) {
        ReproductionList result = new ReproductionList();

        Connection conn = ConnectionData.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(searchByIdQuery)) {
            ps.setInt(1, id);

            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        result.setId(rs.getInt("id_reproductionList"));
                        result.setName(rs.getString("name"));
                        result.setDescription(rs.getString("description"));
                        result.setOwner(UserDAO.getInstance().searchById(rs.getInt("id_user")));
                        // Obtenemos las canciones de la lista
                        result.setSongs(searchSongsById(id));
                    }
                }
            }
            if (result != null) return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean Subcribe(int idUser, int idList) {
        Connection conn = ConnectionData.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(SubcribeQuery)) {
            ps.setInt(1, idUser);
            ps.setInt(2, idList);
            if (ps.executeUpdate() == 1) {
                ConnectionData.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionData.close();
        }

        return false;
    }

    @Override
    public Set<ReproductionList> getUserSubcriptions(int idUser) {
        Connection conn = ConnectionData.getConnection();
        if(conn!=null){
            Set<ReproductionList> result = new HashSet<>();
            try (PreparedStatement ps = conn.prepareStatement(getUserSubcriptionQuery)) {
                ps.setInt(1, idUser);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    result.add(searchReproductionListById(rs.getInt("id_reproductionList")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ConnectionData.close();
            return !result.isEmpty() ? result : null;
        }
        return null;
    }

    @Override
    public boolean getSubcribeToListByUser(int idUser, int idList) {
        Connection conn = ConnectionData.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(getSubcribeToListByUserQuery)) {
            ps.setInt(1, idUser);
            ps.setInt(2, idList);
            // Si la consulta devuelve un resultado, es que el usuario esta subscrito a la lista
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        ConnectionData.close();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConnectionData.close();
        return false;
    }

    @Override
    public boolean unSubcribe(int idUser, ReproductionList reproductionList) {
        Connection conn = ConnectionData.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(unsubscribeQuery)) {
            ps.setInt(1, idUser);
            ps.setInt(2, reproductionList.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionData.close();
        }
        return false;
    }

    @Override
    public Set<Comment> getAllComments(int idList) {
        try {
            if (searchReproductionListById(idList) == null)
                return null;
            Connection conn = ConnectionData.getConnection();
            PreparedStatement ps = conn.prepareStatement(searchAllCommentsQuery);
            ps.setInt(1, idList);
            ResultSet rs = ps.executeQuery();
            Set<Comment> result = new HashSet<>();
            while (rs.next()) {
                result.add(CommentDAO.getInstance().searchComment(rs.getInt("id_comment")));
            }
            if (!result.isEmpty())
                return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionData.close();
        }
        return null;
    }

    @Override
    public boolean addSong(int idSong, int idReproductionList) {
        Connection conn = ConnectionData.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(addSongQuery)) {
            ps.setInt(1, idSong);
            ps.setInt(2, idReproductionList);
            if (ps.executeUpdate() == 1) {
                ConnectionData.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionData.close();
            return existSongOnList(idReproductionList, idSong);
        }
    }

    @Override
    public Set<Song> searchSongsById(int idReproductionList) {
        try (PreparedStatement ps = ConnectionData.getConnection().prepareStatement(searchSonsByIdQuery)) {
            ps.setInt(1, idReproductionList);
            if (ps.execute()) {
                Set<Song>result=new HashSet<>();
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Song aux;
                        if((aux=SongDAO.getInstance().searchById(rs.getInt("id_song")))!=null){
                            result.add(aux);
                        }
                    }
                }
                if (!result.isEmpty())
                    return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean existSongOnList(int idList, int idSong) {
        Connection conn = ConnectionData.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(searchSongOnList)) {
            ps.setInt(1, idSong);
            ps.setInt(2, idList);
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        ConnectionData.close();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean removeSong(int idSong, int idReproductionList, UserDTO user) {
        if (!user.equals(Login.getInstance().getCurrentUser())) return false;
        Connection conn = ConnectionData.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(removeSongQuery);
            ps.setInt(1, idSong);
            ps.setInt(2, idReproductionList);
            if (ps.executeUpdate() == 1) {
                return !existSongOnList(idReproductionList, idSong);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionData.close();
        }
        return false;
    }

    @Override
    public Set<ReproductionList> searchByName(String filter){
        Connection conn = ConnectionData.getConnection();
        Set<ReproductionList> result=null;
        try {
            PreparedStatement ps = conn.prepareStatement(searchByNameQuery);
            ps.setString(1, filter);
            if(ps.execute()){
                try (ResultSet rs = ps.getResultSet()) {
                    result = new HashSet<>();
                    while (rs.next()) {
                        result.add(searchReproductionListById(rs.getInt("id_reproductionList")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionData.close();
        }
        return result;
    }
    public int getAllSubcriptions(int idList){
        Connection conn = ConnectionData.getConnection();
        int result=0;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(id_user) FROM rythm.usersubscriptionlist WHERE id_reproductionList=?;");
            ps.setInt(1, idList);
            if(ps.execute()){
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        result=rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionData.close();
        }
        return result;
    }
    public static ReproductionListDAO getInstance() {
        if (instance == null) {
            instance = new ReproductionListDAO();
        }
        return instance;
    }
}
