package dev.iesfranciscodelosrios.acdmusic.Interfaces;

import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;

import java.util.List;
import java.util.Set;

public interface iCommentDAO {
    /**
     * a partir del id de una lista y el id del usuario que crea la lista (el id del usuario se extrae desde el DAO con
     * la clase singleton Login donde se almacena el usuario logueado). Se agregara una relacion en la tabla
     * "comment_list_user con el id de la lista y el id del usuario que crea el comentario
     * @param comment objeto comentario con los datos del comentario
     * @return objeto comentario extraido a partir de la busqueda en la base de datos una vez agregado
     * si no se ha podido agregar devolvera null
     */
    public Comment add(Comment comment);

    /**
     * se eliminara un comentario con el id recivido por parametro. Se debe verificar q el usuario q lo elimina
     * sea el usuario que tiene la sesion iniciada en la app
     * @param idList id del comentario a eliminar
     * @return true si se ha eliminado correctamente. (Nota: usara el metodo searchByIdList para comprobar que existe)
     */
    public boolean delete(int idList);

    /**
     * Buscara un comentario a partir del id de la lista de reproduccion recivida por parametro. Devolviendo
     * de este modo un objeto Comment
     * @param idList id de la lista de reproduccion a buscar
     * @return objeto Comment con los datos del comentario
     */
    public List<Comment> searchAllByIdList(int idList);

    /**
     * Buscara un comentario a partir del id del usuario recivido por parametro. Devolviendo de este modo un objeto Comment
     * @param idComment id del comentario a buscar
     * @return objeto Comment con los datos del comentario
     */
    public Comment searchComment(int idComment);
}
