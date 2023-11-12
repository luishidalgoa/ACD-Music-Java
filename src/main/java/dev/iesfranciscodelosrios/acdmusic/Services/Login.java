package dev.iesfranciscodelosrios.acdmusic.Services;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iLogin;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.UserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login implements iLogin {

    private static Login instance;

    private Login() {
    }

    private static UserDTO currentUser;

    private UserDAO udao= new UserDAO();

    /**
     * Metodo para autentificar que el usuario se loguea con nickname y password correctos, además
     * seteará un currentUser en caso de ser satisfactoria la autentificación.
     * @param user objeto usuario con los datos del usuario y la contraseña sin encriptar
     * @return UserDTO en caso de que la autenticación sea satisfactoria y null en cualquier otro caso
     */
    @Override
    public UserDTO Auth(User user) {
        if (user == null) {
            return null;
        } else {
            User BDUser = udao.searchByNicknameLogin(user.getNickName());
            if(BDUser!=null && BDUser.getPassword().equals(encryptPassword(user.getPassword()))){
                UserDTO result = UserDAO.getInstance().searchById(BDUser.getId());
                setCurrentUser(result);
                return result;
            }
        }
        return null;
    }

    /**
     * Metodo para encriptar la contraseña usando SHA-256
     * @param password contraseña sin encriptar
     * @return contraseña encriptada en hexademila
     */
    @Override
    public String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for(byte b : passwordBytes){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
                return hexString.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Metodo para registrar un usuario
     * @param user objeto usuario con los datos del usuario y la contraseña sin encriptar
     * @return User provisto en formato DTO o null en caso de que user este vacio
     */
    @Override
    public UserDTO Register(User user) {
        if (user == null) {
            return null;
        } else {
            user.setPassword(encryptPassword(user.getPassword()));
            udao.addUser(user);
            UserDTO result = udao.setUserToUserDTO(user);
            setCurrentUser(result);
            return result;
        }
    }

    /**
     * Metodo para cerrar sesión
     * @return true en caso de que current user se establezca a null, false en cualquier otro caso
     */
    @Override
    public boolean Logout() {
        UserDTO current = getCurrentUser();
        current = null;
        setCurrentUser(current);
        if (current == null) return true;
        return false;
    }


    /**
     * Metodo para obtener el usuario actual
     * @return UserDTO del user activo en la app
     */
    public UserDTO getCurrentUser() {
        return currentUser;
    }

    /**
     * Metodo para setear el usuario actual en la app
     * @param currentUser UserDTO del usaurio actual en la app
     */
    public void setCurrentUser(UserDTO currentUser) {
        this.currentUser = currentUser;
    }

    public static Login getInstance() {
        if (instance == null) instance = new Login() {
        };
        return instance;
    }

}