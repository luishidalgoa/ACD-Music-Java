package dev.iesfranciscodelosrios.acdmusic.Services;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iLogin;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

public class Login implements iLogin {


    private static Login instance;

    private Login() {
    }

    UserDTO currentUser;

    /**
     * Comprueba si el usuario recivido no es nulo y si existe en la bbdd , posteriormente
     * encriptara la contraseña del usuario recibido y la comparara con la contraseña encriptada de la bbdd
     * si coinciden devolvera un objeto usuario con los datos del usuario logueado y lo guardara en el atributo currentUser
     * @param user objeto usuario con los datos del usuario y la contraseña sin encriptar
     * @return objeto usuario con los datos del usuario logueado
     */
    @Override
    public UserDTO Auth(User user) {
        return null;
    }

    /**
     * Recive un string de la contraseña sin encriptar y la encriptara para compararla
     * @param password contraseña sin encriptar
     * @return contraseña encriptada
     */
    @Override
    public String encryptPassword(String password) {
        return null;
    }

    /**
     * Comprueba si el usuario recivido no es nulo y si existe en la bbdd , posteriormente
     * encriptara la contraseña del usuario recibido y agregara el usuario a la bbdd.
     * la contraseña debera tener minimo 2 caracteres
     * @param user objeto usuario con los datos del usuario y la contraseña sin encriptar
     * @return
     */
    @Override
    public UserDTO Register(User user) {
        return null;
    }

    /**
     * Se encargara de setear el atributo currentUser a null
     * @return true si se ha podido cerrar sesion correctamente
     */
    @Override
    public boolean Logout() {
        return false;
    }

    /**
     * Devolvera el usuario logueado actualmente
     * @return usuario logueado actualmente
     */
    public UserDTO getCurrentUser() {
        return currentUser;
    }

    /**
     * Seteara el usuario logueado actualmente
     * @param currentUser usuario logueado actualmente
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
