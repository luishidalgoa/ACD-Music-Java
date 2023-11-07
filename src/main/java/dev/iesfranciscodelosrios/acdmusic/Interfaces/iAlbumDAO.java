package dev.iesfranciscodelosrios.acdmusic.Interfaces;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;

import java.util.Set;

public interface iAlbumDAO {
    /**
     * agregara un album a la bbdd
     *
     * @param album album a agregar
     * @return comprobar si se ha agregado correctamente y devolver true o false
     */
    public boolean addAlbum(Album album);

    /**
     * Realizara una consulta a la bbdd con la palabra que reciba. devolviendo una lista de como
     * <b>maximo 3</b> albumes con un nombre parecido al que se le pasa por parametro
     * @param filterWord palabra a buscar
     * @return lista de albumes
     */
    public Set<Album> searchAlbumByName(String filterWord);

    /**
     * devolvera una lista de albumes a partir del nombre de un artista (el cual no tiene porque conocerlo exactamente el usuario) .
     * @Param artist artista del cual se quiere buscar los albumes
     * @return lista de albumes de un artista
     */
    public Set<Album> searchAllAlbumsByArtist(ArtistDTO artist);

    /**
     * consultara a la bbdd los albumes subidos mas recientemente. Devolviendo asi una lista
     * de albumes ordenados por fecha de subida . Como maximo devolvera 3 albumes
     * @return lista de albumes ordenados por fecha de subida
     */
    public Set<Album> searchMoreRecent();

    /**
     * devolvera un album actualizado (solo se actualizara el nombre del album como mucho
     * @param id id del album a actualizar
     * @return album actualizado
     */
    public Album updateAlbum(int id, String newName);

}
