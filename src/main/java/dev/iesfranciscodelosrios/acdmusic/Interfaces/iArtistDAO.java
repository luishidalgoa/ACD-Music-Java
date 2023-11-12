package dev.iesfranciscodelosrios.acdmusic.Interfaces;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Artist;

import java.util.Set;

public interface iArtistDAO {
    /**
     * a partir del id de un usuario creara un artista y devolvera un boolean si se encuentra el artista al consultarlo
     * en la base de datos
     * @param artist objeto artista con los datos propios del artista y el puntero al usuario
     * @return devolvera un DTO del artista en caso de que se haya encontrado en la base de datos si no devolvera null
     */
    public  ArtistDTO addArtist(Artist artist);

    /**
     * a partir del id del usuario lo eliminara de la base de datos como artista. Sin embargo podra seguir existiendo
     * como Usuario. (Nota para ser eliminado por completo deberia ser borrado de la tabla User)
     * @param artist artista a eliminar
     * @return true si se ha eliminado correctamente
     */
    public  boolean removeArtist(Artist artist);

    /**
     *  a partir del id de un usuario se buscara el artista que le corresponde
     * @param idUser id del usuario
     * @return objeto artista con los datos del artista si no se encuentra devolvera null
     */
    public ArtistDTO searchArtistByIdArtist(int idUser);

    /**
     * a partir del un nombre no bien conocido se debe filtrar para extraer los artistas que aproximadamente tengan el
     * nombre buscado. Se devolvera una lista con un maximo de 3 artistas
     * @param filterWord palabra clave del nombre del artista que se quiere buscar
     * @return lista de artistas que coinciden con la palabra clave
     */
    public Set<ArtistDTO> searchArtistByName(String filterWord);
    /**
     * Metodo que busca coincidencias con un máximo de tres usuarios y los devuelve.
     * Por defecto y en caso de que la conexion con la base de datos falle o la constula
     * no sea existosa devolvera null
     * @param idUser palabra clave del nombre del artista que se quiere buscar
     * @return Set de tres usuarios
     */
    public ArtistDTO searchArtistByIdUser(int idUser);
    /**
     * Metodo que busca un artista por el id del album
     * @param idAlbum id del album
     * @return devuelve un objeto ArtistDTO
     */
    public ArtistDTO searchArtistByIdAlbum(int idAlbum);
}
