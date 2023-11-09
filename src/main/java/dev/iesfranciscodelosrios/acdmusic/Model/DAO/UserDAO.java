package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iUserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDAO extends User implements iUserDAO  {
    private static final String INSERT ="INSERT INTO user (name,email,picture,password,nickname,lastname) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE ="UPDATE user SET name=?, email=?, picture=?, password=?, nickname=?, lastname=? WHERE id_user=?";
    private static final String DELETE ="DELETE FROM user WHERE id_user=?";
    private static final String SELECTBYID ="SELECT id_user,name,email,picture,password,nickname,lastname FROM user WHERE id_user=?";
    private static final String SELECTBYEMAIL ="SELECT id_user,name,email,picture,password,nickname,lastname FROM user WHERE email=?";
    private static final String SELECTBYNICKNAME ="SELECT id_user,name,email,picture,password,nickname,lastname FROM user WHERE nickname=?";
    private static final String SEARCHBYNAME ="SELECT id_user,name,email,picture,password,nickname,lastname FROM user WHERE name LIKE CONCAT('%',?,'%') LIMIT 3";

    private static UserDAO instance;
    public UserDAO() {}

    /**
     * Metodo que obtiene todos los datos de un objeto User y devuelve un objeto UserDTO relleno con
     * los datos del objeto User proporcionado
     * @param user objeto User que se quiere pasar a UserDTO
     * @return
     */
    private UserDTO setUserToUserDTO(User user){
        UserDTO result = new UserDTO();
        result.setId(user.getId());
        result.setName(user.getName());
        result.setEmail(user.getName());
        result.setPicture(user.getPicture());
        result.setNickName(user.getNickName());
        result.setLastName(user.getLastName());
        return result;
    }

    /**
     * Metodo para insertar un user en la base de datos
     * @param user Objeto User a instertar
     * @return UserDTO si la consulta SQL ha tenido exito, false si no ha tenido exito
     */
    @Override
    public UserDTO addUser(User user) {
        Connection conn = ConnectionData.getConnection();
        if(conn==null) return null;

        try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPicture());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getNickName());
            ps.setString(6, user.getLastName());

            if(ps.executeUpdate()==1){
                try(ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        setId(rs.getInt(1));
                        return setUserToUserDTO(user);
                    } else {
                        return null;
                    }
                }
            }
            setId(-1);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo para actualizar un user de la base de datos
     * @param user Usuario del que se desea hacer un update
     * @return true si la consulta SQL a tenido exito false si no ha tenido exito
     */
    private boolean update(User user) {
        Connection conn = ConnectionData.getConnection();
        if(conn==null || user==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPicture());
            ps.setString(4,user.getPassword());
            ps.setString(5,user.getNickName());
            ps.setString(6,user.getLastName());
            ps.setInt(7,user.getId());
            if(ps.executeUpdate()==1)
                return false;
            user.setId(-1);
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que recibe un usuario y lo elimina de la base de datos
     * @param user usuario a eliminar
     * @return True en caso de que se haya borrado con exito o falso
     * en caso de que la conexión falle, user sea null o la consulta sql falle.
     */
    @Override
    public boolean delete(User user) {
        Connection conn = ConnectionData.getConnection();
        if(conn==null || user==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETE)){
            ps.setInt(1,user.getId());
            if(ps.executeUpdate()==1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Este metodo buscca un objeto user dentro de la base de datos el cual lo devolvera si existe
     * puede devolver null en caso de que email sea null, la conexion falle o la base de datos
     * devuelva cualquier tipo de error(Como no encontrado).
     * @param email email del usuario a buscar
     * @return un objeto UserDTO si ha sido satisfactorio o null sino
     */
    @Override
    public UserDTO searchByEmail(String email) {
        Connection conn = ConnectionData.getConnection();
        if (conn==null || email==null) {
            return null;
        } else{
            UserDTO searchedUser = new UserDTO();
            searchedUser.setEmail(email);

            try(PreparedStatement ps = conn.prepareStatement(SELECTBYEMAIL)){
                ps.setString(1,email);
                if(ps.execute()){
                    try(ResultSet rs = ps.getResultSet()){
                        if(rs.next()){
                            searchedUser.setId((rs.getInt("id_user")));
                            searchedUser.setName((rs.getString("name")));
                            searchedUser.setLastName((rs.getString("lastName")));
                            searchedUser.setNickName((rs.getString("nickName")));
                            searchedUser.setPicture((rs.getString("picture")));
                            searchedUser.setEmail((rs.getString("email")));
                            return searchedUser;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * Este metodo buscca un objeto user dentro de la base de datos el cual lo devolvera si existe
     * puede devolver null en caso de que nickname sea null, la conexion falle o la base de datos
     * devuelva cualquier tipo de error(Como no encontrado).
     * @param nickname nickname del usuario a buscar
     * @return un objeto UserDTO si ha sido satisfactorio o null sino
     */
    @Override
    public UserDTO searchByNickname(String nickname) {
        Connection conn = ConnectionData.getConnection();
        if (conn==null || nickname==null) {
            return null;
        } else{
            UserDTO searchedUser = new UserDTO();
            try(PreparedStatement ps = conn.prepareStatement(SELECTBYNICKNAME)){
                ps.setString(1,nickname);
                if(ps.execute()){
                    try(ResultSet rs = ps.getResultSet()){
                        if(rs.next()){
                            searchedUser.setId((rs.getInt("id_user")));
                            searchedUser.setName((rs.getString("name")));
                            searchedUser.setLastName((rs.getString("lastName")));
                            searchedUser.setNickName((rs.getString("nickName")));
                            searchedUser.setPicture((rs.getString("picture")));
                            searchedUser.setEmail((rs.getString("email")));
                            return searchedUser;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * Metodo que busca coincidencias con un máximo de tres usuarios y los devuelve.
     * Por defecto y en caso de que la conexion con la base de datos falle o la constula
     * no sea existosa devolvera null
     * @param filterWord palabra clave del nombre del usuario que se quiere buscar
     * @return Set de tres usuarios
     */
    @Override
    public Set<UserDTO> searchByName(String filterWord) {
        Connection conn = ConnectionData.getConnection();
        if (conn==null || filterWord==null) {
            return null;
        } else{
            Set<UserDTO> searchedUSers = new HashSet<UserDTO>();
            try (PreparedStatement ps = conn.prepareStatement(SEARCHBYNAME)){
                ps.setString(1, filterWord);
                if (ps.execute()) {
                    try(ResultSet rs = ps.executeQuery()){
                        while (rs.next()) {
                            UserDTO su = new UserDTO();
                            su.setId((rs.getInt("id_user")));
                            su.setName((rs.getString("name")));
                            su.setLastName((rs.getString("lastName")));
                            su.setNickName((rs.getString("nickName")));
                            su.setPicture((rs.getString("picture")));
                            su.setEmail((rs.getString("email")));
                            searchedUSers.add(su);
                        }
                    }
                    return searchedUSers;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * Este metodo busca un objeto user dentro de la base de datos
     * @param idUser nickname del usuario a buscar
     * @return Objeto UserDTO si ha sido satisfactorio y null en caso de que idUser sea null,
     * la conexion falle o la base de datos devuelva cualquier tipo de error(Como no encontrado).
     */
    @Override
    public UserDTO searchById(int idUser) {
        Connection conn = ConnectionData.getConnection();
        if (conn==null || idUser==-1) {
            return null;
        } else{
            UserDTO searchedUser = new UserDTO();
            try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
                ps.setInt(1,idUser);
                if(ps.execute()){
                    try(ResultSet rs = ps.getResultSet()){
                        if(rs.next()){
                            searchedUser.setId((rs.getInt("id_user")));
                            searchedUser.setName((rs.getString("name")));
                            searchedUser.setLastName((rs.getString("lastName")));
                            searchedUser.setNickName((rs.getString("nickName")));
                            searchedUser.setPicture((rs.getString("picture")));
                            searchedUser.setEmail((rs.getString("email")));
                            return searchedUser;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
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
