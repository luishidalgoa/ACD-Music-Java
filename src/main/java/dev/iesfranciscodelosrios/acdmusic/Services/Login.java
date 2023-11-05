package dev.iesfranciscodelosrios.acdmusic.Services;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iLogin;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

public class Login implements iLogin {
    private static Login instance;
    private static UserDTO currentUser;
    private Login() {
    }
    @Override
    public UserDTO Auth(User user) {
        return null;
    }

    @Override
    public String encryptPassword(String password) {
        return null;
    }

    @Override
    public UserDTO Register(User user) {
        return null;
    }

    @Override
    public boolean Logout() {
        return false;
    }
    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }
    public static UserDTO getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(UserDTO currentUser) {
        Login.currentUser = currentUser;
    }
}
