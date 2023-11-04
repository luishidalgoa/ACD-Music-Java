package dev.iesfranciscodelosrios.acdmusic.Service;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

public class Login {
    private static Login instance;
    private Login() {}
    UserDTO currentUser;

    public UserDTO Auth(){
        return null;
    }

    public String encryptPassword(String password){
        return null;
    }
    public UserDTO Register(){
        return null;
    }

    /**
     * se encarga de cerrar sesion y establecer el usuario actual a null
     * @return devolvera true si se ha podido cerrar sesion correctamente
     */
    public boolean Logout(){
        return false;
    }
    public UserDTO getCurrentUser(){
        return new UserDTO(new User(3, "RaulNapias", "Raul", "Test", "test", "test", "1234"));
    }
    public static Login getInstance() {
        if (instance == null) instance = new Login(){};
        return instance;
    }
}
