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
    private static final String SEARCHBYNAME ="SELECT id_user,name,email,picture,password,nickname,lastname FROM user WHERE name LIKE CONCAT('%','?','%') LIMIT 3";

    /**
     * Metodo para setear un user con el que vamos a trabajar
     * @param id_user id del user
     * @param name nombre del user
     * @param email email del user
     * @param picture url de la imagen de perfil del user
     * @param password contraseña del user
     * @param lastname apellidos del user
     */
    public UserDAO(int id_user, String name, String email, String picture, String password, String lastname) {
        super(id_user,name,email,picture,password,lastname);
    }

    /**
     * Metodo para obtener un user a través de su id
     * @param id_user Id del user a buscar
     */
    public UserDAO(int id_user){
        getById(id_user);
    }

    /**
     * Metodo para setear un user con el que vamos a trabajar y conocemos previamente
     * @param u Usuario que se va a tratar en el DAO
     */
    public UserDAO(User u){
        super(u.getId(), u.getName(), u.getEmail(), u.getPicture(), u.getPassword(), u.getNickName(), u.getLastName());
    }

    /**
     * Metodo para insertar un user en la base de datos
     * @return true si la consulta SQL a tenido exito false si no ha tenido exito
     */
    @Override
    public boolean addUser() {
        if(getId()!=-1){
            return update();
        } else {
            Connection conn = ConnectionData.getConnection();
            if(conn==null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, getName());
                ps.setString(2, getEmail());
                ps.setString(3, getPicture());
                ps.setString(4, getPassword());
                ps.setString(5, getNickName());
                ps.setString(6, getLastName());

                if(ps.executeUpdate()==1){
                    try(ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            setId(rs.getInt(1));
                            return true; //MODIFICAR PARA RELLENAR EL DTO
                        } else {
                            return false;
                        }
                    }
                }
                setId(-1);
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Metodo para actualizar un user de la base de datos
     * @return true si la consulta SQL a tenido exito false si no ha tenido exito
     */
    private boolean update() {
        if(getId() ==-1) return false;
        Connection conn = ConnectionData.getConnection();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){
            ps.setString(1,getName());
            ps.setString(2,getEmail());
            ps.setString(3,getPicture());
            ps.setString(4,getPassword());
            ps.setString(5, getNickName());
            ps.setString(6, getLastName());
            if(ps.executeUpdate()==1)
                return true;
            setId(-1);
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo para obetener un usuario del que ya conocemos sus id
     * @param id del usuari que queremos obtener
     * @return si la consulta SQL a tenido exito false si no ha tenido exito
     */
    public boolean getById(int id){
        Connection conn = ConnectionData.getConnection();
        if (conn==null) return false;
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
            ps.setInt(1,id);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setName(rs.getString("name"));
                        setEmail(rs.getString("email"));
                        setPicture(rs.getString("picture"));
                        setPassword(rs.getString("password"));
                        setNickName(rs.getString("nickname"));
                        setLastName(rs.getString("lastname"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @param idUser id del usuario a eliminar
     * @return
     */
    @Override
    public boolean delete(int idUser) {
        if(getId()==-1) return false;

        Connection conn = ConnectionData.getConnection();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETE)){
            ps.setInt(1,getId());
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
                            searchedUser.setId((rs.getInt("id")));
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
                            searchedUser.setId((rs.getInt("id")));
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
                            su.setId((rs.getInt("id")));
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
     * Este metodo buscca un objeto user dentro de la base de datos el cual lo devolvera si existe
     * puede devolver null en caso de que nickname sea null, la conexion falle o la base de datos
     * devuelva cualquier tipo de error(Como no encontrado).
     * @param idUser nickname del usuario a buscar
     * @return un objeto UserDTO si ha sido satisfactorio o null sino
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
                            searchedUser.setId((rs.getInt("id")));
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
}
