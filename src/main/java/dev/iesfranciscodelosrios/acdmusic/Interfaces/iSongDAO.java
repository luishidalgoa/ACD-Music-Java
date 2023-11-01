package dev.iesfranciscodelosrios.acdmusic.Interfaces;

import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;

import java.util.Set;

public interface iSongDAO {
    /**
     * agregara una cancion en la tabla Song , a partir de una cancion
     * @param song cancion que se agregara a la tabla
     * @return la cancion que se ha agregado pero extraida de la base de datos. si no se ha podido agregar se devolvera null
     */
    public Song addSong(Song song);

    /**
     * eliminara una cancion a partir de su id . este metodo debe garantizar que el usuario Logueado sea el usuario dueño de la cancion
     *
     * @param idSong id de la cancion a eliminar . Se debe comprobar que el usuario logueado sea el dueño de la cancion
     * @return true si se ha eliminado correctamente a partir de la busqueda en la base de datos
     */
    public boolean removeSong(int idSong);

    /**
     * recibira un enum del genero buscado. y devolvera una lista de como maximo 3 canciones del genero buscado
     * @param genre genero de la cancion a buscar
     * @return lista de canciones del genero buscado
     *
     * <h4>NOTA DESARROLLADOR:</h4><p>El desarrollo de este metodo es secundario</p>
     */
    public Set<Song> searchByGenre(Genre genre);

    /**
     * se devolvera la cancion con el id buscado
     * @param idSong  id de la cancion a buscar
     * @return cancion con el id buscado si no se encuentra devolvera null
     */
    public Song searchById(int idSong);

    /**
     * a partir del id de un album se devolveran todas las canciones de ese album
     * @param idAlbum id del album del que se quieren buscar las canciones
     * @return lista de canciones del album
     */
    public Set<Song> searchByAlbumId(int idAlbum);

    /**
     * se devolvera una lista de como maximo los 4 canciones mas reproducidas
     * @return lista de canciones mas reproducidas
     */
    public Set<Song> searchTopSongs();

    /**
     * devolvera una lista de como maximo los 4 canciones con la fecha mas reciente de subida
     * @return lista de canciones mas recientes
     */
    public Set<Song> searchRecientSongs();

    /**
     * pero el objetivo de este metodo es devolver
     * una lista de Canciones en base a un nombre mas o menos parecido al de la  cancion
     * @param filterWord palabra clave del nombre de la cancion a buscar
     * @return lista de canciones que coinciden con el nombre buscado
     *
     * <h4>NOTA AL DESARROLLADOR:</h4> <p>la prioridad de desarrollo de este metodo es secundario.</p>
     */
    public Set<Song> searchByNombre(String filterWord);
}
