package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iUserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class UserDAO implements iUserDAO {
    String searchById = "SELECT * FROM user WHERE id_user = ?";
    private static UserDAO instance;
    private UserDAO() {
    }
    @Override
    public UserDTO addUser(User user) {
        return null;
    }

    @Override
    public boolean delete(int idUser) {
        return false;
    }

    @Override
    public UserDTO searchByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO searchByNickname(String nickname) {
        return null;
    }

    @Override
    public Set<UserDTO> searchByName(String filterWord) {
        return null;
    }
    /**
     * se buscara un usuario en base a su id.
     * @param idUser id del usuario a buscar
     * @return devolvera un objeto UserDTO, si la base de datos no se encuentra se devolvera null
     */
    @Override
    public UserDTO searchById(int idUser) {
        Connection conn= ConnectionData.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(searchById);
            ps.setInt(1, idUser);
            ResultSet rs=ps.executeQuery();
            UserDTO result = new UserDTO();
            if(rs.next()){
                result.setId(rs.getInt("id_user"));
                result.setName(rs.getString("name"));
                result.setLastName(rs.getString("lastname"));
                result.setEmail(rs.getString("email"));
                result.setNickName(rs.getString("nickname"));
                result.setPicture(rs.getString("picture"));
            }
            if(result!=null)
                return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }
}
