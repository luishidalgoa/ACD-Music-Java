package dev.iesfranciscodelosrios.acdmusic.Services;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

public class Login {
    /**
     * este metodo se encargara de desencriptar la contraseña de encriptar la contraseña del usuario de la bbdd y
     * compararlas . Si son iguales garantizara el inicio de sesion devolviendo un UserDTO sin exponer la contraseña del usuario
     * @param user objeto usuario con los datos del usuario y la contraseña sin encriptar
     * @return objeto UserDTO con los datos del usuario sin la contraseña si el inicio de sesion no ha sido correcto devolvera null
     */
    public UserDTO Auth(User user) {
        return null;
    }

    /**
     * esta funcion devolvera la contraseña encriptada. Podria ser usado por el mismo metodo Auth().
     * @param password contraseña sin encriptar
     * @return contraseña encriptada
     */
    public String encryptPassword(String password) {
        return null;
    }

    /**
     * este metodo recibira un usuario con una contaseña sin encriptar desde un controlador o desde el propio metodo
     * Auth y se encargara de insertarlo en la bbdd con su contraseña encriptada
     * @param user objeto usuario con los datos del usuario y la contraseña sin encriptar
     * @return objeto UserDTO con los datos del usuario sin la contraseña si el registro no ha sido correcto devolvera null
     */
    public UserDTO Register(User user) {
        return null;
    }
}
