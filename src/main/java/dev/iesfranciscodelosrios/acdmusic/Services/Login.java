package dev.iesfranciscodelosrios.acdmusic.Services;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iLogin;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

public class Login implements iLogin {

    private static Login instance;

    private Login() {
    }

    UserDTO currentUser;

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
