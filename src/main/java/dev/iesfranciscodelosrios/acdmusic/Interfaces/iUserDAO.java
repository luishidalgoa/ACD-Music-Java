package dev.iesfranciscodelosrios.acdmusic.Interfaces;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import java.util.Set;

public interface iUserDAO{
    /**
     * Agregara en la bbdd un usuario nuevo
     * @param user sera el usuario que se agregara a la bbdd
     * @return devolvera un boolean segun la operacion haya sido satisfactoria (se puede hacer comprobando que searchUser no devuelve Null)
     */
    public UserDTO addUser(User user);

    /**
     *  elimina un usuario a partir de su id
     * @param idUser id del usuario a eliminar
     * @return true si se ha eliminado correctamente (se puede hacer comprobando que searchUser no devuelve Null)
     */
    public boolean delete(int idUser);

    /**
     * se buscara un usuario en base a su email.
     * @param email email del usuario a buscar
     * @return devolvera un objeto UserDTO, si la base de datos no se encuentra se devolvera null
     */
    public UserDTO searchByEmail(String email);

    /**
     * se buscara un usuario en base a su nickname.
     * @param nickname nickname del usuario a buscar
     * @return devolvera un objeto UserDTO, si la base de datos no se encuentra se devolvera null
     */
    public UserDTO searchByNickname(String nickname);

    /**
     * este metodo recive un string el cual contiene una palabra de lo que el usuario piensa como se podria
     * llamar el usuario que busca. Sin embargo le devolveremos una consulta con un maximo de 3 usuarios con un nombre
     * parecido al que se busca
     * @param filterWord palabra clave del nombre del usuario que se quiere buscar
     * @return lista de usuarios que coinciden con la palabra clave
     */
    public Set<UserDTO> searchByName(String filterWord);
}
