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
    String searchSongByIdQuery = "SELECT id_song,id_reproductionList FROM rythm.reproductionsonglist WHERE id_song=? AND id_reproductionList=?;";
    String unsubscribeQuery = "DELETE FROM rythm.usersubscriptionlist WHERE id_user=? AND id_reproductionList=?;";

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
                        ConnectionData.close();
                        // Devolvemos el objeto recien insertado con el id correspondiente
                        return searchReproductionListById(id);
                    }
                }
            }
            ConnectionData.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
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
                        result.setOwner(Login.getInstance().getCurrentUser());//UserDAO.getInstance().searchById(rs.getInt("id_user"))
                    }
                }
            } else {
                ConnectionData.close();
                return null;
            }
            ConnectionData.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReproductionList searchReproductionListById(int id, boolean isConnClosed) {
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
                        result.setOwner(Login.getInstance().getCurrentUser());//UserDAO.getInstance().searchById(rs.getInt("id_user"))
                    }
                }
            }
            if (isConnClosed)
                ConnectionData.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean Subcribe(int idUser, int idList) {
        Connection conn = ConnectionData.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(SubcribeQuery)) {
            ps.setInt(1, idUser);//Login.getInstance().getCurrentUser().getId()
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
        Set<ReproductionList> result = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(getUserSubcriptionQuery)) {
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(searchReproductionListById(rs.getInt("id_reproductionList"), false));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionData.close();
        return !result.isEmpty() ? result : null;
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
            return searchSongById(idSong, idReproductionList) != null;
        }

    }

    @Override
    public Song searchSongById(int idSong, int idReproductionList) {
        ConnectionData.getConnection();
        try (PreparedStatement ps = ConnectionData.getConnection().prepareStatement(searchSongByIdQuery)) {
            ps.setInt(1, idSong);
            ps.setInt(2, idReproductionList);
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        ConnectionData.close();
                        return null;//SongDAO.getInstance().searchSongById(rs.getInt("id_song"))
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean removeSong(int idSong, int idReproductionList, UserDTO user) {
        return false;
    }
    public static ReproductionListDAO getInstance() {
        if (instance == null) {
            instance = new ReproductionListDAO();
        }
        return instance;
    }
}