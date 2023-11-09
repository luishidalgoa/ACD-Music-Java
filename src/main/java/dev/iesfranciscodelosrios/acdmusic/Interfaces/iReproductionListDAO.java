package dev.iesfranciscodelosrios.acdmusic.Interfaces;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;

import java.util.Set;

public interface iReproductionListDAO {
    /**
     * creara una lista de reproduccion nueva en base a un objetog
     * @param reproductionList lista de reproduccion que se quiere agregar a la bbdd
     * @return devolvera un DTO de la lista de reproduccion en caso de que se haya encontrado en la base de datos si no devolvera null
     * <h3>NOTA: Devolvera las canciones con Lazy Loading</h3>
     */
    public ReproductionList add(ReproductionList reproductionList);

    /**
     * eliminara una lista de reproduccion y devolvera true o false en base a si se encuetra o no la lista de reproduccion
     * (true si no existe) IMPORTANTE garantizar que el que la elimina es su Owner (implica usar UserDAO) .
     * Este metodo debera garantizar que el usuario Logueado sea el mismo que el dueño de la Lista
     *
     * @param id de la lista que se desea eliminar
     */
    public boolean removeReproductionList(int id);

    /**
     * buscara una lista de reproduccion a partir de su id
     * @param id id de la lista que se quiere buscar
     * @return lista de reproduccion buscada si no se encuentra devolvera null
     */
    public ReproductionList searchReproductionListById(int id);

    /**
     * Subcribira un usuario a una lista de reproduccion
     * @param idUser ID del usuario que va a seguir la lista
     * @param idList ID de la lista que van a seguir
     * @return Devolvera un boolean en base si se encuentra la relacion con el metodo getSubcribeToListByUser()
     */
    public boolean Subcribe(int idUser,int idList);

    /**
     * eliminara la relacion entre usuario y la lista de reproduccion indicada
     * @param idUser id del usuario que se desuscribira de la lista
     * @param reproductionList Lista de reproduccion de la que se desuscribira el usuario
     * @return Devolvera true o false en base a si cuando se comprueba
     */
    public boolean unSubcribe(int idUser,ReproductionList reproductionList);

    /**
     * eliminara una cancion de una lista de reproduccion
     * @param idSong  de una cancion y eliminara la cancion  de la tabla Lista_Reproduccion_cancion
     * @param idReproductionList
     * @param user
     * @return devuelve true o false dependiendo de si se ha encontrado la cancion dentro de la lista
     */
    public boolean removeSong(int idSong,int idReproductionList, UserDTO user);

    /**
     * este metodo  devolvera una lista Set con todas las listas a las que un usuario esta subcrito.
     * @param idUser del usuario que se quiere extraer las listas a las que esta subcrito
     * @return lista de reproduccion a las que esta subcrito el usuario
     */

    public Set<ReproductionList> getUserSubcriptions(int idUser);


    /**
     * este metodo su funcion es verificar que existe la relacion entre un usuario y una lista buscada y
     * @param idUser id del usuario que se quiere comprobar si esta subcrito a la lista
     * @param idList id de la lista que se quiere comprobar si el usuario esta subcrito
     * @return devolvera true si existe la relacion entre el usuario y la lista
     */
    public boolean getSubcribeToListByUser(int idUser,int idList);

    /**
     * Extraera una lista de reproduccion a partir de su id
     * @param idList id de la lista que se quiere extraer los comentarios
     * @return lista de todos los comentarios de la lista
     */
    public Set<Comment> getAllComments(int idList);

    /**
     * Agregara una canción a una lista de reproduccion si el usuario que la agrega es el dueño de la lista.
     * En este metodo se verificara automaticamente que el usuario que tiene la sesion iniciada sea el dueño de la lista
     * @param idSong id de la cancion que se quiere agregar
     * @param idReproductionList id de la lista a la que se quiere agregar la cancion
     * @return true si se ha agregado correctamente
     */
    public boolean addSong(int idSong,int idReproductionList);

    /**
     * buscara una lista de canciones dentro de una lista de reproduccion a traves de una consulta Entre 3 tablas (Song y reproductionSongList)
     * @param idReproductionList id de la lista en la que se quiere buscar las canciones que tiene
     * @return cancion buscada si no se encuentra devolvera null
     */
    public Set<Song> searchSongsById(int idReproductionList);
    /**
     * buscara si existe la relacion entre una cancion y una lista de reproduccion
     * @param idList id de la lista de reproduccion
     * @param idSong id de la cancion
     * @return true si existe la relacion
     */
    public boolean existSongOnList(int idList,int idSong);

    /**
     * filtrara una lista de reproduccion a partir de su nombre de maximo 4 listras
     * @param filter nombre de la lista que se quiere buscar
     * @return listas de reproducciones filtrada
     */
    public Set<ReproductionList> searchByName(String filter);
}
