package dev.iesfranciscodelosrios.acdmusic.Services;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iLogin;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.UserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login implements iLogin {

    private static Login instance;

    private Login() {
    }

    UserDTO currentUser;

    private UserDAO udao;

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
            if(BDUser.getPassword() == user.getPassword()){
                UserDTO result = udao.setUserToUserDTO(BDUser);
                setCurrentUser(result);
                return result;
            }
        }
        return null;
    }

    @Override
    public String encryptPassword(String password) {
        return null;
    }

    @Override
    public UserDTO Register(User user) {
        if (user == null) {
            return null;
        } else {

        }
        return null;
    }

    /**
     * Metodo para cerrar sesión
     * @return true en caso de que current user se establezca a null, false en cualquier otro caso
     */
    @Override
    public boolean Logout() {
        UserDTO current getCurrentUser();
        current = null;
        setCurrentUser(current);
        if (current == null) return true;
        return false;
    }


    public UserDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDTO currentUser) {
        this.currentUser = currentUser;
    }

    public static Login getInstance() {
        if (instance == null) instance = new Login() {
        };
        return instance;
    }

}
